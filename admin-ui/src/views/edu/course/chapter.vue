<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <!-- 步骤条 -->
    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <!-- 添加章节按钮 -->
    <el-button type="text" @click="openAddChapterDialog()">添加章节</el-button>

    <!-- 章节数据列表 -->
    <ul class="chanpterList">
      <li
        v-for="chapter in chapterVideoList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button style="" type="text" @click="openAddVideoDialog(chapter.id)">添加小节</el-button>
            <el-button style="" type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
            <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
          </span>
        </p>
        <!-- 视频小节 -->
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                <el-button style="" type="text" @click="openEditVideoDialog(video.id)">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <!-- 添加和修改章节弹窗 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateChapter">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改小节弹窗 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <!-- TODO -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 上一步和下一步 -->
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

  </div>
</template>
<script>
  import chapterApi from '@/api/edu/chapter'
  import videoApi from '@/api/edu/video'

  export default {
    data() {
      return {
        saveBtnDisabled:false,
        courseId:'',//课程id
        chapterVideoList:[],
        chapter:{ //封装章节数据
          title: '',
          sort: 0
        },
        video: { // 封装小节数据
          title: '',
          sort: 0,
          isFree: true,
          videoSourceId: ''
        },
        dialogChapterFormVisible:false,//章节弹框
        dialogVideoFormVisible:false //小节弹框
      }
    },

    created() {
      // 获取路由的参数值
      if (this.$route.params && this.$route.params.id) {
        // 获取课程id
        this.courseId = this.$route.params.id
        // 根据id值查询章节和小节
        this.getChapterVideo()
      }
    },

    methods:{
      // 打开添加章节弹窗
      openAddChapterDialog() {
        // 弹窗
        this.dialogChapterFormVisible = true
        // 清空数据
        this.chapter.title = ''
        this.chapter.sort = 0
      },

      // 打开编辑章节弹窗并数据回显
      openEditChapter(chapterId) {
        // 弹窗
        this.dialogChapterFormVisible = true
        // 数据回显
        chapterApi.getChapterInfo(chapterId)
          .then(response => {
            this.chapter = response.data.chapter
          })
      },

      // 添加和修改章节
      saveOrUpdateChapter() {
        if (!this.chapter.id){
          this.addChapter()
        } else {
          this.updataChapter()
        }
      },

      // 添加章节
      addChapter() {
        this.chapter.courseId = this.courseId
        chapterApi.addChapter(this.chapter)
          .then(response => {
            // 关闭弹窗
            this.dialogChapterFormVisible = false
            // 提示
            this.$message({
              type: 'success',
              message: '添加章节成功!'
            })
            // 刷新界面
            this.getChapterVideo()
          })
      },

      // 修改章节
      updataChapter() {
        chapterApi.updateChapter(this.chapter)
          .then(response => {
            // 关闭弹窗
            this.dialogChapterFormVisible = false
            // 提示
            this.$message({
              type: 'success',
              message: '添加章节成功!'
            })
            // 刷新页面
            this.getChapterVideo()
          })
      },

      // 删除章节
      removeChapter(chapterId) {
        // 确认是否删除
        this.$confirm('此操作将删除章节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { // 点击确认
          chapterApi.deleteChapter(chapterId)
            .then(response => {
              // 提示
              this.$message({
                type: 'success',
                message: '删除章节成功!'
              })
              // 刷新页面
              this.getChapterVideo()
            })
        }).catch(() => {
          // 点击取消
        })
      },

      // 打开添加小节弹窗
      openAddVideoDialog(chapterId) {
        // 弹窗
        this.dialogVideoFormVisible = true
        // 重置显示数据
        this.video = {
          title: '',
          sort: 0,
          isFree: true,
          videoSourceId: ''
        }
        // 设置章节id
        this.video.chapterId = chapterId
      },

      // 打开编辑小节弹窗
      openEditVideoDialog(videoId) {
        // 弹窗
        this.dialogVideoFormVisible = true
        // 数据回显
        videoApi.getVideoInfo(videoId)
          .then(response => {
            this.video = response.data.video
          })
      },

      // 添加和修改小节
      saveOrUpdateVideo() {
        if (!this.video.id) {
          // 添加
          this.addVideo()
        } else {
          // 修改
          this.updateVideo()
        }
      },

      // 添加小节
      addVideo() {
        // 设置小节的课程id
        this.video.courseId = this.courseId
        videoApi.addVideo(this.video)
          .then(response => {
            // 关闭弹窗
            this.dialogVideoFormVisible = false
            // 提示
            this.$message({
              type: 'success',
              message: '添加小节成功'
            })
            // 刷新页面
            this.getChapterVideo()
          })
      },

      // 修改小节
      updateVideo() {
        videoApi.updateVideo(this.video)
          .then(response => {
            // 关闭弹窗
            this.dialogVideoFormVisible = false
            // 提示
            this.$message({
              type: 'success',
              message: '修改小节成功'
            })
            // 刷新页面
            this.getChapterVideo()
          })
      },

      // 删除小节
      removeVideo(id) {
        // 确认是否删除
        this.$confirm('此操作将删除小节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { // 点击确认
          videoApi.deleteVideo(id)
            .then(response => {
              // 提示
              this.$message({
                type: 'success',
                message: '删除小节成功!'
              })
              // 刷新页面
              this.getChapterVideo()
            })
        }).catch(() => {
          // 点击取消
        })
      },

      // 根据课程id值查询章节和小节
      getChapterVideo() {
        chapterApi.getAllChapterVideo(this.courseId)
          .then(response => {
            this.chapterVideoList = response.data.chapterVideoList
          })
      },

      // 上一步
      previous() {
        this.$router.push({path:'/course/info/'+this.courseId})
      },

      // 下一步
      next() {
        //跳转到第三步
        this.$router.push({path:'/course/publish/'+this.courseId})
      }
    }
  }
</script>

<style scoped>
  .chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .chanpterList li{
    position: relative;
  }
  .chanpterList p{
    float: left;
    font-size: 20px;
    margin: 5px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px solid #DDD;
  }
  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>
