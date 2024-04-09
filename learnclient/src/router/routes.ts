import { setupLayouts } from 'virtual:generated-layouts'
import generatedRoutes from 'virtual:generated-pages'

import Chapter from './modules/chapter'
import Home from './modules/home'
import Question from './modules/question'
import English from './modules/english'


import type { Route } from '@/global'
import useSettingsStore from '@/store/modules/settings'

// 固定路由（默认路由）
const constantRoutes: Route.recordRaw[] = [
  {
    path: '/',
    redirect: '/dashboard',
    meta: {
      title: '',
    },
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/login.vue'),
    meta: {
      title: '登录',
    },
  },
  {
    path: '/:all(.*)*',
    name: 'notFound',
    component: () => import('@/views/[...all].vue'),
    meta: {
      title: '找不到页面',
    },
  },
]

// 系统路由
const systemRoutes: Route.recordRaw[] = [
  {
    path: '/dashboard',
    component: () => import('@/layouts/index.vue'),
    meta: {
      title: () => useSettingsStore().dashboard.title,
      breadcrumb: false,
    },
    children: [
      {
        path: '',
        name: 'dashboard',
        component: () => import('@/views/index.vue'),
        meta: {
          title: () => useSettingsStore().dashboard.title,
          breadcrumb: false,
        },
      },
    ],
  },
  {
    path: '/personal',
    component: () => import('@/layouts/index.vue'),
    redirect: '/personal/setting',
    meta: {
      title: '个人中心',
      breadcrumb: false,
    },
    children: [
      {
        path: 'setting',
        name: 'personalSetting',
        component: () => import('@/views/personal/setting.vue'),
        meta: {
          title: '个人设置',
          cache: 'personalEditPassword',
        },
      },
    ],
  },
  {
    path: '/reload',
    component: () => import('@/layouts/index.vue'),
    meta: {
      title: '重新加载',
      breadcrumb: false,
    },
    children: [
      {
        path: '',
        name: 'reload',
        component: () => import('@/views/reload.vue'),
        meta: {
          title: '重新加载',
          breadcrumb: false,
        },
      },
    ],
  },
]

// 动态路由（异步路由、导航栏路由）
const asyncRoutes: Route.recordMainRaw[] = [
  {
    meta: {
      title: '课程',
      icon: 'sidebar-default',
    },
    children: [
      ...Home,
    ],
  },
  // {
  //   meta: {
  //     title: '课程',
  //     icon: 'sidebar-videos',
  //   },
  //   children: [
  //     ...Chapter,
  //   ],
  // },
  {
    meta: {
      title: '搜题',
      icon: 'sidebar-ecology',
    },
    children: [
      ...Question,
    ],
  },
  {
    meta: {
      title: '外文',
      icon: 'sidebar-jsx',
    },
    children: [
      ...English,
    ],
  },
]

const constantRoutesByFilesystem = generatedRoutes.filter((item) => {
  return item.meta?.enabled !== false && item.meta?.constant === true
})

const asyncRoutesByFilesystem = setupLayouts(generatedRoutes.filter((item) => {
  return item.meta?.enabled !== false && item.meta?.constant !== true && item.meta?.layout !== false
})) as Route.recordRaw[]

export {
  constantRoutes,
  systemRoutes,
  asyncRoutes,
  constantRoutesByFilesystem,
  asyncRoutesByFilesystem,
}
