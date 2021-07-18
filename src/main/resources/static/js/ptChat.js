let ws;

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
        let msg = data.data;
        if (msg != null && msg.trim() != '') {
            let d = JSON.parse(msg);
            if (d.type == "getId") {
                let si = d.sessionId != null ? d.sessionId : "";
                if (si != '') {
                    $("#sessionId").val(si);
                }
            } else if (d.type == "message") {
                if (d.sessionId == $("#sessionId").val()) {
                    // $("#chating").append("<p class='me'>나 :" + d.msg + "</p>");
                    $('#chating').append(`<div><div class="bubble you">${d.msg}</div><span class="chatDate youChat">${d.chatTime}</span></div>`);
                } else {
                    $("#chating").append(
                        `<div><div class="bubble me">${d.msg}</div><span class="chatDate meChat">${d.chatTime}</span></div>`);

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
//     let userName = $("#userName").val();
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
    let time = moment().locale("ko").format('LT');
    let option = {
        type: "message",
        sessionId: $("#sessionId").val(),
        userName: $("#userName").val(),
        msg: $("#chatting").val(),
        chatTime : time,
    }
    console.log(option);
    ws.send(JSON.stringify(option))
    $('#chatting').val("");

}

$(function (){

    let today = moment().locale("ko").format('ll');
    $('.today').text(today);
})
