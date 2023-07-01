<script>
import { useMessage } from 'naive-ui'
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
                colName: ['公司名称', '客户编号', '联系人', '电话','省市区'],
                //当前页数
                whichPage: 1,
                //总页数
                totalPage: 2,
            },
            //表数据
            companyFormData:
                [
                    ['宜昌裕丰国际物流有限公司', '91510403JU739CKEP2','资廷', '17245317381', '内蒙古自治区乌兰察布市'],
                    ['宜昌裕丰国际物流有限公司', '91510403JU739CKEP2','资廷', '17245317381', '内蒙古自治区乌兰察布市'],
                    ['宜昌裕丰国际物流有限公司', '91510403JU739CKEP2','资廷', '17245317381', '内蒙古自治区乌兰察布市'],
                    ['宜昌裕丰国际物流有限公司', '91510403JU739CKEP2','资廷', '17245317381', '内蒙古自治区乌兰察布市'],
                    ['宜昌裕丰国际物流有限公司', '91510403JU739CKEP2','资廷', '17245317381', '内蒙古自治区乌兰察布市'],
                ],
            //筛选器数据
            filterData: [
                null,
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

            //查找前，先创建一个message，显示查找中
            if (!this.messageReactive) {
                this.messageReactive = window.$message.loading('查找中', {
                    duration: 0
                })
            }

            //查找数据...

            //模拟1秒后找到数据，message改为查找成功，并在1秒后销毁
            setTimeout(() => {

                this.messageReactive.content = "查找成功";
                this.messageReactive.type = "success"
                setTimeout(() => {
                    this.messageReactive.destroy();
                    this.messageReactive = null;
                }, 1000)

            }, 1000);

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
                    <div class="item-title">联系人</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[2]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">电话</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[3]" type="text" />
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

                <div id="page-wrapper">
                    <span class="iconfont icon-fanye1" @click="lastPage"></span>
                    <span>{{ companyFormMetaData.whichPage }} / {{ companyFormMetaData.totalPage }} 页</span>
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

/* 提交按钮样式 */
#submit-btn {
    position: relative;
    margin: 10px;
    left: 50%;
    width: 0;
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