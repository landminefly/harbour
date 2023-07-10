//导入vue-router
import { createRouter, createWebHashHistory } from "vue-router";
//导入vue
import { createApp } from "vue";
//导入根组件
import App from "./App.vue";
//导入路由组件
import redirect from "./components/redirect.vue";
import login from "./components/login.vue";
import admin from "./components/admin.vue";
import cust from "./components/cust.vue";
import dataSourceManagement from './components/data-source-management.vue';
import logisticsDataView from './components/logistics-data-view.vue';
import cusDataView from './components/cus-data-view.vue';
import companyDataView from './components/company-data-view.vue';
import dataAnalysis from './components/data-analysis.vue';
//导入vuex
import { createStore } from "vuex";
//清除默认样式
import "./assets/reset.css";
//导入主样式
import "./assets/main.css";
//导入vuex-persist持久化插件
import VuexPersistence from 'vuex-persist'

//设置vuex-persist
const vuexLocal = new VuexPersistence({
    storage: window.sessionStorage,
})
//设置vuex
const store = createStore({
	state() {
		return {
			isLogged: 0,
			isDarkMode: false,
		};
	},
	mutations: {
		logout(state) {
			state.isLogged = 0;
		},
		loginAsCust(state) {
			state.isLogged = 1;
		},
		loginAsAdmin(state) {
			state.isLogged = 2;
		},
		toggleDarkMode(state){
			state.isDarkMode = !state.isDarkMode;
		}
	}, 
	//注册vuex-persist
	plugins: [vuexLocal.plugin]
});

//设置路由、路由守卫
const routes = [
	{
		path: "/",
		component: redirect,
	},
	{
		path: "/login",
		component: login,
		beforeEnter: (to, from) => {
			switch (store.state.isLogged) {
				case 0:
					return true;
				case 1:
					alert("您已经以访客身份登录，正在为您跳转");
					return '/cust';
				case 2:
					alert("您已经以管理员身份登录，正在为您跳转");
					return '/admin/source';
			}
		},
	},
	{
		path: "/cust",
		component: cust,
		children:[
			{
				path:'data/logistics',
				component: logisticsDataView
			},
			{
				path:'data/cus',
				component: cusDataView
			},
			{
				path:'data/company',
				component: companyDataView
			},
			{
				path:'analysis',
				component: dataAnalysis
			}
		],
		beforeEnter: (to, from) => {
			switch (store.state.isLogged) {
				case 0:
					alert("您未登录，正在为您跳转");
					return '/login';
				case 1:
					return true;
				case 2:
					alert("您已经以管理员身份登录，正在为您跳转");
					return '/admin/source';
			}
		},
	},
	{
		path: "/admin",
		component: admin,
		children:[
			{
				path:'source',
				component: dataSourceManagement
			},
			{
				path:'data/logistics',
				component: logisticsDataView
			},
			{
				path:'data/cus',
				component: cusDataView
			},
			{
				path:'data/company',
				component: companyDataView
			},
			{
				path:'analysis',
				component: dataAnalysis
			}
		],
		beforeEnter: (to, from) => {
			switch (store.state.isLogged) {
				case 0:
					alert("您未登录，正在为您跳转");
					return '/login';
				case 1:
					alert("您已经以访客身份登录，正在为您跳转");
					return '/cust';
				case 2:
					return true;
			}
		},
	},
];

const router = createRouter({
	history: createWebHashHistory(), //使用历史模式
	routes, // routes: routes 的缩写
});

//注册应用
const app = createApp(App);
//注册路由
app.use(router);
//注册vuex
app.use(store);
//挂载
app.mount("#app");
