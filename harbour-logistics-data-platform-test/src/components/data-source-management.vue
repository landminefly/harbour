<script>
import createSourcePopUp from './create-source-pop-up.vue'
import modifySourcePopUp from './modify-source-pop-up.vue'
import { useMessage } from 'naive-ui'
export default {
    setup() {
        //这样就能在setup外使用Message组件
        window.$message = useMessage()
    },
    components: {
        createSourcePopUp,
        modifySourcePopUp,
    },
    data() {
        return {
            //是否显示创建弹窗
            isCreateSourcePopUpShown: false,
            //是否显示修改弹窗
            isModifySourcePopUpShown: false,
            //将要修改的数据传入修改弹窗组件
            dataToBeModified: [],
            //存储黑夜模式各个布局的颜色
            darkModeColor: {
                border: null,
            },
            //行和列的数据放在数组里，因此从0开始
            //页号则从1开始
            sourceFormMetaData:
            {
                //每页行数
                rowCountEachPage: 5,
                //列名
                colName: ['数据源类型', '数据源地址', '自动同步间隔(分钟)'],
                //当前页数
                whichPage: 1,
                //总页数
                totalPage: 2,
            },
            //表数据
            sourceFormData:
                [
                    ['本地上传', 'test1.txt', '/'],
                    ['MySQL数据库', 'localhost:3306', '10'],
                    ['HDFS分布式存储', 'localhost:1145', '8'],
                    ['MinIO分布式存储', 'localhost:5140', '60'],
                    ['本地上传', 'test2.txt', '/'],
                ]
        }
    },
    methods: {
        //关闭创建弹窗，其中flag表示数据是否添加成功，是由创建弹窗组件传递出来的
        closeCreatePopUp(flag) {
            this.isCreateSourcePopUpShown = false;
            if (flag === true) {
                //添加成功的逻辑...
                window.$message.success(
                    '添加成功！'
                )
            } else if (flag === false) {
                //添加失败的逻辑...
            }
        },
        //关闭修改弹窗
        closeModifyPopUp(flag) {
            this.isModifySourcePopUpShown = false;
            if (flag === true) {
                //修改成功的逻辑...
                window.$message.success(
                    '修改成功！'
                )
            } else if (flag === false) {
                //修改失败的逻辑...
            }
        },
        //打开创建弹窗
        createSource() {
            this.isCreateSourcePopUpShown = true;
        },
        //打开修改弹窗，并传入要修改的数据
        modifySource(row) {
            this.dataToBeModified = row;
            this.isModifySourcePopUpShown = true;
        },
        //删除一行数据
        deleteSource(row, rowIndex) {
            //删除逻辑...
            window.$message.success(
                '删除成功！'
            )
        },
        lastPage() {
            //返回上一页数据
        },
        nextPage() {
            //返回下一页数据
        },
        flush() {
            //刷新数据
        },
    },
    watch: {
        //更改黑夜模式
        '$store.state.isDarkMode': {
            handler(newValue) {
                this.darkModeColor.border = newValue ? 'rgba(255, 255, 255,0.3)' : 'rgba(0, 0, 0, 0.3)';
            },
            //页面首次加载时初始化
            immediate: true
        },
    },
    mounted() {
    },
}
</script>

