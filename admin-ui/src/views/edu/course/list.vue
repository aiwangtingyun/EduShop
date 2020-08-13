<template>
  <div class="app-container">

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseQuery.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseQuery.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>

      <!-- 标题 -->
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程标题"/>
      </el-form-item>

      <!-- 讲师 -->
      <el-form-item>
        <el-select
          v-model="courseQuery.teacherId"
          placeholder="请选择讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getCourseList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 课程列表数据 -->
    <el-table
      v-loading="listLoading"
      element-loading-text="数据加载中"
      :data="courseList"
      border
      fit
      highlight-current-row
      row-class-name="myClassList">

      <!-- 序号 -->
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <!-- 课程信息 -->
      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px">
            </div>
            <div class="title">
              <a href="#">{{ scope.row.title }}</a>
              <p>{{ scope.row.lessonNum }}课时</p>
            </div>
          </div>
        </template>
      </el-table-column>

      <!-- 创建时间 -->
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate.substr(0, 10) }}
        </template>
      </el-table-column>

      <!-- 发布时间 -->
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtModified.substr(0, 10) }}
        </template>
      </el-table-column>

      <!-- 价格 -->
      <el-table-column label="价格" width="100" align="center" >
        <template slot-scope="scope">
          {{ Number(scope.row.price) === 0 ? '免费' :
          '¥' + scope.row.price.toFixed(2) }}
        </template>
      </el-table-column>

      <!-- 付费学员 -->
      <el-table-column prop="buyCount" label="付费学员" width="100" align="center" >
        <template slot-scope="scope">
          {{ scope.row.buyCount }}人
        </template>
      </el-table-column>

      <!-- 操作按钮 -->
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/course/info/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程信息</el-button>
          </router-link>
          <router-link :to="'/edu/course/chapter/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
          </router-link>
          <el-button type="text" size="mini" icon="el-icon-delete" @click="removeCourseById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <!-- 分页消息 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getCourseList"
    />

  </div>
</template>

<script>
  import teacherApi from '@/api/edu/teacher'
  import subjectApi from '@/api/edu/subject'
  import courseApi from '@/api/edu/course'
  export default {
    data() {
      return {
        listLoading: true, // 是否显示loading信息
        courseList: null, // 数据列表
        total: 0, // 总记录数
        page: 1, // 页码
        limit: 10, // 每页记录数
        courseQuery: { // 查询条件
          title: null, // 课程标题
          teacherId: null, // 课程讲师
          subjectParentId: null, // 一级课程id
          subjectId: null // 二级课程id
        },
        teacherList: [], // 讲师列表
        subjectOneList: [], // 一级分类列表
        subjectTwoList: [] // 二级分类列表,
      }
    },

    created() {
      this.getCourseList()
      this.getTeacherList()
      this.getSubjectList()
    },

    methods: {
      // 获取课程列表数据
      getCourseList(page=1) {
        this.page = page
        this.listLoading = true
        courseApi.getCoursePageList(this.page, this.limit, this.courseQuery)
          .then(response => {
            if (response.success === true) {
              this.total = response.data.total
              this.courseList = response.data.rows
            }
            this.listLoading = false
          })
      },

      // 获取讲师列表数据
      getTeacherList() {
        teacherApi.getTeacherList()
          .then(response => {
            this.teacherList = response.data.list
          })
      },

      // 获取subject列表
      getSubjectList() {
        subjectApi.getSubjectList()
          .then(response => {
            this.subjectOneList = response.data.list
          })
      },

      // 一级课程列表选项发生改变
      subjectLevelOneChanged(value) {
        for (let subjectOne of this.subjectOneList) {
          if (value === subjectOne.id) {
            this.subjectTwoList = subjectOne.children
          }
        }
      },

      // 清空查询数据
      resetData() {
        // 清空表单输入项数据
        this.courseQuery = {}
        // 清空二级课程列表
        this.subjectTwoList = []
        // 回显所有课程列表数据
        this.getCourseList()
      },

      // 删除课程
      removeCourseById (id) {
        // 确认提示
        this.$confirm('此操作将删除课程锁对应的所有章节，是否确认？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          courseApi.deleteCourseById(id)
            .then(response => {
              if (response.success === true) {
                this.$message({
                  type: 'success',
                  message: '删除课程成功'
                })
              }
              // 重新加载课程列表
              this.getCourseList()
            })
        }).catch(() => {})
      }
    }
  }
</script>

<style scoped>
  .myClassList .info {
    width: 450px;
    overflow: hidden;
  }
  .myClassList .info .pic {
    width: 150px;
    height: 90px;
    overflow: hidden;
    float: left;
  }
  .myClassList .info .pic a {
    display: block;
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
  }
  .myClassList .info .pic img {
    display: block;
    width: 100%;
  }
  .myClassList td .info .title {
    width: 280px;
    float: right;
    height: 90px;
  }
  .myClassList td .info .title a {
    display: block;
    height: 48px;
    line-height: 24px;
    overflow: hidden;
    color: #00baf2;
    margin-bottom: 12px;
  }
  .myClassList td .info .title p {
    line-height: 20px;
    margin-top: 5px;
    color: #818181;
  }
</style>
