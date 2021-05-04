// 수정완료
$(function () {
    todayData();

});

function dietDelete(a) {
    let id = a.parentNode.parentNode.firstChild.innerText;
    console.log(id);
    $.ajax({
        type: 'post',
        url: '/diet/delete/'+id,
        contentType: 'application/json; charset=utf-8',
    }).done(function () {
        alert('식단이 삭제되었습니다');
        location.reload();
    }).fail(function (error) {
        alert(error);
        console.log(JSON.stringify(error));
    })
}

function mkHtml(result) {
    // 결과 출력
    let id = $('.loginId').text();
    for (let i = 0; i < result.length; i++) {
        var a = result[i].diet_name;
        var b = result[i].diet_kcal;
        var c = result[i].diet_carbo;
        var d = result[i].diet_protein;
        var e = result[i].diet_fat;
        var f = result[i].diet_time;
        var g = result[i].diet_id;
        var h = result[i].diet_member_id;

        function input(time, list, value) {
            if (f == time) {
                let content = "<tr class=" + list + ">"
                content += "<td class='diet_id' style='display: none'>" + g + "</td>"
                content += "<td class='diet_member_id' style='display: none'>" + h + "</td>"
                content += "<td class='diet_name'>" + a + "</td>"
                content += "<td class='diet_kcal'>" + b + "</td>"
                content += "<td class='diet_carbo'>" + c + "</td>"
                content += "<td class='diet_protein'>" + d + "</td>"
                content += "<td class='diet_fat'>" + e + "</td>"
                if (h == id)
                    content += "<td><button type='button' class='diet_Delete' onclick='dietDelete(this)'>삭제</button>" + "</td>"
                content += "</tr>"


                $('.diet').children(value).children('.dietList').append(content);
            }


        }

        input('아침', 'breakfastList', '.breakfast');
        input('점심', 'lunchList', '.lunch');
        input('저녁', 'dinnerList', '.dinner');
        input('간식', 'extraList', '.extra');
    }

    // 시간별 합계
    function mkEat(time, classEat, classKcal, classCarbo, classProtein, classFat) {
        let content = "<tr class=" + classEat + ">"
        content += "<td>영양소 합계</td>"
        content += "<td class=" + classKcal + "></td>"
        content += "<td class=" + classCarbo + "></td>"
        content += "<td class=" + classProtein + "></td>"
        content += "<td class=" + classFat + "></td>"
        content += "</tr>"
        $(time).children('.dietList').append(content);
    }

    mkEat('.breakfast', 'breakfastEat', 'breakfast_kcal', 'breakfast_carbo', 'breakfast_protein', 'breakfast_fat');
    mkEat('.lunch', 'lunchEat', 'lunch_kcal', 'lunch_carbo', 'lunch_protein', 'lunch_fat');
    mkEat('.dinner', 'dinnerEat', 'dinner_kcal', 'dinner_carbo', 'dinner_protein', 'dinner_fat');
    mkEat('.extra', 'extraEat', 'extra_kcal', 'extra_carbo', 'extra_protein', 'extra_fat');

    // 영양소별 합계
    function sumNutr(time, timeList, nutr, timeEat, timeNutr) {
        let sum = 0;
        for (let i = 0; i < $(time).children('.dietList').children(timeList).find(nutr).length; i++) {
            sum += parseFloat($(time).children('.dietList').children(timeList).children(nutr).eq(`${i}`).text());
        }
        $(time).children('.dietList').children(timeEat).children(timeNutr).append(sum.toFixed(1))
    }

    sumNutr('.breakfast', '.breakfastList', '.diet_kcal', '.breakfastEat', '.breakfast_kcal')
    sumNutr('.breakfast', '.breakfastList', '.diet_carbo', '.breakfastEat', '.breakfast_carbo')
    sumNutr('.breakfast', '.breakfastList', '.diet_protein', '.breakfastEat', '.breakfast_protein')
    sumNutr('.breakfast', '.breakfastList', '.diet_fat', '.breakfastEat', '.breakfast_fat')

    sumNutr('.lunch', '.lunchList', '.diet_kcal', '.lunchEat', '.lunch_kcal')
    sumNutr('.lunch', '.lunchList', '.diet_carbo', '.lunchEat', '.lunch_carbo')
    sumNutr('.lunch', '.lunchList', '.diet_protein', '.lunchEat', '.lunch_protein')
    sumNutr('.lunch', '.lunchList', '.diet_fat', '.lunchEat', '.lunch_fat')

    sumNutr('.dinner', '.dinnerList', '.diet_kcal', '.dinnerEat', '.dinner_kcal')
    sumNutr('.dinner', '.dinnerList', '.diet_carbo', '.dinnerEat', '.dinner_carbo')
    sumNutr('.dinner', '.dinnerList', '.diet_protein', '.dinnerEat', '.dinner_protein')
    sumNutr('.dinner', '.dinnerList', '.diet_fat', '.dinnerEat', '.dinner_fat')

    sumNutr('.extra', '.extraList', '.diet_kcal', '.extraEat', '.extra_kcal')
    sumNutr('.extra', '.extraList', '.diet_carbo', '.extraEat', '.extra_carbo')
    sumNutr('.extra', '.extraList', '.diet_protein', '.extraEat', '.extra_protein')
    sumNutr('.extra', '.extraList', '.diet_fat', '.extraEat', '.extra_fat')

    // 일일 합계
    function nutr(str, where) {
        let sum = 0;
        str.forEach(function (n) {
            sum += parseFloat($('.diet').find(n).text());
        })
        $(where).html(sum.toFixed(1));
    }

    var k = [".breakfast_kcal", ".lunch_kcal", ".dinner_kcal", ".extra_kcal"];
    var c = [".breakfast_carbo", ".lunch_carbo", ".dinner_carbo", ".extra_carbo"];
    var p = [".breakfast_protein", ".lunch_protein", ".dinner_protein", ".extra_protein"];
    var f = [".breakfast_fat", ".lunch_fat", ".dinner_fat", ".extra_fat"];

    nutr(k, '.dailyKcal');
    nutr(c, '.dailyCarbo');
    nutr(p, '.dailyProtein');
    nutr(f, '.dailyFat');
}

