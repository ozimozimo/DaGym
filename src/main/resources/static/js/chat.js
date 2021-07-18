var ws;

window.onload = function () {
    wsOpen();
}

function wsOpen() {
    // 웹 소켓 전송시 현재 방의 번호를 넘겨서 보낸다
    ws = new WebSocket("ws://" + location.host + "/chating/" + $('#roomNumber').val());
    wsEvt();
}

function wsEvt() {
    ws.onopen = function (data) {
        //소켓이 열리면 초기화 세팅하기
    }

    ws.onmessage = function (data) {
        // 메세지 받으면 동작
        var msg = data.data;

        console.log("msg data= " + msg);

        if (msg != null && msg.trim() != '') {
            var d = JSON.parse(msg);
            console.log("json 변환한값" + d);
            if (d.type == "getId") {
                var si = d.sessionId != null ? d.sessionId : "";
                if (si != "") {
                    $('#sessionId').val(si);
                }
            } else if (d.type == "message") {
                if (d.sessionId == $("#sessionId").val()) {
                    console.log("d.session ID는" + d.sessionId);
                    console.log("session ID는" + $("#sessionId").val());
                    $('#chating').append(`<div><div class="bubble you">${d.msg}</div><span class="chatDate youChat">${d.chatTime}</span></div>`);
                    // $('#chating').append("<p class='me'>나 :" + d.msg + "</p>")
                } else {
                    // $('#chating').append("<p class='others'>" + d.userName + ":" + d.msg + "</p>")
                    $("#chating").append(
                        `<div><div class="bubble me">${d.msg}</div><span class="chatDate meChat">${d.chatTime}</span></div>`);
                }
            } else {
                console.warn("unknown type");
            }
        }
    }

    document.addEventListener("keypress", function (e) {
        if (e.keyCode == 13) { //enter press
            send();
        }
    });
}

// function chatName() {
//     var userName = $("#userName").val();
//     console.log("userName =" + userName);
//     if (userName == null || userName.trim() == "") {
//         alert("사용자 이름을 입력해주세요.");
//         $("#userName").focus();
//     } else {
//         wsOpen();
//         $("#yourName").hide();
//         $("#yourMsg").show();
//     }
// }

function send() {
    let time = moment().locale("ko").format('LT');
    var option = {
        type: "message",
        roomNumber: $('#roomNumber').val(),
        sessionId: $("#sessionId").val(),
        userName: $("#userName").val(),
        msg: $("#chatting").val(),
        chatTime : time,
    }
    // console.log("option 타입=" + option.type);
    // console.log("option 방번호=" + option.roomNumber);
    // console.log("option 세션아이디=" + option.sessionId);
    // console.log("option 이름=" + option.userName);
    // console.log("option 메시지=" + option.msg);
    ws.send(JSON.stringify(option));
    $('#chatting').val("");
}

$(function () {
    let today = moment().locale("ko").format('ll');
    $('.today').text(today);
})

