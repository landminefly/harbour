<script>
export default {
    data() {
        return {
            logisticsFormMetaData:
            {
                //每页行数
                rowCountEachPage: 20,
                //列名
                colName: ['提单号', '货主名称', '货主代码', '物流公司', '集装箱箱号', '货物名称', '启运地', '目的地', '始发时间', '到达时间'],
                //当前页数
                whichPage: 1,
                //总页数
                totalPage: 2,
            },
            //表数据
            logisticsFormData:
                [
                    ['TKNG2924820', '区克', '511824199602123275', '乾宇国际货运代理有限公司', 'YWCM8054', '大豆粉', '苏州港', '铜陵港', '2022-10-05 19:44:49', '2022-10-09 16:44:49'],
                    ['TKNG2924820', '区克', '511824199602123275', '乾宇国际货运代理有限公司', 'YWCM8054', '大豆粉', '苏州港', '铜陵港', '2022-10-05 19:44:49', '2022-10-09 16:44:49'],
                    ['TKNG2924820', '区克', '511824199602123275', '乾宇国际货运代理有限公司', 'YWCM8054', '大豆粉', '苏州港', '铜陵港', '2022-10-05 19:44:49', '2022-10-09 16:44:49'],
                    ['TKNG2924820', '区克', '511824199602123275', '乾宇国际货运代理有限公司', 'YWCM8054', '大豆粉', '苏州港', '铜陵港', '2022-10-05 19:44:49', '2022-10-09 16:44:49'],
                    ['TKNG2924820', '区克', '511824199602123275', '乾宇国际货运代理有限公司', 'YWCM8054', '大豆粉', '苏州港', '铜陵港', '2022-10-05 19:44:49', '2022-10-09 16:44:49'],
                ],
            //筛选器数据
            filterData: [
                null,
                null,
                null,
                null,
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
    <div id="logistics-data-wrapper">
        <!-- 标题 -->
        <div id="logistics-data-title">物流信息查看</div>

        <!-- 筛选器wrapper -->
        <div id="filter-wrapper"
            :style="{ backgroundColor: darkModeColor.filterBack, borderColor: darkModeColor.filterBorder }">
            <div id="filter-title">
                筛选器
            </div>
            <div id="form-wrapper">
                <div class="form-item">
                    <div class="item-title">提单号</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[0]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">货主名称</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[1]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">货主代码</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[2]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">物流公司</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[3]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">集装箱箱号</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[4]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">货物名称</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[5]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">启运地</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[6]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">目的地</div>
                    <n-space vertical>
                        <n-input class="input" placeholder="请输入" v-model:value="filterData[7]" type="text" />
                    </n-space>
                </div>
                <div class="form-item">
                    <div class="item-title">时间范围</div>
                    <n-space vertical>
                        <n-date-picker class="time" v-model:value="filterData[8]" type="datetimerange" clearable
                            start-placeholder="开始时间" end-placeholder="结束时间" />
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
        <div id="logistics-form-wrapper">
            <table id="logistics-form">
                <!-- 表头 -->
                <tr>
                    <td v-for="colName in logisticsFormMetaData.colName" :style="{ borderColor: darkModeColor.border }">
                        {{ colName }}
                    </td>
                </tr>

                <!-- 表格内容 -->
                <tr v-for="(row, rowIndex) in logisticsFormData">

                    <td v-for="(col, colIndex) in row" :style="{ borderColor: darkModeColor.border }">{{
                        this.logisticsFormData[rowIndex][colIndex] }}
                    </td>

                </tr>
            </table>

            <!-- 表格功能按钮wrapper -->
            <div id="form-btns-wrapper">

                <div id="page-wrapper">
                    <span class="iconfont icon-fanye1" @click="lastPage"></span>
                    <span>{{ logisticsFormMetaData.whichPage }} / {{ logisticsFormMetaData.totalPage }} 页</span>
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
#logistics-data-wrapper {
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
#logistics-data-title {
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
#logistics-form {
    font-size: 16px;
    user-select: text;
    table-layout: fixed;
}

#logistics-form td {
    border-top: 1px solid;
    border-bottom: 1px solid;
}

#logistics-form td:not(:last-child) {
    border-right: 1px solid;
}

#logistics-form td {
    text-align: center;
    padding: 12px;
    min-width: 100px;
    white-space: nowrap;
}

#logistics-form tr {
    transition: all 0.2s;
}

#logistics-form tr:first-child {
    font-size: 18px;
    font-weight: 900;
    background-color: rgba(120, 120, 120, 0.3)
}

#logistics-form tr:first-child td {
    padding: 12px;
}

#logistics-form tr:not(:first-child):hover {
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