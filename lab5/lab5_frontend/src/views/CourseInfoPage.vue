<template>
  <div class="course-page">
    <!-- 工具栏 -->
    <div class="toolbar">
      <div class="left-section">
        <el-button type="primary" @click="openAddDialog">
          <el-icon><Plus /></el-icon>
          新增课程
        </el-button>
        <el-button @click="fetchCourses">
          刷新列表
        </el-button>
      </div>
      <div class="right-section">
        <el-tag type="info" size="large">共 {{ courses.length }} 门课程</el-tag>
      </div>
    </div>

    <!-- 课程卡片列表 -->
    <div class="course-grid" v-loading="loading">
      <div 
        v-for="course in courses" 
        :key="course.id" 
        class="course-card"
      >
        <div class="card-header">
          <span class="course-id">#{{ course.id }}</span>
          <div class="card-actions">
            <el-button type="primary" size="small" circle @click="openEditDialog(course)">
              <el-icon><Edit /></el-icon>
            </el-button>
            <el-button type="danger" size="small" circle @click="handleDelete(course)">
              <el-icon><Delete /></el-icon>
            </el-button>
          </div>
        </div>
        <div class="card-body">
          <h3 class="course-name">{{ course.name }}</h3>
          <div class="course-info">
            <div class="info-item">
              <span class="info-label">授课教师</span>
              <span class="info-value">{{ course.teacher }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">学分</span>
              <el-tag type="warning" effect="dark" size="small">{{ course.credit }} 学分</el-tag>
            </div>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-if="courses.length === 0 && !loading" class="empty-state">
        <el-empty description="暂无课程数据">
          <el-button type="primary" @click="openAddDialog">添加第一门课程</el-button>
        </el-empty>
      </div>
    </div>

    <!-- 新增/编辑对话框 -->
    <el-dialog 
      v-model="dialogVisible" 
      :title="isEdit ? '编辑课程' : '新增课程'" 
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form 
        :model="courseForm" 
        :rules="courseRules" 
        ref="courseFormRef" 
        label-width="80px"
      >
        <el-form-item label="课程名" prop="name">
          <el-input v-model="courseForm.name" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="教师" prop="teacher">
          <el-input v-model="courseForm.teacher" placeholder="请输入授课教师" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input-number 
            v-model="courseForm.credit" 
            :min="1" 
            :max="10" 
            :step="1"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">
          {{ isEdit ? '保存' : '创建' }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { Plus, Edit, Delete } from '@element-plus/icons-vue'
import { courseApi, type Course } from '@/api'

const loading = ref(false)
const submitLoading = ref(false)
const courses = ref<Course[]>([])

// 获取课程列表
const fetchCourses = async () => {
  loading.value = true
  try {
    const res = await courseApi.list()
    courses.value = res.data
  } catch (error) {
    ElMessage.error('获取课程列表失败')
  } finally {
    loading.value = false
  }
}

// 对话框相关
const dialogVisible = ref(false)
const isEdit = ref(false)
const courseFormRef = ref<FormInstance>()
const courseForm = reactive({
  id: 0,
  name: '',
  teacher: '',
  credit: 3
})

const courseRules: FormRules = {
  name: [
    { required: true, message: '请输入课程名称', trigger: 'blur' },
    { min: 2, max: 100, message: '课程名称长度在2-100个字符之间', trigger: 'blur' }
  ],
  teacher: [
    { required: true, message: '请输入授课教师', trigger: 'blur' },
    { min: 2, max: 50, message: '教师名称长度在2-50个字符之间', trigger: 'blur' }
  ],
  credit: [
    { required: true, message: '请输入学分', trigger: 'blur' }
  ]
}

const resetForm = () => {
  courseForm.id = 0
  courseForm.name = ''
  courseForm.teacher = ''
  courseForm.credit = 3
}

const openAddDialog = () => {
  isEdit.value = false
  resetForm()
  dialogVisible.value = true
}

const openEditDialog = (course: Course) => {
  isEdit.value = true
  courseForm.id = course.id
  courseForm.name = course.name
  courseForm.teacher = course.teacher
  courseForm.credit = course.credit
  dialogVisible.value = true
}

// 提交表单
const handleSubmit = async () => {
  if (!courseFormRef.value) return
  
  await courseFormRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        if (isEdit.value) {
          await courseApi.update(courseForm.id, {
            name: courseForm.name,
            teacher: courseForm.teacher,
            credit: courseForm.credit
          })
          ElMessage.success('更新成功')
        } else {
          await courseApi.add({
            name: courseForm.name,
            teacher: courseForm.teacher,
            credit: courseForm.credit
          })
          ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        fetchCourses()
      } catch (error) {
        ElMessage.error(isEdit.value ? '更新失败' : '创建失败')
      } finally {
        submitLoading.value = false
      }
    }
  })
}

// 删除课程
const handleDelete = (course: Course) => {
  ElMessageBox.confirm(
    `确定要删除课程 "${course.name}" 吗？此操作不可恢复！`,
    '删除确认',
    {
      confirmButtonText: '确定删除',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await courseApi.delete(course.id)
      ElMessage.success('删除成功')
      fetchCourses()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  }).catch(() => {})
}

onMounted(() => {
  fetchCourses()
})
</script>

<style scoped>
.course-page {
  background: #fff;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  flex-wrap: wrap;
  gap: 16px;
}

.left-section,
.right-section {
  display: flex;
  gap: 12px;
  align-items: center;
}

.course-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.course-card {
  background: linear-gradient(135deg, #f8f9fa 0%, #fff 100%);
  border: 1px solid #e8e8e8;
  border-radius: 16px;
  padding: 20px;
  transition: all 0.3s ease;
}

.course-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
  border-color: #667eea;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.course-id {
  font-size: 13px;
  color: #999;
  font-weight: 500;
}

.card-actions {
  display: flex;
  gap: 8px;
}

.card-body {
  padding-top: 8px;
}

.course-name {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a2e;
  margin-bottom: 16px;
  line-height: 1.4;
}

.course-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.info-label {
  font-size: 14px;
  color: #666;
}

.info-value {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.empty-state {
  grid-column: 1 / -1;
  padding: 60px 0;
}

:deep(.el-dialog) {
  border-radius: 16px;
}

:deep(.el-dialog__header) {
  border-bottom: 1px solid #eee;
  padding: 20px 24px;
  margin-right: 0;
}

:deep(.el-dialog__body) {
  padding: 24px;
}

:deep(.el-dialog__footer) {
  border-top: 1px solid #eee;
  padding: 16px 24px;
}
</style>
