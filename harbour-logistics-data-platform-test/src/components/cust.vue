<script>
import darkModeBtn from './dark-mode-btn.vue';
import { h } from "vue";
import { RouterLink } from "vue-router";
//导入图标库以及图标字体
import { NIcon } from "naive-ui";
import {
  Data2,
} from "@vicons/carbon";
import {
  BarChartOutline,
} from "@vicons/ionicons5";

export default {
  components: {
    darkModeBtn,
  },
  //获取浏览器当前打开的模块
  setup(){
    var reg = /[^\/]*$/;
    var results = reg.exec(location);
    if(results[0] == 'cust'){
      results[0]  = null;
    }
    return{
      results,
    }
  },
  data() {
    return {
      //侧边栏、模块wrapper的高
      custModuleSideHeight: '720px',
      //分别是模块wrapper的宽、动画
      custModuleWrapperWidth: '85%',
      //在收起、展开侧边栏时需要使用
      custModuleWrapperTransition: 'all 0.3s',
      //是否展开侧边栏
      isSidebarShown: true,
      //当前选择的模块，默认为浏览器当前打开的模块
      selectedModule: this.results[0],
      //设置导航
      menuOptions: [
        {
          label: "数据查看",
          key: "data",
          icon: () => h(NIcon, null, { default: () => h(Data2) }),
          children: [
            {
              label: () => h(
                RouterLink,
                {
                  to: {
                    path: "/cust/data/logistics"
                  }
                },
                { default: () => "物流数据" }
              ),
              key: "logistics",
            },
            {
              label: () => h(
                RouterLink,
                {
                  to: {
                    path: "/cust/data/cus"
                  }
                },
                { default: () => "客户数据" }
              ),
              key: "cus",
            }, {
              label: () => h(
                RouterLink,
                {
                  to: {
                    path: "/cust/data/company"
                  }
                },
                { default: () => "物流公司数据" }
              ),
              key: "company",
            },
          ]
        },
        {
          key: "divider-2",
          type: "divider",
          props: {
            style: {
              margin: "20px 32px"
            }
          }
        },
        {
          label: () => h(
            RouterLink,
            {
              to: {
                path: "/cust/analysis"
              }
            },
            { default: () => "数据分析" }
          ),
          key: "analysis",
          icon: () => h(NIcon, null, { default: () => h(BarChartOutline) }),
        }
      ],
      //存储黑夜模式各个布局的颜色
      darkModeColor: {
        header: null,
        sideBar: null,
        module: null,
        font: null,
      },
    }
  },
  methods: {
    //登出
    custLogout() {
      this.$store.commit('logout');
      this.$router.push('/login');
    },
    //当视口变化时，需要判断并更改侧边栏、模块wrapper的高
    updateCustModuleSideHeight() {
      const windowInnerHeight = window.innerHeight;
      if (windowInnerHeight < 800) {
        this.custModuleSideHeight = `720px`;
      } else {
        this.custModuleSideHeight = `${windowInnerHeight - 80}px`;
      }
    },
  },
  watch: {
    //展开、收起侧边栏的模块wrapper的样式和动画设置
    isSidebarShown(newValue) {
      if (newValue === false) {
        this.custModuleWrapperWidth = '100%';
        this.custModuleWrapperTransition = 'all 0.3s';
      } else if (newValue === true) {
        this.custModuleWrapperWidth = '85%';
        this.custModuleWrapperTransition = 'all 0.3s 0.3s';
      }
    },
    //更改黑夜模式
    '$store.state.isDarkMode': {
      handler(newValue) {
        this.darkModeColor.header = newValue ? '#103f91' : '#41a5ee';
        this.darkModeColor.sideBar = newValue ? '#18181c' : '#e8f8ff';
        this.darkModeColor.module = newValue ? '#101014' : '#f4f6f9';
        this.darkModeColor.font = newValue ? '#e1e1e1' : '#213547';
      },
      //页面首次加载时初始化
      immediate: true
    },
  },
  mounted() {
    // 监听视口改变事件
    window.addEventListener('resize', this.updateCustModuleSideHeight);
    this.updateCustModuleSideHeight()
  },
  beforeDestroy() {
    // 移除视口改变事件监听器
    window.removeEventListener('resize', this.updateCustModuleSideHeight);
  },
}
</script>

<template>
  <div id="cust-wrapper">

    <!-- 头部 -->
    <div id="cust-header" :style="{ backgroundColor: darkModeColor.header }">

      <div id="cust-title-wrapper">
        <div id="cust-title">港口物流信息数据中台系统</div>
      </div>

      <div id="cust-welcome">
        你好，访客
      </div>
      <div class="header-divider">
      </div>

      <div id="cust-dark-mode-btn-wrapper">
        <div>
          <darkModeBtn></darkModeBtn>
        </div>
      </div>

      <button @click="custLogout" id="cust-logout"></button>
      <label for="cust-logout" id="cust-logout-label">登出</label>

    </div>

    <!-- 侧边栏 -->
    <Transition name="cust-sidebar">
      <div id="cust-sidebar" v-if="isSidebarShown"
        :style="{ height: custModuleSideHeight, backgroundColor: darkModeColor.sideBar }">

        <!-- 收起侧边栏按钮 -->
        <div id="collapse-sidebar-btn" @click="isSidebarShown = false">
          <span>收起侧边栏</span>&nbsp&nbsp&nbsp&nbsp&nbsp<span class="iconfont icon-zuojiantou"></span>
        </div>

        <!-- 导航 -->
        <n-menu id="side-bar" :options="menuOptions" v-model:value="selectedModule" />

      </div>
    </Transition>

    <!-- 展开侧边栏按钮 -->
    <Transition name="expand-sidebar-btn">
      <div id="expand-sidebar-btn" v-if="isSidebarShown === false" @click="isSidebarShown = true">
        <span>展开侧边栏</span>&nbsp&nbsp&nbsp&nbsp&nbsp<span class="iconfont icon-youjiantou"></span>
      </div>
    </Transition>

    <n-message-provider>
      <!-- 模块wrapper -->
      <div id="cust-module-wrapper"
        :style="{ height: custModuleSideHeight, width: custModuleWrapperWidth, transition: custModuleWrapperTransition, backgroundColor: darkModeColor.module }">

        <router-view></router-view>

      </div>
    </n-message-provider>

  </div>
