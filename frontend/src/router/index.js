import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import NurseView from "@/views/nurse/NurseView.vue";
import DoctorView from "@/views/doctor/DoctorView.vue";
import TestingDoctorView from "@/views/testing/TestingDoctorView.vue";
import AdminView from "@/views/admin/AdminView.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/Login' // 将根路径重定向到 /login
  },
  {
    path: '/Login',
    name: 'Login',
    component: Login
  },
  {
    path: '/nurse',
    name: 'NurseView',
    component: NurseView
  },
  {
    path: '/doctor',
    name: 'DoctorView',
    component: DoctorView,
  },
  {
    path: '/testingdoctor',
    name: 'TestingDoctorView',
    component: TestingDoctorView
  },
  {
    path: '/admin',
    name: 'AdminView',
    component: AdminView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


// 检查是否存在于免登陆白名单
function inWhiteList(toPath) {
  const whiteList = ['/Login'];
  return whiteList.includes(toPath);
}

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  if (inWhiteList(to.path)) {
    next();
  } else if (!token) {
    next('/Login');
  } else {
    // 用户已登录，检查角色
    if (to.path.startsWith('/Doctor') && role !== 'Doctor') {
      next('/Login');
    } else if (to.path.startsWith('/nurse') && role !== 'Nurse') {
      next('/Login');
    } else if (to.path.startsWith('/testingdoctor') && role !== 'TestingDoctor') {
      next('/Login');
    } else if (to.path.startsWith('/admin') && role !== 'Admin') {
      next('/Login');
    } else {
      next();
    }
  }
});

export default router
