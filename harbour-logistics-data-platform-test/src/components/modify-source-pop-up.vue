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
            modifyNullWarnShown: {
                isShown: false,
                shake: false,
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
                    stepCount: 1, name: '更改数据', color: null, whichConfig: 0, configs:
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
                this.modifyNullWarnShown.isShown = false;
            }
        },
        modifyNextOrFinish() {
            if (this.modifyWhichStep === this.modifySteps.length - 1) {
                this.modifyWhichStep = 0;
                this.modifySource();
                this.$emit('close', true);
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
                this.modifyNullWarnShown.isShown = false;
            } else {
                this.modifyNullWarnShown.isShown = true;
                this.setShake();
            }
            return flag;

        },
        setShake() {
            this.modifyNullWarnShown.shake = false;
            setInterval(() => {
                this.modifyNullWarnShown.shake = true;
            }, 1);
        },
        showWhichConfig(step, config) {
            if (this.modifyWhichStep === 0) {
                step.whichConfig = config.configCount;
                return true;
            } else if (this.modifyWhichStep === 1) {
                if (config.configCount === this.modifySteps[0].configs[0].forms[0].value) {
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
            this.modifySteps[0].configs[0].forms[0].value = this.data[0];
            this.modifySteps[1].configs[this.data[0]].forms[0].value = this.data[1];
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
                        strArr.push(`${temp[j].name}: ${temp[j].options[temp[j].value].label}`)
                    } else if (temp[j].type === 'file') {
                        if (typeof (temp[j].value) === 'string') {
                            strArr.push(`${temp[j].name}: ${temp[j].value}`)
                        } else {
                            strArr.push(`${temp[j].name}: ${temp[j].value.name}`)
                        }
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
                <div id="modify-back-btn" :style="{ opacity: modifyBackBtnOpacity, visibility: modifyBackBtnIsShown }"
                    @click="modifyBackOrNot">上一步
                </div>
                <div id="modify-next-finish-btn" @click="modifyNextOrFinish">{{ modifyWhichStep ===
                    this.modifySteps.length - 1 ? '完成' : '下一步' }}
                </div>
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
                                    <span>{{ form.name + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' }}</span>
                                    <select v-if="form.type === 'select'" v-model="form.value">
                                        <option v-for="option in form.options" :value="option.value">{{ option.label
                                        }}
                                        </option>
                                    </select>

                                    <input v-if="form.type === 'input'" type="text" v-model="form.value">

                                    <input v-if="form.type === 'file'" type="file" id="file-upload"
                                        @change="(e) => form.value = e.target.files[0]">
                                    <label v-if="form.type === 'file'" for="file-upload" id="file-upload-label">选择文件</label>
                                    <div v-if="form.type === 'file'" id="selected-file">已选择的文件: {{ typeof (form.value) ===
                                        'string' ? form.value : form.value.name }}</div>


                                    <div v-if="form.type === 'div'">
                                        <p v-for="str in modifyShowCheckMessage">
                                            {{ str }}</p>
                                    </div>
                                </div>
                            </div>
                        </template>
                    </div>
                </Transition>
            </template>
            <Transition name="modify-null-warn">
                <div id="modify-null-warn" v-if="modifyNullWarnShown.isShown"
                    :class="{ modifyShake: modifyNullWarnShown.shake }">
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
    /* left: 3%; */
}

#modify-back-btn,
#modify-next-finish-btn {
    cursor: pointer;
    text-align: center;
    width: 100px;
    height: 40px;
    line-height: 40px;
    border-radius: 10px;
    border: 2px green solid;
    transition: all 0.2s;
}

#modify-back-btn:hover,
#modify-next-finish-btn:hover {
    background-color: rgba(0, 128, 128, 0.2);
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

#modify-null-warn {
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

.modify-null-warn-enter-active,
.modify-null-warn-leave-active {
    transition: all 0.2s;
}

.modify-null-warn-enter-from,
.modify-null-warn-leave-to {
    opacity: 0;
}

#modify-null-warn.modifyShake {
    animation: shake-horizontal 0.8s cubic-bezier(0.455, 0.030, 0.515, 0.955) both;
}

#config-main {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
}

#form-main span {
    font-size: 18px;
    font-weight: 900;
}

#form-main select,
input {
    font-size: 18px;
    color: #000;
    background-color: #b0b0b0;
    border: 1px rgb(100, 100, 100) solid;
    padding: 5px 10px;
    border-radius: 10px;
    height: 30px;
    width: 200px;
    transition: all 0.2s;
}

#form-main select {
    cursor: pointer;
    height: 40px;
}

#form-main input:focus,
select:focus {
    outline: 3px solid rgb(150, 0, 150);
    outline-offset: -3px;
}

#form-main #file-upload {
    display: none;
}

#form-main #file-upload-label {
    display: inline-block;
    text-align: center;
    cursor: pointer;
    line-height: 40px;
    height: 40px;
    width: 100px;
    border-radius: 10px;
    border: 2px #808080 solid;
    transition: all 0.2s;
}

#form-main #file-upload-label:hover {
    background-color: rgba(128, 128, 128, 0.3);
}

#selected-file {
    position: absolute;
    width: 100%;
    text-align: center;
    top: 60%;
    left: 50%;
    transform: translateX(-50%);
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