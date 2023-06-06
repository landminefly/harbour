<script>
import createSourcePopUp from './create-source-pop-up.vue'
export default {
    components: {
        createSourcePopUp,
    },
    data() {
        return {
            isCreateSourcePopUpShown: false
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

        }
    },
}
</script>

<template>
    <div id="module1-wrapper">
        <createSourcePopUp :isShown="isCreateSourcePopUpShown" @close="closeCreatePopUp"></createSourcePopUp>
        <div id="create-source-btn" @click="isCreateSourcePopUpShown = true">创建新数据源</div>
    </div>
</template>

<style>
#module1-wrapper {
    height: 100%;
    width: 100%;
    position: relative;
    font-size: 18px;
}
#create-source-btn{
    cursor: pointer;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    height: 40px;
    width: 120px;
    background-color: transparent;
    border-radius: 10px;
    border: 2px #808080 solid;
    font-size: 15px;
    line-height: 40px;
    text-align: center;
    transition: all 0.1s;
}

#create-source-btn:hover{
    background-color: rgba(128, 128, 128,0.3);
}
</style>