import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import App from './App.vue'
import pinia from './store'
import router from './router'
import useSettingsStore from './store/modules/settings'

import axios from 'axios'



// 自定义指令
import directive from '@/utils/directive'
// 加载 svg 图标
import 'virtual:svg-icons-register'
// 全局样式
import '@/assets/styles/globals.scss'
// 加载 iconify 图标（element plus）
import { downloadAndInstall } from '@/iconify-ep'
if (useSettingsStore().app.iconifyOfflineUse) {
  downloadAndInstall()
}

const app = createApp(App)
app.config.globalProperties.$http = axios;
axios.defaults.baseURL='http://localhost:9090/client'

app.use(ElementPlus)
app.use(pinia)
app.use(router)
directive(app)

import vue3videoPlay from 'vue3-video-play' // 引入组件
import 'vue3-video-play/dist/style.css' // 引入css
app.use(vue3videoPlay)


import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
app.use(Antd)


app.mount('#app')
