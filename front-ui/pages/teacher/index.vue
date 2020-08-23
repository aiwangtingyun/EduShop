<template>
  <div id="aCoursesList" class="bg-fa of">
    
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#">全部</a>
        </section>
      </header>

      <!-- 讲师列表 -->
      <section class="c-sort-box unBr">
        <div>
          <!-- 无数据提示 -->
          <section v-if="data.total==0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- 讲师列表数据 -->
          <article v-if="data.total>0" class="i-teacher-list">
            <ul class="of">
              <li v-for="teacher in data.items" :key="teacher.id">
                <section class="i-teach-wrap">
                  <!-- 头像 -->
                  <div class="i-teach-pic">
                    <a :href="'/teacher/'+teacher.id" :title="teacher.name" target="_self">
                      <img :src="teacher.avatar" :alt="teacher.name">
                    </a>
                  </div>
                  <!-- 讲师姓名 -->
                  <div class="mt10 hLh30 txtOf tac">
                    <a :href="'/teacher/'+teacher.id" :title="teacher.name" target="_self" class="fsize18 c-666">{{ teacher.name }}</a>
                  </div>
                  <!-- 讲师简介 -->
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999">{{ teacher.intro }}</span>
                  </div>
                  <!-- 讲师资历 -->
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{teacher.career }}</p>
                  </div>
                </section>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>

        <!-- 讲师分页 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首页</a>

            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(data.current-1)">&lt;</a>

            <a
              v-for="page in data.pages"
              :key="page"
              :class="{current: data.current == page, undisable: data.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)">{{ page }}</a>

            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(data.current+1)">&gt;</a>

            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(data.pages)">末页</a>

            <div class="clear"/>
          </div>
        </div>
      </section>
    </section>

  </div>
</template>

<script>

  import teacherApi from '@/api/teacher'

  export default {
    // 异步调用，只调用一次
    // params: 相当于之前 this.$route.params
    asyncData({ params, error }) {
      // 一定要加 return
      return teacherApi.getTeacherList(1, 8).then(response => {
        // 等价于 this.data = response.data.data
        return { data: response.data }
      })
    },
    
    methods: {
      // 讲师分页跳转
      gotoPage(page) {
        // 请求页码合理化判断
        if (page >= 1 && page <= this.data.pages) {
          // 当前页无需重复请求
          if (page == this.data.current) {
            return
          }
          // 请求列表
          teacherApi.getTeacherList(page, 8)
          .then(response => {
            this.data = response.data
          })
        }  
      }
    }
  };
</script>
