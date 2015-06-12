$(function () {
    newSlider('Malt');
    newSlider('Water');
    newSlider('Hop');
    newSlider('Yiest');
    newGauge('#aggregator', 'Agregatr.io');
    newGauge('#dojrzewatr', 'Dojrzewatr.io');
    newGauge('#butelkatr', 'Butelkatr.io');
    newSolidGague('#malt', 'Malt');
    newSolidGague('#water', 'Water');
    newSolidGague('#hop', 'Hop');
    newSolidGague('#yiest', 'Yiest');
});

function newSlider(name) {
    new dhtmlXSlider({
				parent: 'sliderObj' + name,
				linkTo: 'sliderLink' + name,
				step: 1,
				min: 10,
				max: 100,
				value: 15
			});
}

function newSolidGague(id, name) {
var gaugeOptions = {

        chart: {
            type: 'solidgauge'
        },

        title: null,

        pane: {
            size: '100%',
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                innerRadius: '60%',
                outerRadius: '100%',
                shape: 'arc'
            }
        },

        tooltip: {
            enabled: false
        },

        // the value axis
        yAxis: {
            stops: [
                [0.1, '#DF5353'], // red
                [0.5, '#DDDF0D'], // yellow
                [0.9, '#55BF3B'] // green

            ],
            lineWidth: 0,
            minorTickInterval: null,
            tickPixelInterval: 400,
            tickWidth: 0,
            title: {
                y: -70
            },
            labels: {
                y: 16
            }
        },

        plotOptions: {
            solidgauge: {
                dataLabels: {
                    y: 5,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        }
    };

    // The speed gauge
    $(id).highcharts(Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: 0,
            max: 200,
            title: {
                text: name
            }
        },

        credits: {
            enabled: false
        },

        series: [{
            name: name,
            data: [80],
            dataLabels: {
                format: '<div style="text-align:center">{y} items</div>'
            },
            tooltip: {
                valueSuffix: ' items'
            }
        }]

    }));

    setInterval(function () {
            // Speed
            var chart = $(id).highcharts(),
                point,
                newVal,
                inc;

            if (chart) {
                point = chart.series[0].points[0];
                inc = Math.round((Math.random() - 0.5) * 100);
                newVal = point.y + inc;

                if (newVal < 0 || newVal > 200) {
                    newVal = point.y - inc;
                }

                point.update(newVal);
            }

        }, 2000);
}

function newGauge(id, name) {
    $(id).highcharts({

            chart: {
                type: 'gauge',
                alignTicks: false,
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false
            },

            title: {
                text: name
            },

            pane: {
                startAngle: -150,
                endAngle: 150
            },

            yAxis: [{
                min: 0,
                max: 200,
                tickPosition: 'outside',
                minorTickPosition: 'outside',
                lineColor: '#339',
                tickColor: '#339',
                minorTickColor: '#339',
                offset: -25,
                lineWidth: 2,
                labels: {
                    distance: -20,
                    rotation: 'auto'
                },
                tickLength: 5,
                minorTickLength: 5,
                endOnTick: false
            }],

            series: [{
                name: 'Amount',
                data: [80],
                dataLabels: {
                    formatter: function () {
                        var amount = this.y;
                        return '<span style="color:#339">' + amount + ' items</span><br/>';
                    },
                    backgroundColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, '#DDD'],
                            [1, '#FFF']
                        ]
                    }
                },
                tooltip: {
                    valueSuffix: ' items'
                }
            }]

        },
            // Add some life
            function (chart) {
                setInterval(function () {
                    var point = chart.series[0].points[0],
                        newVal,
                        inc = Math.round((Math.random() - 0.5) * 20);

                    newVal = point.y + inc;
                    if (newVal < 0 || newVal > 200) {
                        newVal = point.y - inc;
                    }

                    point.update(newVal);

                }, 3000);

            });
}