function memberCheck() {
    var member_id = $('input[name=member_id]').val();
    console.log("member 값은" + member_id);
    $.ajax({
        type: 'get',
        url: '/ptUser/apply/memcheck?',
        data: 'member_id=' + member_id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        if (data.accept_condition == 0) {
            alert('PT 수락 대기 중입니다.');
            applyInfo(data);
        } else if (data.accept_condition == 1) {
            alert(data.trainer_id.member.user_name + ' 트레이너와 매칭 되었습니다');
            // 로그아웃 보내고 / 나의 트레이너 확인
            // 매칭중인 상태에선 회원은 다른 트레이너와 PT를 진행할수 없음
        } else if (data.accept_condition == 2) {
            alert('PT 신청이 거절 되었습니다');
            applyInfo(data);
            trCancelPay();
        } else {

        }
    }).fail(function () {

    })
}

function check() {
    var member_id = $('input[name=member_id]').val();
    var trainer_id = $('#trainer_id').val();

    console.log("member 값은" + member_id);
    console.log("trainer 값은" + trainer_id);

    $.ajax({
        type: 'get',
        url: '/ptUser/apply/check?',
        data: 'member_id=' + member_id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        if (data.accept_condition == 0) {
            alert('PT 수락 대기 중입니다.');
            window.location.href = '/ptUser/view';
        } else if (data.accept_condition == 1) {
            alert('PT가 진행 중입니다.');
            window.location.href = '/ptUser/view';
        } else if (data.accept_condition) {
            alert('PT신청 페이지로 이동하겠습니다');
            window.location.href = '/ptUser/apply?member_id=' + member_id + "&trainer_id=" + trainer_id;
        }
    }).fail(function () {
        alert('PT신청 페이지로 이동하겠습니다');
        window.location.href = '/ptUser/apply?member_id=' + member_id + "&trainer_id=" + trainer_id;
    })
}

function applyInfo(data) {

    var trName = data.trainer_id.member.user_name;
    var trImg = data.trainer_id.imgName;
    var trPn = data.trainer_id.member.user_pn;
    var trKa = data.trainer_id.trainer_kakao;
    var trInsta = data.trainer_id.trainer_instagram;
    var trGymNomal = data.trainer_id.trainer_address_normal;
    var trGymDetail = data.trainer_id.trainer_address_detail;
    var trPrice = data.trainer_id.trainer_pt_total;
    var trId = data.trainer_id.id;
    var memId = data.member_id.id;


    let content = "<tr>"
    content += `<input type="hidden" value="${memId}" id="memId" name="memId"> `
    content += `<input type="hidden" value="${trId}" id="trId" name="trId"> `
    content += "<td>" + trName + "</td>"
    content += "<td>" + trPn + "</td>"
    content += "<td>" + trKa + "</td>"
    content += "<td>" + trInsta + "</td>"
    content += "<td>" + trGymNomal + "</td>"
    content += "<td>" + trGymDetail + "</td>"
    content += "<td'>" + trPrice + "</td>"
    content += "<td><button type='button' onclick='cancelPay()' class='btn-primary Deny'>신청취소</button></td></tr>"

    console.log(content);
    $('.box2').show();
    $('.applyInfoList').append(content);
}

function cancelPay() {
    let member_id = $('#memId').val();
    let trainer_id = $('#trId').val();
    console.log("회원 아이디는" + member_id);
    console.log("트레이너 아이디는 " + trainer_id);

    $.ajax({
        type: 'post',
        url: '/ptUser/payRefund/' + member_id + '/' + trainer_id,
        contentType: 'application/json; charset=utf-8'
    }).done(function (data) {
        console.log(data);
        $.ajax({
            type: 'post',
            url: '/ptUser/payments/cancel',
            contentType: 'application/json',
            data: JSON.stringify({
                merchant_uid: data.merchant_uid, // 주문번호
                cancel_request_amount: data.pt_amount, // 환불금액
                member_id,
                trainer_id
                // refund_holder: "김도현", // [가상계좌 환불시 필수입력] 환불 수령계좌 예금주
                // "refund_bank": "88", // [가상계좌 환불시 필수입력] 환불 수령계좌 은행코드(ex. KG이니시스의 경우 신한은행은 88번)
                // "refund_account": "56211105948400" // [가상계좌 환불시 필수입력] 환불 수령계좌 번호
            }),
            dataType: "json"
        }).done(function (data) {
            console.log(data);
            if (data == true) {
                alert("결제 환불 및 PT 신청이 취소되었습니다");
                location.href = "/ptUser/view";
            } else {
                alert("결제 환불 및 PT 신청이 실패하였습니다");
                location.href = "/ptUser/view";
            }
        }).fail(function () {
            alert("결제 환불 및 PT 신청이 실패하였습니다");
            location.href = "/ptUser/view";
        });
    }).fail(function () {
        alert("실패");
    });
}

