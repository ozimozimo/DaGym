$(function () {
    ptEnd();
    let str = $('.user_pn').text();
    let phone = str.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
    $('.user_pn').text(phone);
    changAddr();
    $('.search').on("click",function (){
        let url = "https://map.kakao.com/link/search/" + $('.addr').text();
        window.open(url);

    })
});

function changAddr(){
    let addr = $('.addr').text();
    let len = $('.addr').text().length;
    let chageAddr = addr.substring(5,len);
    $('.addr').text(chageAddr);

}

function myTrainerDetailView() {
    var popupX = (window.screen.width / 2) - (800 / 2);
    var popupY = (window.screen.height / 2) - (700 / 2);

    var option = 'status=no, height=600, width=800, left=' + popupX + ', top=' + popupY + ', screenX=' + popupX + ', screenY= ' + popupY;
    // var pageValue = $('#pageValue').val() || "";
    // var typeValue = $('#typeValue').val() || "";
    // var keywordValue = $('#keywordValue').val() || "";
// &page=${pageValue}&type=${typeValue}&keyword=${keywordValue}
    var trainerId = $('#trainer_id').val();
    var url = `/ptUser/detail?id=${trainerId}`;

    window.open(url, 'myTrainerDetailView', option);

}

function ptEnd() {
    let pt_id = $('#pt_id').val();
    let pt_end = $('#pt_end').val();
    let trainer_name = $('#trainer_name').val();
    let trainer_id = $('#trainer_id').val();
    let member_id = $('#member_id').val();

    console.log("pt entity PK = " + pt_id);
    console.log("pt entity 종료 = " + pt_end);
    console.log("pt member_id = " + member_id);
    console.log("pt trainer_name = " + trainer_name);
    console.log("pt trainer_id = " + trainer_id);

    let count = parseInt(pt_end);

    if (count == 1) {
        alert(trainer_name + "님과의 PT가 종료 되었습니다");

        // let url = "http://localhost:8090/ptUser/review/" + member_id + "/" + trainer_id;
        popup();
    } else if (count == 2) {
        let trainer_id = $('#trainer_id').val();
        let member_id = $('#member_id').val();
        console.log(member_id);
        console.log(trainer_id);

        $.ajax({
            type: 'post',
            url: '/ptUser/end/' + member_id + '/' + trainer_id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                if (data == true) {
                    alert('PT가 종료되었습니다');
                    alert('회원님은 재접속 부탁드립니다');
                    location.href = "/member/logout";
                }
            },
            error: function () {
                alert("에러")
            }
        });
    }

}

function popup() {
    let popWidth = 600;
    let popHeight = 700;

    let winWidth = document.body.clientWidth; // 현재창의 너비
    let winHeight = document.body.clientHeight; // 현재창의 높이

    let winX = window.screenX || window.screenLeft || 0; // 현재창의 x좌표
    let winY = window.screenY || window.screenTop || 0; // 현재창의 y좌표

    let left = winX + (winWidth - winX) / 2;
    let top = winY + (winHeight - winY) / 2;
    let trainer_id = $('#trainer_id').val();
    let member_id = $('#member_id').val();
    let url = "http://localhost:8090/pt/review/" + member_id + "/" + trainer_id;

    let option = "status=no, left=" + left + ",top=" + top + ",width=" + popWidth + ',height=' + popHeight

    window.open(url, 'popup', option);

}

var messageBox = document.querySelector('.js-message');
var btn = document.querySelector('.js-message-btn');
var card = document.querySelector('.js-profile-card');
var closeBtn = document.querySelectorAll('.js-message-close');

btn.addEventListener('click', function (e) {
    e.preventDefault();
    card.classList.add('active');
});

closeBtn.forEach(function (element, index) {
    console.log(element);
    element.addEventListener('click', function (e) {
        e.preventDefault();
        card.classList.remove('active');
    });
});

