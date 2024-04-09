<route lang="yaml">
name: dashboard
meta:
  title: 控制台
</route>

<script lang="ts" setup>
function open(url: string) {
  window.open(url, "top");
}

const fantasticStartkitInfo = ref({
  feature: [
    "支持 TypeScript",
    "默认集成vue3 vue-router 和 pinia",
    "全局store和token管理",
    "支持 mock 数据，可摆脱后端束缚独立开发",
    "支持 gzip / brotli 优化项目体积，提高加载速度",
    "结合 IDE 插件、ESlint 、stylelint 、Git 钩子，轻松实现团队代码规范"
  ]
});

const fantasticAdminInfo = ref({
  imageVisible: false,
  index: 0,
  data: [
    "https://hooray.gitee.io/fantastic-admin/preview1.png",
    "https://hooray.gitee.io/fantastic-admin/preview2.png",
    "https://hooray.gitee.io/fantastic-admin/preview3.png",
    "https://hooray.gitee.io/fantastic-admin/preview4.png",
    "https://hooray.gitee.io/fantastic-admin/preview5.png",
    "https://hooray.gitee.io/fantastic-admin/preview6.png"
  ]
});

const oneStepAdminInfo = ref({
  imageVisible: false,
  index: 0,
  data: [
    "https://hooray.gitee.io/one-step-admin/preview1.png",
    "https://hooray.gitee.io/one-step-admin/preview2.png",
    "https://hooray.gitee.io/one-step-admin/preview3.png",
    "https://hooray.gitee.io/one-step-admin/preview4.png",
    "https://hooray.gitee.io/one-step-admin/preview5.png",
    "https://hooray.gitee.io/one-step-admin/preview6.png"
  ]
});

const activities = [
  {
    content: "实现验证码注册及登录业务逻辑实现",
    timestamp: "2022-10-24"
  },
  {
    content: "实现个人信息修改模块",
    timestamp: "2022-10-24"
  },
  {
    content: "更新我的课程业务模块",
    timestamp: "2022-10-16"
  },
  {
    content: "完成课程商城页面开发",
    timestamp: "2022-10-12"
  },
  {
    content: "开始基于脚手架与模板修改路由与基本配置",
    timestamp: "2022-10-10"
  }
];
</script>

<template>
  <div>
    <page-header title="欢迎使用 个人学习平台" style="margin-top:10px">
      <template #content>
        <div>
          <div style="margin-bottom: 5px;">
            这是一款
            <b class="text-emphasis">精简易用</b>的学习平台，同时它也经历过数十个真实项目的技术沉淀，确保框架在开发中可落地、可使用、可维护
          </div>
          <div>注：本项目仅限个人学习使用</div>
        </div>
      </template>
      <el-button-group style="margin-right: 10px;">
        <el-button type="success" size="large" plain @click="open('http://www.wqby.vip')">作者主页</el-button>
        <el-dropdown>
          <el-button type="primary" size="large">
            代码仓库
            <el-icon class="el-icon--right">
              <svg-icon name="ep:arrow-down" />
            </el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="open('https://gitee.com/hooray/fantastic-admin')">Gitee</el-dropdown-item>
              <el-dropdown-item @click="open('https://github.com/hooray/fantastic-admin')">Github</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-button-group>
    </page-header>

    <el-row :gutter="20" style="margin: 0 2px;">
      <el-col :lg="16">
        <page-main style="margin: 0;">
          <h2>更新日志</h2>
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in activities"
              :key="index"
              :timestamp="activity.timestamp"
            >{{ activity.content }}</el-timeline-item>
          </el-timeline>
        </page-main>
        <page-main title="应用场景" style="margin: 10px 0;">
          <div class="question">
            <ol class="answer">
              <li>
                <span>个人的毕业设计项目</span> 基于Springboot+vue的在线学习系统的设计与实现
              </li>
              <li>
                <span>学习平台安全漏洞分析Demo</span> 现有网课平台普遍存在安全性问题，本项目可用于爬虫范例，懂进攻，知防守
              </li>
              <li>
                <span>个人开发者</span> 没有使用开源案例，纯手构
              </li>
            </ol>
          </div>
        </page-main>
      </el-col>
      <el-col :lg="8">
        <page-main style="margin: 0;">
          <div class="ecology vue">
            <div class="main">
              <img src="../assets/images/logo.png" style="width: 30%;" />
              <h1>个人学习平台</h1>
              <h2>一个简易的在线学习网</h2>
            </div>
            <el-divider border-style="dashed" />
            <ul>
              <li v-for="item in fantasticStartkitInfo.feature" :key="item">{{ item }}</li>
            </ul>
          </div>
        </page-main>
      </el-col>
    </el-row>

  </div>
</template>

<style lang="scss" scoped>
.text-emphasis {
  text-emphasis-style: "❤";
}

.ecology {
  padding: 10px 0 0;

  &.vue {
    h1 {
      color: #41b883;
    }
  }

  &.fa {
    h1 {
      color: #e60000;
    }
  }

  &.osa {
    h1 {
      color: #67c23a;
    }
  }

  .main {
    text-align: center;

    img {
      display: block;
      margin: 0 auto;
    }

    h1 {
      margin: 10px auto 0;
      text-align: center;
    }

    h2 {
      font-size: 16px;
      font-weight: normal;
      color: var(--el-text-color-secondary);
      text-align: center;
    }
  }

  .el-carousel {
    box-shadow: var(--el-box-shadow-light);
    transition: var(--el-transition-box-shadow);
  }

  ul li {
    line-height: 28px;
  }
}

.question {
  .answer {
    margin: 20px 0 0;
    padding-left: 20px;
    font-size: 16px;
    color: var(--el-text-color-secondary);

    li {
      margin-bottom: 10px;
      line-height: 1.5;

      &:last-child {
        margin-bottom: 0;
      }
    }

    span {
      color: var(--el-text-color-primary);
      font-weight: bold;
    }
  }
}
</style>
