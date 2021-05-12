export default {
  head: {
    title: 'LOSTARK',
    htmlAttrs: {
      lang: 'ko'
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },

  css: [
    './assets/app.scss',
    './node_modules/element-ui/packages/theme-chalk/src/base.scss',
    './node_modules/element-ui/packages/theme-chalk/src/button.scss',
    './node_modules/element-ui/packages/theme-chalk/src/container.scss',
    './node_modules/element-ui/packages/theme-chalk/src/header.scss',
    './node_modules/element-ui/packages/theme-chalk/src/input.scss',
    './node_modules/element-ui/packages/theme-chalk/src/checkbox.scss',
    './node_modules/element-ui/packages/theme-chalk/src/dialog.scss',
    './node_modules/element-ui/packages/theme-chalk/src/radio.scss',
    './node_modules/element-ui/packages/theme-chalk/src/table.scss',
    './node_modules/element-ui/packages/theme-chalk/src/alert.scss',
    './node_modules/element-ui/packages/theme-chalk/src/table-column.scss'
  ],
  plugins: [
    { src: '@/plugins/element-ui', ssr: true },
    '@/plugins/vue-in-viewport-directive.js',
    '@/plugins/utils.ts'
  ],

  components: true,

  buildModules: [
    '@nuxt/typescript-build'
  ],

  modules: [
    '@nuxtjs/axios'
  ],

  axios: {
    proxy: true
  },

  proxy: {
    '/api': 'http://localhost:8080'
  },

  build: {
    babel: {
      plugins: [
        [
          'component',
          {
            libraryName: 'element-ui',
            styleLibraryName: 'theme-chalk'
          }
        ]
      ]
    }
  }
}
