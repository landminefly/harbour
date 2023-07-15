<script>
import { useMessage } from 'naive-ui'
import axios from 'axios'
export default {
    setup() {
        //这样就能在setup外使用Message组件
        window.$message = useMessage()
    },
    data() {
        return {
            //查找的Message组件
            messageReactive: null,
            companyFormMetaData:
            {
                //每页行数
                rowCountEachPage: 20,
                //列名
                colName: ['公司名称', '客户编号', '联系人', '电话', '省市区'],
                //当前页数
                whichPage: 0,
                //总页数
                totalPage: 0,
            },
            //表数据
            companyFormData: null,
            //筛选器数据，专门给查询按钮使用
            filterData: [
                null,
                null,
                null,
                null,
                null,
            ],
            //上一次点击查询时的筛选器数据，专门给上一页、下一页、刷新按钮使用
            filterDataForPage: null,
            //存储黑夜模式各个布局的颜色
            darkModeColor: {
                border: null,
                filterBack: null,
                filterBorder: null,
            },
        }
    },
    methods: {
        //向后端发送查找请求
        seekData() {
            //先将空字符串转为null
            for (var i = 0; i < this.filterData.length; i++) {
                var item = this.filterData[i];
                if (typeof (item) === "string" && (item = item.trim()) === '') {
                    this.filterData[i] = null;
                }
            }
            //查找前，先创建一个message，显示查找中
            if (!this.messageReactive) {
                this.messageReactive = window.$message.loading('查找中', {
                    duration: 0
                })
            }

            //查数据前先禁止查找按钮的点击事件，防止在查出数据前用户再次点击，造成混乱
            this.$refs.seekBtn.style.pointerEvents = "none";
            //更新filterDataForPage
            this.filterDataForPage = [...this.filterData];
            //查找数据...
            axios({
                method: "POST",
                url: "servlet/view/company",
                data: {
                    originArgs: this.filterData,
                    pageNum: 1,
                }
            }).then(value => {
                var length = value.data.length;
                //更新数据
                this.companyFormData = value.data.slice(0, length - 1);
                //更新页数
                this.companyFormMetaData.totalPage = Math.ceil(Number(value.data[length - 1][0]) / 20);
                //更新whichPage值
                this.companyFormMetaData.whichPage = 1;
                //恢复查找的点击事件
                this.$refs.seekBtn.style.pointerEvents = "";

                //更新message信息
                if (Number(value.data[length - 1][0] == 0)) {
                    this.companyFormMetaData.whichPage = 0;
                    this.messageReactive.content = `没有找到符合条件的数据！`;
                    this.messageReactive.type = "error";
                } else {
                    this.messageReactive.content = `共找到${Number(value.data[length - 1][0])}条数据`;
                    this.messageReactive.type = "success"
                }
                setTimeout(() => {
                    this.messageReactive?.destroy();
                    this.messageReactive = null;
                }, 1000)

            }).catch(reason => {
                this.companyFormMetaData.whichPage = 0;
                this.companyFormMetaData.totalPage = 0;
                this.messageReactive?.destroy();
                this.messageReactive = null;

                window.$message.error('服务器错误！', {
                    duration: 2000
                });
            })
        },
        clearData() {
            this.filterData.forEach((item, index, arr) => {
                arr[index] = null;
            })
        }
    },
    mounted() {
        //加载该模块时要先初始化数据
        if (!this.messageReactive) {
            this.messageReactive = window.$message.loading('加载中', {
                duration: 0
            })
        }
        //初始化filterDataForPage
        this.filterDataForPage = [...this.filterData];
        axios({
            method: "POST",
            url: "servlet/view/company",
            data: {
                originArgs: this.filterData,
                pageNum: 1,
            }
        }).then(value => {
            var length = value.data.length;
            //初始化数据
            this.companyFormData = value.data.slice(0, length - 1);
            //初始化页数
            this.companyFormMetaData.totalPage = Math.ceil(Number(value.data[length - 1][0]) / 20);
            //更新whichPage值
            this.companyFormMetaData.whichPage = 1;

            this.messageReactive?.destroy();
            this.messageReactive = null;
        }).catch(reason => {
            this.companyFormMetaData.whichPage = 0;
            this.companyFormMetaData.totalPage = 0;
            this.messageReactive?.destroy();
            this.messageReactive = null;

            window.$message.error('服务器错误！', {
                duration: 2000
            });
        })
    },
    watch: {
        //更改黑夜模式
        '$store.state.isDarkMode': {
            handler(newValue) {
                this.darkModeColor.border = newValue ? 'rgba(255, 255, 255,0.3)' : 'rgba(0, 0, 0, 0.3)';
                this.darkModeColor.filterBack = newValue ? '#18181c' : '#eef0f2';
                this.darkModeColor.filterBorder = newValue ? '#2d2d30' : '#ced0d2';
            },
            //页面首次加载时初始化
            immediate: true
        },
        'companyFormMetaData.whichPage'() {
            //查找数据...
            axios({
                method: "POST",
                url: "servlet/view/company",
                data: {
                    originArgs: this.filterDataForPage,
                    pageNum: this.companyFormMetaData.whichPage,
                }
            }).then(value => {
                var length = value.data.length;
                //处理数据
                this.companyFormData = value.data.slice(0, length - 1);
                //处理页数
                this.companyFormMetaData.totalPage = Math.ceil(Number(value.data[length - 1][0]) / 20);

            }).catch(reason => {
                this.companyFormMetaData.whichPage = 0;
                this.companyFormMetaData.totalPage = 0;
                this.messageReactive?.destroy();
                this.messageReactive = null;
                
                window.$message.error('服务器错误！', {
                    duration: 2000
                });
            })
        }
    },
}
</script>

