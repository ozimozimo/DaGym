function inBody() {

    var id = $('#inBody_member_id').val();
    var data = {
        inBody_user_id: $('#inBody_user_id').val(),
        inBody_weight: $('#inBody_weight').val(),
        inBody_rmr: $('#inBody_rmr').val(),
        inBody_bfp: $('#inBody_bfp').val(),
        inBody_smm: $('#inBody_smm').val(),
        inBody_date: $('#inBody_date').val()
    }
    console.log(data.inBody_user_id);
    console.log(data.inBody_weight);
    console.log(data.inBody_rmr);
    console.log(data.inBody_bfp);
    console.log(data.inBody_smm);
    console.log(data.inBody_date);
    $.ajax({
        url: '/inBody/register/' + id,
        type: 'POST',
        dataType: "json",
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            alert("인바디 등록에 성공하셨습니다");
            console.log("인바디아이디는=" + data.inBody_user_id);
            console.log("무게는=" + data.inBody_weight);
            console.log("기초대사량=" + data.inBody_rmr);
            console.log("체지방률=" + data.inBody_bfp);
            console.log("골격근량=" + data.inBody_smm);
            console.log("날짜는=" + data.inBody_date);
            location.href = "/inBody/inBodyResult?inBodyId="+data.inBody_user_id;

        },
        error: function () {
            alert("인바디 등록에 실패하셨씁니다");

        }
    })
}

$(function () {
    $('#inBodyBtn').on("click", inBody);
});