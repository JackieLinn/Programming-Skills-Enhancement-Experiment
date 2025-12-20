import CourseInfoPage from "@/views/CourseInfoPage.vue";
import EnrollmentInfoPage from "@/views/EnrollmentInfoPage.vue";
import HomePage from "@/views/HomePage.vue";
import LoginPage from "@/views/LoginPage.vue";
import StudentInfoPage from "@/views/StudentInfoPage.vue";
import { createRouter, createWebHashHistory } from "vue-router";

/**
 * Vue Router 路由配置
 * 使用 Hash 模式（URL 带 #）
 */
const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/home",
      name: "home",
      component: HomePage,  // 主布局（含侧边栏）
      children: [
        {
          path: "/home/student",
          name: "student",
          component: StudentInfoPage,  // 学生管理
        },
        {
          path: "/home",
          redirect: "/home/student",   // 默认跳转学生管理
        },
        {
          path: "/home/course",
          name: "course",
          component: CourseInfoPage,   // 课程管理
        },
        {
          path: "/home/registrations",
          name: "registrations",
          component: EnrollmentInfoPage,  // 选课管理
        },
      ],
    },
    {
      path: "/login",
      name: "login",
      component: LoginPage,  // 登录/注册页
    },
    {
      path: "/",
      redirect: "/login",    // 根路径重定向到登录
    },
  ],
});

export default router;
