<script>
import { darkTheme } from 'naive-ui'
import { inject, ref } from "vue";
export default {
  setup() {
    const store = inject('store');
    const isDarkMode = store.state.isDarkMode;
    return {
      darkTheme,
      theme: ref(isDarkMode ? darkTheme : null)
    };
  },
  watch: {
    //初始化/更改全局黑夜模式
    '$store.state.isDarkMode': {
      handler(newValue) {
        document.body.style.backgroundColor = newValue ? '#010409' : '#ffffff';
        document.body.style.color = newValue ? '#e1e1e1' : '#213547';
        this.theme = newValue ? darkTheme : null
      },
      immediate: true
    },
    '$store.state.isLogged': {
      handler(newValue) {
        switch (newValue) {
          case 0:
            document.body.className = 'login';
            return;
          case 1:
            document.body.className = 'cust';
            return;
          case 2:
            document.body.className = 'admin';
            return;
        }
      },
      immediate: true
    }
  }
}
</script>

<template>
  <n-config-provider :theme="theme">
    <router-view></router-view>
  </n-config-provider>
</template>

<style></style>