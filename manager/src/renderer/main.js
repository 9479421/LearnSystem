import Vue from 'vue'
import axios from 'axios'

import App from './App'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
Vue.use(Antd);
import superagent from "superagent";
Vue.prototype.$superagent = superagent
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
import {Message} from 'element-ui'
Vue.prototype.$message = Message;


axios.defaults.baseURL='http://localhost:9090/manager/'
axios.interceptors.response.use(
  response=>{
    if(response.data.code==200){
      Message.success(response.data.msg);
    }
    if(response.data.code==201){
      Message.warning(response.data.msg);
    }
    if(response.data.code==444){
      Message.error(response.data.msg);
    }
    return response;
  }
)


if (!process.env.IS_WEB) Vue.use(require('vue-electron'))
Vue.axios = Vue.prototype.$axios = axios
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  components: { App },
  router,
  store,
  template: '<App/>'
}).$mount('#app')
