<script>
import createSourcePopUp from './create-source-pop-up.vue'
import { useMessage } from 'naive-ui'
import axios from 'axios'
export default {
    setup() {
        //这样就能在setup外使用Message组件
        window.$message = useMessage()
    },
    components: {
        createSourcePopUp,
    },
    data() {
        return {
            //查找的Message组件
            messageReactive: null,
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
                rowCountEachPage: 20,
                //列名
                colName: ['数据源类型', '数据源地址'],
                //当前页数
                whichPage: 1,
                //总页数
                totalPage: 1,
            },
            //表数据
            sourceFormData: null,
        }
    },
    methods: {
        //关闭创建弹窗，其中flag表示数据是否添加成功，是由创建弹窗组件传递出来的
        closeCreatePopUp(flag) {
            this.isCreateSourcePopUpShown = false;
            if (flag === true) {
                //添加成功的逻辑...
                window.$message.success('添加成功！', {
                    duration: 2000
                });

                axios({
                    method: "POST",
                    url: "servlet/admin/select-source",
                }).then(value => {
                    this.sourceFormData = value.data;
                }).catch(reason => {
                    this.cusFormMetaData.whichPage = 0;
                    this.cusFormMetaData.totalPage = 0;

                    window.$message.error('服务器错误！', {
                        duration: 2000
                    });
                })
            } else if (flag === false) {
                window.$message.error('添加失败！', {
                    duration: 2000
                });
            }
        },
        //打开创建弹窗
        createSource() {
            this.isCreateSourcePopUpShown = true;
        },
        //删除一行数据
        deleteSource(row, rowIndex) {
            //删除逻辑...

            console.log(row[1])
            axios({
                method: "POST",
                url: "servlet/admin/delete-source",
                data: [row[0],row[1]],
            }).then(value => {
                window.$message.success(
                    '删除成功！'
                )
                axios({
                    method: "POST",
                    url: "servlet/admin/select-source",
                }).then(value => {
                    this.sourceFormData = value.data;
                }).catch(reason => {
                    this.cusFormMetaData.whichPage = 0;
                    this.cusFormMetaData.totalPage = 0;

                    window.$message.error('服务器错误！', {
                        duration: 2000
                    });
                })
            }).catch(reason => {
                window.$message.error('删除失败！', {
                    duration: 2000
                });
            })
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
        //加载该模块时要先初始化数据
        if (!this.messageReactive) {
            this.messageReactive = window.$message.loading('加载中', {
                duration: 0
            })
        }

        axios({
            method: "POST",
            url: "servlet/admin/select-source",
        }).then(value => {
            this.sourceFormData = value.data;

            this.messageReactive?.destroy();
            this.messageReactive = null;
        }).catch(reason => {
            this.cusFormMetaData.whichPage = 0;
            this.cusFormMetaData.totalPage = 0;
            this.messageReactive?.destroy();
            this.messageReactive = null;

            window.$message.error('服务器错误！', {
                duration: 2000
            });
        })
    },
}
</script>

<template>
    <div id="data-source-management-wrapper">
        <createSourcePopUp :isShown="isCreateSourcePopUpShown" @close="closeCreatePopUp"></createSourcePopUp>
        <!-- 标题 -->
        <div id="data-source-management-title">数据源管理</div>

        <!-- 模块功能按钮wrapper -->
        <div id="btns-wrapper">
            <n-space>
                <n-button id="create-source-btn" @click="createSource" type="success" size="large">创建新数据源</n-button>
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

                            <n-popconfirm @positive-click="deleteSource(row, rowIndex)" negative-text="取消"
                                positive-text="确认">
                                <template #trigger>
                                    <n-button type="error">删除</n-button>
                                </template>
                                确定删除？
                            </n-popconfirm>
                        </div>
                    </td>
                </tr>
            </table>
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
    font-size: 16px;
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
    padding: 8px;
    min-width: 100px;
    white-space: nowrap;
}

#source-form td:last-child,
#source-form td:first-child {
    width: 10%;
}

#source-form tr {
    /* 作为数据管理按钮的包含快 */
    position: relative;
    transition: all 0.2s;
}

#source-form tr:first-child {
    font-size: 18px;
    font-weight: 900;
    background-color: rgba(120, 120, 120, 0.3)
}

#source-form tr:first-child td {
    padding: 12px;
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
</style>