// 오늘 날짜 가져오기 (2021-04-30 형태)
var date = new Date();
date = getFormatDate(date);

function getFormatDate(date) {
    var year = date.getFullYear();
    var month = (1 + date.getMonth());
    month = month >= 10 ? month : '0' + month;
    var day = date.getDate();
    day = day >= 10 ? day : '0' + day;
    return year + '-' + month + '-' + day;
}

// 오늘 날짜 데이터
function todayData() {
    var today = date;
    var id = $('.loginId').text();
    var data = {
        regDate: today,
        id: id
    }
    $.ajax({
        type: 'get',
        url: '/diet/listToday/',
        data: data,
        contentType: 'application/json; charset=utf-8',
    }).done(function (result) {
        mkHtml(result);
    }).fail(function () {
        alert('문제가 있다');
    })
}

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
        contentHeight: 400,
        events: [],
        // 날짜 클릭했을 때
        dateClick: function (info) {
            var id = $('.loginId').text();
            var date = info.dateStr;
            var data = {
                id: id,
                modDate: date,
            }
            $.ajax({
                type: 'get',
                url: '/diet/listDate',
                data: data,
                contentType: 'application/json; charset=utf-8',
            }).done(function (result) {
                if (result.length == 0)
                    alert('기록된 데이터가 없습니다');

                $('.dietList').empty();
                mkHtml(result);

            }).fail(function (error) {
                alert("fail");
                console.log(JSON.stringify(error));
            })
        },
        // // 이벤트를 클릭했을 때
        eventClick: function (info) {
            alert(info.event.title);
        },
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