</template>

<style>
/* 页面主样式 */
#cust-wrapper {
  width: 100%;
  min-height: 800px;
  height: 100vh;
  position: relative;
}

/* 以下为顶部样式 */
#cust-header {
  height: 80px;
  width: 100%;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
  box-sizing: border-box;
  position: absolute;
  transition: all 0.3s;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  /* 第一 */
  z-index: 999;
}

/* 标题样式 */
#cust-title-wrapper {
  position: absolute;
  left: 30px;
  height: 80px;
  line-height: 80px;
  width: 400px;
  font-size: 30px;
  font-weight: 900;
  text-align: start;
}

/* 登出按钮样式 */
#cust-logout {
  display: none;
}

#cust-logout-label {
  cursor: pointer;
  display: block;
  height: 35px;
  width: 60px;
  line-height: 35px;
  text-align: center;
  font-weight: 900;
  border-radius: 10px;
  background-color: rgba(255, 0, 0, 1);
  position: relative;
  top: 50%;
  left: -10px;
  transform: translateY(-50%);
  transition: all 0.2s;
}

#cust-logout-label:hover {
  transform: translateY(-50%) scale(1.1);
}

#cust-logout-label:active {
  transform: translateY(-50%) scale(1);
}

/* 黑夜模式按钮样式 */
#cust-dark-mode-btn-wrapper {
  height: 100%;
  width: 130px;
}

#cust-dark-mode-btn-wrapper>div {
  position: absolute;
  top: 20px;
  transform: scale(0.6);
}

/* 欢迎语样式 */
#cust-welcome{
  height: 80px;
  line-height: 80px;
  margin:0 10px;
  font-size: 16px;
}

/* 头部分割线样式 */
.header-divider{
  height: 30px;
  border: 1px solid #2f658e;
  margin: 25px 5px;
}

/* 以下为左侧导航栏样式 */
#cust-sidebar {
  width: 15%;
  padding-top: 100px;
  box-sizing: border-box;
  box-shadow: 5px 0px 10px rgba(0, 0, 0, 0.2);
  position: absolute;
  top: 80px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  /* 第二 */
  z-index: 99;
}

#side-bar {
  font-size: 16px;
  text-align: left;
}

/* 收起侧边栏按钮样式 */
#collapse-sidebar-btn {
  cursor: pointer;
  height: 50px;
  width: 170px;
  border-radius: 0 10px 10px 0;
  text-align: center;
  font-size: 18px;
  line-height: 50px;
  background-color: #d2691e;
  position: absolute;
  top: 10px;
  left: -120px;
  transition: all 0.3s;
}

/* 左侧导航栏收起动画 */
.cust-sidebar-leave-active {
  transition: all 0.3s;
}

.cust-sidebar-enter-active {
  transition: all 0.3s 0.29s;
}

.cust-sidebar-leave-to,
.cust-sidebar-enter-from {
  transform: translateX(-100%);
}

#collapse-sidebar-btn>span:last-child {
  font-weight: 900;
}

#collapse-sidebar-btn:hover {
  left: 0;
}

/* 展开侧边栏按钮样式 */
#expand-sidebar-btn {
  cursor: pointer;
  height: 50px;
  width: 180px;
  border-radius: 0 10px 10px 0;
  text-align: center;
  font-size: 20px;
  line-height: 50px;
  background-color: chocolate;
  position: absolute;
  top: 90px;
  left: -130px;
  /* 第三 */
  z-index: 99;
  transition: all 0.3s;
}

#expand-sidebar-btn>span:last-child {
  font-weight: 900;
}

#expand-sidebar-btn:hover {
  left: 0;
}

/* 左侧导航栏展开动画 */
.expand-sidebar-btn-enter-active {
  transition: all 0.3s 0.3s;
}

.expand-sidebar-btn-leave-active {
  transition: all 0.3s;
}

.expand-sidebar-btn-enter-from,
.expand-sidebar-btn-leave-to {
  transform: translateX(-100%);
}

/* 模块wrapper样式 */
#cust-module-wrapper {
  position: absolute;
  right: 0;
  top: 80px;
  /* 第三 */
  z-index: 9;
}

.module-enter-active,
.module-leave-active {
  transition: opacity 0.3s;
}

.module-enter-from,
.module-leave-to {
  opacity: 0;
}
</style>