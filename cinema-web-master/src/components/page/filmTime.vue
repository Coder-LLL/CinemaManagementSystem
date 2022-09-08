<template>
    <div id="charts" :style="{ width: '100%', height: '100%' }">
        <div id="main" :style="{ width: '100%', height: '50%' }"></div>
        <div class="ccc">
            <el-card shadow="hover" style="height: 100%">
                <div class="clearfix">
                    <span>电影场次</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="handleInsertMovieTime">添加</el-button>
                </div>
                <el-table
                    :data="filmTimeData"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                    @selection-change="handleSelectionChange"
                >
                    <el-table-column type="selection" width="55" align="center"></el-table-column>
                    <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                    <el-table-column prop="name" label="电影名"></el-table-column>
                    <el-table-column prop="startTime" label="开始时间"></el-table-column>
                    <el-table-column prop="endTime" label="结束时间"></el-table-column>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑 </el-button>
                            <!--点击时传递当前行索引和数据给函数，并在函数中赋值给form等数据，
                                并将编辑框显示-->
                            <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)"
                                >删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!--添加电影票对话框-->
                <el-dialog title="添加" :visible.sync="insertVisible" width="30%">
                    <el-form ref="insertForm" :model="insertForm" :rules="insertFormRules" label-width="100px">
                        <el-form-item label="电影名" prop="name">
                            <el-select v-model="insertForm.name" placeholder="请选择">
                                <el-option v-for="item in movieAllDataName" :key="item.value" :label="item" :value="item"> </el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="起始时间" prop="startTime">
                            <el-date-picker
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm"
                                format="yyyy-MM-dd HH:mm"
                                v-model="insertForm.startTime"
                            ></el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间" prop="endTime">
                            <el-date-picker
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm"
                                format="yyyy-MM-dd HH:mm"
                                v-model="insertForm.endTime"
                            ></el-date-picker>
                        </el-form-item>

                        <!-- <el-form-item label="起始时间" prop="startTime">
                            <el-date-picker
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm"
                                format="yyyy-MM-dd HH:mm"
                                v-model="insertForm.startTime"
                            ></el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间" prop="endTime">
                            <el-date-picker
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm"
                                format="yyyy-MM-dd HH:mm"
                                v-model="insertForm.endTime"
                            ></el-date-picker>
                        </el-form-item> -->
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="insertVisible = false">取 消</el-button>
                        <el-button type="primary" @click="saveMovieTime">确 定</el-button>
                    </span>
                </el-dialog>
                <!-- 编辑弹出框，拿到刚刚赋值到form的数据 -->
                <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
                    <el-form ref="filmTime" :model="filmTime" label-width="70px">
                        <el-form-item label="电影名">
                            <el-input readonly v-model="filmTime.name"></el-input>
                        </el-form-item>

                        <el-form-item label="起始时间">
                            <el-date-picker
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm"
                                format="yyyy-MM-dd HH:mm"
                                v-model="filmTime.startTime"
                            ></el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间">
                            <el-date-picker
                                type="datetime"
                                value-format="yyyy-MM-dd HH:mm"
                                format="yyyy-MM-dd HH:mm"
                                v-model="filmTime.endTime"
                            ></el-date-picker>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="editVisible = false">取 消</el-button>
                        <el-button type="primary" @click="saveEdit">确 定</el-button>
                    </span>
                </el-dialog>
                <!--底部的分页区域-->
                <div class="pagination">
                    <el-pagination
                        background
                        layout="total, prev, pager, next"
                        :current-page="query.pageIndex"
                        :page-size="query.pageSize"
                        :total="pageTotal"
                        @current-change="handlePageChange"
                    ></el-pagination>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
