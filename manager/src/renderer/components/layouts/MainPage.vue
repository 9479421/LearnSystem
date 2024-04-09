<template>
  <div>
    <MyHeader></MyHeader>
    <a-tabs default-active-key="1" @change="callback" id="tabs">
      <a-tab-pane key="1" tab="课程管理">
        <CourseManager />
      </a-tab-pane>
      <a-tab-pane key="2" tab="外文在线管理">
        <EnglishOnline />
      </a-tab-pane>
      <a-tab-pane key="3" tab="信息配置"> 
        <InfoConfig/>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import CourseManager from "../CourseManager.vue";
import InfoConfig from "../InfoConfig.vue";
import EnglishOnline from "../EnglishOnline.vue";

import NProgress from "nprogress";
import "nprogress/nprogress.css";

import MyHeader from '../MyHeader.vue'
export default {
  components: {
    MyHeader,
    CourseManager,
    InfoConfig,
    EnglishOnline,
  },
  created(){
    NProgress.configure({ parent: '#tabs' });
  },
  data() {
    
    return {
      
    };
  },
  methods: {
    callback(){
      NProgress.start();
      NProgress.done();
    }
  },
};
</script>
