// 달력 관련
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
        contentHeight : "auto",
        themeSystem: 'bootstrap',
        events: [],
        // 날짜 클릭했을 때
        dateClick: function (info) {
            var url = window.location.pathname;

            var id = $('.loginId').text();
            var diet_id = $('.diet_member_id').val();
            var click = info.dateStr;
            $('.date').val(click);
            $('.calendar_date').text(click);
            date_color_change();
            var ex_data = {
                id: id,
                ex_date: click
            }
            var diet_data = {
                id: diet_id,
                diet_date: click
            }
            // 식단 부분
            if (url.includes("diet")) {
                url = "/diet/clickDate";
                $.ajax({
                    type: 'get',
                    url: url,
                    data: diet_data,
                    contentType: 'application/json; charset=utf-8',
                }).done(function (result) {
                    console.log(result);
                    if (result.length == 0) {
                        // alert('기록된 데이터가 없습니다');
                        zeroData();
                        hideEat();
                        chartCreate();
                    } else if (diet_data.diet_date == click) {
                        console.log(result);
                        removeNutr();
                        showEat();
                        mkDiet(result);

                    }

                }).fail(function (error) {
                    alert("fail");
                    console.log(JSON.stringify(error));
                })
            }
            // 운동 기록 부분
            else if (url.includes("ExRecord")) {
                console.log('운동이다 ');
                // url = "/ExRecord/clickDate";
                url = "/ExRecord/exDate";
                $.ajax({
                    type: 'get',
                    url: url,
                    data: ex_data,
                    contentType: 'application/json; charset=utf-8',
                }).done(function (result) {
                    console.log(result);
                    if (result.length == 0) {
                        // alert('기록된 데이터가 없습니다');
                        $('.anotherEx').empty()
                    } else {
                        $('.anotherEx').empty()
                        mkExr(result);
                    }

                }).fail(function (error) {
                    alert("fail");
                    console.log(JSON.stringify(error));
                })
            }
        },
        // // 이벤트를 클릭했을 때
        eventClick: function (info) {


            alert(info.event.title);
        },

        // dayRender: function (date, cell) {
        //     if($('.date').val() === date.getDate()){
        //         cell.css("background-color", "yellow");
        //     }
        // },


        dayMaxEventRows: true, // for all non-TimeGrid views
        views:
            {

                timeGrid: {
                    dayMaxEventRows: 6, // adjust to 6 only for timeGridWeek/timeGridDay
                },
            },
    });
    calendar.render();

});

$(function (){
    var date = new Date();
    date = getFormatDate(date);

    $('.date').val(date);
    $('.calendar_date').text(date);

})

let changeDate;


function date_color_change(){
    let todayDate = getFormatDate(new Date());

    if(changeDate){
        changeDate.css("background","none");
    }
    $('.fc-day').each(function (){
        let date = $(this).attr("data-date");
        if(date == todayDate){
            $(this).css("background","#ffffa1");

        }
        if($(".date").val() == date){
            $(this).css("background","#FFDC28");
            changeDate = $(this);
        }
    })
}