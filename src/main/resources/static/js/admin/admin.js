function Delete() {
    var id = $(this).closest("tr").find("#member_id").text();
    console.log(id);

    $.ajax({
        type: 'delete',
        url: '/admin/delete/' + id,
        // dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function () {
        alert('탈퇴 완료')
        location.reload();
        // window.location.href = "/";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}



function Save() {
    if ($('#title').val().length == 0) {
        alert('제목을 입력하세요');
        return false;
    } else if ($('#board_content').val().length == 0) {
        alert('내용을 입력하세요');
        return false;
    }

    var data = {
        title: $('#title').val(),
        user_id: $('#user_id').val(),
        content: $('#board_content').val()
    };
    console.log("session login id=" + data.id)
    var id = $('#member_id').val();

    $.ajax({
        type: 'post',
        url: '/admin/noticeInsert/' + id,
        // dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function () {
        alert('등록되었습니다');
        window.location.href = '/admin/noticeManagement';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

function BDelete() {
    if (!confirm('삭제하시겠습니까?')) {
        return false;
    }
    var id = $('#hb_num').val();
    console.log("id: " + id);

    $.ajax({
        type: 'DELETE',
        url: '/admin/boardDelete/' + id,
        contentType: 'application/json; charset=utf-8'
    }).done(function () {
        alert('글이 삭제되었습니다.')
        window.location.href = "/admin/boardManagement";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function FAQSave() {
    if ($('#Q').val().length == 0) {
        alert('제목을 입력하세요');
        return false;
    } else if ($('#A').val().length == 0) {
        alert('내용을 입력하세요');
        return false;
    }

    var data = {
        title: $('#Q').val(),
        user_id: $('#user_id').val(),
        content: $('#A').val()
    };
    console.log("session login id=" + data.id)
    var id = $('#member_id').val();

    $.ajax({
        type: 'post',
        url: '/admin/faqInsert/' + id,
        // dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function () {
        alert('등록되었습니다');
        window.location.href = '/admin/faqManagement';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

function FAQDelete() {
    if (!confirm('삭제하시겠습니까?')) {
        return false;
    }

    var id = $('#faq_num').val();
    console.log("id = " + id);
    $.ajax({
        type: 'DELETE',
        url: '/admin/faqDelete/' + id,
        contentType: 'application/json; charset=utf-8'
    }).done(function() {
        alert('FAQ글이 삭제되었습니다. ')
        window.location.href = "/admin/faqManagement";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

function FAQUpdate() {
    if ($('#faq_title').val().length == 0) {
        alert('제목을 입력하세요');
        return false;
    } else if ($('#faq_content').val().length == 0) {
        alert('내용을 입력하세요');
        return false;
    }
    if (!confirm('수정하시겠습니까?')) {
        return false;
    }

    var data = {
        title: $('#faq_title').val(),
        content: $('#faq_content').val()
    };
    var id = $('#faq_num').val();
    var page = $('#page').val();
    $.ajax({
        type: 'PUT',
        url: '/admin/faqUpdate/' + id,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function () {
        alert('FAQ글이 수정되었습니다.');
        window.location.href='/admin/faqManagement?' + 'page=' + page;
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

function Back() {
    history.back();
}

function AnswerSave() {
    if ($('#ooo_answer').val().length == 0) {
        alert('답변 내용을 입력하세요');
        return false;
    }

    var data = {
        answer: $('#ooo_answer').val()
    }
    var id = $('#ooo_num').val();

    $.ajax({
        type: 'PUT',
        url: '/admin/1on1Update/' + id,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function () {
        alert('1:1 답변 완료.');
        window.location.href='/admin/1on1Management';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}
$(function () {
    $(".Delete").on("click", Delete);
    $(".btn-save").on("click", Save);
    $(".board_delete").on('click', BDelete);
    $(".FAQ-save").on('click', FAQSave);
    $("#FAQ-delete").on('click', FAQDelete);
    $("#FAQ-update").on('click', FAQUpdate);
    $("#listBtn").on('click', Back);
    $(".ooo_save").on('click', AnswerSave)
});