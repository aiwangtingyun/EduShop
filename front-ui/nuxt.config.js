module.exports = {
  /*
  ** Headers of the page
  */
  head: {
    title: 'EduShop',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'keywords', name: 'keywords', content: 'EduShop，在线教育'},
      { hid: 'description', name: 'description', content: 'EduShop为在线教育项目' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },

  // plugins
  plugins: [
    {src: '~/plugins/nuxt-swiper-plugin.js', ssr: false}
  ],

  // css
  css: [
    'swiper/swiper-bundle.css'
  ],

  /*
  ** Customize the progress bar color
  */
  loading: { color: '#3B8070' },

  /*
  ** Build configuration
  */
  build: {
    /*
    ** Run ESLint on save - 保存时开启 ESLint 检查
    */
    /*extend (config, { isDev, isClient }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    }*/
  }
}

