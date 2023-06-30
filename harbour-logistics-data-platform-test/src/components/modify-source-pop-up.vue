<script>
import ensureClosePopUp from './ensureClosePopUp.vue';
export default {
    components: {
        ensureClosePopUp
    },
    props: ['isShown', 'data'],
    emits: ['close'],
    data() {
        return {
            readyToClose: false,
            darkModeColor: {
                popUp: null,
            },
            warnShown: {
                isShown: false,
                shake: false,
                warnMsg: null,
            },
            modifyWhichStep: 0,
            modifySteps: [
                {
                    stepCount: 0, name: '更改数据源', color: null, whichConfig: 0, configs:
                        [
                            {
                                configCount: 0, forms:
                                    [
                                        {
                                            name: '数据源类型', type: 'select', value: null, options: [
                                                { value: '本地上传', label: '本地上传' },
                                                { value: 'MySQL数据库', label: 'MySQL数据库' },
                                                { value: 'HDFS分布式存储', label: 'HDFS分布式存储' },
                                                { value: 'MinIO分布式存储', label: 'MinIO分布式存储' },
                                            ],
                                        },
                                    ]
                            }
                        ]
                },
                {
                    stepCount: 1, name: '更改数据', color: null, whichConfig: 0, configs:
                        [
                            {
                                configCount: 0, value: '本地上传', forms:
                                    [
                                        {
                                            name: '上传文件', type: 'file', value: null
                                        },
                                    ]
                            },
                            {
                                configCount: 1, value: 'MySQL数据库', forms:
                                    [
                                        {
                                            name: 'MySQL路径', type: 'input', value: null
                                        },
                                        {
                                            name: '自动同步间隔(分钟)', type: 'number', value: null
                                        },
                                    ]
                            },
                            {
                                configCount: 2, value: 'HDFS分布式存储', forms:
                                    [
                                        {
                                            name: 'HDFS路径', type: 'input', value: null
                                        },
                                        {
                                            name: '自动同步间隔(分钟)', type: 'number', value: null
                                        },
                                    ]
                            },
                            {
                                configCount: 3, value: 'MinIO分布式存储', forms:
                                    [
                                        {
                                            name: 'MinIO路径', type: 'input', value: null
                                        },
                                        {
                                            name: '自动同步间隔(分钟)', type: 'number', value: null
                                        },
                                    ]
                            }
                        ]
                },
                {
                    stepCount: 2, name: '完成', color: null, whichConfig: 0, configs:
                        [
                            {
                                configCount: 0, forms:
                                    [
                                        {
                                            name: '检查信息', type: 'div', value: null
                                        },
                                    ]
                            },
                        ]
                },
            ],
        }
    },
    methods: {
        closeOrNot(flag) {
            if (flag === true) {
                this.modifyWhichStep = 0;
                this.clearModifyData();
                this.$emit('close', false);
            }
            this.readyToClose = false;
        },
        modifyBackOrNot() {
            if (this.modifyWhichStep === 0) {
                return;
            } else {
                this.modifyWhichStep -= 1;
                this.warnShown.isShown = false;
            }
        },
        modifyNextOrFinish() {
            if (this.modifyWhichStep === this.modifySteps.length - 1) {
                this.modifySource();
            } else {
                if (this.checkModifyContent()) {
                    this.modifyWhichStep += 1;
                }
            }
        },
        checkModifyContent() {
            var temp = this.modifySteps[this.modifyWhichStep].configs[this.modifySteps[this.modifyWhichStep].whichConfig].forms;
            for (var item of temp) {
                var flag = true;
                if (typeof (item.value) === "string" && (item.value = item.value.trim()) === '') {
                    flag = false;
                    break;
                }
                if (item.value === null || item.value === undefined) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                this.warnShown.isShown = false;
            } else {
                this.warnShown.isShown = true;
                this.warnShown.warnMsg = '有未完成的选项！'
                this.setShake();
            }
            return flag;

        },
        setShake() {
            this.warnShown.shake = false;
            setInterval(() => {
                this.warnShown.shake = true;
            }, 1);
        },
        showWhichConfig(step, config) {
            if (this.modifyWhichStep === 0) {
                step.whichConfig = config.configCount;
                return true;
            } else if (this.modifyWhichStep === 1) {
                if (config.value === this.modifySteps[0].configs[0].forms[0].value) {
                    step.whichConfig = config.configCount;
                    return true;
                }
            } else if (this.modifyWhichStep === 2) {
                step.whichConfig = config.configCount;
                return true;
            }
        },
        clearModifyData() {
            for (var step of this.modifySteps) {
                for (var config of step.configs) {
                    for (var form of config.forms) {
                        form.value = null;
                    }
                }
            }
        },
        modifySource() {
            //修改逻辑
            //...
                // this.warnShown.isShown = true;
                // this.warnShown.warnMsg = '连接失败！'
                // this.setShake();
                this.modifyWhichStep = 0;
                this.$emit('close', true);
                this.clearModifyData();

        },
    },
    watch: {
        '$store.state.isDarkMode': {
            handler(newValue) {
                this.darkModeColor.popUp = newValue ? '#0d1117' : '#f6f8fa';
            },
            //页面首次加载时初始化
            immediate: true
        },
        modifyWhichStep: {
            handler(newValue) {
                for (var i = 0; i < this.modifySteps.length; i++) {
                    if (i < newValue) {
                        this.modifySteps[i].color = 'green'
                    } else if (i === newValue) {
                        this.modifySteps[i].color = '#0276c4'
                    } else {
                        this.modifySteps[i].color = null;
                    }
                }
            }, immediate: true
        },
        isShown() {
            this.warnShown.isShown = false;
            this.modifySteps[0].configs[0].forms[0].value = this.data[0];
            if (this.data[0] === '本地上传') {
                this.modifySteps[1].configs[0].forms[0].value = this.data[1];
            } else if (this.data[0] === 'MySQL数据库') {
                this.modifySteps[1].configs[1].forms[0].value = this.data[1];
                this.modifySteps[1].configs[1].forms[1].value = this.data[2];
            } else if (this.data[0] === 'HDFS分布式存储') {
                this.modifySteps[1].configs[2].forms[0].value = this.data[1];
                this.modifySteps[1].configs[2].forms[1].value = this.data[2];
            } else if (this.data[0] === 'MinIO分布式存储') {
                this.modifySteps[1].configs[3].forms[0].value = this.data[1];
                this.modifySteps[1].configs[3].forms[1].value = this.data[2];
            }

        }
    },
    computed: {
        modifyBackBtnOpacity() {
            if (this.modifyWhichStep === 0) {
                return '0';
            }
            return '100';
        },
        modifyBackBtnIsShown() {
            if (this.modifyWhichStep === 0) {
                return 'hidden';
            } else {
                return 'visible';
            }
        },
        modifyShowCheckMessage() {
            var strArr = [];
            for (var i = 0; i < this.modifySteps.length - 1; i++) {
                for (var j = 0; j < this.modifySteps[i].configs[this.modifySteps[i].whichConfig].forms.length; j++) {
                    var temp = this.modifySteps[i].configs[this.modifySteps[i].whichConfig].forms;
                    if (temp[j].type === 'input') {
                        strArr.push(`${temp[j].name}: ${temp[j].value}`)
                    } else if (temp[j].type === 'select') {
                        strArr.push(`${temp[j].name}: ${temp[j].value}`)
                    } else if (temp[j].type === 'file') {
                        if (typeof (temp[j].value) === 'string') {
                            strArr.push(`${temp[j].name}: ${temp[j].value}`)
                        } else {
                            strArr.push(`${temp[j].name}: ${temp[j].value.name}`)
                        }
                    } else if (temp[j].type === 'number') {
                        strArr.push(`${temp[j].name}: ${temp[j].value}`)
                    }
                }
            }
            return strArr;
        }
    },
}
</script>

