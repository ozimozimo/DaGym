var draggedEventIsAllDay;
var activeInactiveWeekends = true;

var calendar = $('#calendar').fullCalendar({

    /** ******************
     *  OPTIONS
     * *******************/
    locale: 'ko',
    timezone: "local",
    nextDayThreshold: "09:00:00",
    allDaySlot: true,
    displayEventTime: true,
    displayEventEnd: true,
    firstDay: 0, //월요일이 먼저 오게 하려면 1
    weekNumbers: false,
    selectable: true,
    weekNumberCalculation: "ISO",
    eventLimit: true,
    views: {
        month: {eventLimit: 12} // 한 날짜에 최대 이벤트 12개, 나머지는 + 처리됨
    },
    eventLimitClick: 'week', //popover
    navLinks: true,
    defaultDate: moment().format('YYYY-MM'), //실제 사용시 현재 날짜로 수정
    timeFormat: 'HH:mm',
    defaultTimedEventDuration: '01:00:00',
    editable: true,
    minTime: '00:00:00',
    maxTime: '24:00:00',
    slotLabelFormat: 'HH:mm',
    weekends: true,
    nowIndicator: true,
    dayPopoverFormat: 'MM/DD dddd',
    longPressDelay: 0,
    eventLongPressDelay: 0,
    selectLongPressDelay: 0,
    header: {
        left: 'today, prevYear, nextYear, viewWeekends',
        center: 'prev, title, next',
        right: 'month, agendaWeek, agendaDay, listWeek'
    },
    views: {
        month: {
            columnFormat: 'dddd'
        },
        agendaWeek: {
            columnFormat: 'M/D ddd',
            titleFormat: 'YYYY년 M월 D일',
            eventLimit: false
        },
        agendaDay: {
            columnFormat: 'dddd',
            eventLimit: false
        },
        listWeek: {
            columnFormat: ''
        }
    },
    customButtons: { //주말 숨기기 & 보이기 버튼
        viewWeekends: {
            text: '주말',
            click: function () {
                activeInactiveWeekends ? activeInactiveWeekends = false : activeInactiveWeekends = true;
                $('#calendar').fullCalendar('option', {
                    weekends: activeInactiveWeekends
                });
            }
        }
    },


    eventRender: function (event, element, view) {

        //일정에 hover시 요약
        element.popover({
            title: $('<div />', {
                class: 'popoverTitleCalendar',
                text: event.title
            }).css({
                'background': event.backgroundColor,
                'color': event.textColor
            }),
            content: $('<div />', {
                class: 'popoverInfoCalendar'
            })
                // .append('<p><strong>등록자:</strong> ' + event.username + '</p>')
                .append('<p><strong>구분:</strong> ' + event.type + '</p>')
                .append('<p><strong>시간:</strong> ' + getDisplayEventDate(event) + '</p>')
                .append('<div class="popoverDescCalendar"><strong>설명:</strong> ' + event.description + '</div>'),
            delay: {
                show: "800",
                hide: "50"
            },
            trigger: 'hover',
            placement: 'top',
            html: true,
            container: 'body'
        });

        return filtering(event);

    },

    /* ****************
     *  일정 받아옴
     * ************** */
    events: function (start, end, timezone, callback) {
        var calendar_user = $('#calendar_user').val();
        var member_id = $('#member_id').val();
        var trainer_id = $('#trainer_id').val();
        $.ajax({
            type: "get",
            url: '/calendar/findPT/' + member_id + '/' + trainer_id,
            data: {
                id: calendar_user
                // 화면이 바뀌면 Date 객체인 start, end 가 들어옴
                //startDate : moment(start).format('YYYY-MM-DD'),
                //endDate   : moment(end).format('YYYY-MM-DD')
            },
            success: function (response) {
                var fixedDate = response.map(function (array) {
                    delete array.member;
                    if (array.allDay && array.start !== array.end) {
                        array.end = moment(array.end).add(1, 'days'); // 이틀 이상 AllDay 일정인 경우 달력에 표기시 하루를 더해야 정상출력
                    }
                    return array;
                });
                callback(fixedDate);
            }
        });
    },

    eventAfterAllRender: function (view) {
        if (view.name == "month") $(".fc-content").css('height', 'auto');
    },


    select: function (startDate, endDate, jsEvent, view) {

        $(".fc-body").unbind('click');
        $(".fc-body").on('click', 'td', function (e) {

            $("#contextMenu")
                .addClass("contextOpened")
                .css({
                    display: "block",
                    left: e.pageX,
                    top: e.pageY
                });
            return false;
        });

        var today = moment();

        if (view.name == "month") {
            startDate.set({
                hours: today.hours(),
                minute: today.minutes()
            });
            startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
            endDate = moment(endDate).subtract(1, 'days');

            endDate.set({
                hours: today.hours() + 1,
                minute: today.minutes()
            });
            endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
        } else {
            startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
            endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
        }

        //날짜 클릭시 카테고리 선택메뉴
        var $contextMenu = $("#contextMenu");
        $contextMenu.on("click", "a", function (e) {
            e.preventDefault();

            //닫기 버튼이 아닐때
            if ($(this).data().role !== 'close') {
                newEvent(startDate, endDate, $(this).html());
            }

            $contextMenu.removeClass("contextOpened");
            $contextMenu.hide();
        });

        $('body').on('click', function () {
            $contextMenu.removeClass("contextOpened");
            $contextMenu.hide();
        });

    },

    //이벤트 클릭시 수정이벤트
    eventClick: function (event, jsEvent, view) {
        // editEvent(event);
    }

});

function getDisplayEventDate(event) {

    var displayEventDate;

    if (event.allDay == false) {
        var startTimeEventInfo = moment(event.start).format('HH:mm');
        var endTimeEventInfo = moment(event.end).format('HH:mm');
        displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo;
    } else {
        displayEventDate = "하루종일";
    }

    return displayEventDate;


}