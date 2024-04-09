<template>
  <div>
    <!-- <p>
      课程ID：<span>{{ currentRecord.courseId }}</span>
    </p>
    <p>
      课程名：<span>{{ currentRecord.name }}</span>
    </p>
    <p>
      讲师：<span>{{ currentRecord.teacher }}</span>
    </p>-->
    <el-descriptions class="margin-top" :column="2" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          课程名
        </template>
        {{ currentRecord.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          课程ID
        </template>
        {{ currentRecord.courseId }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          讲师
        </template>
        {{ currentRecord.teacher }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          上架状态
        </template>
        <el-switch
          disabled
          v-model="currentRecord.isPutAway"
          active-color="#13ce66"
          inactive-color="#ff4949"
        ></el-switch>
      </el-descriptions-item>
    </el-descriptions>

    <el-row>
      <el-col :span="8">
        <el-scrollbar
          style="height: 390px; margin-left: 5px; margin-top: 9px"
          wrap-style="overflow-x:hidden;"
        >
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
      </el-col>
      <el-col :span="16">
        <a-table
          bordered
          :pagination="false"
          style="margin-top: 10px"
          :scroll="{ y: 429 }"
          :data-source="chapterData"
          :columns="columns"
          :customRow="rowClick"
        >
          <span slot="operation" slot-scope="text, record">
            <a-button @click="openModifyWindow(record)">修改</a-button>
            <a-button @click="deleteChapter(record)">删除</a-button>
          </span>
        </a-table>
      </el-col>
    </el-row>

    <el-card class="box-card" style="margin-top: 10px; width: 40%">
      <div slot="header" class="clearfix">
        <span>视频预览</span>
      </div>

      <video-player
        style="width: 100%; margin: 0 auto"
        class="video-player vjs-custom-skin"
        ref="videoPlayer"
        :playsinline="true"
        :options="playerOptions"
      ></video-player>
    </el-card>

    <el-dialog append-to-body :visible.sync="modifyWindowVisible">
      章节id：
      <el-input style v-model="modifyForm.chapterId"></el-input>章节名：
      <el-input style v-model="modifyForm.chapterName"></el-input>
      <div style="margin-top:20px"></div>
      <el-button style type="primary" @click="saveChapterName()">保存</el-button>
      <br />
    </el-dialog>
  </div>
</template>
<script>
import { videoPlayer } from "vue-video-player";
import "video.js/dist/video-js.css";
import "vue-video-player/src/custom-theme.css";
const columns = [
  {
    title: "chapterId",
    dataIndex: "chapterId",
    key: "chapterId",
    width: 100
  },
  {
    title: "chapterName",
    dataIndex: "chapterName",
    key: "chapterName"
  },
  {
    title: "duration",
    dataIndex: "duration",
    key: "duration",
    width: 100
  },
  {
    title: "Operation",
    key: "operation",
    scopedSlots: { customRender: "operation" },
    width: 100
  }
];
export default {
  props: ["currentRecord"],
  components: {
    videoPlayer
  },
  mounted() {
    console.log(this.currentRecord.courseId, "哈哈哈");
    this.readChapter();
  },
  data() {
    return {
      playerOptions: {
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
            src: "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4" //url地址
          }
        ],
        poster: "", // 封面地址
        notSupportedMessage: "此视频暂无法播放，请稍后再试", // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controlBar: {
          timeDivider: true, // 当前时间和持续时间的分隔符
          durationDisplay: true, // 显示持续时间
          remainingTimeDisplay: false, // 是否显示剩余时间功能
          fullscreenToggle: true // 全屏按钮
        }
      },
      columns,
      chapterData: [],
      videoFiles: [],
      loadProgress: 0, // 动态显示进度条

      modifyWindowVisible: false,
      modifyForm: {
        id: "",
        chapterId: "",
        chapterName: ""
      }
    };
  },
  methods: {
    readChapter() {
      this.$axios
        .get("/getChapterList?courseId=" + this.currentRecord.courseId)
        .then(res => {
          this.chapterData = res.data.data;
          console.log(res);
          console.log("hahah");
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
        .post(
          "/addChapter?courseId=" + this.currentRecord.courseId,
          videoForm,
          {
            Headers: { "Content-Type": "multipart/form-data" }
          }
        )
        .then(res => {
          console.log(res.data);
          this.videoFiles = [];
          this.$refs.upload.clearFiles();
          this.readChapter();
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
    },
    openModifyWindow(row) {
      this.modifyWindowVisible = true;
      this.modifyForm.id = row.id;
      this.modifyForm.chapterId = row.chapterId;
      this.modifyForm.chapterName = row.chapterName;
    },
    deleteChapter(row) {
      console.log(row);
      this.$axios
        .get(
          "deleteChapter?id=" +
            row.id +
            "&keyPath=" +
            row.path +
            "&courseId=" +
            row.courseId
        )
        .then(res => {
          this.readChapter();
        });
    },
    saveChapterName() {
      this.$axios
        .post(
          "saveChapterName?newName=" +
            this.modifyForm.chapterName +
            "&id=" +
            this.modifyForm.id +
            "&chapterId=" +
            this.modifyForm.chapterId
        )
        .then(res => {
          this.readChapter();
          this.modifyWindowVisible = false;
        });
    },
    rowClick: function(record, index) {
      return {
        on: {
          click: () => {
            console.log("点击了我");
            this.playerOptions.sources[0].src = record.realPath;

          },

          dblclick: () => {
            console.log("双击了我");
          }

          // ...
        }
      };
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
</style>