// 수락한거 조회하기
function acceptList() {
    var id = $('#member_id').val();
    console.log(id + "login 한 id는");

    var data = {
        id: id // 앞에 trainer_id -> id
    }
    $.ajax({
        type: 'get',
        url: '/ptUser/manage',
        data: data,
        contentType: 'application/json; charset=utf-8'
    }).done(function (data) {
        // console.log(data);
    }).fail(function (error) {
        console.log(error);
    });
}

// 수락, 거절 눌러서 accept_condition 바꾸기
function updateAccept() {
    // PTUserApplyConDto에 넘겨줄 apply_if값
    let apply_if = 0;
    // 내가 누른 버튼의 id값
    // let id = a.parentNode.parentNode.firstChild.innerText;

    // let id = $('#member_id').val();

    // let pt_user_id = $('#pt_user_id').val();
    let pt_user_id = $(this).parent().siblings('.pt_user_id2').val();

    let member_id = $(this).parent().siblings('.member_id2').val();

    console.log("pt_user_id=" + pt_user_id)

    let trainer_id = $('#member_id').val();


    // 내가 누른 버튼의 텍스트값
    let con = $(this).text();

    console.log("수락버튼" + con);
    // 수락이면 apply_if에 1 저장하고 아니면 2 저장
    if (con == "수락") {
        apply_if = 1;
    } else {
        apply_if = 2;
    }
    let data = {
        // PTUserApplyConDto에 넘겨줄 id랑 apply_if값
        apply_if: apply_if,
        trainer_id: trainer_id,
        member_id: member_id
    }
    $.ajax({
        type: 'post',
        url: '/ptUser/apply/update/' + pt_user_id,
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        if (con == "수락") {
            alert("PT신청이 수락되었습니다");
            location.reload();
        } else if (con == "거절") {
            alert("PT신청이 거절되었습니다");
            location.reload();
            // trCancelPay();
        }
    }).fail(function (error) {
        console.log(error);
    })
}


// 신청온거 확인하기
function showList() {

    var member_id = $('#member_id').val();
    console.log(member_id);
    var data = {
        member_id: member_id,
    }
    $.ajax({
        type: 'get',
        url: '/ptUser/apply/findMember',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        var count = data;
        if (count.length != 0) {
            alert(count.length + "건의 PT 신청이 있습니다");
            mkApply(data);
        }
    }).fail(function (error) {
        console.log(error);
    })
}

// 신청내용 확인하는 곳
function mkApply(data) {
    $('.applyListDetail').empty();
    for (let i = 0; i < data.length; i++) {
        var a = data[i].member_height;
        var b = data[i].member_weight;
        var c = data[i].pt_purpose;
        var d = data[i].pt_count;
        var e = data[i].pt_positiveDate;
        var f = data[i].pt_wantTime;
        var g = data[i].member.user_name;
        var h = data[i].member.user_pn;
        var j = data[i].member.user_rrn;
        var k = data[i].member.user_gender;
        var l = data[i].id;
        var m = data[i].member.user_id;
        var member_id = data[i].member.id;


        // 삭제할려면 필요한 정보가 회원 pk와 트레이너 pk 트레이너 페이지에선 뒤에꺼는 적용된다

        // let tr_Id = data[i].trainer.id;
        // let mem_Id = data[i].member.id;

        // console.log("트레이너 ID는" + tr_Id);
        // console.log("멤버 ID는 " + mem_Id);
        // hidden 값 넣기
        var p = data[i].id;
        console.log("ptUser pk는" + p);

        console.log(j);
        j = j + k;
        console.log(j);
        k = changeGender(k);
        j = getAge(j);

        let content = "<tr>"
        // content += `<input type="hidden" value="${mem_Id}" id="mem_Id" name="mem_Id"> `
        // content += `<input type="hidden" value="${tr_Id}" id="tr_Id" name="tr_Id"> `
        content += "<td class='ptUserId'>" + m + "</td>"
        content += "<td class='ptUserName'>" + g + "</td>"
        content += "<td class='ptUserPn'>" + h + "</td>"
        content += "<td class='ptUserRrn'>" + j + "</td>"
        content += "<td class='ptUserGender'>" + k + "</td>"
        content += "<td class='ptUserHeight'>" + a + "</td>"
        content += "<td class='ptUserWeight'>" + b + "</td>"
        content += `<input type="hidden" value="${p}" class= "pt_user_id2" id="pt_user_id" name="pt_user_id"> `
        content += `<input type="hidden" value="${member_id}" class= "member_id2" id="member_id2" name="member_id2"> `
        content += `<td><a href='/ptUser/view/detail/${l}' class="detailView">상세보기</a></td>`
        content += "<td><button type='button' class='btn btn-outline-primary btn-sm Accept' style='margin-right:20px'>수락</button>"
        content += "<button type='button' class='btn btn-outline-danger btn-sm Deny'>거절</button></td></tr>"
        $('.applyListDetail').append(content);

        $('.Accept').on("click", updateAccept);
        $('.Deny').on("click", updateAccept);

        $('.ptUserPn').each(function () {
            let str = $(this).text();
            let phone = str.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
            $(this).text(phone);
        })

    }
}

