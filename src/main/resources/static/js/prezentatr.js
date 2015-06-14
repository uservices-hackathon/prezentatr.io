$(function () {
    var nextTimeout;

    newSlider('Order');
    newGauge('#dojrzewatr', 'Dojrzewatr.io', 'present/dojrzewatr');
    newGauge('#butelkatr', 'Butelkatr.io', 'present/butelkatr');
    newAggrgtrMetrics('#aggregator', 'Agregatr.io');

    $('#order').on('click', function () {
        var $btn = $(this).button('loading')
        console.log("Doing business");
        setTimeout(function() {
            $btn.button('reset')
        }, 200);
      })

    $('#auto').on('change', function() {
        // always clear the last timeout if any
        if (nextTimeout) {
            clearTimeout(nextTimeout);
        }

        if(this.checked) {
            console.log("setting intervals");
            var interval = (60 / ($('#sliderLinkOrder').text())) * 1000;
            console.log("interval = " + interval);
            var timeoutFct = function() {
                var $order = $('#order').click();
                nextTimeout = setTimeout(timeoutFct, interval);
            };

            nextTimeout = setTimeout(timeoutFct, interval);
        }
    });

});

function newSlider(name) {
    new dhtmlXSlider({
				parent: 'sliderObj' + name,
				linkTo: 'sliderLink' + name,
				step: 1,
				min: 10,
				max: 120,
				value: 60
			});
}

function newAggrgtrMetrics(element, name) {

            var brandsData = [["Malt", 30], ["Water", 600], ["Hop", 120], ["Yiest", 5]];


            // Create the chart
            $(element).highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: name
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Stock'
                    }
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y}'
                        }
                    }
                },

                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> items<br/>'
                },

                series: [{
                    name: 'Stock',
                    colorByPoint: true,
                    data: brandsData
                }]
            },
                         // Add some life
                         function (chart) {
                             setInterval(function () {
                                for (var i = 0; i < 4; i++) {
                                     var point = chart.series[0].points[i],
                                         newVal,
                                         inc = Math.round((Math.random() - 0.5) * 400);

                                     newVal = point.y + inc;
                                     if (newVal < 0 || newVal > 200) {
                                         newVal = point.y - inc;
                                     }

                                     point.update(newVal);
                                }
                             }, 3000);

                         });
}

function newGauge(id, name, valueUri) {
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
                data: [0],
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
                    $.get(valueUri, function(data) {
                        chart.series[0].points[0].update(parseInt(data));
                    });
                }, 1000);

            });
}