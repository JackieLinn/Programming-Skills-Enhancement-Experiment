import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// ==================== Auth API ====================
export const authApi = {
  // 管理员登录
  adminLogin(username: string, password: string) {
    return api.post<boolean>('/auth/admin/login', { username, password })
  },
  // 学生注册
  studentRegister(username: string, password: string, email: string) {
    return api.post('/auth/student/register', { username, password, email })
  }
}

// ==================== Student API ====================
export interface User {
  id: number
  username: string
  password: string
  email: string
  role: 'ADMIN' | 'STUDENT'
  createTime: string
}

export const studentApi = {
  // 获取学生列表
  list() {
    return api.get<User[]>('/students')
  },
  // 搜索学生
  search(kw: string) {
    return api.get<User[]>('/students/search', { params: { kw } })
  },
  // 更新学生信息
  update(id: number, data: Partial<User>) {
    return api.put<User>(`/students/${id}`, data)
  },
  // 仅更新邮箱
  updateEmail(id: number, email: string) {
    return api.put<number>(`/students/${id}/email`, { email })
  },
  // 删除学生
  delete(id: number) {
    return api.delete(`/students/${id}`)
  }
}

// ==================== Course API ====================
export interface Course {
  id: number
  name: string
  teacher: string
  credit: number
}

export const courseApi = {
  // 新增课程
  add(course: Omit<Course, 'id'>) {
    return api.post<Course>('/courses', course)
  },
  // 课程列表
  list() {
    return api.get<Course[]>('/courses')
  },
  // 更新课程信息
  update(id: number, data: Partial<Course>) {
    return api.put<Course>(`/courses/${id}`, data)
  },
  // 仅更新教师
  updateTeacher(id: number, teacher: string) {
    return api.put<number>(`/courses/${id}/teacher`, null, { params: { teacher } })
  },
  // 删除课程
  delete(id: number) {
    return api.delete(`/courses/${id}`)
  }
}

// ==================== Enrollment API ====================
export interface EnrollmentView {
  enrollmentId: number
  studentId: number
  studentUsername: string
  courseId: number
  courseName: string
  teacher: string
  credit: number
  createTime: string
}

export const enrollmentApi = {
  // 选课
  enroll(studentId: number, courseId: number) {
    return api.post('/enrollments', { studentId, courseId })
  },
  // 查看学生选课
  listByStudent(studentId: number) {
    return api.get<EnrollmentView[]>(`/enrollments/student/${studentId}`)
  },
  // 按选课记录退课
  drop(enrollmentId: number) {
    return api.delete(`/enrollments/${enrollmentId}`)
  },
  // 按学生+课程退课
  dropByStudentCourse(studentId: number, courseId: number) {
    return api.delete('/enrollments', { params: { studentId, courseId } })
  }
}

export default api

