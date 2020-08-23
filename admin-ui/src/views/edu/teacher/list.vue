<template>
  <div class="app-container">

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQueryVo.name" placeholder="讲师名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQueryVo.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQueryVo.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQueryVo.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
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

      <el-table-column prop="name" label="姓名" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="简介" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

  <!-- 分页 -->
  <!-- current-change 事件会把页码作为参数传给绑定的函数  -->
  <el-pagination
    :current-page="page"
    :page-size="limit"
    :total="total"
    style="padding: 30px 0; text-align: center;"
    layout="total, prev, pager, next, jumper"
    @current-change="getList"
  />

  </div>
</template>

<script>

// 引入 teacher.js 文件
import teacherApi from '@/api/edu/teacher'

export default {
  // 写核心代码
  data() { // 定义变量数据
    return {
      listLoading: true, // 正在加载数据
      list: null, // 数据列表
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 5, // 每页记录数
      teacherQueryVo: { // 查询条件
        name: '',
        level: null,
        begin: null,
        end: null
      } 
    }
  },
  created() { // 当页面加载时获取数据
    if (this.$route.params && this.$route.params.page) {
      const page = this.$route.params.page
      this.getList(page)
    } else {
      this.getList() 
    }
  },
  methods: {
    // 获取讲师列表：带条件的分页查询
    getList(page=1) {
      this.page = page
      teacherApi.getPageList(this.page, this.limit, this.teacherQueryVo)
          .then(response => { // 请求成功
            // response 为接口返回的数据
            if (response.success === true) {
              this.list = response.data.rows
              this.total = response.data.total
            }
            this.listLoading = false
          })
          .catch(error => { // 请求失败
            console.log(error)
          })
    },
    // 重置表单查询数据
    resetData() {
      // 清空表单数据
      this.teacherQueryVo = {}
      // 查询所有讲师数据
      this.getList()
    },
    // 删除讲师
    removeDataById(id) {
      this.$confirm('此操作将永久删除讲师记录, 是否继续?', '提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // 点击确定，执行then方法
        teacherApi.deleteTeacherById(id)
          .then(response => { // 删除成功
            // 提示信息
            this.$message({
              type: 'success',
              message: '删除成功'
            })
            // 回到列表页面
            this.getList()
          }) 
      }).catch(() => {
        //点击取消，执行catch方法
      })
    }
  }
}
</script>
