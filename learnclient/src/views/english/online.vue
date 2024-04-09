<template>
  <div>
    <vue3VideoPlay
      @loadedmetadata="loadedmetadata"
      ref="video"
      v-bind="options"
      poster
      @ended="ended"
    />
  </div>
</template>
<script setup lang="ts">
import api from "@/api";
import { ref, reactive } from "vue";

let video: any = ref(null)

const options = reactive({
  width: "60%", //播放器高度
  height: "720px", //播放器高度
  color: "#409eff", //主题色
  title: "", //视频名称
  src:
    "", //视频源
  muted: false, //静音
  webFullScreen: false,
  speedRate: ["0.75", "1.0", "1.25", "1.5", "2.0"], //播放倍速
  autoPlay: false, //自动播放
  loop: false, //循环播放
  mirror: false, //镜像画面
  ligthOff: false, //关灯模式
  volume: 0.3, //默认音量大小
  control: true, //是否显示控制器
  speed: true, //控制快进
  currentTime: 0,
  notSupportedMessage: "此视频暂无法播放，请稍后再试"
});


function loadedmetadata(event: any) {
  video.value.play();
}

onMounted(()=>{
  getURL();
});

function ended(event: any) {
  getURL();
}

function getURL(){
  api.get('/english/getRandomVideo').then(res=>{
    options.src= res.data.url;
  })
}

</script>
<style>
#refPlayerWrap {
  margin: 0 auto;
}
</style>
