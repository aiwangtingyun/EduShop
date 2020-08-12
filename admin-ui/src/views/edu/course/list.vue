<template>
  <div class="app-container">

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option value="Normal" label="已发布"/>
          <el-option value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 课程列表数据 -->
    <el-table
      :data="courseList"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="300" />

      <el-table-column label="课程状态" width="100">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" width="80" />

      <el-table-column prop="gmtCreate" label="添加时间" width="180"/>

      <el-table-column prop="viewCount" label="浏览数量" width="120" />

      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲息</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeCourseById(scope.row.id)">删除课程信息</el-button>
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
  import courseApi from '@/api/edu/course'
  export default {
    data() {
      return {
        courseList: null,
        page: 1,
        limit: 10,
        total: 0,
        courseQuery: {}
      }
    },

    created() {
      this.getCourseList()
    },

    methods: {
      // 查询课程列表
      getCourseList(page=1) {
        this.page = page
        courseApi.getCourseList()
          .then(response => {
            this.courseList = response.data.list
          })
      },

      // 清空查询数据
      resetData() {
        // 清空表单输入项数据
        this.courseQuery = {}
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
