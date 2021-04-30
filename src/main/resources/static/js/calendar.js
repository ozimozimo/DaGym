//달력 관련

document.addEventListener("DOMContentLoaded", function () {
    var calendarEl = document.getElementById("calendar");

    var calendar = new FullCalendar.Calendar(calendarEl, {
        //한글버전
        locale: "ko",
        initialView: "dayGridMonth",
        headerToolbar: {
            start: "",
            center: "prev title next",
            // end: "dayGridMonth,timeGridWeek,timeGridDay",
            end: "",
        },
        contentHeight: 700,

        events: [
            // {
            //     // this object will be "parsed" into an Event Object
            //     title: "김수미 회원님 PT", // a property!
            //     start: "2021-04-02T12:30:00",
            // },
        ],
        // 날짜 클릭했을 때
        // dateClick: function () {
        //   alert("날짜 클릭");
        // },
        // // 이벤트를 클릭했을 때
        eventClick: function (info) {
            alert(info.event.title);
        },
        dayMaxEventRows: true, // for all non-TimeGrid views
        views: {
            timeGrid: {
                dayMaxEventRows: 6, // adjust to 6 only for timeGridWeek/timeGridDay
            },
        },
    });

    calendar.render();

    calendar.on("dateClick", function (info) {
        let date = info.dateStr;
        window.open(
            `/calendar/memoPop?date=${date}`,
            "등록창",
            "width=400, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=yes"
        );
    });
    // calendar.on("eventClick", function (info) {
    //     // console.log(info["el"]);
    // });

    // calendar.addEvent({ title: "evt4", start: "2021-04-15T12:30:00" });
    // calendar.addEvent({ title: "evt2", start: "2021-04-11T12:30:00" });

    // 캘린더 이벤트 배열타입으로 다 가져옴
    // var arrCal = calendar.getEvents();
    // // alert(arrCal[0].title, arrCal[0].title);

    // console.log(calendar.getOption("locale"));
});


