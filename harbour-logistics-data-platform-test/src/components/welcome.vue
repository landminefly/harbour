<template>
    <div id="welcome-wrapper" class="scale-in-center">
        <div id="welcome-header">
            <n-gradient-text :gradient="{
                deg: 90,
                from: '#00b4eb',
                to: '#eb3f45'
            }" style="fontWeight:900">
                欢迎使用本系统
            </n-gradient-text>

        </div>
        <div id="welcome-text">
            请选择并点击左侧导航栏中的模块以开始使用
        </div>
    </div>
</template>

<style>
#welcome-wrapper {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

#welcome-header {
    font-size: 80px;
    margin: 30px;
}

#welcome-text {
    font-size: 20px;
    margin: 30px;
}

</style>