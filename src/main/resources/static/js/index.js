$(function () {
    $(".name,.student,.small-intro").lettering();

    // IMAGES OVERLAY EFFECT
    anime({
        targets: ".home .person-overlay",
        translateX: 1100,
        delay: 100,
        duration: 150,
        easing: "easeInSine",
    });
    anime({
        targets: ".home .person img",
        opacity: 1,
        delay: 150,
        duration: 150,
        easing: "easeInSine",
    });

    // Student
    anime({
        targets: ".student span",
        opacity: 1,
        duration: 150,
        easing: "easeInSine",
        delay: anime.stagger(100, { start: 300 }),
    });

    // MICHAEL NAME - FADE in
    anime({
        targets: ".name span",
        opacity: 1,
        duration: 300,
        easing: "easeInSine",
        delay: anime.stagger(100, { start: 450 }),
    });

    // MICHAEL 2ND ANIMATION UP AND DOWN LETTERS  :DAGYM부분 글씨 UP AND DOWN
    // anime({
    //   targets: ".name .char2, .name .char4, .name .char6",
    //   keyframes: [{ translateY: 100, duration: 1500 }],
    //   easing: "easeInOutExpo",
    //   delay: 8000,
    // });
    // anime({
    //   targets: ".name .char3, .name .char5",
    //   keyframes: [{ translateY: -100, duration: 1500 }],
    //   easing: "easeInOutExpo",
    //   delay: 8000,
    // });

    // logo
    anime({
        targets: ".logo",
        opacity: 1,
        duration: 300,
        delay: 950,
    });
    //menu
    anime({
        targets: ".menu li",
        opacity: 1,
        duration: 300,
        delay: anime.stagger(150, { start: 1050 }),
    });
    // INTRO
    anime({
        targets: ".small-intro span",
        opacity: 1,
        duration: 100,
        easing: "easeInSine",
        delay: anime.stagger(50, { start: 1100 }),
    });
});

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(35.896743, 128.621085), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };
// 35.89674354780393, 128.6210854932502
var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 지도를 클릭한 위치에 표출할 마커입니다
var marker = new kakao.maps.Marker({
    // 지도 중심좌표에 마커를 생성합니다
    position: map.getCenter()
});
// 지도에 마커를 표시합니다
marker.setMap(map);

// 지도에 클릭 이벤트를 등록합니다
// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

    // 클릭한 위도, 경도 정보를 가져옵니다
    var latlng = mouseEvent.latLng;

    // 마커 위치를 클릭한 위치로 옮깁니다
    marker.setPosition(latlng);

    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
    message += '경도는 ' + latlng.getLng() + ' 입니다';

    var resultDiv = document.getElementById('clickLatlng');
    resultDiv.innerHTML = message;

});