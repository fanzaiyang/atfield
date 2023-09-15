import {defineConfig} from 'vitepress'

export default defineConfig({
    lang: 'zh',
    title: "Breeze-Spring",
    description: "一个易用的SpringBoot增强组件库",
    lastUpdated: true,
    themeConfig: {
        logo: '/logo.png',
        siteTitle: 'BreezeSpring',
        outline: {label: '章节'},
        nav: [
            {text: '首页', link: '/'},
            {text: '帮助文档', link: '/zh/guide/intro'},
            {text: '更新日志', link: '/zh/guide/changelog'},
            {text: 'Java特性', link: '/markdown-examples'},
        ],
        sidebar: [
            {
                text: '简介',
                items: [
                    {text: '介绍', link: '/zh/guide/intro'},
                    {text: '快速开始', link: '/zh/guide/started'},
                    {text: 'Maven依赖', link: '/zh/guide/maven'},
                    {text: '更新日志', link: '/zh/guide/changelog'},
                ]
            }, {
                text: '通用工具',
                items: [
                    {text: 'Java缓存', link: '/zh/core/storage'},
                    {text: '缓存', link: '/zh/core/cache'},
                    {text: '网页转图片', link: '/zh/core/html2image'},
                    {text: '其他', link: '/zh/core/others'},
                ]
            }, {
                text: 'SpringBoot增强',
                items: [
                    {text: '功能总览', link: '/zh/spring/overview'},
                    {text: '全局异常', link: '/zh/spring/exception'},
                    {text: '响应封装', link: '/zh/spring/response'},
                    {text: '全局过滤器', link: '/zh/spring/filter'},
                    {text: 'Redis扩展', link: '/zh/spring/redis'},
                    {text: '安全管理', link: '/zh/spring/safe'},
                    {text: '验证码', link: '/zh/spring/code'},
                ]
            }, {
                text: 'MinIO扩展',
                items: [
                    {text: '快速开始', link: '/zh/minio/index'},
                ]
            }, {
                text: '授权扩展',
                items: [
                    {text: '快速开始', link: '/zh/auth/index'},
                ]
            }, {
                text: '持久层',
                items: [
                    {text: '快速开始', link: '/zh/orm/index'},
                ]
            },{
                text: '企业微信',
                items: [
                    {text: '快速开始', link: '/zh/wechat/work/index'},
                    {
                        text: '通讯录管理', items: [
                            {text: '成员管理', link: '/zh/wechat/work/user/index'},
                            {text: '部门管理', link: '/zh/wechat/work/department/index'},
                            {text: '标签管理', link: '/zh/wechat/work/tag/index'},
                        ]
                    }
                ]
            }
        ],
        socialLinks: [
            {icon: 'github', link: 'https://gitee.com/it-xiaofan/breeze-spring-cloud'}
        ]
    }
})
