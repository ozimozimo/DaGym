var ws;


window.onload = function () {
    wsOpen();
}

function wsOpen() {
    ws = new WebSocket("ws://" + location.host + "/ptChat/websocket");
    wsEvt();
}

function wsEvt() {
    ws.onopen = function (data) {
        //소켓이 열리면 동작
    }

    ws.onmessage = function (data) {
        //메시지를 받으면 동작
        var msg = data.data;
        if (msg != null && msg.trim() != '') {
            var d = JSON.parse(msg);
            if (d.type == "getId") {
                var si = d.sessionId != null ? d.sessionId : "";
                if (si != '') {
                    $("#sessionId").val(si);
                }
            } else if (d.type == "message") {
                if (d.sessionId == $("#sessionId").val()) {
                    // $("#chating").append("<p class='me'>나 :" + d.msg + "</p>");
                    $('#chating').append(`<div class="bubble you">${d.msg}</div>`);
                } else {
                    $("#chating").append(
                        `<div class="bubble me">${d.msg}</div>`);

                }

            } else {
                console.warn("unknown type!")
            }
        }
    }

    document.addEventListener("keypress", function (e) {
        if (e.keyCode == 13) { //enter press
            send();
        }
    });
}

// function chatName(){
//     var userName = $("#userName").val();
//     if(userName == null || userName.trim() == ""){
//         alert("사용자 이름을 입력해주세요.");
//         $("#userName").focus();
//     }else{
//         wsOpen();
//         $("#yourName").hide();
//         $("#yourMsg").show();
//     }
// }

function send() {
    var option = {
        type: "message",
        sessionId: $("#sessionId").val(),
        userName: $("#userName").val(),
        msg: $("#chatting").val()
    }
    console.log(option);
    ws.send(JSON.stringify(option))
    $('#chatting').val("");
}

$(function (){
    let today = moment().locale("ko").format('lll');
    $('.today').text(today);
})