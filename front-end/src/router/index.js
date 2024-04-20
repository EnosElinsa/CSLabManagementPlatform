import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/', name: 'Manager', component: () => import('@/views/Manager.vue'), redirect: '/home',
      children: [
        { path: 'home', name: 'Home', component: () => import('@/views/manager/Home.vue') },
        { path: 'person', name: 'Person', component: () => import('@/views/manager/Person.vue') },
        { path: 'password', name: 'Password', component: () => import('@/views/manager/Password.vue') },
        { path:'semester', name: 'Semester', component: () => import('@/views/manager/Semester.vue') },
        { path: 'technician', name: 'Technician', component: () => import('@/views/manager/user/Technician.vue') },
        { path: 'teacher', name: 'Teacher', component: () => import('@/views/manager/user/Teacher.vue') },
        { path: 'student', name: 'Student', component: () => import('@/views/manager/user/Student.vue') },
        { path: 'schedule', name: 'Schedule', component: () => import('@/views/manager/Schedule.vue') },
        { path: 'scheduleRequest', name: 'ScheduleRequest', component: () => import('@/views/manager/ScheduleRequest.vue') },
        { path: 'borrow', name: 'Borrow', component: () => import('@/views/manager/Borrow.vue') },
        { path: 'borrowRequest', name: 'BorrowRequest', component: () => import('@/views/manager/BorrowRequest.vue') },
        { path: 'repair', name: 'Repair', component: () => import('@/views/manager/Repair.vue') },
        { path: 'repairRequest', name: 'RepairRequest', component: () => import('@/views/manager/RepairRequest.vue') },
      ]
    },
    { path: '/login', name: 'Login', component: () => import('@/views/Login.vue') },
    { path: '/403', name: '403', component: () => import('@/views/403.vue') },
    { path: '/404', name: '404', component: () => import('@/views/404.vue') },
    { path: '/:catchAll(.*)', redirect: '/404' },
  ]
})

router.beforeEach((to, from, next) => {
  const adminPaths= ['/semester', '/technician', '/teacher', '/student', '/schedule', '/borrow']
  const teacherPaths=['/scheduleRequest', 'repairRequest']
  const technicianPaths=['/repair']
  const studentPaths=['/borrowRequest']
  const user = JSON.parse(localStorage.getItem('user') || '{}')

  if (user.role !== '系统管理员' && adminPaths.includes(to.path)) {
    next('/403')
  } else if (user.role !== '教师' && teacherPaths.includes(to.path)) {
    next('/403')
  } else if (user.role !== '实验员' && technicianPaths.includes(to.path)) {
    next('/403')
  } else if (user.role !== '学生' && studentPaths.includes(to.path)) { 
    next('/403')
  }
  next()
})

export default router
