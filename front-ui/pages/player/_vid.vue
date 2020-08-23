<template>
  <div>
    <!-- 阿里云视频播放器样式 -->
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.8/skins/default/aliplayer-min.css" >
    <!-- 阿里云视频播放器脚本 -->
    <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.8/aliplayer-min.js" />

    <!-- 定义播放器dom -->
    <div id="J_prismPlayer" class="prism-player" />
  </div>
</template>

<script>
   
  import vodApi from '@/api/vod'

  export default {
    layout: 'default',  // 布局
    asyncData({ params, error }) {
       return vodApi.getPlayAuth(params.vid)
        .then(response => {
            return { 
                playAuth: response.data.playAuth,
                vid: params.vid
            }
        })
    },
    mounted() {
      new Aliplayer({
          id: 'J_prismPlayer',
          vid: this.vid, // 视频id
          playauth: this.playAuth, // 播放凭证
          encryptType: '1', // 是否为加密视频
          width: '100%',
          height: '500px',

          // 以下可选设置
          cover: 'https://', // 封面
          qualitySort: 'asc', // 清晰度排序
          mediaType: 'video', // 返回音频还是视频
          autoplay: false, // 自动播放
          isLive: false, // 直播
          rePlay: false, // 循环播放
          preload: true,
          controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
          useH5Prism: true, // 播放器类型：html5
        }, 
        function(player) {
          console.log('播放器创建成功')
        }
      )
    }
  }
</script>
