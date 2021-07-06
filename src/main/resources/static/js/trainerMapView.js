var AllMemberArr = [];

var userNormalAddr = $('#userNormalAddr').val();
var userDetailAddr = $('#userDetailAddr').val();
var userAddr = userNormalAddr.substring(5, userNormalAddr.length) + " " + userDetailAddr;

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
var markers = [];
var infowindows = [];
var nowMarkers;

userToLocation();
onLoad();

//유저 주소를 좌표로 변환 후 마커 생성
function userToLocation() {
// 유저 주소 좌표 변환후

    geocoder.addressSearch(userAddr, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
            let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            centerX = coords.Ma;
            centerY = coords.La;

            // 유저 집
            // var marker = new kakao.maps.Marker({
            //     map: map,
            //     position: coords,
            // });

            // // console.log(centerX, centerY);
            var imageSrc =
                    "../../image/home.png", // 마커이미지의 주소입니다
                imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
            // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(
                imageSrc,
                imageSize,
                imageOption
                ),
                markerPosition = new kakao.maps.LatLng(centerX, centerY); // 마커가 표시될 위치입니다

            var marker = new kakao.maps.Marker({
                position: markerPosition,
                image: markerImage // 마커이미지 설정
            });
            marker.setMap(map);

            nowMarkers = new kakao.maps.Marker({
                map: map,
                position: coords,
            });

            map.setCenter(coords);
            drawMarker(centerX, centerY);
        } else {
            alert('오류로 회원님의 주소를 나타낼 수 없습니다.');
            let coords = new kakao.maps.LatLng(33.450701, 126.570667);
            nowMarkers = new kakao.maps.Marker({
                map: map,
                position: coords,
            });
        }
    });
}

function onLoad() {
    $.ajax({
        url: "/ptUser/map/trainers",
        method: "get",
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        // console.log(data);
        data.forEach(function (item, index) {
            var addr = item.member.address_normal;
            addr = addr.substring(5, addr.length);
            AllMemberArr.push({
                id: item.member.id,
                trainer_id: item.member.user_id,
                address: addr + " " + item.member.address_detail,
                trainer_name: item.member.user_name,
                trainer_pn: item.member.user_pn,
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

function drawMarker(x, y) {
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
                    let km = 2000;


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

                        content += `<tr>`
                        if(item.imgName == null){
                            content += `<td><img class="trainer_image" width="200px" height="200px" src="../../image/noImg.png"></td>`
                        } else {
                            content += `<td><img class="trainer_image" width="200px" height="200px" src="/display(fileName=${item.imgName})"></td>`
                        }
                        content += `<td>${item.trainer_id}</td>`
                        content += `<td>${item.trainer_name}</td>`
                        content += `<td>${item.trainer_pn}</td>`
                        content += `<td>${item.trainer_gymName}</td>`
                        content += `<td>${item.trainer_kakao}</td>`
                        content += `<td>${item.trainer_instagram}</td>`
                        content += `<td class="checkArea"><input type="hidden" class="id" value="${item.id}"><button type="button" class="checkBtn">PT신청</button></td>`
                        content += `<td><button type="button" class="detailBtn">상세보기</button></td></tr>`
                        $('.tbody').append(content);

                    }
                    $('.checkBtn').unbind('click');
                    $('.checkBtn').on("click", function () {

                        check($(this).prev('.id').val());
                    });
                    $('.detailBtn').on("click", function () {
                        detailView($(this).parent('td').prev('td').find('.id').val());
                    });
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

kakao.maps.event.addListener(map, "dragend", function () {

    nowMarkers.setMap(null);
    removeMarkers();
    removeInfowindows();
    $('.tbody').empty();

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


// function clickMarker() {
// // 마커에 클릭이벤트를 등록합니다
//     kakao.maps.event.addListener(marker, "click", function () {
//         // 마커 위에 인포윈도우를 표시합니다
//         infowindow.open(map, marker);
//     });
// }

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

function check(trainer_id) {
    var member_id = $('input[name=member_id]').val();
    console.log("member 값은" + member_id);
    console.log("trainer 값은" + trainer_id);
    console.log(trainer_id);
    $.ajax({
        type: 'get',
        url: '/ptUser/apply/check?',
        data: 'member_id=' + member_id,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data);
        if (data.accept_condition == 0) {
            alert('PT 수락 대기 중입니다.');
            window.location.href = '/ptUser/view';
        } else if (data.accept_condition == 1) {
            alert('PT가 진행 중입니다.');
            window.location.href = '/ptUser/view';
        } else if (data.accept_condition == null || data.accept_condition == 2) {
            console.log(data);
        }
    }).fail(function () {
        alert('PT신청 페이지로 이동하겠습니다');
        window.location.href = '/ptUser/apply?member_id=' + member_id + "&trainer_id=" + trainer_id;
    })
}

function detailView(trainer_id) {
    var popupX = (window.screen.width / 2) - (800 / 2);
    var popupY = (window.screen.height / 2) - (700 / 2);

    var option = 'status=no, height=600, width=800, left=' + popupX + ', top=' + popupY + ', screenX=' + popupX + ', screenY= ' + popupY;
    // var pageValue = $('#pageValue').val() || "";
    // var typeValue = $('#typeValue').val() || "";
    // var keywordValue = $('#keywordValue').val() || "";
    // var url = `/ptUser/detail?id=${trainer_id}&page=${pageValue}&type=${typeValue}&keyword=${keywordValue}`
    var url = `/ptUser/detail?id=${trainer_id}`;

    window.open(url, 'detailView', option);

}

