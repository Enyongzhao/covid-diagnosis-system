<template>
  <div class="login-page">
    <el-form ref="form" :model="form" :rules="rules" class="login-box" label-position="top">
      <h3 class="login-title">辅助诊断医疗系统</h3>
      <el-form-item label="账号" prop="name">
        <el-input type="text" placeholder="请输入用户名" v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item class="login-btn-item">
        <el-button type="primary" class="login-btn" @click="submitForm('form')">登录</el-button>
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
.login-page {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  box-sizing: border-box;
  overflow: hidden;
  background: linear-gradient(135deg, #e6f7e6 0%, #d5f0d5 100%);
}

.login-box {
  width: 420px;
  max-width: 100%;
  border: 1px solid #d9ead9;
  padding: 30px 28px 18px;
  border-radius: 14px;
  background: #ffffff;
  box-shadow: 0 10px 30px rgba(71, 143, 71, 0.16);
  transform: translateY(-60px); /* 负数=上移 */
}

.login-title {
  text-align: center;
  margin: 0 0 8px;
  font-size: 22px;
  color: #2f5f2f;
}

.login-subtitle {
  margin: 0 0 20px;
  text-align: center;
  color: #6b8d6b;
  font-size: 14px;
}

.login-btn-item {
  margin-top: 8px;
}

.login-btn {
  width: 100%;
  height: 42px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  background-color: #69b56f;
}

.login-btn:hover,
.login-btn:focus {
  background-color: #5aa260;
}
</style>

