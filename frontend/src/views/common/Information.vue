<template>
  <div>
    <el-descriptions title="个人信息列表" direction="vertical" :column="3" border>
      <el-descriptions-item label="姓名">{{ name }}</el-descriptions-item>
      <el-descriptions-item label="年龄">{{ age }}</el-descriptions-item>
      <el-descriptions-item label="性别" :span="2">{{ sex }}</el-descriptions-item>
      <el-descriptions-item label="职务">
        <el-tag size="small">{{ role }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="手机号">{{ phone }}</el-descriptions-item>
      <el-descriptions-item label="地址">{{ address }}</el-descriptions-item>
      <el-descriptions-item label="电子邮箱">{{ email }}</el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>

export default {
  name: "Information",
  data() {
    return {
      name: localStorage.getItem('name'),
      age: localStorage.getItem('age'),
      sex: localStorage.getItem('sex'),
      phone: localStorage.getItem('phone'),
      address: localStorage.getItem('address'),
      email: localStorage.getItem('email') ,
      role: localStorage.getItem('role'),
    }
  },

  created() {

  },
  methods: {


  }
}
</script>

