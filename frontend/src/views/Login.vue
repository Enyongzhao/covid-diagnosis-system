<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" class="login-box">
      <h3 class="login-title">欢迎使用辅助诊断医疗系统</h3>
      <el-form-item label="账号" prop="name">
        <el-input type="text" placeholder="请输入用户名" v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('form')">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request"; // 引入axios

export default {
  name: 'Login',
  data() {
    return {
      form: {
        name: '',
        password: ''
      },
      rules: {
        name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    async submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            const response = await request.post('/auth/login', this.form);
            if (response.token) {
              localStorage.setItem('token', response.token);
              localStorage.setItem('role', response.role);
              localStorage.setItem('name', response.name);
              localStorage.setItem('age', response.age);
              localStorage.setItem('sex', response.sex);
              localStorage.setItem('phone', response.phone);
              localStorage.setItem('address', response.address);
              localStorage.setItem('email', response.email);
              const role = response.role;
              if (role === 'Doctor' || role === 'Nurse' || role === 'TestingDoctor'|| role === 'Admin') {
                await this.$router.push({ name: `${role}View` });
              } else {
                this.$message.error('没有权限操作，请联系相关人员了解详情');
              }
            } else {
              this.$message.error(response.message || '用户名或密码错误');
            }
          } catch (error) {
            this.$message.error('登录失败');
            console.error('Login error:', error);
          }
        } else {
          this.$message.error('表单验证失败');
        }
      });
    }

  }
}
</script>


<style lang="scss" scoped>
.login-box {
  width: 300px;
  margin: 120px auto;
  border: 1px solid #DCDFE6;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 0 30px #DCDFE6;
}

.login-title {
  text-align: center;
}

.page-background {
  background-color: rgb(48, 65, 86); /* 设置除表单外的背景颜色 */
  min-height: 100vh; /* 确保整个视口高度都被背景色覆盖 */
  padding: 20px; /* 如果需要，可以添加一些内边距 */
}
</style>

