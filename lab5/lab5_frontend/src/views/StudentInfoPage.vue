<template>
  <div class="student-page">
    <!-- 工具栏 -->
    <div class="toolbar">
      <div class="search-section">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索学生（用户名/邮箱）"
          prefix-icon="Search"
          clearable
          class="search-input"
          @keyup.enter="handleSearch"
          @clear="fetchStudents"
        />
        <el-button type="primary" @click="handleSearch">
          搜索
        </el-button>
        <el-button @click="fetchStudents">
          刷新列表
        </el-button>
      </div>
      <div class="action-section">
        <el-tag type="info" size="large">共 {{ students.length }} 名学生</el-tag>
      </div>
    </div>

    <!-- 数据表格 -->
    <div class="table-container">
      <el-table 
        :data="students" 
        v-loading="loading"
        stripe 
        style="width: 100%"
        row-key="id"
      >
        <el-table-column prop="id" label="ID" width="80" align="center" />
        <el-table-column prop="username" label="用户名" min-width="120">
          <template #default="{ row }">
            <span class="username-cell">{{ row.username }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="200">
          <template #default="{ row }">
            <el-tag type="info" effect="plain">{{ row.email }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="role" label="角色" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="row.role === 'ADMIN' ? 'danger' : 'success'">
              {{ row.role === 'ADMIN' ? '管理员' : '学生' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间" width="180" align="center">
          <template #default="{ row }">
            {{ formatDateTime(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="openEditDialog(row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 编辑对话框 -->
    <el-dialog 
      v-model="editDialogVisible" 
      title="编辑学生信息" 
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form 
        :model="editForm" 
        :rules="editRules" 
        ref="editFormRef" 
        label-width="80px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input 
            v-model="editForm.password" 
            type="password" 
            placeholder="留空则不修改密码"
            show-password 
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleUpdate">
          保存
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { studentApi, type User } from '@/api'

const loading = ref(false)
const submitLoading = ref(false)
const students = ref<User[]>([])
const searchKeyword = ref('')

// 获取学生列表
const fetchStudents = async () => {
  loading.value = true
  try {
    const res = await studentApi.list()
    students.value = res.data
  } catch (error) {
    ElMessage.error('获取学生列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索学生
const handleSearch = async () => {
  if (!searchKeyword.value.trim()) {
    fetchStudents()
    return
  }
  loading.value = true
  try {
    const res = await studentApi.search(searchKeyword.value)
    students.value = res.data
  } catch (error) {
    ElMessage.error('搜索失败')
  } finally {
    loading.value = false
  }
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

// 编辑对话框
const editDialogVisible = ref(false)
const editFormRef = ref<FormInstance>()
const editForm = reactive({
  id: 0,
  username: '',
  email: '',
  password: ''
})

const editRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 50, message: '用户名长度在2-50个字符之间', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ]
}

const openEditDialog = (row: User) => {
  editForm.id = row.id
  editForm.username = row.username
  editForm.email = row.email
  editForm.password = ''
  editDialogVisible.value = true
}

// 更新学生信息
const handleUpdate = async () => {
  if (!editFormRef.value) return
  
  await editFormRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        const updateData: Partial<User> = {
          username: editForm.username,
          email: editForm.email
        }
        // 如果输入了密码才传递
        if (editForm.password) {
          updateData.password = editForm.password
        }
        await studentApi.update(editForm.id, updateData)
        ElMessage.success('更新成功')
        editDialogVisible.value = false
        fetchStudents()
      } catch (error) {
        ElMessage.error('更新失败')
      } finally {
        submitLoading.value = false
      }
    }
  })
}

// 删除学生
const handleDelete = (row: User) => {
  ElMessageBox.confirm(
    `确定要删除学生 "${row.username}" 吗？此操作不可恢复！`,
    '删除确认',
    {
      confirmButtonText: '确定删除',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await studentApi.delete(row.id)
      ElMessage.success('删除成功')
      fetchStudents()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  }).catch(() => {})
}

onMounted(() => {
  fetchStudents()
})
</script>

<style scoped>
.student-page {
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

.search-section {
  display: flex;
  gap: 12px;
  align-items: center;
}

.search-input {
  width: 280px;
}

.action-section {
  display: flex;
  gap: 12px;
  align-items: center;
}

.table-container {
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid #ebeef5;
}

.username-cell {
  font-weight: 600;
  color: #1a1a2e;
}

:deep(.el-table th.el-table__cell) {
  background-color: #f8f9fa;
  color: #333;
  font-weight: 600;
}

:deep(.el-table td.el-table__cell) {
  padding: 16px 0;
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
