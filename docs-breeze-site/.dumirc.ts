import {defineConfig} from 'dumi';

export default defineConfig({
  // base:'/breeze-spring-cloud',
  // publicPath:'/breeze-spring-cloud/',
  themeConfig: {
    name: '微风组件',
    logo: 'https://c2.im5i.com/2022/12/27/ReZC7.png',
    autoAlias: false,
    favicons: ['/favicon.ico'],
    nav: [
      {title: '指南', link: '/guide'},
      {title: '微信', link: '/weixin'},
      {title: '更新日志', link: '/changelog'},
    ],
  },
});
