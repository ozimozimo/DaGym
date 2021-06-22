function Active() {
    var id = $(this).closest("tr").find("#notice_id").text();
    var activeBool = $(this).prop(`checked`)
    console.log(id);
    console.log(activeBool);
    var act;
    if (activeBool)
        act = 1;
    else if (!activeBool)
        act = 0;

    $.ajax({
        type: 'put',
        url: '/admin/update/active/' + id + "/" + act,
        contentType: 'application/json; charset=utf-8',
    }).done(function () {
        if (activeBool)
            alert("활성화");
        else if (!activeBool)
            alert("비활성화");
        location.reload();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function update() {
    if ($('#title').val().length == 0) {
        alert('제목을 입력하세요');
        return false;
    } else if ($('#board_content').val().length == 0) {
        alert('내용을 입력하세요');
        return false;
    }
    if (!confirm('수정하시겠습니까?')) {
        return false;
    }

    var data = {
        title: $('#title').val(),
        content: $('#board_content').val()
    };
    var id = $('#hb_num').val();
    var page = $('#page').val();
    $.ajax({
        type: 'PUT',
        url: '/admin/noticeUpdate/' + id,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function () {
        alert('글이 수정되었습니다.');
        window.location.href = '/admin/noticeManagement?' + 'page=' + page;
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function Delete() {
    if (!confirm('삭제하시겠습니까?')) {
        return false;
    }

    var id = $('#hb_num').val();
    console.log("id:" + id);

    $.ajax({
        type: 'DELETE',
        url: '/admin/noticeDelete/' + id,
        contentType: 'application/json; charset=utf-8'
    }).done(function () {
        alert('글이 삭제되었습니다.')
        window.location.href = "/admin/noticeManagement";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}



$(function () {
    $(".activeCBox").on("click", Active);
    $('#btn-update').on('click', update);
    $('#btn-delete').on('click', Delete);
});