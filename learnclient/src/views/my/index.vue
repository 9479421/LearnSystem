<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="6" v-if="data.courseListVisible">
        <div style="width:100%">
          <div style="display: flex">
            <el-scrollbar :native="true">
              <div v-if="course.data.length==0" v-for="(item,index) in 3">
                <img
                  style="width:100%;"
                  src="https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/QQ截图20221021105305.png"
                />
              </div>
              <div v-if="course.data.length!=0" v-for="(item,index) in course.data">
                <el-card @click="enterCourse(item.courseId,item.intro)">
                  <img style="width:100%;" :src="item.coverImg" />
                  <h3>{{item.name}}</h3>
                  <el-progress
                    status="exception"
                    :text-inside="true"
                    :stroke-width="26"
                    :percentage="item.progress"
                  />
                </el-card>
              </div>
            </el-scrollbar>
          </div>
        </div>
      </el-col>

      <el-col :span="6" v-if="data.chapterListVisible">
        <el-table
          :row-class-name="editRowClassName"
          @row-click="selectChapter"
          :data="chapter.data"
        >
          <el-table-column prop="name" label="视频目录(1/60)">
            <template #header>
              <el-button @click="returnCourse()" :icon="DArrowLeft" />&nbsp;
              <span>
                视频目录&nbsp;
                <span style="color:cornflowerblue">(1/60)</span>
              </span>
            </template>

            <template #default="scope">
              <div>
                <h3>
                  {{scope.row.chapterName}}
                  <span
                    style="font-size:12px;float:right"
                  >{{scope.row.duration}}</span>
                </h3>

                <el-progress
                  style="margin-top:5px"
                  :show-text="false"
                  :percentage="scope.row.progress"
                />
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-col>

      <el-col :span="18">
        <div>
          <vue3VideoPlay
            @loadedmetadata="loadedmetadata"
            ref="video"
            v-bind="options"
            poster
            @timeupdate="timeupdate"
            @ended="ended"
          />
        </div>

        <div style="display:inline" v-if="course.data.length==0" v-for="(item,index) in 3">
          <img
            style="width:33%;margin-right:3px"
            src="https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/QQ截图20221021105305.png"
          />
        </div>
        <!-- 评论区 -->
        <el-card
          v-if="course.data.length!=0 && data.chapterListVisible"
          style="margin-top:10px"
          shadow="always"
        >
          <span style="font-size:24px">课程介绍</span>
          <br />
          <span
            v-html="formatText(data.intro)"
            style="color:rgb(43,32,80);font-size:18px;margin-top:10px;display:block"
          ></span>
          <div style="margin-top:20px"></div>

          <span style="font-size:24px">
            评论
            <span style="font-size:14px">共{{comment.data.length}}条评论</span>
          </span>
          <br />
          <el-timeline>
            <el-timeline-item center placement="top" v-for="(item,index) in comment.data">
              <el-card>
                <img
                  style="width:60px;height:60px"
                  :src="item.avatar"
                  alt
                />
                <h4 v-if="userStore.username!=item.username" style="display:inline;margin-left:10px">{{item.nickname}}[id:{{item.memberId}}]</h4>
                <h4 v-if="userStore.username==item.username" style="display:inline;margin-left:10px;color:red">我</h4>
                <el-divider></el-divider>
                <h3 v-html="formatText(item.content)"></h3>
                <el-divider></el-divider>
                <p style="text-align:right">{{item.username}} committed {{item.time}}</p>
              </el-card>
            </el-timeline-item>

          </el-timeline>

          <h4>来参与评论把~~~~</h4>
          <el-input v-model="commentForm.content" :autosize="{ minRows: 6}" type="textarea" placeholder="评论自己想说的话" />
          <el-button @click="addComment" style="width:100%" type="primary">发表评论</el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" setup>
import api from "@/api";
import { ref, reactive } from "vue";
import { DArrowLeft } from "@element-plus/icons-vue";
import useUserStore from '@/store/modules/user'
const userStore = useUserStore()
const chapter: any = ref({
  data: []
});
const course: any = ref({
  data: []
});

const comment:any = ref({
  data: []
})

onMounted(() => {
  getMyCourseList();
});

