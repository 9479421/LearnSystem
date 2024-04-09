import useRouteStore from './route'
import useMenuStore from './menu'
import api from '@/api'

const useUserStore = defineStore(
  // 唯一ID
  'user',
  {
    state: () => ({
      username: localStorage.username || '',
      token: localStorage.token || '',
      permissions: [] as string[],
      avatar: localStorage.avatar || '',
      nickname: localStorage.nickname || '',
      phone: localStorage.phone || '',
    }),
    getters: {
      isLogin: (state) => {
        let retn = false
        if (state.token) {
          retn = true
        }
        return retn
      },
    },
    actions: {
      login(data: {
        username: string
        password: string
      }) {
        return new Promise<void>((resolve, reject) => {
          api.post('/member/login', data, {
          }).then((res: any) => {
            console.log(res);
            if (res.code == 200) {
              localStorage.setItem('username', res.data.username)
              localStorage.setItem('token', res.data.token)
              this.username = res.data.username
              this.token = res.data.token

              localStorage.setItem('avatar', res.data.avatar)
              localStorage.setItem('nickname', res.data.nickname)
              localStorage.setItem('phone', res.data.phone)
              this.avatar = res.data.avatar
              this.nickname = res.data.nickname
              this.phone = res.data.phone
              resolve()
            } else {
              ElMessage({
                message: res.msg,
                type: 'error',
              })
              resolve()
            }

          }).catch((error) => {
            reject(error)
          })
        })
      },
      sendRegisterCode(
        phone: string
      ) {
        return new Promise<void>((resolve, reject) => {
          api.post('/member/sendRegisterCode?phone=' + phone).then((res: any) => {
            console.log(res);
            if (res.code == 200) {
              ElMessage({
                message: res.msg,
                type: 'success',
              })
              resolve(res)
            } else {
              ElMessage({
                message: res.msg,
                type: 'error',
              })
              resolve()
            }

          }).catch((error) => {
            reject(error)
          })
        })
      },
      register(data: {
        username: string,
        phone: string,
        captcha: string,
        password: string
      }) {
        return new Promise<void>((resolve, reject) => {
          api.post('/member/register', data, {
          }).then((res: any) => {
            if (res.code == 200) {
              ElMessage({
                message: res.msg,
                type: 'success',
              })
              resolve()
            } else {
              ElMessage({
                message: res.msg,
                type: 'error',
              })
              resolve()
            }

          }).catch((error) => {
            reject(error)
          })
        })
      },
      modifyInfo(data: {
        nickname: string,
        phone: string,
      }) {
        return new Promise<void>((resolve, reject) => {
          api.post('/member/modifyInfo', data, {
          }).then((res: any) => {
            if (res.code == 200) {
              ElMessage({
                message: res.msg,
                type: 'success',
              })
              resolve(res)
            } else {
              ElMessage({
                message: res.msg,
                type: 'error',
              })
              resolve()
            }

          }).catch((error) => {
            reject(error)
          })
        })
      },
      modifyPass(data: {
        username: string,
        password: string,
        newPassword: string,
      }) {
        return new Promise<void>((resolve, reject) => {
          api.post('/member/modifyPass', data, {
          }).then((res: any) => {
            if (res.code == 200) {
              ElMessage({
                message: res.msg,
                type: 'success',
              })
              resolve(res)
            } else {
              ElMessage({
                message: res.msg,
                type: 'error',
              })
              resolve()
            }

          }).catch((error) => {
            reject(error)
          })
        })
      },
      logout() {
        return new Promise<void>((resolve) => {
          const routeStore = useRouteStore()
          const menuStore = useMenuStore()
          localStorage.removeItem('username')
          localStorage.removeItem('token')
          localStorage.removeItem('avatar')
          localStorage.removeItem('nickname')
          localStorage.removeItem('phone')
          this.username = ''
          this.token = ''
          this.avatar = ''
          this.nickname = ''
          this.phone = ''
          routeStore.removeRoutes()
          menuStore.setActived(0)
          resolve()
        })
      },
      // 获取我的权限
      getPermissions() {
        return new Promise<string[]>((resolve) => {
          // 通过 mock 获取权限
          api.get('member/permission', {
            baseURL: '/mock/',
            params: {
              username: this.username,
            },
          }).then((res) => {
            this.permissions = res.data.permissions
            resolve(res.data.permissions)
          })
        })
      },
      editPassword(data: {
        password: string
        newpassword: string
      }) {
        return new Promise<void>((resolve) => {
          api.post('member/edit/password', {
            username: this.username,
            password: data.password,
            newpassword: data.newpassword,
          }, {
            baseURL: '/mock/',
          }).then(() => {
            resolve()
          })
        })
      },
    },
  },
)

export default useUserStore
