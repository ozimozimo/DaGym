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
        data: data,
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            alert("인바디 등록에 성공하셨습니다");
            console.log(data);
        },
        error: function () {
            alert("인바디 등록에 실패하셨습니다");
            JSON.stringify(data)
        }
    })

}

$(function () {
    $('#inBodyBtn').on("click", inBody);
});