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

    <!-- 上一步和下一步 -->
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

  </div>
</template>
<script>
  import chapterApi from "@/api/edu/course";

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
        video: {
          title: '',
          sort: 0,
          free: 0,
          videoSourceId: ''
        },
        dialogChapterFormVisible:false,//章节弹框
        dialogVideoFormVisible:false //小节弹框
      }
    },

    created() {
      // 获取路由的参数值
      if (this.$router.params && this.$router.params.id) {
        // 获取课程id
        this.courseId = this.$router.params.id
        // 根据id值查询章节和小结
        this.getChapterVideo()
      }
    },

    methods:{
      // 添加和更新章节
      saveOrUpdateChapter() {
        if (!this.chapter.id){
          this.addChapter()
        } else {
          this.updataChapter()
        }
      },
      // 添加章节
      addChapter() {
        //
      },
      // 更新章节
      updataChapter() {
        //
      },
      // 打开添加章节弹窗
      openAddChapterDialog() {
        // 弹窗
        this.dialogChapterFormVisible = true
        // 清空数据
        this.chapter.title = ''
        this.chapter.sort = 0
      },
      // 根据id值查询章节和小结
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
        this.$router.push({path:'/course/publish/1'})
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
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
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
