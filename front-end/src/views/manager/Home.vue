<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div style="font-weight: bold; text-align: center; margin-top: 10px;"> {{ semester.semester }} 学期排课表 </div>
    </div>

    <div class="card" style="margin-bottom: 10px">
      
    </div>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import router from '@/router';
import {reactive} from "vue";

const user = JSON.parse(localStorage.getItem('user') || '{}')
const semester = JSON.parse(localStorage.getItem('semester') || '{}')
if (Object.keys(user).length === 0) {
  ElMessage.error('请先登录');
  router.push("/login")
}

const data = reactive({
  records: []
})

const initialize = () => {
  request.get('/schedule/list').then(res => {
    data.records = res.data
    console.log(data.records)
  })
}

initialize()

</script>