<template>
    <div id="data-source-management-wrapper">
        <createSourcePopUp :isShown="isCreateSourcePopUpShown" @close="closeCreatePopUp"></createSourcePopUp>
        <modifySourcePopUp :isShown="isModifySourcePopUpShown" :data="dataToBeModified" @close="closeModifyPopUp">
        </modifySourcePopUp>
        <!-- 标题 -->
        <div id="data-source-management-title">数据源管理</div>

        <!-- 模块功能按钮wrapper -->
        <div id="btns-wrapper">
            <n-space>
                <n-button id="create-source-btn" @click="createSource()" type="success" size="large">创建新数据源</n-button>
            </n-space>
        </div>

        <!-- 表格wrapper -->
        <div id="source-form-wrapper">
            <table id="source-form">
                <!-- 表头 -->
                <tr>
                    <td v-for="colName in sourceFormMetaData.colName" :style="{ borderColor: darkModeColor.border }">
                        {{ colName }}
                    </td>
                    <td :style="{ borderColor: darkModeColor.border }"></td>
                </tr>

                <!-- 表格内容 -->
                <tr v-for="(row, rowIndex) in sourceFormData">

                    <td v-for="(col, colIndex) in row" :style="{ borderColor: darkModeColor.border }">{{
                        this.sourceFormData[rowIndex][colIndex] }}
                    </td>

                    <td :style="{ borderColor: darkModeColor.border }">
                        <!-- 数据管理按钮wrapper -->
                        <div id="manageBtnsWrapper">
                            <n-button type="warning" @click="modifySource(row)">修改</n-button>

                            <n-popconfirm @positive-click="deleteSource(row, rowIndex)" negative-text="取消"
                                positive-text="确认">
                                <template #trigger>
                                    <n-button type="error">删除</n-button>
                                </template>
                                确定删除？
                            </n-popconfirm>

                            <n-button type="success">同步</n-button>
                        </div>
                    </td>
                </tr>
            </table>

            <!-- 表格功能按钮wrapper -->
            <div id="form-btns-wrapper">

                <div id="flush-wrapper">
                    <span class="iconfont icon-shuaxin" @click="flush"> 刷新</span>
                </div>

                <div id="page-wrapper">
                    <span class="iconfont icon-fanye1" @click="lastPage"></span>
                    <span>{{ sourceFormMetaData.whichPage }} / {{ sourceFormMetaData.totalPage }} 页</span>
                    <span class="iconfont icon-fanye" @click="nextPage"></span>
                </div>

            </div>
        </div>
    </div>
</template>

<style>
/* 模块整体样式 */
#data-source-management-wrapper {
    height: 100%;
    width: 100%;
    font-size: 18px;
    overflow: auto;
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: center;
    /* 阻止外边距折叠 */
    float: left;
}

/* 标题样式 */
#data-source-management-title {
    font-size: 30px;
    font-weight: 900;
    margin: 20px 0;
}

/* 模块功能按钮样式 */
#btns-wrapper {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin: 20px 0;
}

/* 表格样式 */
#source-form {
    font-size: 18px;
    user-select: text;
    width: 100%;
    table-layout: fixed;
}

#source-form td {
    border-top: 1px solid;
    border-bottom: 1px solid;
}

#source-form td:not(:last-child) {
    border-right: 1px solid;
}

#source-form td {
    width: 30%;
    text-align: center;
    padding: 12px;
    min-width: 100px;
    white-space: nowrap;
}

#source-form td:nth-child(3) {
    width: 20%;
}

#source-form td:last-child {
    width: 20%;
}

#source-form tr {
    /* 作为数据管理按钮的包含快 */
    position: relative;
    transition: all 0.2s;
}
#source-form tr:first-child {
    font-size: 20px;
    font-weight: 900;
    background-color: rgba(120, 120, 120, 0.3)
}

#source-form tr:first-child td {
    padding: 18px;
}

#source-form tr:not(:first-child):hover {
    background-color: rgba(180, 180, 180, 0.2)
}

/* 数据管理按钮样式 */
#source-form #manageBtnsWrapper {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
}

/* 表格功能按钮样式 */
#form-btns-wrapper {
    height: 40px;
    margin: 20px 30px;
    display: flex;
    flex-direction: row;
    justify-content: end;
}

#flush-wrapper,
#page-wrapper {
    font-size: 18px;
    height: 40px;
    padding: 3px 13px;
    margin-left: 10px;
    line-height: 40px;
    text-align: center;
    background-color: rgb(128, 128, 128, 0.3);
    border-radius: 10px;
}

#form-btns-wrapper span {
    font-size: 18px;
    padding: 0 2px;
    display: inline-block;
    cursor: pointer;
    transition: all 0.2s;
}

#form-btns-wrapper span:not(:nth-child(2)):hover {
    transform: scale(1.1);
}

#form-btns-wrapper span:not(:nth-child(2)):active {
    transform: scale(0.9);
}
</style>