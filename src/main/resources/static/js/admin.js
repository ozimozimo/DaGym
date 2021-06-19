

function Delete() {

    var id = $(this).closest("tr").find("#member_id").text();
    console.log(id);

    $.ajax({
        type: 'delete',
        url: '/admin/delete/' + id,
        // dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function () {
        alert('답글이 삭제되었습니다.')
        location.reload();
        // window.location.href = "/";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

$(function () {
    $(".Delete").on("click", Delete);
});