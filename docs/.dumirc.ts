import {defineConfig} from 'dumi';

export default defineConfig({
  themeConfig: {
    name: '微风组件',
    logo: '/logo.png',
    autoAlias: false,
    favicons: ['/favicon.icon'],
    nav: [
      {title: '指南', link: '/guide'},
      {title: '微信', link: '/weixin'},
    ],
  },
});