<template>
    <div id="company-data-wrapper">
        <!-- 标题 -->
        <div id="company-data-title">物流公司信息查看</div>

        <!-- 筛选器wrapper -->
        <div id="filter-wrapper"
            :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
            <div id="filter-title">
                筛选器
            </div>
            <div id="form-wrapper">
                <div class="form-item">
                    <div class="item-title">公司名称</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[0]" type="text" clearable />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">客户编号</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[1]" type="text" clearable />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">联系人</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[2]" type="text" clearable />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">电话</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[3]" type="text" clearable />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">省市区</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[4]" type="text" clearable />
                    </n-space>
                </div>
            </div>

            <div id="submit-btns" ref="seekBtn">
                <n-button type="error" size="large" @click="clearData" style="margin:0 30px;">
                    清空
                </n-button>
                <n-button type="success" size="large" @click="seekData" style="margin:0 10px;">
                    查找
                </n-button>
            </div>

        </div>

        <!-- 表格wrapper -->
        <div id="company-form-wrapper">
            <table id="company-form">
                <!-- 表头 -->
                <tr>
                    <td v-for="colName in companyFormMetaData.colName" :style="{ borderColor: darkModeColor.border }">
                        {{ colName }}
                    </td>
                </tr>

                <!-- 表格内容 -->
                <tr v-for="(row, rowIndex) in companyFormData">

                    <td v-for="(col, colIndex) in row" :style="{ borderColor: darkModeColor.border }">{{
                        this.companyFormData[rowIndex][colIndex] }}
                    </td>

                </tr>
            </table>

            <!-- 表格功能按钮wrapper -->
            <div id="form-btns-wrapper">
                <n-pagination v-model:page="companyFormMetaData.whichPage" :page-count="companyFormMetaData.totalPage"
                    size="large" show-quick-jumper>
                    <template #goto>
                        跳转至
                    </template>
                </n-pagination>
            </div>
        </div>
    </div>
</template>

<style>
/* 模块整体样式 */
#company-data-wrapper {
    height: 100%;
    width: 100%;
    font-size: 18px;
    overflow: auto;
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: left;
    /* 阻止外边距折叠 */
    float: left;
}

/* 标题样式 */
#company-data-title {
    font-size: 30px;
    font-weight: 900;
    margin: 20px 0;
    text-align: center;
}

/* 筛选器样式 */
#filter-wrapper {
    box-sizing: border-box;
    width: 90%;
    margin: 30px 5%;
    border-radius: 20px;
    border: 1px solid;
}

#filter-title {
    font-size: 20px;
    font-weight: 900;
    margin: 20px 20px 0 20px;
}

/* 筛选器表单wrapper */
#form-wrapper {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    flex-wrap: wrap;
}

.form-item {
    width: 300px;
    display: flex;
    flex-direction: row;
    justify-content: end;
    align-items: center;
    margin: 20px;
}

/* 时间、输入表单样式 */
.form-item .time,
.form-item .input {
    width: 200px;
}

/* 表单标题样式 */
.form-item .item-title {
    font-size: 16px;
    margin-right: 10px;
    text-align: center;
}

/* 提交按钮区样式 */
#submit-btns {
    position: relative;
    margin: 10px;
    left: 50%;
    width: 0;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
}

/* 表格样式 */
#company-form {
    font-size: 16px;
    user-select: text;
    width: 100%;
}

#company-form td {
    border-top: 1px solid;
    border-bottom: 1px solid;
}

#company-form td:not(:last-child) {
    border-right: 1px solid;
}

#company-form td {
    text-align: center;
    padding: 12px;
    min-width: 100px;
    white-space: nowrap;
}

#company-form tr {
    transition: all 0.2s;
}

#company-form tr:first-child {
    font-size: 18px;
    font-weight: 900;
    background-color: rgba(120, 120, 120, 0.3)
}

#company-form tr:first-child td {
    padding: 12px;
}

#company-form tr:not(:first-child):hover {
    background-color: rgba(180, 180, 180, 0.2)
}

/* 表格功能按钮样式 */
#form-btns-wrapper {
    height: 40px;
    margin: 20px 20px;
    display: flex;
    flex-direction: row;
    justify-content: start;
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