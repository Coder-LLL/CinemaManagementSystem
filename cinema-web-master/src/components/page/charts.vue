<template>
    <div id="charts" :style="{ width: '100%', height: '100%' }">
        <div id="main" :style="{ width: '100%', height: '50%' }"></div>
        <div id="sales" :style="{ width: '100%', height: '50%' }"></div>
    </div>
</template>

<script>
import { rechargeRecord } from '../../api/index.js';
var echarts = require('echarts');
export default {
    data() {
        return {
            incomeAndExpenditureData: [],
            dataList: [],
            incomeList: [],
            outcomeList: [],
            baseYear: '2021',
            baseMonth: '12',
            xAxisData: [],
            yAxisIncome: [],
            yAxisOutcome: []
        };
    },
    created() {
        // 请求收入与支出的数据
        this.getRechargeRecord();
    },
    mounted() {},
    methods: {
        getRechargeRecord() {
            rechargeRecord().then((res) => {
                if (res.status == 200) {
                    this.incomeAndExpenditureData = res.data;
                    this.initChartData();
                    console.log(this.incomeAndExpenditureData);
                } else {
                    this.$message.error(`收入与支出数据请求失败`);
                }
            });
        },
        initChartData() {
            // x轴上日期数据初始化
            let baseYear = this.baseYear;
            let baseMonth = this.baseMonth;
            this.xAxisData = this.incomeAndExpenditureData.map(function (item, index) {
                return baseYear + '-' + baseMonth + '-' + item.day;
            });
            console.log(this.xAxisData);

            // y轴上收入数据的初始化
            this.yAxisIncome = this.incomeAndExpenditureData.map(function (item, index) {
                return item.value;
            });
            console.log(this.yAxisIncome);

            // y轴上支出数据的初始化
            this.yAxisOutcome = this.yAxisIncome.map(function (item, index) {
                // 随机生成一个0到1.5的数字
                let i = (Math.random() + 0.3).toFixed(2);
                // 设置一个最低的支出百分比0.3，然后随机在0.3-1.3之间产生一个随机数
                console.log((Math.random() + 0.3).toFixed(2));
                return item * i;
            });
            console.log(this.yAxisOutcome);

            this.initSalesChart();
            this.initIncomeChart();
        },
        initSalesChart() {
            var chartDom = document.getElementById('main');
            var myChart = echarts.init(chartDom);
            var option;
            // prettier-ignore
            let dataAxis = ['龙争虎斗', '雪国列车', '饥饿站台', '西虹市首富', '让子弹飞', '雪山飞狐', '黄金甲', '回到明朝当王爷'];
            // prettier-ignore
            let data = [220, 182, 191, 234, 290, 330, 310, 123];
            let yMax = 500;
            let dataShadow = [];
            for (let i = 0; i < data.length; i++) {
                dataShadow.push(yMax);
            }
            option = {
                title: {
                    text: '各电影销量展示',
                    subtext: '点击各柱状图可缩放'
                },
                xAxis: {
                    data: dataAxis,
                    axisLabel: {
                        color: 'black'
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    z: 10
                },
                yAxis: {
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        color: '#999'
                    }
                },
                dataZoom: [
                    {
                        type: 'inside'
                    }
                ],
                series: [
                    {
                        type: 'bar',
                        showBackground: true,
                        itemStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: '#83bff6' },
                                { offset: 0.5, color: '#188df0' },
                                { offset: 1, color: '#188df0' }
                            ])
                        },
                        emphasis: {
                            itemStyle: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                    { offset: 0, color: '#2378f7' },
                                    { offset: 0.7, color: '#2378f7' },
                                    { offset: 1, color: '#83bff6' }
                                ])
                            }
                        },
                        data: data
                    }
                ]
            };
            // Enable data zoom when user click bar.
            const zoomSize = 6;
            myChart.on('click', function (params) {
                console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
                myChart.dispatchAction({
                    type: 'dataZoom',
                    startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
                    endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
                });
            });
            option && myChart.setOption(option);
        },
        initIncomeChart() {
            var chartDom = document.getElementById('sales');
            var myChart = echarts.init(chartDom);
            var option;
            let xAxisData = this.xAxisData;
            let yAxisIncome = this.yAxisIncome;
            let yAxisOutcome = this.yAxisOutcome;
            console.log(xAxisData);
            console.log(yAxisIncome);
            console.log(yAxisOutcome);

            var colors = ['#5470C6', '#EE6666'];
            option = {
                color: colors,
                title: {
                    text: '2021年12月收入与支出'
                },
                tooltip: {
                    trigger: 'none',
                    axisPointer: {
                        type: 'cross'
                    }
                },
                legend: {
                    data: ['2021年12月收入', '2021年12月支出']
                },
                grid: {
                    top: 70,
                    bottom: 50
                },
                xAxis: [
                    {
                        type: 'category',
                        axisTick: {
                            alignWithLabel: true
                        },
                        axisLine: {
                            onZero: false,
                            lineStyle: {
                                color: colors[1]
                            }
                        },
                        axisPointer: {
                            label: {
                                formatter: function (params) {
                                    return '支出  ' + params.value + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                                }
                            }
                        },
                        data: xAxisData
                        // [
                        //     '2021-12-1',
                        //     '2021-12-2',
                        //     '2021-12-3',
                        //     '2021-12-4',
                        //     '2021-12-5',
                        //     '2021-12-6',
                        //     '2021-12-7',
                        //     '2021-12-8',
                        //     '2021-12-9',
                        //     '2021-12-10',
                        //     '2021-12-11',
                        //     '2021-12-12',
                        //     '2021-12-13',
                        //     '2021-12-14',
                        //     '2021-12-15',
                        //     '2021-12-16',
                        //     '2021-12-17',
                        //     '2021-12-18',
                        //     '2021-12-19',
                        //     '2021-12-20',
                        //     '2021-12-21',
                        //     '2021-12-22',
                        //     '2021-12-23',
                        //     '2021-12-24',
                        //     '2021-12-25',
                        //     '2021-12-26',
                        //     '2021-12-27',
                        //     '2021-12-28',
                        //     '2021-12-29',
                        //     '2021-12-30',
                        //     '2021-12-31'
                        // ]
                    },
                    {
                        type: 'category',
                        axisTick: {
                            alignWithLabel: true
                        },
                        axisLine: {
                            onZero: false,
                            lineStyle: {
                                color: colors[0]
                            }
                        },
                        axisPointer: {
                            label: {
                                formatter: function (params) {
                                    return '收入  ' + params.value + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
                                }
                            }
                        },
                        data: xAxisData
                        // [
                        //     '2021-12-1',
                        //     '2021-12-2',
                        //     '2021-12-3',
                        //     '2021-12-4',
                        //     '2021-12-5',
                        //     '2021-12-6',
                        //     '2021-12-7',
                        //     '2021-12-8',
                        //     '2021-12-9',
                        //     '2021-12-10',
                        //     '2021-12-11',
                        //     '2021-12-12',
                        //     '2021-12-13',
                        //     '2021-12-14',
                        //     '2021-12-15',
                        //     '2021-12-16',
                        //     '2021-12-17',
                        //     '2021-12-18',
                        //     '2021-12-19',
                        //     '2021-12-20',
                        //     '2021-12-21',
                        //     '2021-12-22',
                        //     '2021-12-23',
                        //     '2021-12-24',
                        //     '2021-12-25',
                        //     '2021-12-26',
                        //     '2021-12-27',
                        //     '2021-12-28',
                        //     '2021-12-29',
                        //     '2021-12-30',
                        //     '2021-12-31'
                        // ]
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '2021年12月收入',
                        type: 'line',
                        xAxisIndex: 1,
                        smooth: true,
                        emphasis: {
                            focus: 'series'
                        },

                        data: yAxisIncome
                        //  [
                        //     12.6, 15.9, 19.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 16.0, 2.3, 5.7, 87.6, 97.1, 85.4, 126.7, 45.1,
                        //     53.5, 10.2, 87.4, 15.4, 87.6, 94.4, 160.4, 53.8, 49.5, 96.7, 13.5, 36.4, 23.4
                        // ]
                    },
                    {
                        name: '2021年12月支出',
                        type: 'line',
                        smooth: true,
                        emphasis: {
                            focus: 'series'
                        },
                        data: yAxisOutcome

                        // [
                        //     3.9, 5.9, 11.1, 18.7, 48.3, 49.2, 81.6, 56.6, 55.4, 18.4, 10.3, 0.7, 10.2, 87.4, 35.4, 87.6, 94.4, 60.4,
                        //     33.8, 49.5, 76.7, 13.5, 36.4, 23.4, 12.3, 16.7, 36.5, 13.4, 26.8, 35.4, 41.6
                        // ]
                    }
                ]
            };
            option && myChart.setOption(option);
        }
    }
};
</script>

<style>
</style>