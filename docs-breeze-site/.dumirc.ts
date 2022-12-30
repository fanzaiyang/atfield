import {defineConfig} from 'dumi';

export default defineConfig({
  // base:'/breeze-spring-cloud',
  // publicPath:'/breeze-spring-cloud/',
  themeConfig: {
    name: '微风组件',
    logo: 'https://raw.githubusercontent.com/fanzaiyang/breeze-spring-cloud/master/docs-breeze-site/public/logo.png',
    autoAlias: false,
    favicons: ['https://raw.githubusercontent.com/fanzaiyang/breeze-spring-cloud/master/docs-breeze-site/public/favicon.ico'],
    nav: [
      {title: '指南', link: '/guide'},
      {title: '微信', link: '/weixin'},
      {title: '更新日志', link: '/changelog'},
    ],
  },
});
