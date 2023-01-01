import {defineConfig} from 'dumi';

export default defineConfig({
  // base:'/breeze-spring-cloud',
  // publicPath:'/breeze-spring-cloud/',
  themeConfig: {
    name: '微风组件',
    logo: '/logo.png',
    autoAlias: false,
    favicons: ['/favicon.ico'],
    nav: [
      {title: '指南', link: '/guide'},
      {title: '微信', link: '/weixin'},
      {title: '更新日志', link: '/changelog'},
    ],
  },
});
