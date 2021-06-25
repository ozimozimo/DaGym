function Save() {
    if ($('#title').val().length == 0) {
        alert('제목을 입력하세요');
        return false;
    } else if ($('#bb_content').val().length == 0) {
        alert('내용을 입력하세요');
        return false;
    }

    var data = {
        title: $('#title').val(),
        user_id: $('#user_id').val(),
        content: $('#bb_content').val()
    };
    console.log("session login id=" + data.id)
    var id = $('#member_id').val();

    $.ajax({
        type: 'post',
        url: '/boastboard/bbInsert/' + id,
        // dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function () {
        alert('등록되었습니다');
        window.location.href = '/boastboard/bb';
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

function Update() {
    if($('#title').val().length==0){
        alert('제목을 입력하세요');
        return false;
    } else if($('#bb_content').val().length==0){
        alert('내용을 입력하세요');
        return false;
    }
    if(!confirm('수정하시겠습니까?')){
        return false;
    }
    var main2 = {
        init: function () {
            var _this = this;
            _this.Update();
        },
        Update: function () {
            var data = {
                title: $('#title').val(),
                content: $('#bb_content').val()
            };
            var id = $('#bb_num').val();
            var page = $('#page').val();
            $.ajax({
                type: 'PUT',
                url: '/boastboard/bbUpdate/'+id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('글이 수정되었습니다.');
                window.location.href = '/boastboard/bb?'+'page='+page;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
    main2.init()
}

function Delete() {
    if(!confirm('삭제하시겠습니까?')){
        return false;
    }
    var main3 = {
        init: function () {
            var _this = this;
            _this.Delete();
        },
        Delete: function () {
            var id = $('#bb_num').val();
            console.log("id:"+id);

            $.ajax({
                type: 'DELETE',
                url: '/boastboard/bbDelete/'+id ,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert('글이 삭제되었습니다.')
                window.location.href = "/boastboard/bb";
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
    main3.init();
}

$(function (){
    $("#btn-save").on('click',Save);
    $("#btn-update").on('click',Update);
    $("#btn-delete").on('click',Delete);
})
