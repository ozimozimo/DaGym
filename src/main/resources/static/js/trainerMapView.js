let mapContainer = document.getElementById("map"), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 5, // 지도의 확대 레벨
    };
// 지도를 생성합니다
let map = new kakao.maps.Map(mapContainer, mapOption);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 주소-좌표 변환 객체를 생성합니다
let geocoder = new kakao.maps.services.Geocoder();

let centerX;
let centerY;
let userAddr = "대구광역시 수성구 범어로 18길 22";
// 유저 주소 좌표 변환후
geocoder.addressSearch(userAddr, function (result, status) {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
        let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        // console.log("중심좌표 : " + coords);
        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        centerX = coords.Ma;
        centerY = coords.La;
        // console.log(centerX, centerY);
        map.setCenter(coords);
        drawMarker(centerX, centerY);
    }
});
var positions = [
    {trainer: "김소희", lating: "대구광역시 수성구 범어로 19길 23"},
    {trainer: "박진성", lating: "대구광역시 복현로 71"},
    {trainer: "이동기", lating: "서울 특별시 강남구 영동대로 517"},
    {trainer: "조수현", lating: "대전광역시 서구 청사로 189"},
    {trainer: "장주경", lating: "대구광역시 달성군 다사읍 대실역남로 93"},
    {trainer: "김도현", lating: "대구광역시 북구 산격로 2-2"},
];
var markers = [];
var infowindows = [];

function drawMarker(x, y) {
    positions.forEach(function (item, index) {
        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(
            positions[index].lating,
            function (result, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === kakao.maps.services.Status.OK) {
                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                    // console.log("gg", coords.Ma, coords.La);
                    let length = calDistence(x, y, coords.Ma, coords.La);
                    // km
                    let km = 5;

                    if (length < km) {
                        // console.log(length + "km");
                        // 결과값으로 받은 위치를 마커로 표시합니다
                        var marker = new kakao.maps.Marker({
                            map: map,
                            position: coords,
                            title: positions[index].trainer,

                            // image: markerImage,
                        });
                        // 인포윈도우로 장소에 대한 설명을 표시합니다
                        var infowindow = new kakao.maps.InfoWindow({
                            disableAutoPan: true,
                            content: `<div style="width:150px;text-align:center;padding:6px 0;">${positions[index].trainer}</div>`,
                        });
                        infowindow.open(map, marker);

                        markers.push(marker);
                        infowindows.push(infowindow);

                        kakao.maps.event.addListener(
                            marker,
                            "mouseover",
                            makeOverListener(map, marker, infowindow)
                        );
                        kakao.maps.event.addListener(
                            marker,
                            "mouseout",
                            makeOutListener(infowindow)
                        );
                    }

                    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                    // map.setCenter(coords);
                }
            }
        );
    });
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
    return function () {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
    return function () {
        infowindow.close();
    };
}

// kakao.maps.event.addListener(map, "dragend", function () {
//   // 지도 중심좌표를 얻어옵니다
//   var latlng = map.getCenter();
//   drawMarker(latlng.getLng(), latlng.getLat());
// });

kakao.maps.event.addListener(map, "dragend", function () {
    removeMarkers();
    removeInfowindows();
    markers = [];
    infowindows = [];
    // 지도 중심좌표를 얻어옵니다
    var latlng = map.getCenter();
    // console.log(centerX);
    // console.log(centerY);

    centerX = latlng.getLat();
    centerY = latlng.getLng();
    drawMarker(centerX, centerY);
});

// var imageSrc =
//     "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png", // 마커이미지의 주소입니다
//   imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
//   imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
// // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
// var markerImage = new kakao.maps.MarkerImage(
//     imageSrc,
//     imageSize,
//     imageOption
//   ),
//   markerPosition = new kakao.maps.LatLng(37.54699, 127.09598); // 마커가 표시될 위치입니다

// // 마커에 클릭이벤트를 등록합니다
// kakao.maps.event.addListener(marker, "click", function () {
//   // 마커 위에 인포윈도우를 표시합니다
//   infowindow.open(map, marker);
// });

// 거리 계산 함수
function calDistence(x, y, ax, ay) {
    var polyline = new daum.maps.Polyline({
        // map: map,
        path: [new daum.maps.LatLng(x, y), new daum.maps.LatLng(ax, ay)],
        // strokeWeight: 2,
        // strokeColor: "#FF00FF",
        // strokeOpacity: 0.8,
        // strokeStyle: "dashed",
    });
    let length = (Math.ceil(polyline.getLength()) / 1000).toFixed(2);
    //return getTimeHTML(polyline.getLength());//미터단위로 길이 반환;
    return length;
}

// 마커 제거
function removeMarkers() {
    // infowindow.close();
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}

// 정보창 제거
function removeInfowindows() {
    for (var i = 0; i < infowindows.length; i++) {
        infowindows[i].close();
    }
}

//
function viewTrainer(){

}