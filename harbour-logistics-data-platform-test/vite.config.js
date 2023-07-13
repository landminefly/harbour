import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { NaiveUiResolver } from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
	base:"/hldp/",
	plugins: [
		vue(),
		//自动导入naive-ui所需组件
		AutoImport({
			imports: [
			  'vue',
			  {
				'naive-ui': [
				  'useDialog',
				  'useMessage',
				  'useNotification',
				  'useLoadingBar'
				]
			  }
			]
		  }),
		  Components({
			resolvers: [NaiveUiResolver()]
		  })
		],
	resolve: {
		alias: {
			"@": fileURLToPath(new URL("./src", import.meta.url)),
		},
	},
	// //服务器代理，开发环境使用
	// server: {
	// 	host:true,
	// 	proxy: {
	// 	  '/api': {
	// 		target: 'http://36.140.31.145:32612',
	// 		rewrite: (path) => path.replace(/^\/api/, ''),
	// 		changeOrigin: true,
	// 	  },
	// 	}
	// }
});
