function check() {
    var member = $('.loginUserId').val();
    console.log(member);
    var data = {
        member_id: member,
    }
    $.ajax({
        type: 'get',
        url: '/ptUser/apply/check',
        data: data,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        if (data[0].accept_condition == 0) {
            alert('PT 수락 대기 중입니다.');
            window.location.href = '/ptUser/view';
            console.log(data);
        } else if (data[0].accept_condition == 1) {
            alert('PT가 진행 중입니다.')
            window.location.href = '/ptUser/view';
        } else if (data[0].accept_condition == null || data[0].accept_condition == 2) {
            console.log(data);
        }
    }).fail(function (error) {
        console.log(error);
    })
}

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

// 수락한거 조회하기
function acceptList() {
    var id = $('.member_id').val();
    var data = {
        id: id // 앞에 trainer_id -> id
    }
    $.ajax({
        type: 'get',
        url: '/ptUser/manage',
        data: data,
        contentType: 'application/json; charset=utf-8'
    }).done(function (data) {
    }).fail(function (error) {
        console.log(error);
    })
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
        mkApply(data);
        console.log(data);
    }).fail(function (error) {
        console.log(error);
    })
}

// 신청내용 확인하는 곳
function mkApply(data) {
    $('.applyListDetail').empty();
    for (let i = 0; i < data.length; i++) {
        var a = data[i].user_id;
        var b = data[i].user_name;
        var c = data[i].start_date;
        var d = data[i].end_date;
        var e = data[i].id;
        var f = data[i].user_pn;

        let content = "<tr>"
        content += "<td class= 'ptId' style='display: none'>" + e + "</td>"
        content += "<td class='ptUserId'>" + a + "</td>"
        content += "<td class='ptUserName'>" + b + "</td>"
        content += "<td class='ptStartDate'>" + c + "</td>"
        content += "<td class='ptEndDate'>" + d + "</td>"
        content += "<td class='ptPhone'>" + f + "</td>"
        content += "<td><button type='button' class='btn-primary Accept' onclick='updateAccept(this)'>수락</button></td>"
        content += "<td><button type='button' class='btn-primary Deny' onclick='updateAccept(this)'>거절</button></td>"
        $('.applyListDetail').append(content);
    }
}

// 수락, 거절 눌러서 accept_condition 바꾸기
function updateAccept(a) {
    // PTUserApplyConDto에 넘겨줄 apply_if값
    let apply_if = 0;
    // 내가 누른 버튼의 id값
    let id = a.parentNode.parentNode.firstChild.innerText;
    // 내가 누른 버튼의 텍스트값
    let con = a.innerText;
    // 수락이면 apply_if에 1 저장하고 아니면 2 저장
    if (con == "수락") {
        apply_if = 1;
    } else {
        apply_if = 2;
    }
    let data = {
        // PTUserApplyConDto에 넘겨줄 id랑 apply_if값
        id: id,
        apply_if: apply_if
    }
    $.ajax({
        type: 'post',
        url: '/ptUser/apply/update/' + id,
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        if (con == "수락") {
            alert("PT신청이 수락되었습니다");
        } else if (con == "거절") {
            alert("PT신청이 거절되었습니다");
        }
        location.reload();
    }).fail(function (error) {
        console.log(error);
    })
}

$(function () {
    acceptList();
    showList();
})

function a() {
    $('#selectEmail').change(function () {
        $(".category option:selected").each(function () {
            if ($(this).val() == '1') { //직접입력일 경우
                $('.category').removeId
                $('#trainer_category').append("<input type='text' id='trainer_category'>");

                $("#str_email02").val(''); //값 초기화
                $("#str_email02").attr("disabled", false); //활성화
            } else { //직접입력이 아닐경우
                $("#str_email02").val($(this).text()); //선택값 입력
                $("#str_email02").attr("disabled", true); //비활성화
            }
        });
    });

}