<template>
  <div class="header text-white">
    <img
      style="margin-left: 10px; width: 40px; height: 40px; border-radius: 50%"
      src="https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/1.webp"
      alt=""
    />
    <span style="margin-left: 10px; color: aliceblue; margin-right: 20px"
      >在线学习系统管理器</span
    >
    <iframe
      ref="tianqi"
      frameborder="0"
      width="200px"
      height="36"
      scrolling="no"
      hspace="0"
      src="https://i.tianqi.com/?c=code&id=99"
      style="margin-left: 20px"
    >
    </iframe>

    <span class="float-right">
      <span style="margin-left: 10px; color: aliceblue; margin-right: 20px"
        >作者QQ9479421</span
      >
      <i
        style="font-size: 20px; color: white"
        class="el-icon-minus opt"
        @click="toMin()"
      ></i>
      <i
        style="font-size: 20px; color: white"
        class="el-icon-close opt"
        @click="toClose()"
      ></i>
    </span>
  </div>
</template>

<script>
import { ipcRenderer } from "electron";
export default {
  name: "MyHeader",
  mounted() {
    const iframe = this.$refs["tianqi"];
    iframe.addEventListener("load", () => {
      const a = iframe.contentDocument.querySelector(".t1");
      a.style.color = "white";
    });
  },
  data() {
    return {
        
    };
  },
  methods: {
    toMin() {
      ipcRenderer.send("min-app");
    },
    toClose() {
      ipcRenderer.send("close-app");
    },
  },
};
</script>

<style lang="scss">
.header {
  width: 100%;
  height: 50px;
  background-color: rgb(118, 201, 181);
  display: flex;
  align-items: center;
  -webkit-app-region: drag; // 设置可拖动 但是收不到@click事件
}
.float-right {
  position: absolute;
  right: 10px;
}
.opt{
    -webkit-app-region: no-drag;
}
</style>
