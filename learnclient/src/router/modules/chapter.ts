import type { Route } from '@/global'
const Layout = () => import('@/layouts/index.vue')
const routes: Route.recordRaw[] = [
    {
        path:'/chapter',
        component: Layout,
        name: 'chapter',
        redirect: '/chapter/index',
        meta: {
            title: '第一章：函数的定义',
            icon: 'pro',
        },
        children:[
            {
                path: '/chapter/index',
                name: 'chapterIndex',
                component: () => import('@/views/chapter/index.vue'),
                meta: {
                    title: '测试章节',
                    sidebar: false,
                    breadcrumb: false,
                    activeMenu: '/chapter',
                  },
            }
        ]
    },
    {
        path:'/chapter1',
        component: Layout,
        name: 'chapter1',
        redirect: '/chapter/index?chapter=1',
        meta: {
            title: '第二章：导数的性质',
            icon: 'pro',
        },
        children:[
            {
                path: '/chapter/index',
                name: 'chapterIndex',
                component: () => import('@/views/chapter/index.vue'),
                meta: {
                    title: '测试章节',
                    sidebar: false,
                    breadcrumb: false,
                    activeMenu: '/chapter',
                  },
            }
        ]
    }
]

export default routes