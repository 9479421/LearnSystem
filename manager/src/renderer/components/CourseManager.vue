<template>
  <div>
    <a-button
      style="
        margin: 10px 0 10px 20px;
        width: 60px;
        height: 60px;
        font-size: 26px;
      "
      type="primary"
      shape="circle"
      icon="plus"
      @click="openAddCourseWindow()"
    />
    <a-button
      style="
        margin: 10px 0 10px 20px;
        width: 60px;
        height: 60px;
        font-size: 26px;
      "
      shape="circle"
      icon="reload"
      @click="readCourseData()"
    />
    <el-select
      style="margin-left: 20px"
      v-model="searchType"
      placeholder="请选择"
    >
      <el-option
        v-for="item in searchOptions"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      >
      </el-option>
    </el-select>
    <el-input
      @input="readCourseData()"
      style="margin-left: 10px; width: 300px"
      v-model="searchText"
    ></el-input>
    <el-button
      style="margin: 10px 0 10px 20px; position: absolute; right: 10px"
      type="primary"
      >导出<i class="el-icon-download el-icon--right"></i
    ></el-button>
    <a-table
      :customRow="handleClickRow"
      :pagination="pagination"
      :columns="columns"
      :data-source="courseList"
    >
      <span slot="coverImg" slot-scope="coverImg">
        <img style="width: 100px; height: 50px" :src="coverImg" alt="" />
      </span>
      <span slot="teacher" slot-scope="teacher">
        <el-tag type="success" size="medium">{{ teacher }}</el-tag>
      </span>
      <span slot="name" slot-scope="name">
        <h4 style="color: cornflowerblue">{{ name }}</h4>
      </span>
      <span @click.stop="" slot="isPutAway" slot-scope="text, record">
        <!--record是对应的数据-->
        <el-switch
          style="display: block"
          v-model="record.isPutAway"
          active-color="#13ce66"
          @change="changePutAwayStatus(record.courseId, record.isPutAway)"

        >
        </el-switch>
      </span>
      <span slot="action" slot-scope="text, record">
        <el-button
          type="primary"
          icon="el-icon-edit"
          circle
          @click.stop="openChangeCourseInfoWindow(record)"
        ></el-button>
        <el-button
          type="danger"
          icon="el-icon-delete"
          circle
          @click.stop="deleteCourse(record.courseId)"
        ></el-button>
      </span>
    </a-table>
    <a-modal
      v-model="addCourseWindowVisible"
      title="添加课程"
      @ok="addCourse()"
    >
      <a-form>
        <a-form-item label="课程名">
          <a-input v-model="addCourseForm.name"></a-input>
        </a-form-item>
        <a-form-item label="封面链接">
          <a-input v-model="addCourseForm.coverImg"></a-input>
          <div style="width: 100%; text-align: center">
            <img
              style="width: 200px; height: 100px"
              :src="addCourseForm.coverImg"
              alt=""
            />
          </div>
        </a-form-item>
        <a-form-item label="讲师">
          <a-input v-model="addCourseForm.teacher"></a-input>
        </a-form-item>
        <a-form-item label="课程介绍">
          <a-textarea v-model="addCourseForm.intro" :rows="3"></a-textarea>
        </a-form-item>
        <a-button @click="addCourse()">添加课程</a-button>
      </a-form>
    </a-modal>

    <el-dialog
      title="提示"
      :visible.sync="changeCourseInfoWindowVisible"
      width="30%"
    >
      <el-form>
        <el-form-item label="课程ID">
          <el-tag>{{ changeCourseInfoForm.courseId }}</el-tag>
        </el-form-item>
        <el-form-item label="课程名">
          <el-input v-model="changeCourseInfoForm.name"></el-input>
        </el-form-item>
        <el-form-item label="封面地址">
          <el-input v-model="changeCourseInfoForm.coverImg"></el-input>
          <div style="width: 100%; text-align: center">
            <img
              style="width: 200px; height: 100px"
              :src="changeCourseInfoForm.coverImg"
              alt=""
            />
          </div>
        </el-form-item>
        <el-form-item label="讲师">
          <el-input v-model="changeCourseInfoForm.teacher"></el-input>
        </el-form-item>
        <el-form-item label="介绍">
          <el-input
            type="textarea"
            :rows="3"
            v-model="changeCourseInfoForm.intro"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="changeCourseInfoWindowVisible = false"
          >取 消</el-button
        >
        <el-button type="primary" @click="changeCourseInfo()">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 视频管理 -->
    <el-drawer
      destroy-on-close
      size="70%"
      title="视频管理--"
      :visible.sync="videoManagerVisible"
      direction="rtl"
    >
      <VideoManager :currentRecord="currentRecord" />

    </el-drawer>

