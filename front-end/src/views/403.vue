<template>
    <div class="error-page">
        <el-row type="flex" justify="center" align="middle" class="error-content">
        <el-col :span="12">
            <h1 class="error-code">403</h1>
            <p class="error-message">无权访问。</p >
            <router-link to="/" class="home-link">返回首页</router-link>
        </el-col>
        </el-row>
    </div>
</template>

<script setup>

</script>

<style scoped>
.error-page {
    font-family: 'Helvetica Neue', Arial, sans-serif;
    color: #333;
    text-align: center;
    height: 100vh;
    overflow: hidden;
    background: #e1e1e1;
}

.error-content {
    margin-top: 100px;
}

.error-code {
    font-size: 120px;
    color: #555;
}

.error-message {
    font-size: 24px;
    margin-bottom: 20px;
}

.home-link {
    display: inline-block;
    margin: 20px 0;
    padding: 10px 20px;
    background: #409eff;
    color: #fff;
    border-radius: 4px;
    text-decoration: none;
}

.error-description {
    margin-bottom: 40px;
}

.error-search {
    width: 300px;
}
</style>