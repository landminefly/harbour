<script>
export default {
    data() {
        return {
            cusFormMetaData:
            {
                //每页行数
                rowCountEachPage: 20,
                //列名
                colName: ['客户名称', '客户编号', '手机号', '省市区'],
                //当前页数
                whichPage: 1,
                //总页数
                totalPage: 2,
            },
            //表数据
            cusFormData:
                [
                    ['区克', '511824199602123275', '14790823638', '江苏省宿迁市'],
                    ['区克', '511824199602123275', '14790823638', '江苏省宿迁市'],
                    ['区克', '511824199602123275', '14790823638', '江苏省宿迁市'],
                    ['区克', '511824199602123275', '14790823638', '江苏省宿迁市'],
                    ['区克', '511824199602123275', '14790823638', '江苏省宿迁市'],
                ],
            //筛选器数据
            filterData: [
                null,
                null,
                null,
                null,
            ],
            //存储黑夜模式各个布局的颜色
            darkModeColor: {
                border: null,
                filterBack: null,
                filterBorder: null,
            },
        }
    },
    methods: {
        lastPage() {
            //返回上一页数据
        },
        nextPage() {
            //返回下一页数据
        },
        flush() {
            //刷新数据
        },
        //向后端发送查找请求
        seekData() {
            //先将空字符串转为null
            for (var i = 0; i < this.filterData.length; i++) {
                var item = this.filterData[i];
                if (typeof (item) === "string" && (item = item.trim()) === '') {
                    this.filterData[i] = null;
                }
            }
            //查找数据...
        }
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
    },
}
</script>

<template>
    <div id="cus-data-wrapper">
        <!-- 标题 -->
        <div id="cus-data-title">客户信息查看</div>

        <!-- 筛选器wrapper -->
        <div id="filter-wrapper"
            :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
            <div id="filter-title">
                筛选器
            </div>
            <div id="form-wrapper">
                <div class="form-item">
                    <div class="item-title">客户名称</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[0]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">客户编号</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[1]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">手机号</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[2]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">省市区</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[3]" type="text" />
                    </n-space>
                </div>
            </div>

            <div id="submit-btn">
                <n-button type="success" size="large" @click="seekData">
                    查找
                </n-button>
            </div>

        </div>

        <!-- 表格wrapper -->
        <div id="cus-form-wrapper">
            <table id="cus-form">
                <!-- 表头 -->
                <tr>
                    <td v-for="colName in cusFormMetaData.colName" :style="{ borderColor: darkModeColor.border }">
                        {{ colName }}
                    </td>
                </tr>

                <!-- 表格内容 -->
                <tr v-for="(row, rowIndex) in cusFormData">

                    <td v-for="(col, colIndex) in row" :style="{ borderColor: darkModeColor.border }">{{
                        this.cusFormData[rowIndex][colIndex] }}
                    </td>

                </tr>
            </table>

            <!-- 表格功能按钮wrapper -->
            <div id="form-btns-wrapper">

                <div id="page-wrapper">
                    <span class="iconfont icon-fanye1" @click="lastPage"></span>
                    <span>{{ cusFormMetaData.whichPage }} / {{ cusFormMetaData.totalPage }} 页</span>
                    <span class="iconfont icon-fanye" @click="nextPage"></span>
                </div>

                <div id="flush-wrapper">
                    <span class="iconfont icon-shuaxin" @click="flush"> 刷新</span>
                </div>

            </div>
        </div>
    </div>
</template>

<style>
/* 模块整体样式 */
#cus-data-wrapper {
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
#cus-data-title {
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

/* 提交按钮样式 */
#submit-btn {
    position: relative;
    margin: 10px;
    left: 50%;
    width: 0;
}

/* 表格样式 */
#cus-form {
    font-size: 16px;
    user-select: text;
    width: 100%;
    table-layout: fixed;
}

#cus-form td {
    border-top: 1px solid;
    border-bottom: 1px solid;
}

#cus-form td:not(:last-child) {
    border-right: 1px solid;
}

#cus-form td {
    text-align: center;
    padding: 12px;
    min-width: 100px;
    white-space: nowrap;
}

#cus-form tr {
    transition: all 0.2s;
}

#cus-form tr:first-child {
    font-size: 18px;
    font-weight: 900;
    background-color: rgba(120, 120, 120, 0.3)
}

#cus-form tr:first-child td {
    padding: 12px;
}

#cus-form tr:not(:first-child):hover {
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