<template>
    <Transition name="shade">
        <div id="shade" v-if="isShown"></div>
    </Transition>

    <Transition name="modify-source-pop-up">
        <div id="modify-source-pop-up" v-if="isShown" :style="{ backgroundColor: darkModeColor.popUp }">

            <div id="close-modify-source-pop-up-btn" @click="readyToClose = !readyToClose">
                <span class="iconfont icon-guanbi"></span>
            </div>

            <div id="modify-step-btns">
                <n-space>
                    <n-button id="modify-back-btn"
                        :style="{ opacity: modifyBackBtnOpacity, visibility: modifyBackBtnIsShown }"
                        @click="modifyBackOrNot" type="success" size="large">上一步</n-button>
                </n-space>
                <n-space>
                    <n-button id="modify-next-finish-btn" @click="modifyNextOrFinish" type="success" size="large">{{
                        modifyWhichStep ===
                        this.modifySteps.length - 1 ? '完成' : '下一步' }}</n-button>
                </n-space>
            </div>


            <ensureClosePopUp :readyToClose="readyToClose" @response="closeOrNot"></ensureClosePopUp>

            <div id="modify-source-steps">
                <div v-for="step in modifySteps" :style="{ backgroundColor: step.color }">
                    {{ step.stepCount + 1 }}
                    <p>
                        {{ step.name }}
                    </p>
                </div>
            </div>

            <template v-for="step in modifySteps">
                <Transition name="modify-source" mode="out-in">
                    <div id="modify-source-main" v-if="step.stepCount === modifyWhichStep">
                        <template v-for="config in step.configs">
                            <div id="config-main" v-if="showWhichConfig(step, config)">
                                <div id="form-main" v-for="form in config.forms">
                                    <div class="form-title">{{ form.name + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' }}</div>
                                    <n-space vertical>
                                        <n-select class="select" v-if="form.type === 'select'" v-model:value="form.value"
                                            :options="form.options" placeholder="请选择" />
                                    </n-space>

                                    <n-space vertical>
                                        <n-input class="input" v-if="form.type === 'input'" v-model:value="form.value" type="text" placeholder="请输入" />
                                    </n-space>

                                    <div v-if="form.type === 'file'" id="file-upload-wrapper">
                                        <input type="file" ref="fileUploadBtn" id="file-upload"
                                            @change="(e) => form.value = e.target.files[0]" />
                                        <n-space vertical>
                                            <n-button @click="this.$refs.fileUploadBtn[0].click()" type="success"
                                                size="large">选择文件</n-button>
                                        </n-space>
                                        <div id="selected-file">已选择的文件: {{ typeof (form.value) ===
                                            'string' ? form.value : form.value.name }}</div>
                                    </div>

                                    <n-space vertical>
                                        <n-input-number class="number" v-if="form.type === 'number'"
                                            v-model:value="form.value" placeholder="0为不自动同步" :min="0" :max="65535" />
                                    </n-space>

                                    <div v-if="form.type === 'div'" style="transform: translateY(-12%);">
                                        <p v-for="str in modifyShowCheckMessage">
                                            {{ str }}</p>
                                    </div>
                                </div>
                            </div>
                        </template>
                    </div>
                </Transition>
            </template>
            <Transition name="modify-warn">
                <div id="modify-warn" v-if="warnShown.isShown" :class="{ modifyShake: warnShown.shake }">
                    {{ warnShown.warnMsg }}
                </div>
            </Transition>
        </div>
    </Transition>
</template>

<style>
#shade {
    height: 100%;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    position: absolute;
    top: 0;
    z-index: 99999;
}

.shade-enter-active,
.shade-leave-active {
    transition: all 0.3s;
}

.shade-enter-from,
.shade-leave-to {
    opacity: 0;
}

#modify-source-pop-up {
    height: 700px;
    width: 900px;
    background-color: red;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    border-radius: 20px;
    box-sizing: border-box;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.8);
    padding: 1%;
    z-index: 999999;
}

