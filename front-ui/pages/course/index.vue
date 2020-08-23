<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 课程列表 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <!-- 一级课程分类 -->
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click="resetSearch()" :class="{active:subjectOneIndex==-1}">全部</a>
                </li>
                <li v-for="(item, index) in subjectOneList" :key="index" :class="{active:subjectOneIndex===index}">
                  <a :title="item.title" href="#" @click="searchBySubjectOne(item.id, index)">{{ item.title }}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <!-- 二级课程分类 -->
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(item, index) in subjectTwoList" :key="index" :class="{active:subjectTwoIndex===index}">
                  <a :title="item.title" href="#" @click="searchBySubjectTwo(item.id, index)">{{ item.title }}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <!-- 特殊条件排序 -->
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
             <li :class="{'current bg-orange':buyCountSort!=''}">
                <a title="销量" href="javascript:void(0);" @click="searchByBuyCount()">销量
                <span :class="{hide:buyCountSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchByGmtCreate()">最新
                <span :class="{hide:gmtCreateSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchByPrice()">价格&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <!-- 课程列表数据 -->
        <div class="mt40">
          <!-- 无数据时的提示 -->
          <section v-if="data.total==0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- 有数据则显示课程列表 -->
          <article v-if="data.total>0" class="comm-course-list">
            <ul class="of" id="bna">
              <li v-for="item in data.items" :key="item.id">
                <div class="cc-l-wrap">
                  <!-- 课程封面 -->
                  <section class="course-img">
                    <img :src="item.cover" class="img-responsive" :alt="item.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+item.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <!-- 课程标题 -->
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+item.id" :title="item.title" class="course-title fsize18 c-333">{{item.title}}</a>
                  </h3>
                  <!-- 课程信息 -->
                  <section class="mt10 hLh20 of">
                    <span v-if="Number(item.price) === 0" class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">9634人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 分页导航 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首</a>
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
              @click.prevent="gotoPage(data.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
      </section>
    </section>

  </div>
</template>
<script>

  import courseApi from '@/api/course'

  export default {
    data() {
      return {
        page:1, //当前页
        data:{}, //课程列表数据
        subjectOneList: [], // 一级分类列表
        subjectTwoList: [], // 二级分类列表

        searchObj: {}, // 查询表单对象

        subjectOneIndex: -1, // 一级分类的index
        subjectTwoIndex: -1, // 二级分类的index
        buyCountSort: "", // 购买数
        gmtCreateSort: "", // 课程发布时间
        priceSort: "" // 价格
      }
    },

    created() {
      // 初始化课程列表
      this.initCourseList()
      // 初始化课程分类列表
      this.initSubject()
    },

    methods: {
      // 初始化课程列表
      initCourseList() {
        courseApi.getCourseList(1, 8, this.searchObj)
          .then(response => {
            this.data = response.data
          })
      },
      // 初始化课程分类列表
      initSubject() {
        courseApi.getAllSubject()
          .then(response => {
            this.subjectOneList = response.data.list
          })
      },
      // 跳转指定页面
      gotoPage(page=1) {
        if (page >= 1 && page <= this.data.pages) {
          // 当前页无需重复请求
          if (page == this.data.current) {
            return
          }
          // 请求列表
          courseApi.getCourseList(page, 8, this.searchObj)
            .then(response => {
              this.data = response.data
            })
        }
      },
      // 根据条件查询
      searchByCondition() {
        courseApi.getCourseList(1, 8, this.searchObj)
            .then(response => {
              this.data = response.data
            })
      },
      // 根据一级分类查询
      searchBySubjectOne(subjectOneId, index) {
        // 传递index激活选中样式
        this.subjectOneIndex = index

        // 清空二级分类显示数据
        this.subjectTwoIndex = -1
        this.searchObj.subjectId = ""
        this.subjectTwoList = []

        // 查询一级分类课程
        this.searchObj.subjectParentId = subjectOneId
        this.searchByCondition()

        // 查询一级分类对应的二级分类
        for (let oneSubject of this.subjectOneList) {
          if (oneSubject.id === subjectOneId) {
            this.subjectTwoList = oneSubject.children
          }
        }
      },
      // 根据二级分类查询
      searchBySubjectTwo(subjectTwoId, index) {
        // 传递index激活选中样式
        this.subjectTwoIndex = index
        // 赋值查询的二级分类id
        this.searchObj.subjectId = subjectTwoId 
        this.searchByCondition()
      }, 
      // 根据销量排序
      searchByBuyCount() {
        // 设置对应变量值，激活选中样式
        this.buyCountSort = "1"
        this.gmtCreateSort = ""
        this.priceSort = ""

        // 把值赋值到searchObj
        this.searchObj.buyCountSort = this.buyCountSort
        this.searchObj.gmtCreateSort = this.gmtCreateSort
        this.searchObj.priceSort = this.priceSort

        // 调用方法查询
        this.searchByCondition()
      },
      // 根据最新排序
      searchByGmtCreate() {
        // 设置对应变量值，激活选中样式
        this.buyCountSort = ""
        this.gmtCreateSort = "1"
        this.priceSort = ""

        // 把值赋值到searchObj
        this.searchObj.buyCountSort = this.buyCountSort
        this.searchObj.gmtCreateSort = this.gmtCreateSort
        this.searchObj.priceSort = this.priceSort

        // 调用方法查询
        this.searchByCondition()
      },
      // 根据价格排序
      searchByPrice() {
        // 设置对应变量值，激活选中样式
        this.buyCountSort = ""
        this.gmtCreateSort = ""
        this.priceSort = "1"

        // 把值赋值到searchObj
        this.searchObj.buyCountSort = this.buyCountSort
        this.searchObj.gmtCreateSort = this.gmtCreateSort
        this.searchObj.priceSort = this.priceSort

        // 调用方法查询
        this.searchByCondition()
      },
      // 重置查询条件
      resetSearch() {
        this.subjectOneIndex = -1
        this.subjectTwoIndex = -1
        this.subjectTwoList = []
        this.searchObj = {}
        this.searchByCondition()
      }
    }
  };
</script>

<style scoped>
  .active {
    background: #94D8F6;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>
