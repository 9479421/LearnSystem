<route lang="yaml">
name: personalSetting
meta:
  title: 个人设置
  cache: personal-edit.password
</route>

<script lang="ts" setup name="PersonalSetting">
import type { UploadProps, FormRules } from 'element-plus'
import useUserStore from '@/store/modules/user'
const userStore = useUserStore()

const router = useRouter()

const changeInfoForm = ref({
  avatar: userStore.avatar,
  nickname: userStore.nickname,
  phone: userStore.phone,
})
const changePassForm = ref({
  username: userStore.username,
  password: '',
  newPassword: '',
})

const handleSuccess: UploadProps['onSuccess'] = (res) => {
  console.log(res)
  if (res.code === 200) {
    ElMessage.success(res.msg)
    changeInfoForm.value.avatar = res.data.path
    //修改存储
    userStore.avatar = res.data.path
    localStorage.setItem('avatar', res.data.path)
  }
  else {
    ElMessage.warning(res.msg)
  }
}
function modifyInfo(){
  userStore.modifyInfo(changeInfoForm.value).then((res:any)=>{
    if(res.code==200){
      userStore.nickname = res.data.nickname
      localStorage.setItem('nickname', res.data.nickname)
    }
  });
}
function modifyPass(){
  userStore.modifyPass(changePassForm.value).then((res:any)=>{
    if(res.code==200){
      //修改成功，清除coookie，重新登录
      userStore.logout().then(() => {
        router.push({
          name: 'login',
        })
      })

    }
  });
}

const modifyInfoRules = ref<FormRules>({
  username: [
    { required: true, trigger: 'blur', message: '请输入用户名' },
  ],
  captcha: [
    { required: true, trigger: 'blur', message: '请输入验证码' },
  ],
  newPassword: [
    { required: true, trigger: 'blur', message: '请输入新密码' },
    { min: 5, max: 18, trigger: 'blur', message: '密码长度为5到18位' },
  ],
})

const modifyPassRules = ref<FormRules>({
  username: [
    { required: true, trigger: 'blur', message: '请输入用户名' },
  ],
  captcha: [
    { required: true, trigger: 'blur', message: '请输入验证码' },
  ],
  newPassword: [
    { required: true, trigger: 'blur', message: '请输入新密码' },
    { min: 5, max: 18, trigger: 'blur', message: '密码长度为5到18位' },
  ],
})
</script>

<template>
  <div>
    <page-main>
      <el-tabs tab-position="left" style="height: 600px;">
        <el-tab-pane label="基本设置" class="basic">
          <h2>基本设置</h2>
          <el-divider></el-divider>

          <el-form ref="formRef" :model="changeInfoForm" label-width="120px" label-suffix="：">
            <el-form-item>
              <image-upload
                v-model:url="changeInfoForm.avatar"
                action="http://localhost:9090/client/member/uploadAvatar"
                name="image"
                :headers="{ token: userStore.token }"
                notip
                class="headimg-upload"
                @on-success="handleSuccess"
              />
            </el-form-item>
            <el-form-item label="昵 称">
              <el-input v-model="changeInfoForm.nickname" placeholder="请输入你的昵称" />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input disabled v-model="changeInfoForm.phone" placeholder="请输入你的手机号" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="modifyInfo()">保存</el-button>
            </el-form-item>
          </el-form>
          <el-divider></el-divider>
        </el-tab-pane>
        <el-tab-pane label="安全设置" class="security">
          <h2>安全设置</h2>
          <el-divider></el-divider>
          <el-form :model="changePassForm" ref="formRef" label-width="120px" label-suffix="：">
            <el-form-item label="账 号">
              <el-input v-model="changePassForm.username" placeholder="请输入你账号" />
            </el-form-item>
            <el-form-item label="原密码">
              <el-input v-model="changePassForm.password" placeholder="请输入你的旧密码" />
            </el-form-item>
            <el-form-item label="新密码">
              <el-input v-model="changePassForm.newPassword" placeholder="请输入你的新密码" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="modifyPass()">修改</el-button>
            </el-form-item>
          </el-form>
          <el-divider></el-divider>
        </el-tab-pane>
      </el-tabs>
    </page-main>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-tabs) {
  .el-tabs__header .el-tabs__nav {
    .el-tabs__active-bar {
      z-index: 0;
      width: 100%;
      background-color: var(--el-color-primary-light-9);
      border-right: 2px solid var(--el-color-primary);
      transition: transform 0.3s, background-color 0.3s,
        var(--el-transition-border);
    }

    .el-tabs__item {
      text-align: left;
      padding-right: 100px;
    }
  }

  .el-tab-pane {
    padding: 0 20px 0 30px;
  }
}

h2 {
  margin: 0;
  margin-bottom: 30px;
  font-weight: normal;
}

.basic {
  :deep(.headimg-upload) {
    text-align: center;

    .el-upload-dragger {
      border-radius: 50%;
    }
  }
}

.security {
  .setting-list {
    .item {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px 0;
      border-bottom: 1px solid var(--el-border-color-lighter);
      transition: var(--el-transition-border);

      .content {
        .title {
          margin-bottom: 5px;
          color: var(--el-text-color-primary);
          transition: var(--el-transition-color);
        }

        .desc {
          font-size: 14px;
          color: var(--el-text-color-secondary);
          transition: var(--el-transition-color);
        }
      }

      &:last-child {
        border-bottom: 0;
      }
    }
  }
}
</style>
