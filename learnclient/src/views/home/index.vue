<template>
  <div ref="test">
    <!-- <el-image
      ref="test"
      style="width: 25%;"
      src="https://learnsys-1304194722.cos.ap-nanjing.myqcloud.com/10004/cover.png"
    />-->

    <el-row
      justify="center"
      v-for="(item, index) in course.rows"
      :gutter="10"
      style="margin: 0 auto; width: 99%;"
    >
      <el-col style="margin-top: 10px;" v-for="(it, index) in 4" :span="6">
        <el-tooltip placement="bottom">
          <template #content>
            <h3 v-html="formatText(course.list[(item - 1) * 4 + it - 1].intro)"></h3>
          </template>
          <el-card
            shadow="hover"
            :body-style="{ padding: '0px' }"
            v-if="course.list[(item - 1) * 4 + it - 1]"
          >
            <img
              class="coverImg"
              style="width: 100%;"
              :src="course.list[(item - 1) * 4 + it - 1].coverImg"
            />
            <div class="card-body">
              <h3 class="title">{{ course.list[(item - 1) * 4 + it - 1].name }}</h3>
              <span
                style="text-align: right; display: block;"
              >讲师：{{ course.list[(item - 1) * 4 + it - 1].teacher }}</span>
              <el-rate v-model="data.rate" />

              <h3 style="color: red;">¥免费</h3>
            </div>
            <el-popconfirm title="您确定添加学习该课程吗?" @confirm="addCourse(course.list[(item - 1) * 4 + it - 1].courseId)">
              <template #reference>
                <el-button
                  :type="selectStatus(course.list[(item - 1) * 4 + it - 1].isPutAway,course.list[(item - 1) * 4 + it - 1].isAdd)"
                  :disabled="!course.list[(item - 1) * 4 + it - 1].isPutAway || course.list[(item - 1) * 4 + it - 1].isAdd"
                  style="width: 100%;"
                  size="large"
                >{{selectText(course.list[(item - 1) * 4 + it - 1].isPutAway,course.list[(item - 1) * 4 + it - 1].isAdd)}}</el-button>
              </template>
            </el-popconfirm>
          </el-card>
        </el-tooltip>
      </el-col>
    </el-row>
    <el-pagination background style="margin-top: 20px;" layout="->,prev, pager, next" :total="100" />
  </div>
</template>
<script lang="ts" setup>
import api from "@/api";
import { ref, getCurrentInstance, reactive } from "vue";
const internalInstance = getCurrentInstance();
const http = internalInstance!.appContext.config.globalProperties.$http;

const data = ref({
  rate: 5
});

const course: any = ref({
  list: [],
  rows: 0
});

const state = reactive({
  height: "100px"
});

let test: any = ref(null);
onMounted(() => {
  getCourseList();
  //1.4的长度进行计算
  state.height = test.value.clientWidth * 0.25 * (200 / 356) + "px";
});
function getCourseList() {
  api.get("/course/listCourse?page=1").then((res: any) => {
    console.log(res);
    course.value.list = res.data;
    course.value.rows = Math.ceil(course.value.list.length / 4);
    console.log(course.value.rows);
  });
}
function addCourse(courseId: any) {
  api.get("/course/addCourse?courseId=" + courseId).then(res => {
    getCourseList();
  });
}
function selectText(isPutAway: Boolean, isAdd: String) {
  if (isPutAway == false) {
    return "已下架";
  } else {
    if (isAdd) {
      return "已添加";
    } else {
      return "加入课程";
    }
  }
}

function selectStatus(isPutAway: Boolean, isAdd: String) {
  if (isPutAway == false) {
    return "danger";
  } else {
    if (isAdd) {
      return "primary";
    } else {
      return "";
    }
  }
}

function formatText(text: any) {
  console.log(text);

  return text.replace("\\n", "<br/>");
}
</script>
<style scoped >
.card-body {
  width: 90%;
  margin: 0 auto;
}

.coverImg {
  height: v-bind("state.height");
}

.title {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  overflow: hidden;
  -webkit-box-orient: vertical;
  min-height: 50px;
}
</style>
