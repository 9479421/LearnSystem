import type { Route } from '@/global'
const Layout = () => import('@/layouts/index.vue')
const routes: Route.recordRaw[] = [
    {
        path:'/englishOnline',
        component: Layout,
        name: 'englishOnline',
        redirect: '/english/online',
        meta: {
            title: '在线短视频',
            icon: 'example-star',
        },
        children:[
            {
                path: '/english/online',
                name: 'englishOnline',
                component: () => import('@/views/english/online.vue'),
                meta: {
                    title: '测试章节',
                    sidebar: false,
                    breadcrumb: false,
                    activeMenu: '/englishOnline',
                  },
            }
        ]
    },
    // {
    //     path:'/chapter1',
    //     component: Layout,
    //     name: 'chapter1',
    //     redirect: '/chapter/index?chapter=1',
    //     meta: {
    //         title: '一起追剧',
    //         icon: 'pro',
    //     },
    //     children:[
    //         {
    //             path: '/chapter/index',
    //             name: 'chapterIndex',
    //             component: () => import('@/views/chapter/index.vue'),
    //             meta: {
    //                 title: '测试章节',
    //                 sidebar: false,
    //                 breadcrumb: false,
    //                 activeMenu: '/chapter',
    //               },
    //         }
    //     ]
    // }
]

export default routes