function formatText(comment:any){
  console.log(comment);
  return comment.replace(/\n/g, '<br/>').replace('\\n','<br/>')
}

function loadedmetadata(event: any) {
  video.value.pause();
}

function getMyCourseList() {
  api.get("/mycourse/getMyCourseList").then(res => {
    course.value.data = res.data;
  });
}
const options = reactive({
  width: "100%", //播放器高度
  height: "100%", //播放器高度
  color: "#409eff", //主题色
  title: "", //视频名称
  src:
    "", //视频源 https://wqby-1304194722.cos.ap-nanjing.myqcloud.com/img/1-所以你是在第几秒心动的呢？-1080P 高清-AVC.mp4
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

const data: any = ref({
  courseListVisible: true,
  chapterListVisible: false,
  commentListVisible: false,
  currentCourseId: "",
  currentChapterId: "",
  currentRecord: "", //防止重复记录使用
  nowRow: "",
  nextChapterId: "", //跳转下一集
  allTime: "",
  intro:""
});

function timeupdate(event: any) {
  var currentTime = parseInt(event.srcElement.currentTime);
  //5秒记录一次
  if (currentTime % 5 == 0 && currentTime != data.value.currentRecord) {
    data.value.currentRecord = currentTime;
    api
      .get(
        "/record/updateRecord?courseId=" +
          data.value.currentCourseId +
          "&chapterId=" +
          data.value.currentChapterId +
          "&currentTime=" +
          event.srcElement.currentTime +
          "&allTime=" +
          event.srcElement.duration
      )
      .then(res => {});
    getMyChapterList(data.value.currentCourseId);
  }
}

let video: any = ref(null);

function ended(event: any) {
  api
    .get(
      "/record/updateRecord?courseId=" +
        data.value.currentCourseId +
        "&chapterId=" +
        data.value.currentChapterId +
        "&currentTime=" +
        data.value.allTime +
        "&allTime=" +
        data.value.allTime
    )
    .then(res => {
      getMyChapterList(data.value.currentCourseId);
      //跳到下一个
      if(data.value.nowRow!=chapter.value.data.length){
        selectChapter(chapter.value.data[data.value.nowRow]);
      }

    });
}

function returnCourse() {
  data.value.courseListVisible = true;
  data.value.chapterListVisible = false;
  getMyCourseList();
}

function enterCourse(courseId: any,intro: any) {
  data.value.courseListVisible = false;
  data.value.chapterListVisible = true;
  data.value.currentCourseId = courseId;

  data.value.intro = intro

  getMyChapterList(courseId);
  getCommentList(courseId);

}
function getCommentList(courseId:any){
  api.get('/comment/getCommentList?courseId='+courseId).then(res=>{
    comment.value.data = res.data;
  })
}

function getMyChapterList(courseId: any) {
  api.get("/mycourse/getMyChapterList?courseId=" + courseId).then(res => {
    chapter.value.data = res.data;
    for (var i = 0; i < chapter.value.data.length; i++) {
      chapter.value.data[i].row = i + 1;
    }
  });
}
function selectChapter(row: any) {
  console.log(row);

  data.value.currentRecord = "";
  data.value.currentChapterId = row.chapterId;
  //下一个章节id
  data.value.nowRow = row.row;
  data.value.nextChapterId = chapter.value.data[row.row - 1].chapterId;
  options.src = row.path;
  options.currentTime = row.currentTime;
  data.value.allTime = row.allTime;
}

function editRowClassName({ row, rowIndex }: { row: any; rowIndex: number }) {
  if (rowIndex === data.value.nowRow - 1) {
    return "success-row";
  }
  return "";
}

const commentForm:any = ref({
  courseId:'',
  content:''
})
function addComment(){
  commentForm.value.courseId = data.value.currentCourseId;
  api.post('/comment/addComment',commentForm.value).then((res:any)=>{
    ElMessage({
        message: res.msg,
        type: 'success',
      })
    commentForm.value.content = ''
    getCommentList(data.value.currentCourseId);
  })
}
</script>
<style>
.el-scrollbar__wrap {
  overflow-x: hidden;
}
.el-table .success-row {
  background-color: rgb(32, 204, 226);
}
</style>
