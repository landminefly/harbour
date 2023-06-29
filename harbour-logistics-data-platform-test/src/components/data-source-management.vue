<script>
import createSourcePopUp from './create-source-pop-up.vue'
import modifySourcePopUp from './modify-source-pop-up.vue'
export default {
    components: {
        createSourcePopUp,
        modifySourcePopUp,
    },
    data() {
        return {
            isCreateSourcePopUpShown: false,
            isModifySourcePopUpShown: false,
            isManageBtnsShown: false,
            isEnsureToDelete: -1,
            isEnsureToDeleteInterval: null,
            isEnsureToDeleteMessage: '再次点击即可删除',
            dataToBeModified: [],
            darkModeColor: {
                border: null,
            },
            //行和列的数据放在数组里，因此从0开始
            //页号则从1开始
            sourceFormMetaData:
            {
                rowCountEachPage: 5,
                colName: ['第一列', '第二列'],
                whichPage: 1,
                totalPage: 2,
                formHeight: null,
                formWidth: null,
            },
            sourceFormData:
                [
                    [0, 'test'],
                    [1, 'test test test'],
                    [2, 'test test test test'],
                    [3, 'test test test test test'],
                    [0, 'test test'],
                ],
            dataTransfer:
                [
                    {
                        index: 0, transfer:
                            [
                                { value: 0, label: '本地上传' },
                                { value: 1, label: 'MySQL数据库' },
                                { value: 2, label: 'HDFS分布式存储' },
                                { value: 3, label: 'MinIO分布式存储' },
                            ]
                    }
                ]
        }
    },
    methods: {
        closeCreatePopUp(flag) {
            this.isCreateSourcePopUpShown = false;
            if (flag === true) {
                //添加成功的逻辑...
            } else if (flag === false) {
                //添加失败的逻辑...
            }
        },
        closeModifyPopUp(flag) {
            this.isModifySourcePopUpShown = false;
            if (flag === true) {
                //添加成功的逻辑...
            } else if (flag === false) {
                //添加失败的逻辑...
            }
        },
        modifySource(row) {
            this.dataToBeModified = row;
            this.isModifySourcePopUpShown = true;
        },
        deleteSource(row, rowIndex) {
            this.isEnsureToDeleteMessage = '再次点击即可删除';
            if (this.isEnsureToDeleteInterval != null) {
                clearTimeout(this.isEnsureToDeleteInterval)
            }
            if (this.isEnsureToDelete != rowIndex) {
                this.isEnsureToDelete = rowIndex;
                this.isEnsureToDeleteInterval = setTimeout(() => {
                    this.isEnsureToDelete = -1;
                }, 1000);
            } else if (this.isEnsureToDelete === rowIndex) {
                //删除逻辑...
                this.isEnsureToDeleteMessage = '删除！';
                this.isEnsureToDeleteInterval = setTimeout(() => {
                    this.isEnsureToDelete = -1;
                }, 1000);
            }
        },
        colValue(col, index) {
            if (index === 0) {
                return this.dataTransfer[index].transfer[col].label;
            } else if (index >= 1) {
                return col;
            }
        },
        lastPage() {
            this.sourceFormData.pop();
        },
        nextPage() {
            this.sourceFormData.push([1, '测试测试'])
        },
        flush() {

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
        <div id="data-source-management-title">数据源管理</div>
        <div id="btns-wrapper">
            <!-- <div id="create-source-btn" @click="isCreateSourcePopUpShown = true">创建新数据源</div> -->
            <n-space>
                <n-button id="create-source-btn" @click="isCreateSourcePopUpShown = true" type="success"
                    size="large">创建新数据源</n-button>
            </n-space>

            <!-- <div id="manage-source-btn" @click="isManageBtnsShown = !isManageBtnsShown">{{isManageBtnsShown === false? '打开' : '关闭'}}数据源管理</div> -->
        </div>
        <div id="source-form-wrapper">
            <table id="source-form">
                <tr>
                    <td v-for="colName in sourceFormMetaData.colName" :style="{ borderColor: darkModeColor.border }">
                        {{ colName }}
                    </td>
                    <td :style="{ borderColor: darkModeColor.border }"></td>
                </tr>

                <tr v-for="(row, rowIndex) in sourceFormData">
                    
                    <td v-for="(col, colIndex) in row" :style="{ borderColor: darkModeColor.border }">{{ colValue(col, colIndex) }}
                        <!-- <Transition>
                            <span v-if="colIndex === 0 && isManageBtnsShown" class="iconfont icon-banshou"
                                @click="modifySource(row)"></span>
                        </Transition>
                        <Transition>

                            <span v-if="colIndex === 0 && isManageBtnsShown" class="iconfont icon-quxiao"
                                @click="deleteSource(row, rowIndex)"></span>
                        </Transition>
                        <Transition>
                            <div id="clickAgainToDelete" v-if="colIndex === 0 && isEnsureToDelete === rowIndex"> {{
                                isEnsureToDeleteMessage }} </div>
                        </Transition> -->
                    </td>
                    
                    <td :style="{ borderColor: darkModeColor.border }">
                        <div id="manageBtnsWrapper">
                            <n-button type="warning"  @click="modifySource(row)">修改</n-button>
                            <n-button type="error" @click="deleteSource(row, rowIndex)">删除</n-button>
                            <n-button type="success">同步</n-button>
                        </div>
                    </td>
                </tr>
            </table>
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

#data-source-management-title {
    font-size: 30px;
    font-weight: 900;
    margin: 20px 0;
}

#btns-wrapper {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin: 20px 0;
}

/* #btns-wrapper div {
    cursor: pointer;
    position: relative;
    margin: 20px;
    height: 40px;
    width: 130px;
    border-radius: 10px;
    border: 2px #808080 solid;
    font-size: 15px;
    line-height: 40px;
    text-align: center;
    transition: all 0.2s;
} */

/* #btns-wrapper div:hover {
    background-color: rgba(128, 128, 128, 0.3);
}

#source-form-wrapper {
    position: relative;
} */

#source-form {
    font-size: 18px;
    user-select: text;
    width: 100%;
    table-layout: fixed;
}

