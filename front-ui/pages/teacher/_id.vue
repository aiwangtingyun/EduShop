<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师介绍 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">讲师介绍</span>
        </h2>
      </header>
      <!-- 讲师基本信息 -->
      <div class="t-infor-wrap">
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20">
            <!-- 头像 -->
            <section class="t-infor-pic">
              <img :src="teacher.avatar">
            </section>
            <!-- 讲师姓名 -->
            <h3 class="hLh30">
              <span class="fsize24 c-333">{{teacher.name}}&nbsp;{{teacher.level===1?'高级讲师':'首席讲师'}}</span>
            </h3>
            <!-- 讲师简介 -->
            <section class="t-infor-txt">
              <p class="mt20" >{{teacher.intro}}</p>
            </section>
            <!-- 讲师资历 -->
            <section class="mt10">
              <span class="t-tag-bg">{{teacher.career}}</span>
            </section>
            <div class="clear"></div>
          </div>
        </section>
        <div class="clear"></div>
      </div>
      <!-- 讲师课程列表信息 -->
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">主讲课程</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
          </header>
          <!-- 无数据时的提示信息-->
          <section v-if="courseList.length===0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- 有数据则展示对应课程列表 -->
          <article v-if="courseList.length!==0" class="comm-course-list">
            <ul class="of">
              <li v-for="course in courseList" :key="course.id">
                <div class="cc-l-wrap">
                  <!-- 课程封面 -->
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" >
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" title="开始学习" target="_self" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <!-- 课程标题 -->
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+course.id" :title="course.title" target="_self" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
      </section>
    </section>
  </div>
</template>
<script>

  import teacherApi from '@/api/teacher'

  export default {
    // params.id 获取路径id值
    asyncData({ params, error }) {
      return teacherApi.getTeacherInfo(params.id)
        .then(response => {
          return { 
            teacher: response.data.teacher,
            courseList: response.data.courseList
          }
        })
    }
  };
</script>
