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
            } else if (index === 1) {
                return col;
            }
        },
        lastPage() {
            this.sourceFormData.pop();
        },
        nextPage() {
            this.sourceFormData.push([1,'测试测试'])
        },
        flush() {

        },
    },
    watch: {
        //更改黑夜模式
        '$store.state.isDarkMode': {
            handler(newValue) {
                this.darkModeColor.border = newValue ? '#e1e1e1' : '#213547';
            },
            //页面首次加载时初始化
            immediate: true
        },
    },
    mounted() {
        const observer = new ResizeObserver(entries => {
            for (let entry of entries) {
                // 处理目标元素的尺寸变化
                this.sourceFormMetaData.formHeight = this.$refs.form.clientHeight;
                this.sourceFormMetaData.formWidth = this.$refs.form.offsetWidth;
            }
        });
        observer.observe(this.$refs.form);
    },
}
</script>

<template>
    <div id="module1-wrapper">
        <createSourcePopUp :isShown="isCreateSourcePopUpShown" @close="closeCreatePopUp"></createSourcePopUp>
        <modifySourcePopUp :isShown="isModifySourcePopUpShown" :data="dataToBeModified" @close="closeModifyPopUp">
        </modifySourcePopUp>
        <div id="source-form-wrapper"
            :style="{ height: sourceFormMetaData.formHeight + 'px', width: sourceFormMetaData.formWidth + 'px' }">
            <div id="source-form-border" ref="form">
                <table id="source-form">
                    <tr>
                        <td v-for="colName in sourceFormMetaData.colName">
                            {{ colName }}
                        </td>
                    </tr>

                    <tr v-for="(row, rowIndex) in sourceFormData">
                        <td v-for="(col, colIndex) in row">{{ colValue(col, colIndex) }}
                            <Transition>
                                <span v-if="colIndex === row.length - 1 && isManageBtnsShown" class="iconfont icon-banshou"
                                    @click="modifySource(row)"></span>
                            </Transition>
                            <Transition>
                                <span v-if="colIndex === row.length - 1 && isManageBtnsShown" class="iconfont icon-quxiao"
                                    @click="deleteSource(row, rowIndex)"></span>
                            </Transition>
                            <Transition>
                                <div id="clickAgainToDelete"
                                    v-if="colIndex === row.length - 1 && isEnsureToDelete === rowIndex"> {{
                                        isEnsureToDeleteMessage }} </div>
                            </Transition>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="form-btns-wrapper">
                <div id="flush-wrapper">
                    <span class="iconfont icon-shuaxin" @click="flush"></span>
                </div>
                <div id="page-wrapper">
                    <span class="iconfont icon-fanye1" @click="lastPage"></span>
                    {{ sourceFormMetaData.whichPage }} / {{ sourceFormMetaData.totalPage }} 页
                    <span class="iconfont icon-fanye" @click="nextPage"></span>
                </div>
            </div>

        </div>
        <div id="btns-wrapper">
            <div id="create-source-btn" @click="isCreateSourcePopUpShown = true">创建新数据源</div>
            <div id="manage-source-btn" @click="isManageBtnsShown = !isManageBtnsShown">管理数据源</div>
        </div>
    </div>
</template>

<style>
#module1-wrapper {
    height: 100%;
    width: 100%;
    font-size: 18px;
    overflow: auto;
    /* 阻止外边距折叠 */
    float: left;
}

#btns-wrapper {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
}

#btns-wrapper div {
    cursor: pointer;
    position: relative;
    margin: 50px;
    height: 40px;
    width: 120px;
    background-color: transparent;
    border-radius: 10px;
    border: 2px #808080 solid;
    font-size: 15px;
    line-height: 40px;
    text-align: center;
    transition: all 0.2s;
}


#btns-wrapper div:hover {
    background-color: rgba(128, 128, 128, 0.3);
}

#source-form-wrapper {
    position: relative;
    left: 50%;
    margin-top: 50px;
    margin-bottom: 50px;
    transform: translateX(-50%);
    transition: height 0.2s;
}

#source-form-border {
    position: absolute;
    border: 2px solid;
    border-radius: 7px;
}

#source-form tr:not(:last-child) {
    border-bottom: 1px solid;
}

#source-form td:not(:last-child) {
    border-right: 1px solid;
}

#source-form td {
    text-align: center;
    padding: 15px 20px;
    min-width: 100px;
    white-space: nowrap;
}

#source-form tr:first-child {
    font-size: 20px;
    font-weight: 900;
    background-color: rgba(16, 63, 145, 0.5);
}

#source-form tr:nth-child(2n):not(:first-child) {
    background-color: rgb(128, 128, 128, 0.3);
}

#source-form span:nth-child(1) {
    cursor: pointer;
    position: absolute;
    right: -35px;
    font-size: 20px;
    transition: all 0.2s;
}

#source-form span:nth-child(2) {
    cursor: pointer;
    position: absolute;
    color: red;
    right: -70px;
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
    right: -250px;
    transform: translateY(-22px);
    transition: all 0.2s;
}

#source-form span:hover {
    transform: rotate(90deg);
}

.v-enter-active,
.v-leave-active {
    transition: all 0.2s;
}

.v-enter-from,
.v-leave-to {
    opacity: 0;
    transform: translateX(-100%);
}

#form-btns-wrapper {
    height: 40px;
    padding: 0 10px;
    position: absolute;
    bottom: -60px;
    right: -10px;
    display: flex;
    flex-direction: row;
    justify-content: end;
}

#flush-wrapper,
#page-wrapper {
    height: 40px;
    padding: 0 10px;
    margin-left: 10px;
    line-height: 40px;
    text-align: center;
    background-color: rgb(128, 128, 128, 0.3);

    border-radius: 10px;
}

#form-btns-wrapper span {
    display: inline-block;
    cursor: pointer;
    transition: all 0.2s;
}

#form-btns-wrapper span:hover {
    transform: scale(1.2);
}

#form-btns-wrapper span:active {
    transform: scale(1);
}
</style>