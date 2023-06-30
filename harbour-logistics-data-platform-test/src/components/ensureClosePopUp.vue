<!-- 确认关闭弹窗组件 -->
<script>
export default {
    props: ['readyToClose'],
    emits: ['response'],
    data() {
        return {
            //黑夜模式颜色
            darkModeColor: {
                backgroundColor: null,
            }
        }
    },
    watch: {
        //更改黑夜模式
        '$store.state.isDarkMode': {
            handler(newValue) {
                this.darkModeColor.backgroundColor = newValue ? '#010409' : '#ffffff';
            },
            //页面首次加载时初始化
            immediate: true
        }
    }
}
</script>

<template>
    <Transition name="ensure-close-pop-up">
        <div id="ensure-close-pop-up" v-if="readyToClose" :style="darkModeColor">
            <span>确定要关闭吗？<br>所有未保存的内容都将丢失！</span>
            <br>
            <div id="btns">
                <n-space>
                    <n-button id="ensure"
                    @click="$emit('response', true)" type="error" size="large">确认</n-button>
                </n-space>
                <n-space>
                    <n-button id="cancel"
                    @click="$emit('response', false)" type="success" size="large">取消</n-button>
                </n-space>
            </div>
        </div>
    </Transition>
</template>

<style>
#ensure-close-pop-up {
    height: 40%;
    width: 50%;
    border: 1px rgb(200, 0, 0) solid;
    border-radius: 20px;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    box-sizing: border-box;
    box-shadow: 0px 0px 10px rgba(200, 0, 0, 0.5);
    font-size: 20px;
    padding-top: 50px;
    line-height: 50px;
    text-align: center;
    z-index: 9999999;
}

#ensure-close-pop-up>#btns {
    left: 0;
    right: 0;
    margin: auto;
    position: absolute;
    bottom: 20%;
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
}

#ensure-close-pop-up>#btns #ensure,
#ensure-close-pop-up>#btns #cancel{
    font-size: 18px;
    height: 40px;
    width: 80px;
}

.ensure-close-pop-up-enter-active,
.ensure-close-pop-up-leave-active {
    transition: all 0.3s;
}

.ensure-close-pop-up-enter-from,
.ensure-close-pop-up-leave-to {
    opacity: 0;
}
</style>