<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div>欢迎您，<span style="color: dodgerblue;">{{ admin.nickname }}</span> 祝您今天过得开心！</div>
    </div>

  </div>
</template>

<script setup>
  import request from "@/utils/request";
  import router from '@/router';
  const admin = JSON.parse(localStorage.getItem('admin') || '{}')
  if (Object.keys(admin).length === 0) {
    ElMessage.error('请先登录');
    router.push("/login")
  }
</script>