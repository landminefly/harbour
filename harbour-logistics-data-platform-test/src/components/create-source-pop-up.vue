<script>
import ensureClosePopUp from './ensureClosePopUp.vue';
export default {
    components: {
        ensureClosePopUp
    },
    props: ['isShown'],
    // 向外传递close事件
    emits: ['close'],
    data() {
        return {
            //准备关闭弹窗
            readyToClose: false,
            //黑夜模式颜色
            darkModeColor: {
                popUp: null,
            },
            //警告提示
            warnShown: {
                isShown: false,
                shake: false,
                warnMsg: null,
            },
            //当前步骤
            createWhichStep: 0,
            //表单数据
            createSteps: [
                {
                    stepCount: 0, name: '选择数据源', color: null, whichConfig: 0, configs:
                        [
                            {
                                configCount: 0, forms:
                                    [
                                        {
                                            name: '数据源类型', type: 'select', value: null, options: [
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
                    stepCount: 1, name: '填写数据', color: null, whichConfig: 0, configs:
                        [
                            {
                                configCount: 0, value: 'MySQL数据库', forms:
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
                                configCount: 1, value: 'HDFS分布式存储', forms:
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
                                configCount: 2, value: 'MinIO分布式存储', forms:
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
        // 根据确认关闭弹窗传递出来的参数，来确定是否关闭数据修改弹窗
        closeOrNot(flag) {
            if (flag === true) {
                this.createWhichStep = 0;
                this.clearCreateData();
                this.$emit('close', false);
            }
            this.readyToClose = false;
        },
        //上一步
        createBackOrNot() {
            if (this.createWhichStep === 0) {
                return;
            } else {
                this.createWhichStep -= 1;
                this.warnShown.isShown = false;
            }
        },
        //下一步/完成
        createNextOrFinish() {
            if (this.createWhichStep === this.createSteps.length - 1) {
                this.createSource();
            } else {
                if (this.checkCreateContent()) {
                    this.createWhichStep += 1;
                }
            }
        },
        //检查表单信息是否为空
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
                this.warnShown.isShown = false;
            } else {
                //显示警告信息
                this.warnShown.isShown = true;
                this.warnShown.warnMsg = '有未完成的选项！'
                this.setShake();
            }
            return flag;
        },
        //设置警告信息shake动画
        setShake() {
            this.warnShown.shake = false;
            setInterval(() => {
                this.warnShown.shake = true;
            }, 1);
        },
        //设置当前步骤要展示的表单
        showWhichConfig(step, config) {
            if (this.createWhichStep === 0) {
                step.whichConfig = config.configCount;
                return true;
            } else if (this.createWhichStep === 1) {
                if (config.value === this.createSteps[0].configs[0].forms[0].value) {
                    step.whichConfig = config.configCount;
                    return true;
                }
            } else if (this.createWhichStep === 2) {
                step.whichConfig = config.configCount;
                return true;
            }
        },
        //清除弹窗数据
        clearCreateData() {
            for (var step of this.createSteps) {
                for (var config of step.configs) {
                    for (var form of config.forms) {
                        form.value = null;
                    }
                }
            }
        },
        //向后端请求新增数据
        createSource() {
            //创建逻辑
            //...
            // this.warnShown.isShown = true;
            // this.warnShown.warnMsg = '连接失败！'
            // this.setShake();
            this.createWhichStep = 0;
            this.clearCreateData();
            this.$emit('close', true);
        },
    },
    watch: {
        //更改黑夜模式
        '$store.state.isDarkMode': {
            handler(newValue) {
                this.darkModeColor.popUp = newValue ? '#0d1117' : '#f6f8fa';
            },
            //页面首次加载时初始化
            immediate: true
        },
        //更改步骤显示颜色
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
        },
        //防止打开弹窗时直接显示警告信息
        isShown() {
            this.warnShown.isShown = false;
        }
    },
    computed: {
        //设置上一步按钮是否显示及其动画
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
        //设置检查步骤的信息展示
        createShowCheckMessage() {
            var strArr = [];
            for (var i = 0; i < this.createSteps.length - 1; i++) {
                for (var j = 0; j < this.createSteps[i].configs[this.createSteps[i].whichConfig].forms.length; j++) {
                    var temp = this.createSteps[i].configs[this.createSteps[i].whichConfig].forms;
                    if (temp[j].type === 'input') {
                        strArr.push(`${temp[j].name}: ${temp[j].value}`)
                    } else if (temp[j].type === 'select') {
                        strArr.push(`${temp[j].name}: ${temp[j].value}`)
                    } else if (temp[j].type === 'number') {
                        strArr.push(`${temp[j].name}: ${temp[j].value}`)
                    }
                }
            }
            return strArr;
        }
    }
}
</script>

<template>
    <!-- 遮罩 -->
    <Transition name="shade">
        <div id="shade" v-if="isShown"></div>
    </Transition>

    <Transition name="create-source-pop-up">
        <div id="create-source-pop-up" v-if="isShown" :style="{ backgroundColor: darkModeColor.popUp }">

            <!-- 关闭按钮 -->
            <div id="close-create-source-pop-up-btn" @click="readyToClose = !readyToClose">
                <span class="iconfont icon-guanbi"></span>
            </div>

            <!-- 上一步/下一步/完成按钮 -->
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

            <!-- 确认关闭弹窗组件 -->
            <ensureClosePopUp :readyToClose="readyToClose" @response="closeOrNot"></ensureClosePopUp>

            <!-- 步骤信息显示 -->
            <div id="create-source-steps">
                <div v-for="step in createSteps" :style="{ backgroundColor: step.color }">
                    {{ step.stepCount + 1 }}
                    <p>
                        {{ step.name }}
                    </p>
                </div>
            </div>

            <!-- 表单内容 -->
            <template v-for="step in createSteps">
                <Transition name="create-source" mode="out-in">
                    <div id="create-source-main" v-if="step.stepCount === createWhichStep">
                        <template v-for="config in step.configs">
                            <div id="config-main" v-if="showWhichConfig(step, config)">
                                <div id="form-main" v-for="form in config.forms">
                                    <!-- 表单标题 -->
                                    <div class="form-title">{{ form.name + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' }}</div>

                                    <!-- 选择表单 -->
                                    <n-space vertical>
                                        <n-select class="select" v-if="form.type === 'select'" v-model:value="form.value"
                                            placeholder="请选择" :options="form.options" />
                                    </n-space>

                                    <!-- 输入表单 -->
                                    <n-space vertical>
                                        <n-input class="input" v-if="form.type === 'input'" v-model:value="form.value"
                                            placeholder="请输入" type="text" />
                                    </n-space>

                                    <!-- 数字输入表单 -->
                                    <n-space vertical>
                                        <n-input-number class="number" v-if="form.type === 'number'"
                                            v-model:value="form.value" placeholder="0为不自动同步" :min="0" :max="65535" />
                                    </n-space>

                                    <!-- 信息检查部分 -->
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

            <!-- 警告信息 -->
            <Transition name="create-warn">
                <div id="create-warn" v-if="warnShown.isShown" :class="{ createShake: warnShown.shake }">
                    {{ warnShown.warnMsg }}
                </div>
            </Transition>

        </div>
    </Transition>
</template>

<style>
/* 遮罩样式及动画 */
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

/* 弹窗主样式及动画 */
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

/* 关闭按钮样式 */
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

/* 步骤信息样式 */
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

/* 上一步/下一步/完成按钮样式 */
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

/* 表单主样式及动画 */
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
    justify-content: end;
    align-items: center;
}

#form-main .form-title {
    font-size: 17px;
    font-weight: 900;
    width: 180px;
    text-align: center;
}

/* 选择、输入、数字输入表单样式 */
#form-main .select,
#form-main .input,
#form-main .number {
    width: 250px;
}

/* 检查信息样式 */
#form-main p {
    margin-top: 30px;
}

/* 警告信息样式 */
#create-warn {
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

.create-warn-enter-active,
.create-warn-leave-active {
    transition: all 0.2s;
}

.create-warn-enter-from,
.create-warn-leave-to {
    opacity: 0;
}

#create-warn.createShake {
    animation: shake-horizontal 0.8s cubic-bezier(0.455, 0.030, 0.515, 0.955) both;
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