.modify-source-pop-up-enter-active,
.modify-source-pop-up-leave-active {
    transition: all 0.3s;
}

.modify-source-pop-up-enter-from,
.modify-source-pop-up-leave-to {
    opacity: 0;
}

#close-modify-source-pop-up-btn {
    cursor: pointer;
    width: 50px;
    height: 50px;
    line-height: 50px;
    text-align: center;
    position: absolute;
    top: 10px;
    right: 10px;
    transition: all 0.3s;
}

#close-modify-source-pop-up-btn>span {
    font-size: 20px;
}

#close-modify-source-pop-up-btn:hover {
    transform: rotate(90deg);
}

#modify-source-steps {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    height: 16%;
    text-align: center;
    border-bottom: 1px rgba(128, 128, 128, 0.3) solid;
    line-height: 50px;
}

#modify-source-steps :not(p) {
    height: 50px;
    width: 50px;
    background-color: rgba(128, 128, 128, 0.3);
    border-radius: 50%;
    position: relative;
    top: 10px;
    transition: all 0.3s;
}

#modify-source-steps p {
    width: 100px;
    position: relative;
    left: -25px;
    top: -5px;
}

#modify-step-btns {
    width: 60%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    bottom: 5%;
}

#modify-source-main {
    position: absolute;
    top: 20%;
    left: 3%;
    height: 60%;
    width: 94%;
}

.modify-source-enter-active {
    transition: all 0.2s 0.2s;
}

.modify-source-leave-active {
    transition: all 0.2s;
}

.modify-source-enter-from,
.modify-source-leave-to {
    opacity: 0;
}

#modify-warn {
    height: 50px;
    width: 300px;
    line-height: 50px;
    font-size: 20px;
    border-radius: 10px;
    border: 1px red solid;
    box-sizing: border-box;
    background-color: rgba(255, 0, 0, 0.3);
    position: absolute;
    bottom: 12%;
    left: 0;
    right: 0;
    margin: auto;
    text-align: center;
}

.modify-warn-enter-active,
.modify-warn-leave-active {
    transition: all 0.2s;
}

.modify-warn-enter-from,
.modify-warn-leave-to {
    opacity: 0;
}

#modify-warn.modifyShake {
    animation: shake-horizontal 0.8s cubic-bezier(0.455, 0.030, 0.515, 0.955) both;
}

#config-main {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
}

#form-main {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    align-items: center;
}

#form-main .form-title {
    font-size: 17px;
    font-weight: 900;
    width: 180px;
    text-align: center;
}

#form-main .select,
#form-main .input,
#form-main .number {
    width: 250px;
}

#form-main #file-upload {
    display: none;
}

#file-upload-wrapper {
    position: relative;
}

#selected-file {
    position: absolute;
    text-align: center;
    width: 800px;
    left: -400px;
    top: 150%;
}

#form-main p {
    margin-top: 30px;
}

@keyframes shake-horizontal {

    0%,
    100% {
        transform: translateX(0);
    }

    10%,
    30%,
    50%,
    70% {
        transform: translateX(-10px);
    }

    20%,
    40%,
    60% {
        transform: translateX(10px);
    }

    80% {
        transform: translateX(8px);
    }

    90% {
        transform: translateX(-8px);
    }
}
</style>