function changeGender(string){
    let gender = "";
    if(string == 1 || string == 3) gender = "남";
    else gender = "여";
    return gender;
}

function getAge(ssnNo) {

    var curDateObj = new Date(); // 날짜 Object 생성
    var tmpSSN = ssnNo; // 주민번호
    var curDate = ''; // 현재일자
    var tmpAge = 0; // 임시나이
    var y = curDateObj.getFullYear(); // 현재년도
    var m = curDateObj.getMonth() + 1; // 현재월
    if(m < 10) m = '0' + m; // 현재 월이 10보다 작을경우 '0' 문자 합한다
    var d = curDateObj.getDate(); // 현재일
    if(d < 10) d = '0' + d; // 현재 일이 10보다 작을경우 '0' 문자 합한다
    curDate = y + m + d;

    var genType = tmpSSN.substring(6, 7); // 주민번호 성별구분 문자 추출

    if(genType <= 2) {
        tmpAge = y - (1900 + parseInt(tmpSSN.substring(0, 2))); // 1, 2 일경우
    } else {
        tmpAge = y - (2000 + parseInt(tmpSSN.substring(0, 2))); // 그 외의 경우
    }

    var tmpBirthday = tmpSSN.substring(2, 6); // 주민번호 4자리 생일문자 추출

    if(curDate < (y + tmpBirthday)) {
        tmpAge += 1;
    }

    return tmpAge;
}


function trCancelPay() {
    let member_id = $('#memId').val();
    let trainer_id = $('#trId').val();
    console.log("회원 아이디는" + member_id);
    console.log("트레이너 아이디는 " + trainer_id);

    $.ajax({
        type: 'post',
        url: '/ptUser/payRefund/' + member_id + '/' + trainer_id,
        contentType: 'application/json; charset=utf-8'
    }).done(function (data) {
        console.log(data);
        $.ajax({
            type: 'post',
            url: '/ptUser/payments/cancel',
            contentType: 'application/json',
            data: JSON.stringify({
                merchant_uid: data.merchant_uid, // 주문번호
                cancel_request_amount: data.pt_amount, // 환불금액
                member_id,
                trainer_id
                // refund_holder: "김도현", // [가상계좌 환불시 필수입력] 환불 수령계좌 예금주
                // "refund_bank": "88", // [가상계좌 환불시 필수입력] 환불 수령계좌 은행코드(ex. KG이니시스의 경우 신한은행은 88번)
                // "refund_account": "56211105948400" // [가상계좌 환불시 필수입력] 환불 수령계좌 번호
            }),
            dataType: "json"
        }).done(function (data) {
            console.log(data);
            if (data == true) {
                alert("결제 환불 및 PT 신청이 취소되었습니다");
                location.reload();
            } else {
                alert("결제 환불 및 PT 신청이 실패하였습니다");
                location.reload();
            }
        }).fail(function () {
            alert("결제 환불 및 PT 신청이 실패하였습니다");
            location.reload();
        });
    }).fail(function () {
        alert("실패");
    });
}


function a() {
    $('#selectEmail').change(function () {
        $(".category option:selected").each(function () {
            if ($(this).val() == '1') { //직접입력일 경우
                $('.category').removeId
                $('#trainer_category').append("<input type='text' id='trainer_category'>");

                $("#str_email02").val(''); //값 초기화
                $("#str_email02").attr("disabled", false); //활성화
            } else { //직접입력이 아닐경우
                $("#str_email02").val($(this).text()); //선택값 입력
                $("#str_email02").attr("disabled", true); //비활성화
            }
        });
    });

}

$(function () {
    let role = $('#role').val();
    // let state = $('#')
    console.log(role);

    if (role == 'trainer') {
        acceptList();
        showList();
    } else {
        memberCheck();
    }

    $('.pt_basic_info').each(function (i, el){
        let total_string = $(this).closest('.card').find('.pt_total');

        let total = total_string.text();
        total = total.replace(/(\s*)/g,"");
        // total = total.replace('/','');

        let totalArr =  total.split('/');
        let sale_price = totalArr[1];
        let pt_discount = $(this).closest('.card').find('.pt_discount').text();
        let pt_addCount = $(this).closest('.card').find('.pt_addCount').text();
        console.log(sale_price);
        console.log(pt_discount);

        let origin_price = sale_price / ((100-parseInt(pt_discount))/100);
        let text = "10회 / " + origin_price;
        let pt_basic_info =  $(this).closest('.card').find('.pt_basic_info');
        pt_basic_info.text(text);
    })




})


