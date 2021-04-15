function save() {
    if($('#title').val().length==0){
        alert('제목을 입력하세요');
        return false;
    } else if($('#content').val().length==0){
        alert('내용을 입력하세요');
        return false;
    }
    var main = {
        init: function () {
            var _this = this;
            _this.save();
        },
        save: function () {
            var data = {
                title: $('#title').val(),
                user_id: $('#user_id').val(),
                content: $('#content').val()
            };
            console.log("session login id="+data.id)
            var id = $('#member_id').val();

            $.ajax({
                type: 'post',
                url: '/board/trainerBoard/save/'+id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('등록되었습니다');
                window.location.href = '/board';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
    main.init();
}

function update() {
    if($('#title').val().length==0){
        alert('제목을 입력하세요');
        return false;
    } else if($('#content').val().length==0){
        alert('내용을 입력하세요');
        return false;
    }
    if(!confirm('수정하시겠습니까?')){
        return false;
    }
    var main2 = {
        init: function () {
            var _this = this;
            _this.update();
        },
        update: function () {
            var data = {
                title: $('#title').val(),
                content: $('#content').val()
            };
            var id = $('#hb_num').val();
            var page = $('#page').val();
            $.ajax({
                type: 'POST',
                url: '/board/trainerBoard/update/'+id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('글이 수정되었습니다.');
                window.location.href = '/board?'+'page='+page;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
    main2.init()
}


function Bdelete() {
    if(!confirm('삭제하시겠습니까?')){
        return false;
    }
    var main3 = {
        init: function () {
            var _this = this;
            _this.delete();
        },
        delete: function () {
            var id = $('#hb_num').val();
            console.log("id:"+id);

            $.ajax({
                type: 'post',
                url: '/board/trainerBoard/delete/'+id ,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert('글이 삭제되었습니다.')
                window.location.href = "/board";
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
    main3.init();
}

function isData(){
    var actionForm = $('#actionForm');

    var page = $('#page');
    var type = $('#type');
    var keyword = $('#keyword');

    actionForm.empty();

    actionForm.append(page);
    actionForm.append(type);
    actionForm.append(keyword);
    actionForm
        .attr("action", "/board")
        .attr("method", "get")
    actionForm.submit();
}

$(function (){
    $('#btn-update').on('click',update);
    $('#btn-delete').on('click',Bdelete);
    $(".listBtn").on('click',isData);
    $("#btn-save").on('click',save);
})

