<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 课程详情 -->
    <section class="container">
      <!-- 课程路径 -->
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a href="#" title class="c-999 fsize14">{{courseWebInfo.subjectLevelOne}}</a>
        \
        <span class="c-333 fsize14">{{courseWebInfo.subjectLevelTwo}}</span>
      </section>
      <div>
        <!-- 课程封面 -->
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section class="p-h-video-box" id="videoPlay">
            <img :src="courseWebInfo.cover" :alt="courseWebInfo.title" class="dis c-v-pic">
          </section>
        </article>
        <!-- 课程详细信息 -->
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{courseWebInfo.title}}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;">￥{{courseWebInfo.price}}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{courseWebInfo.teacherName}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#" >收藏</a>
              </span>
            </section>
            <section v-if="isBuy || Number(courseWebInfo.price) === 0" class="c-attr-mt">
              <a href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
            </section>
            <section v-else class="c-attr-mt">
              <a @click="createOrder()" href="#" title="立即购买" class="comm-btn c-btn-3">立即购买</a>
            </section>
          </section>
        </aside>
        <!-- 其它信息 -->
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol clearfix">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebInfo.buyCount}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebInfo.lessonNum}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseWebInfo.viewCount}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- 课程章节信息 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="courseWebInfo.description">{{courseWebInfo.description}}</p>
                    </section>
                  </div>
                </div>
                <!-- 课程章节列表 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 章节列表 -->
                          <li v-for="chapter in chapterVideoList" :key="chapter.id" class="lh-menu-stair">
                            <a href="javascript: void(0)" :title="chapter.title" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.title}}
                            </a>
                            <!-- 小节列表 -->
                            <ol class="lh-menu-ol" style="display: block;">
                              <li v-for="vodeo in chapter.children" :key="vodeo.id" class="lh-menu-second ml30">
                                <a :href="'/player/'+vodeo.videoSourceId" target="_self">
                                  <span class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{vodeo.title}}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <!-- 课程讲师 -->
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a :href="'/teacher/'+courseWebInfo.teacherId">
                        <img :src="courseWebInfo.avatar" width="50" height="50" alt>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" :href="'/teacher/'+courseWebInfo.teacherId">{{courseWebInfo.teacherName}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseWebInfo.intro}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>

<script>

  import courseApi from '@/api/course'
  import orderApi from '@/api/order'

  export default {
    asyncData({ params, error }) {
      return { courseId: params.id }
    },
    data() {
      return {
        courseWebInfo: {},
        chapterVideoList: {},
        isBuy: false
      }
    },
    created() {
      this.initCourseInfo()
    },
    methods: {
      // 查询课程详情信息
      initCourseInfo() {
        courseApi.getCourseInfo(this.courseId)
          .then(response => {
              this.courseWebInfo = response.data.courseWebInfo,
              this.chapterVideoList = response.data.chapterVideoList,
              this.isBuy = response.data.isBuy
          })
      },
      // 生成订单
      createOrder() {
        orderApi.createOrder(this.courseId)
          .then(response => {
            // 生成订单返回订单号后跳转订单显示页面
            this.$router.push({path:'/orders/'+response.data.orderNo})
          })
      }
    }
  };
</script>
