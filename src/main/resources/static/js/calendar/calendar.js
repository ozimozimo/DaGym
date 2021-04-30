// //달력 관련
//
// document.addEventListener("DOMContentLoaded", function () {
//     var calendarEl = document.getElementById("calendar");
//
//     var calendar = new FullCalendar.Calendar(calendarEl, {
//         //한글버전
//         locale: "ko",
//         initialView: "dayGridMonth",
//         headerToolbar: {
//             start: "",
//             center: "prev title next",
//             // end: "dayGridMonth,timeGridWeek,timeGridDay",
//             end: "",
//         },
//         contentHeight: 700,
//
//         events: [
//             // {
//             //     // this object will be "parsed" into an Event Object
//             //     title: "김수미 회원님 PT", // a property!
//             //     start: "2021-04-02T12:30:00",
//             // },
//         ],
//         // 날짜 클릭했을 때
//         // dateClick: function () {
//         //   alert("날짜 클릭");
//         // },
//         // // 이벤트를 클릭했을 때
//         // eventClick: function (e) {
//         //     alert(e.event.title);
//         //     if (e.which == 1) {
//         //         alert('1');
//         //     } else if (e.which == 2) {
//         //         alert('2');
//         //     } else if (e.which == 3) {
//         //         alert('3');
//         //     }
//         //
//         // },
//         dayMaxEventRows: true, // for all non-TimeGrid views
//         views: {
//             timeGrid: {
//                 dayMaxEventRows: 6, // adjust to 6 only for timeGridWeek/timeGridDay
//             },
//         },
//         // eventRender: function(event, element) {
//         //     element.bind('dblclick', function() {
//         //
//         //     })
//         // },
//     });
//
//     calendar.render();
//
//     calendar.on("dateClick", function (e) {
//         let date = e.dateStr;
//         window.open(
//             `/calendar/memoPop?date=${date}`,
//             "등록창",
//             "width=400, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=yes"
//         );
//         alert(e.event.title);
//     });
//     // calendar.on("eventClick", function (info) {
//     //     // console.log(info["el"]);
//     // });
//
//     let arr = createArr();
//     arr.forEach((item) => {
//         calendar.addEvent({color: item[3], title: item[2], start: item[1]});
//         console.log(item[1], item[2], item[3]);
//     });
//
//     // 캘린더 이벤트 배열타입으로 다 가져옴
//     // var arrCal = calendar.getEvents();
//     // // alert(arrCal[0].title, arrCal[0].title);
//
//     // console.log(calendar.getOption("locale"));
// });
//
// function createArr() {
//     let arr = [];
//     $(".data").each(function (i, el) {
//         el = $(el);
//
//         let x = [];
//         el.find("span").each(function (j, ele) {
//             ele = $(ele);
//             x.push(ele.text());
//         });
//         arr.push(x);
//     });
//     return arr;
// }
//
// $(function () {
//     $(".fc-body").on('contextmenu', function (e) {
//         alert('ㅎㅇ');
//         return false;
//     });
// })