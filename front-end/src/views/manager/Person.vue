<template>
    <div>
        <el-card style="width: 40%; margin:auto; margin-top: 50px; padding: 10px;" v-if="data.user">
            <div style="text-align: center; margin-bottom: 20px; font-size: 18px; font-weight: bold;">个人信息</div>
            <el-form :model="data.user" :rules="rules" ref="formRef" label-width="100px" style="padding-right: 20px;">
                <el-form-item label="用户名:" prop="username">
                    <el-input v-model="data.user.username" disabled></el-input>
                </el-form-item>
                <el-form-item label="姓名:" prop="fullName">
                    <el-input v-model="data.user.fullName" disabled></el-input>
                </el-form-item>
                <el-form-item label="职称:" prop="title" v-if="data.user.role === '教师' || data.user.role === '实验员'">
                    <el-input v-model="data.user.title" placeholder="请输入职称"></el-input>
                </el-form-item>
                <el-form-item label="专业:" prop="major" v-if="data.user.role === '学生'">
                    <el-input v-model="data.user.major" placeholder="请输入专业"></el-input>
                </el-form-item>
                <el-form-item label="班级:" prop="studentClass" v-if="data.user.role === '学生'">
                    <el-input v-model="data.user.studentClass" placeholder="请输入班级"></el-input>
                </el-form-item>
                <div style="text-align: center; margin-top: 20px; margin-left: 20px;">
                    <el-button type="primary" @click="update" style="width: 50%;">保存</el-button>
                </div>
            </el-form>
        </el-card>
    </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request";

const data = reactive({

    user: JSON.parse(localStorage.getItem("user") || '{}'),

})

const rules = {
    title: [
        { required: true, message: '请输入职称', trigger: 'blur' },
        { min: 1, max: 16, message: '职称长度在 1 到 16 个字符', trigger: 'blur' }
    ],
    major: [
        { required: true, message: '请输入专业', trigger: 'blur' },
        { min: 1, max: 32, message: '专业长度在 1 到 32 个字符', trigger: 'blur' }
    ],
    studentClass: [
        { required: true, message: '请输入班级', trigger: 'blur' },
        { min: 1, max: 32, message: '班级长度在 1 到 32 个字符', trigger: 'blur' }
    ]
}

const formRef = ref()

const update = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            request.request({
                url: '/user/updateById',
                method: 'PUT',
                data: data.user
            }).then(res => {
                if (res.code === "200") {
                    ElMessage.success("操作成功")
                    ElMessage.success("请重新登录")
                    localStorage.removeItem('user')
                    router.push('/login')
                } else {
                    ElMessage.error(res.msg)
                }
            })
        } else {
            ElMessage.error('请检查输入项')
        }
  })
}

</script>

<style scoped>

</style>