<!--     
    <el-drawer
        :modal-append-to-body="false"
        :append-to-body="true"
        class="list"
        size="40%"
        style="float: right; padding-right: 691px"
        :visible.sync="videoManagerVisible"
        direction="ltr"
        :modal="false"
        :with-header="false"
      >
        <el-button>哈哈哈</el-button>
      </el-drawer> -->
  </div>
</template>

<script>
import VideoManager from "./VideoManager.vue";
const columns = [
  {
    title: "课程Id",
    dataIndex: "courseId",
    key: "courseId",
  },
  {
    title: "课程名",
    dataIndex: "name",
    key: "name",
    scopedSlots: { customRender: "name" },
  },
  {
    title: "上架",
    dataIndex: "isPutAway",
    key: "isPutAway",
    scopedSlots: { customRender: "isPutAway" },
  },
  {
    title: "课程封面",
    dataIndex: "coverImg",
    key: "coverImg",
    scopedSlots: { customRender: "coverImg" },
  },
  {
    title: "课程简介",
    dataIndex: "intro",
    key: "intro",
  },
  {
    title: "讲师",
    dataIndex: "teacher",
    key: "teacher",
    scopedSlots: { customRender: "teacher" },
  },
  {
    title: "Action",
    key: "action",
    scopedSlots: { customRender: "action" },
  },
];
export default {
  created() {
    this.readCourseData();
  },
  data() {
    return {
      columns,
      courseList: [],
      pagination: {
        defaultPageSize: 8,
        showTotal: (total) => `共 ${total} 条数据`,
        showSizeChanger: true,
        pageSizeOptions: ["8", "10", "15", "20"],
        onShowSizeChange: (current, pageSize) => (this.pageSize = pageSize),
      },
      addCourseWindowVisible: false,
      addCourseForm: {
        name: "",
        coverImg: "",
        teacher: "",
        intro: "",
      },
      searchOptions: [
        {
          value: "all",
          label: "全部",
        },
        {
          value: "courseId",
          label: "课程ID",
        },
        {
          value: "name",
          label: "课程名",
        },
        {
          value: "teacher",
          label: "讲师名",
        },
        {
          value: "coverImg",
          label: "封面链接",
        },
      ],
      searchType: "all",
      searchText: "",
      changeCourseInfoWindowVisible: false,
      changeCourseInfoForm: {
        courseId: "",
        name: "",
        coverImg: "",
        teacher: "",
        intro: "",
      },
      videoManagerVisible: false,
      currentRecord: {},
    };
  },
  methods: {
    addCourse() {
      this.$axios.post("addCourse", this.addCourseForm).then((res) => {
        if (res.data.code == 200) {
          this.readCourseData();
          this.addCourseForm = {};
          this.addCourseWindowVisible = false;
        }
      });
    },
    openAddCourseWindow() {
      this.addCourseWindowVisible = true;
    },
    readCourseData() {
      this.$axios
        .get(
          "getCourseList?searchType=" +
            this.searchType +
            "&searchText=" +
            this.searchText
        )
        .then((res) => {
          console.log(res);
          if (res.data.code == 200) {
            this.courseList = res.data.data;
          }
        });
    },
    openChangeCourseInfoWindow(record) {
      this.changeCourseInfoWindowVisible = true;
      this.changeCourseInfoForm.courseId = record.courseId;
      this.changeCourseInfoForm.name = record.name;
      this.changeCourseInfoForm.coverImg = record.coverImg;
      this.changeCourseInfoForm.teacher = record.teacher;
      this.changeCourseInfoForm.intro = record.intro;
    },
    changeCourseInfo() {
      this.changeCourseInfoWindowVisible = false;
      this.$axios
        .post("changeCourseInfo", this.changeCourseInfoForm)
        .then((res) => {
          if (res.data.code == 200) {
            this.readCourseData();
          }
        });
    },
    changePutAwayStatus(courseId, status) {
      if (status) {
        status = 1;
      } else {
        status = 0;
      }
      this.$axios
        .get("changePutAwayStatus?courseId=" + courseId + "&status=" + status)
        .then((res) => {
          if (res.data.code == 200) {
            this.readCourseData();
          }
        });
    },
    deleteCourse(courseId) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios.get("deleteCourse?courseId=" + courseId).then((res) => {
            if (res.data.code == 200) {
              this.readCourseData();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    handleClickRow(record, index) {
      return {
        on: {
          click: () => {
            console.log(record, index);
            console.log("点击行内容record" + record);
            console.log("序号索引index" + index);
            //跳出抽屉，管理视频
            console.log(record.courseId);

            this.currentRecord = record;
            this.videoManagerVisible = true;
          },
        },
      };
    },
  },
  components: { VideoManager },
};
</script>

<style>
.list > .el-drawer__container {
  position: relative;
  left: 0;
  right: 0;
  top: 490px;
  bottom: 0;
  height: 50%;
  width: 100%;
}

</style>
