$(document).ready(function () {
    $('.checkBtn').on("click", function () {
        let pt_id = $(this).parent().parent().children('.pt_id').val();
        let pt_times = $(this).parent().parent().children('.pt_times').val();
        PTFinish(pt_id, pt_times);
    });
});
function PTFinish(pt_id, pt_times) {

    console.log(pt_id);
    console.log(pt_times);
    let count = parseInt(pt_times);
    if (count > 0) {
        alert("PT 잔여 횟수가 " + count + "회 남았습니다");
    } else {
        $.ajax({
            type: 'post',
            url: '/ptUser/ptEnd/' + pt_id,
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            alert('PT가 종료되었습니다.');
            location.reload();
        }).fail(function () {
            alert('PT 잔여 횟수가 아직 남았습니다');
        });
    }
}