<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <!-- 步骤条 -->
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <!-- 课程信息表单 -->
    <el-form label-width="120px">

      <!-- 课程标题 -->
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title"
                  placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类 -->
      <el-form-item label="课程分类">
        <!-- 一级分类 -->
        <el-select v-model="courseInfo.subjectParentId" placeholder="一级分类" @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <!-- 课程总课时 -->
      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right"
                         placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介：使用富文本编辑器 -->
      <el-form-item label="课程简介">
        <!--<el-input v-model="courseInfo.description" placeholder=" "/>-->
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <!-- 课程封面 -->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/file/upload'"
          class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>
      </el-form-item>

      <!-- 课程价格 -->
      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>

      <!-- 下一步按钮 -->
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>
import courseApi from '@/api/edu/course'
import subjectApi from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'
export default {
  // 声明组件
  components: {
    Tinymce // 富文本组件
  },
  data() {
    return {
      saveBtnDisabled:false,
      courseInfo:{
        title: '',
        subjectId: '',//二级分类id
        subjectParentId:'',//一级分类id
        teacherId: '',
        lessonNum: 0,
        description: '',
        cover: '/static/cover01.png',
        price: 0
      },
      courseId: '', // 课程id
      BASE_API: process.env.BASE_API, // 接口API地址
      teacherList:[],//封装所有的讲师
      subjectOneList:[],//一级分类
      subjectTwoList:[]//二级分类
    }
  },
  created() {
    // 获取路由的参数值
    if (this.$router.params && this.$router.params.id) {
      // 获取课程id
      this.courseId = this.$router.params.id
      // 根据课程id查询课程基本信息
      this.getCourseInfo()
    } else {
      //初始化一级分类
      this.getOneSubject()
      //初始化所有讲师
      this.getListTeacher()
    }
  },
  methods:{
    // 根据课程id查询课程基本信息
    getCourseInfo() {
      courseApi.getCourseInfo(this.courseId)
        .then(response => {
          this.courseInfo = response.data.courseInfoVo
        })
    },
    //查询所有的一级分类
    getOneSubject() {
      subjectApi.getSubjectList()
        .then(response => {
          this.subjectOneList = response.data.list
        })
    },
    //查询所有的讲师
    getListTeacher() {
      courseApi.getListTeacher()
        .then(response => {
          this.teacherList = response.data.items
        })
    },
    //课程封面上传之前调用的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG || !isPNG) {
        this.$message.error('上传头像图片只能是 JPG 和 PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    //上传封面成功调用的方法
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url
    },
    //点击某个一级分类，触发change，显示对应二级分类
    subjectLevelOneChanged(value) {
      //value就是一级分类id值
      for(let i = 0; i < this.subjectOneList.length; i++) {
        //每个一级分类
        let oneSubject = this.subjectOneList[i]
        //判断：所有一级分类id 和 点击一级分类id是否一样
        if(value === oneSubject.id) {
          //从一级分类获取里面所有的二级分类
          this.subjectTwoList = oneSubject.children
          //把二级分类id值清空
          this.courseInfo.subjectId = ''
          break
        }
      }
    },
    //保存和更新
    saveOrUpdate() {
      courseApi.addCourseInfo(this.courseInfo)
        .then(response => {
          if (response.success === true) {
            //提示
            this.$message({
              type: 'success',
              message: '添加课程信息成功!'
            });
            //跳转到第二步
            this.$router.push({path:'/course/chapter/'+response.data.courseId})
          }
        })
    }
  }
}
</script>

<style scoped>
  .tinymce-container {
    line-height: 29px;
  }
</style>
