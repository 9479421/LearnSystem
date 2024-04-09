import type { Route } from '@/global'
const Layout = () => import('@/layouts/index.vue')
const routes: Route.recordRaw[] = [
    {
        path:'/questionSearch',
        component: Layout,
        name: 'questionSearch',
        redirect: '/question/search',
        meta: {
            title: '搜索题目',
            icon: 'fixed-right-chat',
        },
        children:[
            {
                path: '/question/search',
                name: 'questionSearch',
                component: () => import('@/views/question/search.vue'),
                meta: {
                    title: '测试章节',
                    sidebar: false,
                    breadcrumb: false,
                    activeMenu: '/questionSearch',
                  },
            }
        ]
    },

]

export default routes