#source-form td {
    border-top: 1px solid ;
    border-bottom: 1px solid ;
}

#source-form td:not(:last-child) {
    border-right: 1px solid ;
}

#source-form td {
    width: 40%;
    text-align: center;
    padding: 12px;
    min-width: 100px;
    white-space: nowrap;
}

#source-form td:last-child {
    width: 20%;
}

#source-form tr{
    position: relative;
    transition: all 0.2s;
}

#source-form #manageBtnsWrapper{
    display: flex;
    flex-direction: row;
    justify-content:space-evenly;
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

/* #source-form tr:nth-child(2n):not(:first-child) {
    background-color: rgb(128, 128, 128, 0.3);
} */

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

/* #source-form span:nth-child(1) {
    cursor: pointer;
    position: absolute;
    left: 20px;
    font-size: 20px;
    transition: all 0.2s;
}

#source-form span:nth-child(2) {
    cursor: pointer;
    position: absolute;
    color: red;
    left: 60px;
    font-size: 20px;
    transition: all 0.2s;
}

#source-form #clickAgainToDelete {
    background-color: rgb(255, 0, 0, 0.8);
    font-size: 16px;
    height: 30px;
    width: 160px;
    line-height: 30px;
    border-radius: 8px;
    text-align: center;
    position: absolute;
    left: 90px;
    transform: translateY(-22px);
    transition: all 0.2s;
}

#source-form span:hover {
    transform: rotate(90deg);
}

.v-enter-active,
.v-leave-active {
    transition: all 0.3s;
}

.v-enter-from,
.v-leave-to {
    opacity: 0;
    transform: translateX(-200%);
} */
</style>