function CommentSave() {
    var CommentSave = {
        init: function () {
            var _this = this;
            _this.save();
        },
        save: function () {
            var data = {
                comments: $('#comments').val(),
                parentNum: $('#parentNum').val(),
                user_id: $('#user_id').val()
            };
            var parentNum = $('#parentNum').val();
            console.log("유저아이디" + data.user_id)
            console.log("보드번호" + data.parentNum)
            console.log("댓글" + data.comments)

            $.ajax({
                type: 'post',
                url: '/comments/save/' + parentNum,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('댓글이 등록되었습니다');
                location.reload();
            }).fail(function (error) {
                alert("에러남");
                alert(JSON.stringify(error));
            });
        }
    }
    CommentSave.init();
}

function CDelete() {
    var main3 = {
        init: function () {
            var _this = this;
            _this.delete();
        },
        delete: function () {
            var id = $('#commentId').val();
            console.log("id=" + id);
            $.ajax({
                type: 'post',
                url: '/comments/delete/' + id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert('댓글이 삭제되었습니다.')
                location.reload();
                // window.location.href = "/";
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
    main3.init();
}

function ReCommentSave() {
    let form = $(this).parent();

    let re_user_id = form.children("#re_user_id").val();
    let re_parentCoNum = form.children("#re_parentCoNum").val();
    let re_comments = form.children(".comment_write_area").children().val();

    // $('re_comments').click(function () {
    //     if ($('#re_user_id') == null) {
    //         alert("로그인을 해주세요");
    //         location.href = "/member/login";
    //     }
    // });
    console.log(re_user_id);
    console.log(re_parentCoNum);
    console.log(re_comments);
    var main = {

        init: function () {
            var _this = this;
            _this.save();
        },
        save: function () {
            var data = {
                re_user_id: re_user_id,
                re_parentCoNum: re_parentCoNum,
                re_comments: re_comments,
            };
            console.log("유저아이디" + data.re_user_id);
            console.log("댓글부모" + data.re_parentCoNum);
            console.log("리댓" + data.re_comments);

            $.ajax({
                type: 'post',
                url: '/recomments/save/' + re_parentCoNum,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('답글이 등록되었습니다.');
                // window.location.href = '/board/trainerBoard/detail'+'hb_num'=hb_num;
                location.reload();
            }).fail(function (request, status, error) {
                alert(data.re_parentCoNum)
                alert('답글 등록에 실패하였습니다.');
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                alert('댓글 등록이 실패하였습니다.')
                console.log(JSON.stringify(error));
            });
        }
    };
    main.init();
}

function recommend() {

    var id = $('#recommendId').val().length;
    var boardId = $('#boards_id').val();
    var loginId = $('#recommendId').val();
    console.log(loginId);
    console.log(boardId);

    if (id == 0) {
        confirm("로그인을 이용해주세요");
        location.href = "/member/login";
    } else {
        $.ajax({
            type: 'post',
            url: '/board/recommend/' + boardId,
            data: {
                id: $('#recommendId').val(),
                boards_id: $('#boards_id').val(),
            },
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (data === true) {
                    alert("게시글을 추천 하셨습니다");
                } else {
                    alert("한개의 글에 한번만 클릭이 가능합니다");
                }
            },
            error: function () {
                alert("한개의 글에 한번만 클릭이 가능합니다");
            }
        })
    }
}

function RCDelete() {
    var Rcdel = {
        init: function () {
            var _this = this;
            _this.delete();
        },
        delete: function () {
            var id = $('#recommentId').val();
            console.log("id=" + id);
            $.ajax({
                type: 'post',
                url: '/recomments/delete/' + id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function () {
                alert('댓글이 삭제되었습니다.')
                location.reload();
                // window.location.href = "/";
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }
    Rcdel.init();
}

function likeUnlike() {
    var like_button = $(this).parent().parent();
    var boardLike = like_button.children(".boardLike").val();
    var com = like_button.children(".comId").val();
    var id = like_button.children(".likeId").val();

    var likeUrl = "/board/like/"+ boardLike;
    var unlikeUrl = "/board/dislike/"+ boardLike;
    var buttonClass = $(this).attr("class")
    var url;
    if(buttonClass=="like_button") url = likeUrl;
    else url = unlikeUrl;
    // if (likeId == 0) {
    //     confirm("로그인을 이용해주세요");
    //     location.href = "/member/login";
    // }
    console.log("로그인 아이디" + id);
    console.log("게시글번호" + boardLike);
    console.log("댓글부모" + com);

    var likeId = $('#likeId').val().length;

    if (likeId == 0) {
        confirm("로그인을 해주세요");
        location.href = "/member/login";
    }

    $.ajax({
        type: 'post',
        url: url,
        data: {
            likeId: id,
            boardLike: boardLike,
            comId: com,
        },

        dataType: "json",
        success: function (data) {
            console.log(data);
            if (data === 1) {
                alert("댓글을 추천 하셨습니다");
                location.reload();
            } else if (data === 2) {
                alert("댓글을 추천 하셨습니다");
                location.reload();
            } else if (data === 3) {
                alert("이미 추천하신 댓글입니다")
                location.reload();
            } else if (data === 0) {
                alert("댓글을 추천 하셨습니다")
                location.reload();
            }
        },
        error: function () {
            alert("잘못된 접근입니다");
        }
    })
}

function buttonToggle(){
    $(this).parent().parent().children('#reCmt').toggleClass("on off");
}

$(function () {
    $('#comment_save').on("click", CommentSave);
    $('#delete').on("click", CDelete);
    $('#RCDelete').on("click", RCDelete);
    $('.recomment_save_btn').on("click", ReCommentSave);
    $('.recmt_button').on("click", buttonToggle);
    $('.like_button').on("click", likeUnlike);
    $('.unlike_button').on("click", likeUnlike);
});
