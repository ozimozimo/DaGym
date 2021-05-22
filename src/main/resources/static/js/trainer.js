function apply() {
    var main = {
        init: function () {
            var _this = this;
            _this.apply();
        },
        apply: function () {
            var data = {
                user_name: $('#user_name').val(),
                user_id: $('#user_id').val(),
                user_email: $('#user_email').val(),
                user_pn: $('#user_pn').val(),
                start_date: $('#start_date').val(),
                end_date: $('#end_date').val()
            }
            var member_id = $('#member_id').val();
            var trainer_id = $('#trainer_id').val();


            $.ajax({
                type: 'post',
                url: '/ptUser/apply/success/' + member_id + "/" + trainer_id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('등록되었습니다');
                window.location.href = '/ptUser/view';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            })
        }
    }
    main.init();
}

// 신청온거 확인하기
function showList() {
    var id = $('.member_id').val();
    var data = {
        trainer_id: id
    }
    $.ajax({
        type: 'get',
        url: '/ptUser/apply/findMember',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        mkHtml(data);
    }).fail(function (error) {
        console.log(error);
    })
}

function mkHtml(data) {
    for(let i = 0; i < data.length; i++) {
        var a = data[i].member_id;
        var b = data[i].user_name;
        var c = data[i].start_date;
        var d = data[i].end_date;
        var e = data[i].trainer_id;
        var f = data[i].id;
        var g = data[i].accept_condition;

        let content = "<tr>"
        content += "<td class=ptId style='display: none'>" + f + "</td>"
        content += "<td class='ptUserId'>" + a + "</td>"
        content += "<td class='ptUserName'>" + b + "</td>"
        content += "<td class='ptStartDate'>" + c + "</td>"
        content += "<td class='ptEndDate'>" + d + "</td>"
        content += "<td class='ptTrainerId'>" + e + "</td>"
        content += "<td class='ptAccept'>" + g + "</td>"
        content += "<td><button type='button' class='ptAccept' onclick='updateAcceptCondition()'>수락</button></td>"
        content += "<td><button type='button' class='ptDecline'>거절</button></td>"
        $('.applyListDetail').append(content);
    }
    console.log(f);
}

// 수락, 거절 눌러서 accept_condition 바꾸기
// set_conditioin update 하면 될듯??
function updateAcceptCondition() {
    var id = $('.ptId').text();
    var ptAccept = $('.ptAccept').text();
    var data = {
        id: id,
        accept_condition: ptAccept
    }
    $.ajax({
        type: 'post',
        url: '/ptUser/ptList/update/'+id,
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (result) {
        alert('hello');
        console.log(result);
    }).fail(function (error) {
        console.log(error);
    });
}

// 알림받기,, 이거 좀 오래걸릴듯??