$(document).ready(function(){
    Kakao.init('68b1c0ad829dcea6227e9ff4fe7d44a9');
    let userInputId = getCookie("userInputId");//저장된 쿠기값 가져오기
    $("input[name='user_id']").val(userInputId);

    if($("input[name='user_id']").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩
        // 아이디 저장하기 체크되어있을 시,
        $("#save_id").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }

    $("#save_id").change(function(){ // 체크박스에 변화가 발생시
        if($("#save_id").is(":checked")){ // ID 저장하기 체크했을 때,
            let userInputId = $("input[name='user_id']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        }else{ // ID 저장하기 체크 해제 시,
            deleteCookie("userInputId");
        }
    });

    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("input[name='user_id']").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($("#save_id").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            let userInputId = $("input[name='user_id']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        }
    });


});

function setCookie(cookieName, value, exdays){
    let exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    let cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName){
    let expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    let cookieData = document.cookie;
    let start = cookieData.indexOf(cookieName);
    let cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}

function checkLoginStatus() {
    var auth2 = gapi.auth2.getAuthInstance();
    if (gauth.isSignedIn.get()) {
        var token = gauth.currentUser.get().getAuthResponse().id_token; // 토큰값 받음.
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/login/google'); // controller 주소로 연결
        xhr.setRequestHeader('Content-Type', 'application/json'); // 헤더 이름, 헤더 본문 값
        xhr.onload = function () {
            console.log('Signed in as: ' + xhr.responseText);
        };
        xhr.send('token=' + token);
        auth2.signOut().then(function () {
            console.log("logout");
        });
        auth2.disconnect();
        setTimeout(function () {
            location.href = '/member/mypage/social'; // 소셜 로그인시 마이페이지(소셜 정보 추가 입력)로 이동
        }, 1500);
    } else {
        console.log('dont login');
    }
}

function googleLogin(googleUser) {
    gauth.signIn().then(function () {
        console.log('gauth.signIn()');
        checkLoginStatus();
    });
}

function onSignIn(googleUser) {
    console.log('sign');
    var auth2 = gapi.auth2.getAuthInstance();
    if (auth2.isSignedIn.get()) {
        alert("auth2 googleUser");
        var id_token = googleUser.getAuthResponse().id_token; // 구글 id토큰값

        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/login/google'); // controller 주소로 연결
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); // 헤더 이름, 헤더 본문 값
        xhr.onload = function () {
            console.log('Signed in as: ' + xhr.responseText);
        };
        xhr.send('idtoken=' + id_token);

    }
}

function init() {
    console.log('init');
    gapi.load('auth2', function () {        //google API auth2관련된 부분을 실행
        console.log('auth2');

        window.gauth = gapi.auth2.init({          // 초기화 하는 부분이라함.
            client_id: '831600705653-q3poakj6j0ehm7ihhe09l4k77n1tnbme.apps.googleusercontent.com'
        })
        gauth.then(function () {          // gapi.auth2.init. then(A,B) A = 성공, B = 실패 시 실행됨.
            console.log('googleAuth success');
            checkLoginStatus();
        }, function () {
            console.log('googleAuth fail');
        });

    });
}

//로그인 시
function kakaoLogin() {
    console.log("kakaologin");

    try {
        console.log("try In");
        return new Promise((resolve => {
            if (!Kakao) {
                reject('Kakao 인스턴스 존재 X');
            }
            Kakao.Auth.login({
                scope: 'profile,account_Email',
                success: (auth) => {
                    console.log('정상적으로 로그인 됐습니다.', auth);

                    Kakao.API.request({
                        url: '/v2/user/me',
                        success: function (res) {
                            var userId = res.id;
                            var userNickName = res.properties.nickname;
                            var userEmail = res.kakao_account.email;
                            // var userRrn = res.kakao_account.birthday;
                            // var userGender = res.kakao_account.gender;
                            console.log(userId);
                            console.log(userEmail);
                            console.log(userNickName);
                            // console.log(userRrn);
                            // console.log(userGender);
                            if(userEmail==null){
                                // Kakao.Auth.authorize({
                                //     redirectUri: encodeURI('http://localhost:8090/member/login'),
                                //     scope: 'account_Email, gender, birthday',
                                //     success : ,
                                // })
                                alert("이메일, 성별, 생일 정보 동의를 해주세요.");
                                return false;
                            }
                            $.ajax({
                                data: {
                                    userId: userId, // data 옵션
                                    userEmail: userEmail,
                                    userNickName: userNickName,
                                    // userRrn: userRrn,
                                    // userGender: userGender
                                },   // 끝에 컴마(,)를 주의해야됨
                                type: "post",
                                url: "/login/kakao", // url 필수
                                success: function (data) { // success option
                                    console.log(data.length);
                                }
                            });
                            setTimeout(function () {
                                // 소셜 로그인시 마이페이지(소셜 정보 추가 입력)로 이동
                                location.href = '/member/mypage/social';
                            }, 1500);
                        },
                        fail: function (error) {
                            console.error(error)
                        }
                    })
                },
                fail: (err) => {
                    console.log("fail In");
                    console.error(err)
                }
            });
        }))
    } catch (err) {
        console.error(err);
    }
}


function login() {
    var frm = document.loginForm;
    if (frm.user_id.value.length == 0) {
        alert("아이디를 입력하세요");
        frm.user_id.focus();
        return false;
    } else if (frm.user_pw.value.length == 0) {
        alert("비밀번호를 입력해주세요");
        frm.user_pw.focus();
        return false;
    }
    $.ajax({
        type: "post",
        url: "/member/loginCheck",
        data: {
            user_id: $("#user_id").val(),
            user_pw: $("#user_pw").val()
        },
        dataType: "json",
        error: function (error) {
            alert("잘못된 정보입니다");
            location.href = "/member/login";
        },
        success: function (data) {
            console.log(data);
            if ($.trim(data) == "1") {
                alert("일치하는 정보가 없습니다");
                return false;
            } else if ($.trim(data) == "2") {
                alert("로그인에 성공하셨습니다");
                location.href = "/";
            }else if ($.trim(data) == "3") {
                alert("관리자 페이지로 이동하겠습니다");
                location.href = "/admin/adminMain";
            } else {
                alert("잘못된 정보입니다");
            }
        },

    });
}