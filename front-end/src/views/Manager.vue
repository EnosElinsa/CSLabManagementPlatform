<template>
  <div>
    <div style="height: 60px; background-color: #fff; display: flex; align-items: center; border-bottom: 1px solid #ddd">
      <div style="flex: 1">
        <div style="padding-left: 20px; display: flex; align-items: center">
          <img src="@/assets/imgs/logo.png" alt="" style="width: 40px">
          <div style="font-weight: bold; font-size: 24px; margin-left: 5px">计算机系实验室管理平台</div>
        </div>
      </div>

      <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end;">
        <el-dropdown style="margin-right: 15px" placement="bottom">
          <div style="display: flex; align-items: center; cursor: default;">
            <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" alt="" style="width: 40px; height: 40px">
            <span style="margin-left: 5px">{{ user.fullName }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click.native="router.push('/person')">个人信息</el-dropdown-item>
              <el-dropdown-item @click.native="router.push('/password')">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logout">退出系统</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <div style="display: flex">
      <div style="width: 200px; border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
        <el-menu
            router
            style="border: none"
            :default-active="$route.path"
            :default-openeds="['/home', '2', '3', '4']"
        >
          <el-menu-item index="/home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><Files /></el-icon>
              <span>平台信息管理</span>
            </template>
            <el-menu-item index="/semester">
              <el-icon><SetUp /></el-icon>
              <span>学期管理</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/technician">
              <el-icon><Postcard /></el-icon>
              <span>实验员管理</span>
            </el-menu-item>
            <el-menu-item index="/teacher">
              <el-icon><Postcard /></el-icon>
              <span>教师管理</span>
            </el-menu-item>
            <el-menu-item index="/student">
              <el-icon><Postcard /></el-icon>
              <span>学生管理</span>
            </el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="4">
            <template #title>
              <el-icon><Monitor /></el-icon>
              <span>实验室管理</span>
            </template>
            <el-menu-item index="/schedule">
              <el-icon><DataBoard /></el-icon>
              <span>实验排课</span>
            </el-menu-item>
            <el-menu-item index="/borrow">
              <el-icon><Check /></el-icon>
              <span>实验室借用审批</span>
            </el-menu-item>
          </el-sub-menu>
    
          <el-menu-item index="login" @click="logout">
            <el-icon><SwitchButton /></el-icon>
            <span>退出系统</span>
          </el-menu-item>
        </el-menu>
      </div>
      
      <div style="flex: 1; width: 0; background-color: #f8f8ff; padding: 10px">
        <router-view />
      </div>
    </div>

    <div class="icp">
      <el-text>
        {{`© ${year} ${author} ` }}<a href="http://beian.miit.gov.cn/" target="_blank">{{ record }}</a>
      </el-text>
    </div>
  </div>


</template>


<script setup>
import router from '@/router';

const year = new Date().getFullYear()
const author = 'Enos'					
const record = '粤ICP备2023094696号'

const user = JSON.parse(localStorage.getItem('user') || '{}')

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

</script>

<style scoped>
.el-menu-item.is-active {
  background-color: #dcede9 !important;
}
.el-menu-item:hover {
  color: #11A983;
}
:deep(th)  {
  color: #333;
}
</style>