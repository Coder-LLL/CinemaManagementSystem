<template>
    <div class="container">
        <el-card shadow="hover" style="height: 100%">
            <div slot="header" class="clearfix">
                <span>电影票</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="handleInsertMovie">添加</el-button>
            </div>
            <el-table
                :data="movieData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="movieId" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="电影名"></el-table-column>
                <el-table-column prop="price" label="价格" width="55"></el-table-column>
                <el-table-column prop="time" label="时间"></el-table-column>
                <el-table-column prop="integral" label="积分" width="55"></el-table-column>
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
            <!-- 编辑弹出框，拿到刚刚赋值到form的数据 -->
            <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
                <el-form ref="form" :model="form" label-width="70px">
                    <el-form-item label="电影名">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="价格">
                        <el-input v-model="form.price"></el-input>
                    </el-form-item>
                    <el-form-item label="积分">
                        <el-input v-model="form.integral"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
            </el-dialog>
            <!--添加电影票对话框-->
            <el-dialog title="添加" :visible.sync="insertVisible" width="30%">
                <el-form ref="insertForm" :model="insertForm" :rules="insertFormRules" label-width="70px">
                    <el-form-item label="电影名" prop="name">
                        <el-input v-model="insertForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="价格" prop="price">
                        <el-input type="number" v-model="insertForm.price"></el-input>
                    </el-form-item>
                    <el-form-item label="积分" prop="integral">
                        <el-input type="number" v-model="insertForm.integral"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="insertVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveMovie">确 定</el-button>
                </span>
            </el-dialog>
        </el-card>
    </div>
</template>

<script>
import bus from '../common/bus';
import { movieData } from '../../api/index';
import { delMovie } from '../../api/index';
import { modifyMovie } from '../../api/index';
import { insertMovie } from '../../api/index';
export default {
    data() {
        var checkInsertFormPrice = (rule, value, callback) => {
            console.log(value);
            if (!value) {
                return callback(new Error('价格不能为空'));
            }
            if (value >= 0) {
                callback();
            } else {
                callback(new Error('价格不能小于0'));
            }
        };

        var checkInsertFormIntegral = (rule, value, callback) => {
            console.log(value);
            if (!value) {
                return callback(new Error('积分不能为空'));
            }
            if (value >= 0) {
                callback();
            } else {
                callback(new Error('积分不能小于0'));
            }
        };
        return {
            query: {
                pageIndex: 1,
                pageSize: 7,
                movieId: '',
                managerId: localStorage.getItem('managerId')
            },
            movieData: [],
            movieAllData: [],
            pageTotal: 0,
            insertForm: {},
            insertFormRules: {
                name: [{ required: true, message: '请输入活动名称', trigger: 'blur' }],
                price: [
                    { required: true, message: '请输入活动名称', trigger: 'blur' },
                    {
                        validator: checkInsertFormPrice, // 自定义验证
                        trigger: 'blur'
                    }
                ],
                integral: [
                    { required: true, message: '请输入活动名称', trigger: 'blur' },
                    {
                        validator: checkInsertFormIntegral, // 自定义验证
                        trigger: 'blur'
                    }
                ]
            },
            editVisible: false,
            insertVisible: false,
            form: {}
        };
    },
    created() {
        this.getMovieData();
        bus.$on('movie-change', () => {
            this.getStatisticData();
            this.getDealData();
        });
    },
    methods: {
        /*            changeDate() {
                const now = new Date().getTime();
                /!*                this.data.forEach((item, index) => {
                    const date = new Date(now - (6 - index) * 86400000);
                    item.name = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
                });*!/
            },*/
        getSecond() {
            let second = new Date(new Date() - new Date(this.managerData.time)).getSeconds();
            if (second < 10) {
                return '0' + second;
            } else {
                return second;
            }
        },
        getHour() {
            let hour = new Date(new Date() - new Date(this.managerData.time)).getHours() - 8;
            if (hour > 0) {
                return hour + ':';
            } else {
                return '';
            }
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    delMovie(row).then((res) => {
                        if (res.status == 200) {
                            //弹出过渡消失的成功！消息提示
                            this.$message.success('删除成功');
                            /*splice(index,len,[item])它也可以用来替换/删除/添加数组内某一个或者几个值（该方法会改变原始数组）
                                  index:数组开始下标
                                  len: 替换/删除的长度
                                  item:替换的值，删除操作的话 item为空*/
                            //this.movieData.splice(index, 1);
                            this.getMovieData();
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
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            modifyMovie(this.form).then((res) => {
                if (res.status == 200) {
                    this.editVisible = false;
                    this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                    //this.$set(this.movieData, this.idx, this.form);
                    this.getMovieData();
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
            this.movieData = [];
            let start = (this.query.pageIndex - 1) * this.query.pageSize;
            let end = this.query.pageSize * this.query.pageIndex;
            if (this.pageTotal < end) {
                end = this.pageTotal;
            }
            for (i = start; i < end; i++) {
                this.movieData.push(this.movieAllData[i]);
            }
        },
        /*为了将所有movie的数据传递给card组件使用，直接从服务器数据库中查所有movie数据，
         * 然后在接收函数位置手动对接收数据进行分页显示*/
        getMovieData() {
            let _query = {
                pageIndex: this.query.pageIndex,
                pageSize: 0
            };
            movieData(_query).then((res) => {
                this.movieData = [];
                this.movieAllData = res.data.list;
                this.pageTotal = res.data.pageTotal || 20;
                let i;
                let start = (this.query.pageIndex - 1) * this.query.pageSize;
                let end = this.query.pageSize * this.query.pageIndex;
                if (this.pageTotal < end) {
                    end = this.pageTotal;
                }
                for (i = start; i < end; i++) {
                    this.movieData.push(this.movieAllData[i]);
                }
                localStorage.setItem('movieData', JSON.stringify(this.movieAllData));
                bus.$emit('movieData-change');
            });
        },
        getManagerData() {
            managerData(this.query).then((res) => {
                this.managerData = res.data;
                //得到当前已工作时间
                this.workTime = //修改数据date
                    this.getHour() + new Date(new Date() - new Date(this.managerData.time)).getMinutes() + ':' + this.getSecond();
            });
        },
        getDealData() {
            dealData().then((res) => {
                this.dealData = res;
            });
        },
        getStatisticData() {
            statisticData().then((res) => {
                this.statisticData = res.data;
            });
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
        handleInsertMovie() {
            this.insertForm = {};
            this.insertVisible = true;
        },
        saveMovie() {
            this.$refs['insertForm'].validate((valid) => {
                if (valid) {
                    insertMovie(this.insertForm).then((res) => {
                        console.log(res);
                        if (res.status == 200) {
                            this.insertVisible = false;
                            this.$message.success(`添加成功`);
                            this.getMovieData();
                        } else {
                            this.insertVisible = false;
                            this.$message.error(`添加失败，服务端出现问题`);
                        }
                    });
                } else {
                    this.$message.error(`添加失败，`);
                    return false;
                }
            });
        },
        getDealData() {
            requestDealData().then((res) => {
                if (res.status == 200) {
                    this.dealData = res.data;
                }
            });
        }
    }
};
</script>

<style scoped>
.container {
    width: 100%;
    height: 100%;
    padding: 0;
    border: 0;
}
</style>