let weight = document.getElementById("weight").getContext("2d");
let rmr = document.getElementById("rmr").getContext("2d");
let bfp = document.getElementById("bfp").getContext("2d");
let smm = document.getElementById("smm").getContext("2d");

// 랜덤 값
var randomScalingFactor = function () {
    return ("#" + Math.round(Math.random() * 0xffffff).toString(16));
}

// 체중
let weightData = [];
// 기초 대사량(Resting Metabolic Rate)
let rmrData = [];
// 체지방률(Body fat percentage)
let bfpData = [];
// 골격근량(Skeletal muscle mass)
let smmData = [];

let lenth;
let label = [];
$(".inBody_weight").each(function () {
    let text = $(this).text();
    lenth = weightData.push(text);
});
$(".inBody_rmr").each(function () {
    let text = $(this).text();
    rmrData.push(text);
});
$(".inBody_bfp").each(function () {
    let text = $(this).text();
    bfpData.push(text);
});
$(".inBody_smm").each(function () {
    let text = $(this).text();
    smmData.push(text);
});
$(".inBody_date").each(function () {
    let text = $(this).text();
    text = moment(text).format("YYYY년 M월 D일-HH:mm");
    text = text.split("-");
    label.push(text);
});

createChart(weight, weightData);
createChart(rmr, rmrData);
createChart(bfp, bfpData);
createChart(smm, smmData);


//   데이터 값 넣기
//   rmr.data.datasets[0].data = [1, 2, 3, 4, 5, 6, 7];
// 라벨 변경
//   rmr.data.labels = [1, 2, 3, 4, 5, 6, 7];

function chartName(name) {
    switch (name) {
        case weight:
            return "체중(kg)";
            break;
        case rmr:
            return "기초대사량(kcal)";
            break;
        case bfp:
            return "체지방률(%)";
            break;
        case smm:
            return "골격근량(kg)";
            break;
    }
}

function createChart(name, data) {
    let myChart = new Chart(name, {
        type: "line",
        data: {
            labels: label,
            datasets: [
                {
                    data: data,
                    // 뒷배경 색깔
                    backgroundColor: ["rgba(0, 0, 0, 0)"],
                    // 선 색깔
                    borderColor: [
                        randomScalingFactor(),
                    ],
                    // 선 굵기
                    borderWidth: 3,
                    // 선 곡선률
                    lineTension: [0],
                },
            ],
        },

        options: {

            // 레이아웃
            //   layout: {
            //     padding: {
            //       left: 50,
            //       right: 0,
            //       top: 0,
            //       bottom: 0,
            //     },
            //   },
            responsive: false,
            //제목
            title: {
                display: true,
                text: chartName(name),
                fontSize: 13,
                // fontFamily: "",
                // fontColor: "red",
            },

            legend: {
                // 위에 라벨 숨기기
                display: false,
                // labels: { fontColor: "rgb(255, 99, 132)", fontSize: 20 },
            },
            scales: {
                yAxes: [
                    {
                        //왼쪽 라벨 없애기
                        display: false,
                        ticks: {
                            //제로 부터 시작
                            beginAtZero: false,
                            fontSize: 20,
                        },
                        gridLines: {
                            display: false,
                        },
                    },
                ],
                // x축 관련
                xAxes: [
                    {
                        gridLines: {
                            display: true,
                        },
                        ticks: {
                            //   beginAtZero: true,
                            fontSize: 9,
                        },
                    },
                ],
            },
            // 올리면 보이는 정보 안보이게 하기
            tooltips: {
                enabled: false,
            },
            hover: {
                animationDuration: 0,
            },
            animation: {
                duration: 1,
                onComplete: function () {
                    var chartInstance = this.chart,
                        ctx = chartInstance.ctx;
                    // 폰트 옵션들
                    ctx.font = Chart.helpers.fontString(
                        // Chart.defaults.global.defaultFontSize,
                        15,
                        Chart.defaults.global.defaultFontStyle,
                        Chart.defaults.global.defaultFontFamily
                    );
                    ctx.fillStyle = "black";
                    ctx.textAlign = "center";
                    ctx.textBaseline = "bottom";

                    this.data.datasets.forEach(function (dataset, i) {
                        var meta = chartInstance.controller.getDatasetMeta(i);
                        meta.data.forEach(function (line, index) {
                            var data = dataset.data[index];
                            ctx.fillText(data, line._model.x, line._model.y - 5);
                        });
                    });
                },
            },
            // 옵션 끝
        },
    });
}

