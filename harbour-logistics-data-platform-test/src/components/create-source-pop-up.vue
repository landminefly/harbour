<script>
import ensureClosePopUp from './ensureClosePopUp.vue';
export default {
    components: {
        ensureClosePopUp
    },
    props: ['isShown'],
    emits: ['close'],
    data() {
        return {
            readyToClose: false,
            darkModeColor: {
                popUp: null,
            },
            createNullWarnShown: {
                isShown: false,
                shake: false,
            },
            createWhichStep: 0,
            createSteps: [
                {
                    stepCount: 0, name: '选择数据源', color: null, whichConfig: 0, configs:
                        [
                            {
                                configCount: 0, forms:
                                    [
                                        {
                                            name: '数据源类型', type: 'select', value: null, options: [
                                                { value: 0, label: '本地上传' },
                                                { value: 1, label: 'MySQL数据库' },
                                                { value: 2, label: 'HDFS分布式存储' },
                                                { value: 3, label: 'MinIO分布式存储' },
                                            ],
                                        },
                                    ]
                            }
                        ]
                },
                {
                    stepCount: 1, name: '填写数据', color: null, whichConfig: 0, configs:
                        [
                            {
                                configCount: 0, forms:
                                    [
                                        {
                                            name: '上传文件', type: 'file', value: null
                                        },
                                    ]
                            },
                            {
                                configCount: 1, forms:
                                    [
                                        {
                                            name: 'MySQL路径', type: 'input', value: null
                                        }
                                    ]
                            },
                            {
                                configCount: 2, forms:
                                    [
                                        {
                                            name: 'HDFS路径', type: 'input', value: null
                                        },
                                        {
                                            name: 'HDFS路径', type: 'input', value: null
                                        },
                                        {
                                            name: 'HDFS路径', type: 'input', value: null
                                        },
                                    ]
                            },
                            {
                                configCount: 3, forms:
                                    [
                                        {
                                            name: 'MinIO路径', type: 'input', value: null
                                        }
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
                this.createWhichStep = 0;
                this.clearCreateData();
                this.$emit('close', false);
            }
            this.readyToClose = false;
        },
        createBackOrNot() {
            if (this.createWhichStep === 0) {
                return;
            } else {
                this.createWhichStep -= 1;
                this.createNullWarnShown.isShown = false;
            }
        },
        createNextOrFinish() {
            if (this.createWhichStep === this.createSteps.length - 1) {
                this.createWhichStep = 0;
                this.createSource();
                this.$emit('close', true);
            } else {
                if (this.checkCreateContent()) {
                    this.createWhichStep += 1;
                }
            }
        },
        checkCreateContent() {
            var temp = this.createSteps[this.createWhichStep].configs[this.createSteps[this.createWhichStep].whichConfig].forms;
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
                this.createNullWarnShown.isShown = false;
            } else {
                this.createNullWarnShown.isShown = true;
                this.setShake();
            }
            return flag;

        },
        setShake() {
            this.createNullWarnShown.shake = false;
            setInterval(() => {
                this.createNullWarnShown.shake = true;
            }, 1);
        },
        showWhichConfig(step, config) {
            if (this.createWhichStep === 0) {
                step.whichConfig = config.configCount;
                return true;
            } else if (this.createWhichStep === 1) {
                if (config.configCount === this.createSteps[0].configs[0].forms[0].value) {
                    step.whichConfig = config.configCount;
                    return true;
                }
            } else if (this.createWhichStep === 2) {
                step.whichConfig = config.configCount;
                return true;
            }
        },
        clearCreateData() {
            for (var step of this.createSteps) {
                for (var config of step.configs) {
                    for (var form of config.forms) {
                        form.value = null;
                    }
                }
            }
        },
        createSource() {
            //创建逻辑
            //...
            this.clearCreateData();
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
        createWhichStep: {
            handler(newValue) {
                for (var i = 0; i < this.createSteps.length; i++) {
                    if (i < newValue) {
                        this.createSteps[i].color = 'green'
                    } else if (i === newValue) {
                        this.createSteps[i].color = '#0276c4'
                    } else {
                        this.createSteps[i].color = null;
                    }
                }
            }, immediate: true
        }
    },
    computed: {
        createBackBtnOpacity() {
            if (this.createWhichStep === 0) {
                return '0';
            }
            return '100';
        },
        createBackBtnIsShown() {
            if (this.createWhichStep === 0) {
                return 'hidden';
            } else {
                return 'visible';
            }
        },
        createShowCheckMessage() {
            var strArr = [];
            for (var i = 0; i < this.createSteps.length - 1; i++) {
                for (var j = 0; j < this.createSteps[i].configs[this.createSteps[i].whichConfig].forms.length; j++) {
                    var temp = this.createSteps[i].configs[this.createSteps[i].whichConfig].forms;
                    if (temp[j].type === 'input') {
                        strArr.push(`${temp[j].name}: ${temp[j].value}`)
                    } else if (temp[j].type === 'select') {
                        strArr.push(`${temp[j].name}: ${temp[j].options[temp[j].value].label}`)
                    } else if (temp[j].type === 'file') {
                        strArr.push(`${temp[j].name}: ${temp[j].value.name}`)
                    }
                }
            }
            return strArr;
        }
    }
}
</script>

<template>
    <Transition name="shade">
        <div id="shade" v-if="isShown"></div>
    </Transition>

    <Transition name="create-source-pop-up">
        <div id="create-source-pop-up" v-if="isShown" :style="{ backgroundColor: darkModeColor.popUp }">

            <div id="close-create-source-pop-up-btn" @click="readyToClose = !readyToClose">
                <span class="iconfont icon-guanbi"></span>
            </div>

            <div id="create-step-btns">
                <n-space>
                    <n-button id="create-back-btn"
                        :style="{ opacity: createBackBtnOpacity, visibility: createBackBtnIsShown }"
                        @click="createBackOrNot" type="success" size="large">上一步</n-button>
                </n-space>
                <n-space>
                    <n-button id="create-next-finish-btn" @click="createNextOrFinish" type="success" size="large">{{
                        createWhichStep ===
                        this.createSteps.length - 1 ? '完成' : '下一步' }}</n-button>
                </n-space>
            </div>


            <ensureClosePopUp :readyToClose="readyToClose" @response="closeOrNot"></ensureClosePopUp>

            <div id="create-source-steps">
                <div v-for="step in createSteps" :style="{ backgroundColor: step.color }">
                    {{ step.stepCount + 1 }}
                    <p>
                        {{ step.name }}
                    </p>
                </div>
            </div>

            <template v-for="step in createSteps">
                <Transition name="create-source" mode="out-in">
                    <div id="create-source-main" v-if="step.stepCount === createWhichStep">
                        <template v-for="config in step.configs">
                            <div id="config-main" v-if="showWhichConfig(step, config)">
                                <div id="form-main" v-for="form in config.forms">
                                    <div class="form-title">{{ form.name + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' }}</div>
                                    <n-space vertical>
                                        <n-select class="select" v-if="form.type === 'select'" v-model:value="form.value"
                                            :options="form.options" />
                                    </n-space>

                                    <n-space vertical>
                                        <n-input v-if="form.type === 'input'" v-model:value="form.value" type="text" />
                                    </n-space>

                                    <div v-if="form.type === 'file'" id="file-upload-wrapper">
                                        <input type="file" ref="fileUploadBtn" id="file-upload"
                                            @change="(e) => form.value = e.target.files[0]" />
                                        <n-space vertical>
                                            <n-button @click="this.$refs.fileUploadBtn[0].click()" type="success"
                                                size="large">选择文件</n-button>
                                        </n-space>
                                        <div id="selected-file">已选择的文件: {{ form.value ===
                                            null || undefined ? '无' : form.value.name }}</div>
                                    </div>

                                    <div v-if="form.type === 'div'">
                                        <p v-for="str in createShowCheckMessage">
                                            {{ str }}</p>
                                    </div>
                                </div>
                            </div>
                        </template>
                    </div>
                </Transition>
            </template>
            <Transition name="create-null-warn">
                <div id="create-null-warn" v-if="createNullWarnShown.isShown"
                    :class="{ createShake: createNullWarnShown.shake }">
                    有未完成的选项!
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

#create-source-pop-up {
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

.create-source-pop-up-enter-active,
.create-source-pop-up-leave-active {
    transition: all 0.3s;
}

.create-source-pop-up-enter-from,
.create-source-pop-up-leave-to {
    opacity: 0;
}

#close-create-source-pop-up-btn {
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

#close-create-source-pop-up-btn>span {
    font-size: 20px;
}

#close-create-source-pop-up-btn:hover {
    transform: rotate(90deg);
}

#create-source-steps {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    height: 16%;
    text-align: center;
    border-bottom: 1px rgba(128, 128, 128, 0.3) solid;
    line-height: 50px;
}

#create-source-steps :not(p) {
    height: 50px;
    width: 50px;
    background-color: rgba(128, 128, 128, 0.3);
    border-radius: 50%;
    position: relative;
    top: 10px;
    transition: all 0.3s;
}

#create-source-steps p {
    width: 100px;
    position: relative;
    left: -25px;
    top: -5px;
}

#create-step-btns {
    width: 60%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    bottom: 5%;
}

#create-source-main {
    position: absolute;
    top: 20%;
    left: 3%;
    height: 60%;
    width: 94%;
}

.create-source-enter-active {
    transition: all 0.2s 0.2s;
}

.create-source-leave-active {
    transition: all 0.2s;
}

.create-source-enter-from,
.create-source-leave-to {
    opacity: 0;
}

#create-null-warn {
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

.create-null-warn-enter-active,
.create-null-warn-leave-active {
    transition: all 0.2s;
}

.create-null-warn-enter-from,
.create-null-warn-leave-to {
    opacity: 0;
}

#create-null-warn.createShake {
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
    font-size: 18px;
    font-weight: 900;
    text-align: center;
}

#form-main .select {
    font-size: 18px;
    padding: 5px 10px;
    border-radius: 10px;
    height: 30px;
    width: 200px;
    transition: all 0.2s;
}

#form-main #file-upload {
    display: none;
}

#file-upload-wrapper{
    position: relative;
}

#selected-file {
    position:absolute;
    text-align: center;
    width: 800px;
    left:-400px;
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