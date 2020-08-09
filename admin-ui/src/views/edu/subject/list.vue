<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree
      ref="subjectTree"
      :data="subjectList"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />
  </div>
</template>

<script>
  import subject from '@/api/edu/subject'
  export default {

    data() {
      return {
        filterText: '',
        subjectList: [],  //返回所有分类数据
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },

    created() {
      this.getAllSubjectList()
    },

    watch: {
      // 监听变量值的变化
      filterText(val) {
        this.$refs.subjectTree.filter(val)
      }
    },

    methods: {
      // 获取所有课程分类列表
      getAllSubjectList() {
        subject.getSubjectList()
          .then(response => {
            this.subjectList = response.data.list
          })
      },
      // 搜索过滤
      filterNode(value, data) {
        if (!value) return true
        // 使用 toLowerCase 来支持不区分大小写
        return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
      }
    }
  }
</script>
