<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="6">
        <div style="margin-left:5px">
          <el-scrollbar>
            <el-upload
              accept=".mp4"
              list-type="text"
              action="#"
              ref="upload"
              class="upload-demo"
              drag
              multiple
              :auto-upload="true"
              :http-request="uploadHttpRequest"
              :on-remove="handleRemove"
              :on-change="uploadVideoProcess"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">
                将视频文件拖到此处，或
                <em>点击上传</em>
              </div>
              <div class="el-upload__tip" slot="tip"></div>
            </el-upload>

            <el-progress :percentage="loadProgress"></el-progress>
            <h1
              v-if="this.videoFiles.length == 0"
              style="text-align: center; margin-top: 50px"
            >等待文件上传…</h1>
          </el-scrollbar>

          <el-button
            style="
            width: 90%;
            margin-left: 15px;
            margin-top: 8px;
            background-color: rgb(0, 255, 255);
            color: black;
          "
            type="success"
            @click="uploadVideos()"
          >上传服务器</el-button>
          <el-button
            style="width: 90%; margin-left: 15px; margin-top: 8px"
            type="danger"
            @click="clearFileList()"
          >清空列表</el-button>
        </div>
      </el-col>
      <el-col :span="18">
        <el-scrollbar style="height:870px">
          <el-table border :data="videoList">
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="path" label="path"></el-table-column>
            <el-table-column prop="digest" label="digest"></el-table-column>
            <el-table-column label="realPath" width="400">
              <template slot-scope="scope">
                <video-player
                  style="width: 100%; margin: 0 auto"
                  class="video-player vjs-custom-skin"
                  ref="videoPlayer"
                  :playsinline="true"
                  :options="getOptions(scope.row.realPath)"
                ></video-player>
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { videoPlayer } from "vue-video-player";
import "video.js/dist/video-js.css";
import "vue-video-player/src/custom-theme.css";
export default {
  components: {
    videoPlayer
  },
  mounted() {
    this.readVideoList();
  },
  data() {
    return {
      loadProgress: 0, // 动态显示进度条
      videoFiles: [],

      videoList: []
    };
  },
  methods: {
    getOptions(src) {
      return {
        playbackRates: [0.7, 1.0, 1.25, 1.5, 2.0], // 播放速度
        autoplay: false, // 如果true,浏览器准备好时开始回放。
        muted: false, // 默认情况下将会消除任何音频。
        loop: false, // 导致视频一结束就重新开始。
        preload: "auto", // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: "zh-CN",
        aspectRatio: "16:9", // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时, player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        sources: [
          {
            type: "video/mp4", // 类型
            src: src //url地址
          }
        ],
        poster: "", // 封面地址
        notSupportedMessage: "此视频暂无法播放，请稍后再试", // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controlBar: {
          timeDivider: true, // 当前时间和持续时间的分隔符
          durationDisplay: true, // 显示持续时间
          remainingTimeDisplay: false, // 是否显示剩余时间功能
          fullscreenToggle: false // 全屏按钮
        }
      };
    },
    readVideoList() {
      this.$axios.get("/listVideos").then(res => {
        this.videoList = res.data.data;
      });
    },
    uploadHttpRequest(param) {
      this.videoFiles.push(param.file);
      console.log("自定义上传", param);
    },
    uploadVideos() {
      if (this.videoFiles.length == 0) {
        this.$message.warning("请先上传文件再上传服务器");
        return;
      }
      var videoForm = new FormData();
      for (var i = 0; i < this.videoFiles.length; i++) {
        videoForm.append("file", this.videoFiles[i]);
      }
      this.$axios
        .post("/addVideo", videoForm, {
          Headers: { "Content-Type": "multipart/form-data" }
        })
        .then(res => {
          console.log(res.data);
          this.videoFiles = [];
          this.$refs.upload.clearFiles();
          this.readVideoList();
        });
    },
    handleRemove(file, fileList) {
      console.log(file);
      console.log(fileList);
      console.log(this.videoFiles);
      for (var i = 0; i < this.videoFiles.length; i++) {
        if (this.videoFiles[i].uid == file.uid) {
          this.videoFiles.splice(i, 1);
        }
      }
    },
    clearFileList() {
      if (this.videoFiles.length == 0) {
        this.$message.warning("文件列表已空");
        return;
      }
      this.videoFiles = [];
      this.$refs.upload.clearFiles();
      this.$message.success("清空完成");
    },
    uploadVideoProcess(file, fileList) {
      console.log(file.status);
      if (file.status === "ready") {
        this.loadProgress = 0;
        const interval = setInterval(() => {
          this.loadProgress += 1;
          if (this.loadProgress >= 99) {
            this.loadProgress += 1;
            clearInterval(interval);
            return;
          }
        }, 20);
      }
    }
  }
};
</script>
<style lang="scss" scoped>
/deep/ .el-upload-dragger {
  width: 310px;
  height: 200px;
}

/deep/ .el-upload-list__item > .el-upload-list__item-name {
  white-space: normal;
}

/deep/ .el-scrollbar__wrap {
  overflow-x: hidden;
}
</style>