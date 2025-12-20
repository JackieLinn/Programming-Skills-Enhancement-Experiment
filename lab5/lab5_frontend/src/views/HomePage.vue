<template>
  <div class="home-container">
    <!-- 侧边导航栏 -->
    <aside class="sidebar">
      <div class="logo-section">
        <div class="logo-icon">📚</div>
        <h2 class="logo-text">选课系统</h2>
      </div>

      <nav class="nav-menu">
        <router-link 
          to="/home/student" 
          class="nav-item"
          :class="{ active: $route.path === '/home/student' }"
        >
          <span class="nav-icon">👨‍🎓</span>
          <span class="nav-label">学生管理</span>
        </router-link>

        <router-link 
          to="/home/course" 
          class="nav-item"
          :class="{ active: $route.path === '/home/course' }"
        >
          <span class="nav-icon">📖</span>
          <span class="nav-label">课程管理</span>
        </router-link>

        <router-link 
          to="/home/registrations" 
          class="nav-item"
          :class="{ active: $route.path === '/home/registrations' }"
        >
          <span class="nav-icon">📝</span>
          <span class="nav-label">选课管理</span>
        </router-link>
      </nav>

      <div class="sidebar-footer">
        <button class="logout-btn" @click="handleLogout">
          <span class="nav-icon">🚪</span>
          <span class="nav-label">退出登录</span>
        </button>
      </div>
    </aside>

    <!-- 主内容区 -->
    <main class="main-content">
      <header class="top-header">
        <div class="page-title">
          <h1>{{ currentPageTitle }}</h1>
        </div>
        <div class="header-right">
          <span class="welcome-text">欢迎使用学生选课管理系统</span>
          <el-avatar :size="36" class="user-avatar">管</el-avatar>
        </div>
      </header>

      <div class="content-wrapper">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()

const currentPageTitle = computed(() => {
  const path = route.path
  if (path.includes('student')) return '学生信息管理'
  if (path.includes('course')) return '课程信息管理'
  if (path.includes('registrations')) return '选课信息管理'
  return '首页'
})

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    router.push('/login')
  }).catch(() => {})
}
</script>

<style scoped>
.home-container {
  display: flex;
  min-height: 100vh;
  background: #f5f7fa;
}

/* 侧边栏 */
.sidebar {
  width: 240px;
  background: linear-gradient(180deg, #1a1a2e 0%, #16213e 100%);
  display: flex;
  flex-direction: column;
  box-shadow: 4px 0 20px rgba(0, 0, 0, 0.1);
}

.logo-section {
  padding: 28px 20px;
  display: flex;
  align-items: center;
  gap: 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.logo-icon {
  font-size: 32px;
}

.logo-text {
  color: #fff;
  font-size: 20px;
  font-weight: 600;
  letter-spacing: 2px;
}

.nav-menu {
  flex: 1;
  padding: 20px 0;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 16px 24px;
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  transition: all 0.3s ease;
  margin: 4px 12px;
  border-radius: 12px;
  gap: 14px;
}

.nav-item:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
}

.nav-item.active {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
}

.nav-icon {
  font-size: 20px;
}

.nav-label {
  font-size: 15px;
  font-weight: 500;
}

.sidebar-footer {
  padding: 20px 12px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.logout-btn {
  width: 100%;
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 14px 24px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
  transition: all 0.3s ease;
}

.logout-btn:hover {
  background: rgba(239, 68, 68, 0.2);
  border-color: rgba(239, 68, 68, 0.4);
  color: #ef4444;
}

/* 主内容区 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.top-header {
  height: 70px;
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 32px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.page-title h1 {
  font-size: 22px;
  font-weight: 600;
  color: #1a1a2e;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.welcome-text {
  color: #666;
  font-size: 14px;
}

.user-avatar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
  font-weight: 600;
}

.content-wrapper {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
}
</style>
