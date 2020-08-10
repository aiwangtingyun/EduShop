<template>
  <div class="app-container">
    <el-form label-width="120px">

      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>

      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right"/>
      </el-form-item>

      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>

      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

          <!-- 头像缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像</el-button>
          <!--
            图片上传弹窗组件：
              v-show：是否显示上传组件
              :key：类似于id，如果一个页面多个图片上传控件，可以做区分
              :url：后台上传的url地址
              @close：关闭上传组件
              @crop-upload-success：上传成功后的回调 
                <input type="file" name="file"/>
          -->
          <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/eduoss/file/upload'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="addOrUpdate">保存</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default {
  data() {
    return {
      teacher: {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: ''
      },
      imagecropperShow: false, //上传弹框组件是否显示
      imagecropperKey: 0, //上传组件key值
      BASE_API: process.env.BASE_API, //获取dev.env.js里面地址
      saveBtnDisabled: false // 保存按钮是否禁用,
    }
  },
  created() { // 页面渲染之前执行
    this.init() // 这里只执行一次，所以需要监听路由变化
  },
  watch: { // 监听
    $route(to, from) { // 路由的变化方式，路由发生变化，方法就会执行
      // 重新初始化数据
      this.init()
    }
  },
  components: { // 引入组件
    ImageCropper, 
    PanThumb 
  },
  methods: {
    // 初始化数据
    init() {
      // 如何路径有id值,则为修改操作
      if (this.$route.params && this.$route.params.id){
        // 从路径获取id值
        const id = this.$route.params.id
        // 根据id查询数据并回显
        this.getTeacherInfo(id)
      } else { // 路径没有id值则为添加操作
        // 清空表单数据
        this.teacher = {}
      }
    },
    // 根据ID查询讲师
    getTeacherInfo(id) {
      teacherApi.getTeacherInfoById(id)
        .then(response => {
          this.teacher = response.data.item
        })
    },
    // 根据条件判断是否为添加或更新
    addOrUpdate() {
      // 根据teacher是否有id
      if (!this.teacher.id) {
        // 添加
        this.addTeacher()
      } else {
        // 修改
        this.updateTeacher()
      }
    },
    // 添加讲师
    addTeacher() {
      teacherApi.addTeacher(this.teacher)
        .then(response => {
          if (response.success === true) {
            // 提示信息
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            // 回到列表页面 路由跳转
            this.$router.push({path:'/edu/teacher/list'})
          }
        })
    },
    // 修改讲师
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher)
        .then(response => {
          if (response.success === true) {
            // 提示信息
            this.$message({
              type: 'success',
              message: '修改成功'
            })
            // 回到列表页面 路由跳转
            this.$router.push({path:'/edu/teacher/list'})
          }
        })
    },
    // 关闭上传弹框的方法
    close(){
      this.imagecropperShow = false
      //上传组件初始化
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 上传成功方法
    cropSuccess(data){
      this.imagecropperShow = false
      this.teacher.avatar = data.url
      //上传组件初始化
      this.imagecropperKey = this.imagecropperKey + 1
    }
  }
}
</script>