import { getAllMovieTime, getMovieTime, insertMovieTime, modifyMovieTime, delMovieTime } from '../../api/filmTime';
import { movieData } from '../../api/index';
var echarts = require('echarts');
export default {
    data() {
        return {
            query: {
                pageIndex: 1,
                pageSize: 4
            },
            filmTimeAllData: [],
            filmTimeData: [],
            filmTime: '',
            filmNameList: [],
            barList: [],
            chartsData: [],
            barColorList: [],
            colorList: [],
            minTime: '',
            filmIndex: {}, // 因为请求到的数据中每个电影没有一个固定的id（因为有些电影有很多场，id会有很多歌），所以为每个电影生成一个对应的序列id
            pageTotal: 20,
            editVisible: false,
            insertForm: {},
            insertFormRules: {
                name: [{ required: true, message: '请选择活动区域', trigger: 'change' }],
                startTime: [{ required: true, message: '请选择时间', trigger: 'change' }],
                endTime: [{ required: true, message: '请选择时间', trigger: 'change' }]
            },
            editVisible: false,
            insertVisible: false,
            movieAllData: [],
            movieAllDataName: []
        };
    },
    created() {
        this.getMovieTimeData();
    },
    mounted() {
        this.openNotify();
    },

    methods: {
        openNotify() {
            this.$notify({
                title: '提示',
                message: '由于定义的是12月25日的电影场次数据，所以在添加场次时日期请选择2021年12月25日',
                duration: 5000
            });
        },
        getMovieTimeData() {
            // 请求电影场次信息
            getAllMovieTime().then((res) => {
                if (res.status == 200) {
                    //弹出过渡消失的成功！消息提示
                    // 保存请求到的数据
                    this.filmTimeAllData = res.data;
                    this.initChartData();
                    this.initIncomeChart();
                } else {
                    this.$message.error('电影场次信息获取失败，服务器出现故障');
                }
            });

            getMovieTime(this.query).then((res) => {
                if (res.status == 200) {
                    this.pageTotal = res.data.pageTotal || 20;
                    this.filmTimeData = res.data.list;
                } else {
                    this.$message.error('电影场次信息获取失败，服务器出现故障');
                }
            });

            // 拿到所有的电影信息，包括没安排场次的
            let _query = {
                pageIndex: this.query.pageIndex,
                pageSize: 0
            };
            movieData(_query).then((res) => {
                this.movieAllData = res.data.list;
                this.movieAllDataName = this.movieAllData.map(function (item, index) {
                    return item.name;
                });
            });
        },
        initChartData() {
            // 初始化起始时间（即最小时间）
            let minTimeList = this.filmTimeAllData[0].startTime.split(' ');
            this.minTime = minTimeList[0] + ' 0:00';

            // 初始化电影名列表信息。先用一个数组装，然后再利用Set集合数据类型去重
            let filmNameListArray = this.filmTimeAllData.map(function (item, index) {
                return item.name;
            });
            let filmNameList = [...new Set(filmNameListArray)];
            this.filmNameList = filmNameList;

            // 初始化图表底部的圆柱体信息
            this.barList = this.filmNameList.map(function (item, index) {
                let barListItem = {
                    name: item,
                    type: 'bar',
                    data: []
                };
                return barListItem;
            });

            // 初始化图表的圆柱体颜色信息
            let colorList = [];
            for (let i = 0; i < this.filmNameList.length; i++) {
                // 随机颜色算法
                var hex = Math.floor(Math.random() * 16777216).toString(16); //生成ffffff以内16进制数
                while (hex.length < 6) {
                    //while循环判断hex位数，少于6位前面加0凑够6位
                    hex = '0' + hex;
                }
                var color = '#' + hex; //返回‘#'开头16进制颜色
                colorList.push(color);
            }
            this.colorList = colorList;

            // 初始化图表内的数据信息
            this.chartsData = this.filmTimeAllData.map(function (item, index) {
                // 找出该电影名在filmNameList中的下标，因为filmNameList中每个元素对应一个颜色
                var i = filmNameList.length;
                while (i--) {
                    if (filmNameList[i] === item['name']) {
                        break;
                    }
                }
                let chartsDataItem = {
                    itemStyle: { normal: { color: colorList[i] } }, // 条形颜色,从刚刚定义的颜色数组中取
                    name: item.name,
                    value: [i, item.startTime, item.endTime] //0,1,2......代表y轴的索引，后两位代表x轴数据开始和结束
                };
                return chartsDataItem;
            });
        },
        initIncomeChart() {
            var chartDom = document.getElementById('main');
            var myChart = echarts.init(chartDom);
            var state = this.filmNameList; //三种状态
            // let barList = [
            //     { name: state[0], type: 'bar', data: [] },
            //     { name: state[1], type: 'bar', data: [] },
            //     { name: state[2], type: 'bar', data: [] }
            // ];

            // 注入y轴的信息
            let filmNameList = this.filmNameList;
            // 注入起始时间（即最早时间）
            let minTime = this.minTime;
            // 注入图表底部的圆柱体信息
            let barList = this.barList;
            // 注入图表内的圆柱体信息
            let chartsData = this.chartsData;
            // 注入图表圆柱体的颜色信息
            let colorList = this.colorList;

            // echart配置
            var option = {
                color: colorList,
                tooltip: {
                    //提示框
                    formatter: function (params) {
                        return params.name + ':' + params.value[1] + '~' + params.value[2];
                    } //数据的值
                },
                legend: {
                    //图例
                    data: state,
                    bottom: '1%',
                    selectedMode: false, // 图例设为不可点击
                    textStyle: {
                        color: '#000'
                    }
                },
                grid: {
                    //绘图网格
                    left: '3%',
                    right: '3%',
                    top: '1%',
                    bottom: '10%',
                    containLabel: true
                },
                xAxis: {
                    type: 'time',
                    interval: 3600 * 1000, //以一个小时递增
                    min: minTime, //将data里最小时间的整点时间设为min,否则min会以data里面的min为开始进行整点递增
                    axisLabel: {
                        formatter: function (value) {
                            var date = new Date(value);
                            return getzf(date.getHours()) + ':00';
                            function getzf(num) {
                                if (parseInt(num) < 10) {
                                    num = '0' + num;
                                }
                                return num;
                            }
                        }
                    },
                    axisLine: {
                        onZero: true,
                        lineStyle: {
                            color: 'blue'
                        }
                    }
                },
                yAxis: {
                    data: filmNameList
                },
                series: [
                    // 用空bar来显示三个图例
                    // { name: state[0], type: 'bar', data: [] },
                    // { name: state[1], type: 'bar', data: [] },
                    // { name: state[2], type: 'bar', data: [] },
                    ...barList,
                    {
                        type: 'custom',
                        renderItem: function (params, api) {
                            //开发者自定义的图形元素渲染逻辑，是通过书写 renderItem 函数实现的
                            var categoryIndex = api.value(0); //这里使用 api.value(0) 取出当前 dataItem 中第一个维度的数值。
                            var start = api.coord([api.value(1), categoryIndex]); // 这里使用 api.coord(...) 将数值在当前坐标系中转换成为屏幕上的点的像素值。
                            var end = api.coord([api.value(2), categoryIndex]);
                            var height = api.size([0, 1])[1];

                            return {
                                type: 'rect', // 表示这个图形元素是矩形。还可以是 'circle', 'sector', 'polygon' 等等。
                                shape: echarts.graphic.clipRectByRect(
                                    {
                                        // 矩形的位置和大小。
                                        x: start[0],
                                        y: start[1] - height / 2,
                                        width: end[0] - start[0],
                                        height: height
                                    },
                                    {
                                        // 当前坐标系的包围盒。
                                        x: params.coordSys.x,
                                        y: params.coordSys.y,
                                        width: params.coordSys.width,
                                        height: params.coordSys.height
                                    }
                                ),
                                style: api.style()
                            };
                        },
                        encode: {
                            x: [1, 2], // data 中『维度1』和『维度2』对应到 X 轴
                            y: 0 // data 中『维度0』对应到 Y 轴
                        },
                        data: chartsData
                        // [
                        //     // 维度0 维度1 维度2
                        //     ({
                        //         itemStyle: { normal: { color: 'red' } }, //条形颜色
                        //         name: '龙争虎斗',
                        //         value: [0, '2021/12/25 1:28', '2021/12/25 5:00'] //0,1,2代表y轴的索引，后两位代表x轴数据开始和结束
                        //     },
                        //     {
                        //         itemStyle: { normal: { color: 'red' } },
                        //         name: '雪国列车',
                        //         value: [0, '2021/12/25 6:13', '2021/12/25 8:22']
                        //     },
                        //     {
                        //         itemStyle: { normal: { color: 'red' } },
                        //         name: '龙争虎斗',
                        //         value: [1, '2021/12/25 5:00', '2021/12/25 6:13']
                        //     },
                        //     {
                        //         itemStyle: { normal: { color: 'red' } },
                        //         name: '龙争虎斗',
                        //         value: [1, '2021/12/25 8:22', '2021/12/25 9:10']
                        //     },
                        //     {
                        //         itemStyle: { normal: { color: 'red' } },
                        //         name: '龙争虎斗',
                        //         value: [1, '2021/12/25 12:47', '2021/12/25 14:52']
                        //     },
                        //     {
                        //         itemStyle: { normal: { color: 'red' } },
                        //         name: '龙争虎斗',
                        //         value: [2, '2021/12/25  9:10', '2021/12/25  12:47']
                        //     },
                        //     {
                        //         itemStyle: { normal: { color: 'red' } },
                        //         name: '龙争虎斗',
                        //         value: [2, '2021/12/25 14:52', '2021/12/25 17:00']
                        //     })
                        // ]
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        // 多选操作，//val 为选中数据的集合
        handleSelectionChange(val) {
            this.multipleSelection = val;
            /*
                可以通过以下方式来获取val中的值
                for(var i = 0;i<val.length;i++){
                alert(val[i].name);
                }*/
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    delMovieTime(row).then((res) => {
                        console.log(res);
                        if (res.status == 200) {
                            //弹出过渡消失的成功！消息提示
                            this.$message.success('删除成功');
                            /*splice(index,len,[item])它也可以用来替换/删除/添加数组内某一个或者几个值（该方法会改变原始数组）
                                  index:数组开始下标
                                  len: 替换/删除的长度
                                  item:替换的值，删除操作的话 item为空*/
                            //this.movieData.splice(index, 1);
                            this.getMovieTimeData();
                        } else {
                            this.$message.error('删除失败，服务器出现故障');
                        }
                    });
                })
                .catch(() => {}); //异常捕捉
        },
        // 编辑操作，
        handleEdit(index, row) {
            this.idx = index;
            this.filmTime = row;
            console.log(this.filmTime);
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            console.log(this.filmTime);
            modifyMovieTime(this.filmTime).then((res) => {
                if (res.status == 200) {
                    this.editVisible = false;
                    this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                    this.getMovieTimeData();
                } else {
                    this.$message.error('修改失败，服务端出现错误');
                }
            });
        },
        // 分页导航
        handlePageChange(val) {
            let i;
            this.$set(this.query, 'pageIndex', val);
            //在这里判断，并且清空并插入到数组
            this.filmTimeData = [];
            let start = (this.query.pageIndex - 1) * this.query.pageSize;
            let end = this.query.pageSize * this.query.pageIndex;
            if (this.pageTotal < end) {
                end = this.pageTotal;
            }
            for (i = start; i < end; i++) {
                this.filmTimeData.push(this.filmTimeAllData[i]);
            }
        },
        handleInsertMovieTime() {
            this.insertForm = {};
            this.insertVisible = true;
        },
        saveMovieTime() {
            this.$refs['insertForm'].validate((valid) => {
                console.log(this.insertForm);
                if (valid) {
                    insertMovieTime(this.insertForm).then((res) => {
                        if (res.status == 200) {
                            this.insertVisible = false;
                            this.$message.success(`添加成功`);
                            this.getMovieTimeData();
                        } else {
                            this.insertVisible = false;
                            this.$message.success(`添加失败，服务端出现问题`);
                        }
                    });
                } else {
                    this.$message.error(`数据校验失败`);
                    return false;
                }
            });
        }
    }
};
</script>

<style scoped>
.ccc {
    width: 100%;
    height: 50%;
}
.clearfix {
    height: 30px;
}
.el-table {
    height: calc(100% -30px);
}
</style>