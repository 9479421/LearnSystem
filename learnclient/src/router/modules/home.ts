import type {Route} from '@/global'
const Layout = () => import('@/layouts/index.vue')

const routes: Route.recordRaw[] = [
    {
        path:'/home',
        component: Layout,
        name: 'home',
        redirect: '/home/index',
        meta: {
            title: '课程商城',
            icon: 'ep:grid',
        },
        children:[
            {
                path: '/home/index',
                name: 'homeIndex',
                component: () => import('@/views/home/index.vue'),
                meta: {
                    title: '选购课程',
                  },
            }
        ]
    },
    {
      path:'/my',
      component: Layout,
      name: 'my',
      redirect: '/my/index',
      meta: {
          title: '我的课程',
          icon: 'ep:avatar',
      },
      children:[
          {
              path: '/my/index',
              name: 'myIndex',
              component: () => import('@/views/my/index.vue'),
              meta: {
                  title: '在线观看',
                },
          }
      ]
  },

]
export default routes
