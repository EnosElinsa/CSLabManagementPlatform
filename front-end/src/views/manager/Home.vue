<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div>欢迎您，<span style="color: dodgerblue;">{{ user.fullName }}</span> 祝您今天过得开心！</div>
    </div>

  </div>
</template>

<script setup>
  import router from '@/router';
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  if (Object.keys(user).length === 0) {
    ElMessage.error('请先登录');
    router.push("/login")
  }
</script>