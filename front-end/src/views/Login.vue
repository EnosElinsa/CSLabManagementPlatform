<template>
    <div>
        <div class="login-container">
            <div class="login-box">
                <div style="text-align: center; margin-bottom: 20px; ">
                    <img src="@/assets/imgs/logo.png" alt="" style="width: 60px; height: 60px; border-radius: 4%" >
                </div>
                <div style="font-size: 18px; text-align: center; margin-bottom: 30px; font-weight: bold">
                    计算机系实验室管理平台
                </div>
 
                <el-form :model="data.form" ref="formRef" :rules="rules">
                    <el-form-item prop="username">
                        <el-input prefix-icon="User" v-model="data.form.username" placeholder="请输入账号"/>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input show-password prefix-icon="Lock" v-model="data.form.password" placeholder="请输入密码"/>
                    </el-form-item>
                    <el-form-item prop="inputCaptcha" v-if="data.loginFailed">
                        <el-input prefix-icon="CircleCheck" v-model="data.form.inputCaptcha" placeholder="请输入验证码" style="flex: 1; margin-right: 10px; " />
                        <Captcha style="height: 32px; flex: 0.7;" @input="generateCaptcha"/>
                    </el-form-item>
                    <el-form-item>
                        <el-button type ="primary" style="width: 100%; background-color: rgb(77, 136, 166);" @click="login">
                            登 录
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
    <div class="icp-login">{{`© ${year} ${author} ` }}<a href="http://beian.miit.gov.cn/" target="_blank">{{ record }}</a></div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import request from '@/utils/request';
import {ElMessage} from "element-plus";
import router from '@/router';
import Captcha from "@/components/Captcha.vue"

const data = reactive({
    form: {},

    generatedCaptcha: '',

    loginFailed: localStorage.getItem('loginFailed') === null ? false : JSON.parse(localStorage.getItem('loginFailed')),

})

const year = new Date().getFullYear()
const author = 'Enos'					
const record = '粤ICP备2023094696号'

const rules = reactive({
    username: [
        { required: true, message: '请输入账号', trigger: 'blur'},
    ],

    password: [
        { required: true, message: '请输入密码', trigger: 'blur'},
    ],

    inputCaptcha: [
        { required: true, message: '请输入验证码', trigger: 'blur'},
        { min: 4, max: 4, message: '验证码长度为4位', trigger: 'blur'},
        { validator: (rule, value, callback) => {
            if (value.toUpperCase() !== data.generatedCaptcha.toUpperCase()) {
                callback(new Error('验证码错误'))
            } else {
                callback()
            }
        }, trigger: 'blur' },
    ],
})

const generateCaptcha = (captcha) => {
    data.generatedCaptcha = captcha
}

const formRef = ref()

const login = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            request.post('/user/login', data.form).then(res => {
                if (res.code === 1) {
                    localStorage.setItem('user', JSON.stringify(res.data))
                    localStorage.setItem('loginFailed', false)
                    ElMessage.success('登录成功')
                    router.push('/home')
                } else {
                    localStorage.setItem('loginFailed', true)
                    data.loginFailed = true
                    ElMessage.error(res.msg)
                }
            })
        }
    })
}

</script>

<style scoped>

</style>