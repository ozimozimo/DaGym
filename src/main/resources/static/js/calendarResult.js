// calendar
function calendar() {

    var id = $('#calendar_user').val().length;

    var calendar_user = $('#calendar_user').val();
    console.log("id 는 =" + calendar_user);
    var data = {
            calendar_date: $('#calendar_date').val(),
            calendar_memo: $('#calendar_memo').val(),
        };
    // if (id == 0) {
    //     confirm("로그인을 이용해주세요");
    //     location.href = "/member/login";
    // } else {
        $.ajax({
            type: 'post',
            url: '/calendar/save/'+calendar_user,
            data: JSON.stringify(data),
            dataType: "json",
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                alert("등록에 성공하셨습니다");
                console.log(data);
            },
            error: function () {
                alert("등록에 실패하셨습니다");
            }
        })

}