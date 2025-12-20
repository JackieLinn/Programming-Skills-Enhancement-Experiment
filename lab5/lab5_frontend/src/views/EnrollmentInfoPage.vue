<template>
  <div class="enrollment-page">
    <!-- 选课操作区 -->
    <div class="enroll-section">
      <div class="section-header">
        <h3>📝 添加选课记录</h3>
      </div>
      <div class="enroll-form">
        <div class="form-item">
          <label>选择学生</label>
          <el-select 
            v-model="selectedStudentId" 
            placeholder="请选择学生"
            filterable
            class="form-select"
            @change="handleStudentChange"
          >
            <el-option
              v-for="student in students"
              :key="student.id"
              :label="student.username"
              :value="student.id"
            >
              <span>{{ student.username }}</span>
              <span class="option-email">{{ student.email }}</span>
            </el-option>
          </el-select>
        </div>
        <div class="form-item">
          <label>选择课程</label>
          <el-select 
            v-model="selectedCourseId" 
            placeholder="请选择课程"
            filterable
            class="form-select"
          >
            <el-option
              v-for="course in courses"
              :key="course.id"
              :label="course.name"
              :value="course.id"
            >
              <span>{{ course.name }}</span>
              <span class="option-teacher">{{ course.teacher }} | {{ course.credit }}学分</span>
            </el-option>
          </el-select>
        </div>
        <el-button 
          type="primary" 
          :loading="enrollLoading"
          :disabled="!selectedStudentId || !selectedCourseId"
          @click="handleEnroll"
        >
          确认选课
        </el-button>
      </div>
    </div>

    <!-- 选课记录查询区 -->
    <div class="query-section">
      <div class="section-header">
        <h3>📋 学生选课记录</h3>
        <div class="query-controls">
          <el-select 
            v-model="queryStudentId" 
            placeholder="选择学生查看选课"
            filterable
            clearable
            class="query-select"
            @change="fetchEnrollments"
          >
            <el-option
              v-for="student in students"
              :key="student.id"
              :label="student.username"
              :value="student.id"
            />
          </el-select>
        </div>
      </div>

      <div class="enrollments-container" v-loading="loading">
        <!-- 选课列表 -->
        <div v-if="enrollments.length > 0" class="enrollment-list">
          <div 
            v-for="enrollment in enrollments" 
            :key="enrollment.enrollmentId" 
            class="enrollment-card"
          >
            <div class="enrollment-header">
              <span class="enrollment-id">#{{ enrollment.enrollmentId }}</span>
              <el-button 
                type="danger" 
                size="small" 
                @click="handleDrop(enrollment)"
              >
                退课
              </el-button>
            </div>
            <div class="enrollment-body">
              <div class="course-title">{{ enrollment.courseName }}</div>
              <div class="course-details">
                <div class="detail-item">
                  <span class="detail-label">授课教师</span>
                  <span class="detail-value">{{ enrollment.teacher }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">学分</span>
                  <el-tag type="warning" size="small">{{ enrollment.credit }}</el-tag>
                </div>
                <div class="detail-item">
                  <span class="detail-label">选课时间</span>
                  <span class="detail-value">{{ formatDateTime(enrollment.createTime) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 空状态 -->
        <div v-else class="empty-state">
          <el-empty :description="queryStudentId ? '该学生暂无选课记录' : '请先选择一个学生'">
            <template v-if="!queryStudentId">
              <p class="empty-hint">在上方选择学生以查看其选课记录</p>
            </template>
          </el-empty>
        </div>
      </div>
    </div>

    <!-- 统计信息 -->
    <div v-if="queryStudentId && enrollments.length > 0" class="stats-section">
      <div class="stat-card">
        <span class="stat-value">{{ enrollments.length }}</span>
        <span class="stat-label">已选课程数</span>
      </div>
      <div class="stat-card">
        <span class="stat-value">{{ totalCredits }}</span>
        <span class="stat-label">总学分</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { studentApi, courseApi, enrollmentApi, type User, type Course, type EnrollmentView } from '@/api'

const loading = ref(false)
const enrollLoading = ref(false)
const students = ref<User[]>([])
const courses = ref<Course[]>([])
const enrollments = ref<EnrollmentView[]>([])

// 选课表单
const selectedStudentId = ref<number | null>(null)
const selectedCourseId = ref<number | null>(null)

// 查询
const queryStudentId = ref<number | null>(null)

// 总学分计算
const totalCredits = computed(() => {
  return enrollments.value.reduce((sum, e) => sum + e.credit, 0)
})

// 获取学生列表
const fetchStudents = async () => {
  try {
    const res = await studentApi.list()
    students.value = res.data
  } catch (error) {
    ElMessage.error('获取学生列表失败')
  }
}

// 获取课程列表
const fetchCourses = async () => {
  try {
    const res = await courseApi.list()
    courses.value = res.data
  } catch (error) {
    ElMessage.error('获取课程列表失败')
  }
}

// 获取选课记录
const fetchEnrollments = async () => {
  if (!queryStudentId.value) {
    enrollments.value = []
    return
  }
  loading.value = true
  try {
    const res = await enrollmentApi.listByStudent(queryStudentId.value)
    enrollments.value = res.data
  } catch (error) {
    ElMessage.error('获取选课记录失败')
  } finally {
    loading.value = false
  }
}

// 学生选择变化时同步查询
const handleStudentChange = () => {
  if (selectedStudentId.value) {
    queryStudentId.value = selectedStudentId.value
    fetchEnrollments()
  }
}

// 选课
const handleEnroll = async () => {
  if (!selectedStudentId.value || !selectedCourseId.value) return
  
  enrollLoading.value = true
  try {
    await enrollmentApi.enroll(selectedStudentId.value, selectedCourseId.value)
    ElMessage.success('选课成功！')
    selectedCourseId.value = null
    // 刷新选课记录
    if (queryStudentId.value === selectedStudentId.value) {
      fetchEnrollments()
    }
  } catch (error: any) {
    ElMessage.error(error.response?.data?.message || '选课失败，可能已选择该课程')
  } finally {
    enrollLoading.value = false
  }
}

// 退课
const handleDrop = (enrollment: EnrollmentView) => {
  ElMessageBox.confirm(
    `确定要退选课程 "${enrollment.courseName}" 吗？`,
    '退课确认',
    {
      confirmButtonText: '确定退课',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await enrollmentApi.drop(enrollment.enrollmentId)
      ElMessage.success('退课成功')
      fetchEnrollments()
    } catch (error) {
      ElMessage.error('退课失败')
    }
  }).catch(() => {})
}

// 格式化时间
const formatDateTime = (dateStr: string) => {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

onMounted(() => {
  fetchStudents()
  fetchCourses()
})
</script>

<style scoped>
.enrollment-page {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.enroll-section,
.query-section {
  background: #fff;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a2e;
}

.enroll-form {
  display: flex;
  gap: 20px;
  align-items: flex-end;
  flex-wrap: wrap;
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-item label {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.form-select {
  width: 280px;
}

.option-email,
.option-teacher {
  float: right;
  font-size: 12px;
  color: #999;
}

.query-select {
  width: 240px;
}

.enrollments-container {
  min-height: 200px;
}

.enrollment-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 16px;
}

.enrollment-card {
  background: linear-gradient(135deg, #f8f9fa 0%, #fff 100%);
  border: 1px solid #e8e8e8;
  border-radius: 12px;
  padding: 16px;
  transition: all 0.3s ease;
}

.enrollment-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.08);
  border-color: #667eea;
}

.enrollment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  padding-bottom: 12px;
  border-bottom: 1px solid #eee;
}

.enrollment-id {
  font-size: 12px;
  color: #999;
  font-weight: 500;
}

.enrollment-body {
  padding-top: 4px;
}

.course-title {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a2e;
  margin-bottom: 12px;
}

.course-details {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-label {
  font-size: 13px;
  color: #666;
}

.detail-value {
  font-size: 13px;
  color: #333;
  font-weight: 500;
}

.empty-state {
  padding: 40px 0;
}

.empty-hint {
  font-size: 14px;
  color: #999;
  margin-top: 8px;
}

.stats-section {
  display: flex;
  gap: 20px;
}

.stat-card {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  padding: 24px;
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #fff;
}

.stat-value {
  font-size: 36px;
  font-weight: 700;
  line-height: 1;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  opacity: 0.9;
}
</style>
