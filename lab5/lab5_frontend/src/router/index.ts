import CourseInfoPage from "@/views/CourseInfoPage.vue";
import EnrollmentInfoPage from "@/views/EnrollmentInfoPage.vue";
import HomePage from "@/views/HomePage.vue";
import LoginPage from "@/views/LoginPage.vue";
import StudentInfoPage from "@/views/StudentInfoPage.vue";
import { createRouter, createWebHashHistory } from "vue-router";

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/home",
      name: "home",
      component: HomePage,
      children: [
        {
          path: "/home/student",
          name: "student",
          component: StudentInfoPage,
        },
        {
          path: "/home",
          redirect: "/home/student",
        },
        {
          path: "/home/course",
          name: "course",
          component: CourseInfoPage,
        },
        {
          path: "/home/registrations",
          name: "registrations",
          component: EnrollmentInfoPage,
        },
      ],
    },
    {
      path: "/login",
      name: "login",
      component: LoginPage,
    },
    {
      path: "/",
      redirect: "/login",
    },
  ],
});

export default router;
