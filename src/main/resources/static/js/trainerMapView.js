$(function () {
});

var AllMemberArr = [];

function onLoad() {

    $.ajax({
        url: "/ptUser/map/trainers",
        method: "get",
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        data.forEach(function (item, index) {
            var addr = item.member.address_normal;
            addr = addr.substring(5, addr.length);
            console.log(addr);
            AllMemberArr.push({
                id: item.member.id,
                trainer_id: item.member.user_id,
                address: addr + " " + item.member.address_detail,
                trainer_name: item.member.user_name,
                trainer_pn : item.member.user_pn,
                trainer_gymName: item.trainer_gymName,
                trainer_kakao: item.trainer_kakao,
                trainer_instagram: item.trainer_instagram,
                imgName: item.imgName
            });
        })
        // 프로필이미지  이름 휴대폰 번호 헬스장 이름	카카오톡 아이디	인스타그램 아이디	신청	상세보기


    }).fail(function (error) {
        console.log(error);
    });

    console.log(AllMemberArr);

    // function showList() {
    //
    //     var member_id = $('#member_id').val();
    //     console.log(member_id);
    //     var data = {
    //         member_id: member_id,
    //     }
    //     $.ajax({
    //         type: 'get',
    //         url: '/ptUser/apply/findMember',
    //         data: data,
    //         contentType: 'application/json; charset=utf-8',
    //     }).done(function (data) {
    //         console.log(data);
    //         mkApply(data);
    //     }).fail(function (error) {
    //         console.log(error);
    //     })
    // }
}

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
var markers = [];
var infowindows = [];
var nowMarkers;

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
        nowMarkers = new kakao.maps.Marker({
            map: map,
            position: coords,
        });

        map.setCenter(coords);
        drawMarker(centerX, centerY);
    }
});

function drawMarker(x, y) {
    onLoad();
    AllMemberArr.forEach(function (item, index) {
        // 주소로 좌표를 검색합니다
        geocoder.addressSearch(
            AllMemberArr[index].address,
            function (result, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === kakao.maps.services.Status.OK) {
                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                    // console.log("gg", coords.Ma, coords.La);
                    let length = calDistence(x, y, coords.Ma, coords.La);
                    // km
                    let km = 20000;


                    if (length < km) {
                        // console.log(length + "km");
                        // 결과값으로 받은 위치를 마커로 표시합니다
                        var marker = new kakao.maps.Marker({
                            map: map,
                            position: coords,
                            title: AllMemberArr[index].trainer_name,
                            // image: markerImage,
                        });
                        // 인포윈도우로 장소에 대한 설명을 표시합니다
                        var infowindow = new kakao.maps.InfoWindow({
                            disableAutoPan: true,
                            content: `<div style="width:150px;text-align:center;padding:6px 0;">${AllMemberArr[index].trainer_name}</div>`,
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

                        var content = "";
                        console.log('1');
                        content += `<tr><input type="hidden" class="trainer_id" value="${item.trainer_id}">`
                        content += `<td><img className="trainer_image" width="200px" height="200px" src="@{/display(fileName=${item.imgName})}"></td>`
                        content += `<td>${item.trainer_id}</td>`
                        content += `<td>${item.trainer_name}</td>`
                        content += `<td>${item.trainer_pn}</td>`
                        content += `<td>${item.trainer_gymName}</td>`
                        content += `<td>${item.trainer_kakao}</td>`
                        content += `<td>${item.trainer_instagram}</td>`
                        content += `<td> <div><button type="button" onClick="check()">PT신청</button></div></td>`
                        content += `<td><button type="button" onClick="detailView()">상세보기</button></td></tr>`
                        $('.tbody').append(content);

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
    nowMarkers.setMap(null);
    removeMarkers();
    removeInfowindows();
    $('.tbody').empty();
    AllMemberArr = [];
    markers = [];
    infowindows = [];

    // 지도 중심좌표를 얻어옵니다
    var latlng = map.getCenter();
    // console.log(centerX);
    // console.log(centerY);
    nowMarkers = new kakao.maps.Marker({
        map: map,
        position: latlng,
    });


    // nowMarkers.setMap(map);

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
