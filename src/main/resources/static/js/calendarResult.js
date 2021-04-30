// calendar
function calendar() {
    date();
    var id = $('#calendar_user').val().length;
    var calendar_user = $('#calendar_user').val();
    console.log("id 는 =" + calendar_user);
    var data = {
        calendar_date: $('#calendar_date').val(),
        calendar_memo: $('#calendar_memo').val(),
        color: $('#color').val()
    };
    // if (id == 0) {
    //     confirm("로그인을 이용해주세요");
    //     location.href = "/member/login";
    // } else {
    $.ajax({
        type: 'post',
        url: '/calendar/save/' + calendar_user,
        data: JSON.stringify(data),
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            alert("등록에 성공하셨습니다");
            console.log(data);
            opener.location.reload();
            WinClose();
        },
        error: function () {
            alert("등록에 실패하셨습니다");
            opener.location.reload();
            WinClose();
        }
    })

}

function date(){
    let calendar_time = $('#calendar_time').val();
    let date = $('#date').val();

    let calendar_date = date + " " + calendar_time;
    $('#calendar_date').val(calendar_date);
}

$(function () {
    // 캘린더 Input JS
    var link = document.location.href.split("=");
    let date = link[1];
    $("#date").val(date);
    $('#submitBtn').on("click", calendar);

});

function WinClose() {
    window.open('', '_self').close();
}

