<template>
    <div>
        <el-card style="width: 40%; margin:auto; margin-top: 50px; padding: 10px;" v-if="data.user">
            <div style="text-align: center; margin-bottom: 20px; font-size: 18px; font-weight: bold;">修改密码</div>
            <el-form :model="data" :rules="rules" ref="formRef" label-width="100px" style="padding-right: 20px;">
                <el-form-item label="旧密码:" prop="oldPassword">
                    <el-input show-password type="password" v-model="data.oldPassword" placeholder="请输入旧密码"></el-input>
                </el-form-item>
                <el-form-item label="新密码:" prop="newPassword">
                    <el-input show-password type="password" v-model="data.newPassword" placeholder="请输入新密码"></el-input>
                </el-form-item>
                <div style="text-align: center; margin-top: 20px; margin-left: 20px;">
                    <el-button type="primary" @click="update" style="width: 50%;">确认修改</el-button>
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

    oldPassword: '',

    newPassword: '',

})

const rules = {
    oldPassword: [
        { required: true, message: '请输入旧密码', trigger: 'blur' },
        { validator: (rule, value, callback) => {
            if (data.user.password !== value) {
                callback(new Error('旧密码错误'))
            } else {
                callback()
            }
        }, trigger: 'blur' }
    ],
    newPassword: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 8, max: 128, message: '密码长度在8到128个字符之间', trigger: 'blur' },
        { validator: (rule, value, callback) => {
            if (value && !/\d/.test(value)) {
                callback(new Error('密码必须包含数字'))
            } else {
                callback()
            }
        }, trigger: 'blur' },
        { validator: (rule, value, callback) => {
            if (value && !/[a-z]/.test(value)) {
                callback(new Error('密码必须包含小写字母'))
            } else {
                callback()
            }
        }, trigger: 'blur' },
        { validator: (rule, value, callback) => {
            if (value && !/[A-Z]/.test(value)) {
                callback(new Error('密码必须包含大写字母'))
            } else {
                callback()
            }
        }, trigger: 'blur' },
        { validator: (rule, value, callback) => {
            if (value && !/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(value)) {
                callback(new Error('密码必须包含特殊字符'))
            } else {
                callback()
            }
        }, trigger: 'blur' },
    ],
}

const formRef = ref()

const update = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            data.user.password = data.newPassword
            request.request({
                url: '/user/updateById',
                method: 'PUT',
                data: data.user
            }).then(res => {
                if (res.code === "200") {
                    ElMessage.success("操作成功")
                    localStorage.setItem("user", JSON.stringify(data.user))
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