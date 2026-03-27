<template>
  <div style="height: 100%">
    <el-container style="height: 100%">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 1000px">
        <el-menu :default-openeds="['1', '3']" style="height: 100%; overflow-x: hidden"
                 background-color="rgb(48,65,86)"
                 text-color="#fff"
                 active-text-color="#ffd04b"
                 :collapse-transition="false"
        >
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px">
            <b style="color: white">辅助诊断系统医生端</b>
          </div>
          <el-menu-item-group>
            <el-menu-item index="1-1" @click="currentComponent = 'AnnouncementView'">公告栏</el-menu-item>
            <el-menu-item index="1-2" @click="currentComponent = 'DiagnosisListComponent'">诊断列表</el-menu-item>
            <el-menu-item index="1-3" @click="currentComponent = 'Chart'">模型准确率图表</el-menu-item>
          </el-menu-item-group>
          <el-menu-item index="1-4-1" @click="currentComponent = 'Information'">个人信息</el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px">
          <el-button type="danger" @click="logout">退出登录</el-button>
        </el-header>

        <el-main>
          <component :is="currentComponent"></component>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'DoctorView',
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      currentComponent: 'AnnouncementView'  // 默认加载的组件
    }
  },
  components: {
    AnnouncementView: () => import('@/views/common/AnnouncementView.vue'),
    DiagnosisListComponent: () => import('@/views/doctor/DiagnosisListComponent.vue'),
    Information: () => import('@/views/common/Information.vue'),
    Chart: () => import('@/views/doctor/Chart.vue'),
    // 导入其他组件
  },
  created() {
    // 请求分页查询数据
    this.load();

  },

  methods: {
    load() {
      //加载数据到表格
      request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name
        }
      }).then(res => {
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })

    },
    logout() {//退出登录
      localStorage.clear()

      this.$router.replace('/Login');
    }
  }
}
</script>

