<template>
    <div>

        <div class="card" style="margin-bottom: 10px;">
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.labId" placeholder="请输入实验室编号查询"></el-input>
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.status" placeholder="请输入申请状态查询"></el-input>
            <el-button type="primary" @click="load">查询</el-button>
            <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
        </div>

        <div class="card" style="margin-bottom: 10px">

        <el-table :data="data.records" :header-cell-style="{background:'aliceblue'}" table-layout="auto">
            <el-table-column label="编号" prop="requestId" align="center" width=80% sortable />
            <el-table-column label="学期" prop="semester" align="center" sortable />
            <el-table-column label="姓名" prop="studentName" align="center" sortable />
            <el-table-column label="专业" prop="major" align="center" sortable />
            <el-table-column label="班级" prop="studentClass" align="center" sortable />
            <el-table-column label="周次" prop="week" align="center" sortable />
            <el-table-column label="节次" prop="session" align="center" sortable />
            <el-table-column label="实验室编号" prop="labId" align="center" sortable />
            <el-table-column label="申请理由" prop="reason" align="center" />
            <el-table-column label="填报日期" prop="date" align="center" sortable />
            <el-table-column label="状态" prop="status" align="center" sortable>
                <template #default="scope">
                    <el-tag type="success" v-if="scope.row.status==='通过'">通过</el-tag>
                    <el-tag type="warning" v-if="scope.row.status==='未审核'">未审核</el-tag>
                    <el-tag type="danger" v-if="scope.row.status==='驳回'">驳回</el-tag>
                    <el-tag type="info" v-if="scope.row.status==='使用完毕'">使用完毕</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作" align="center">
                <template #default="scope">
                    <el-button type="primary" @click="handleEdit(scope.row)" v-if="scope.row.status != '使用完毕'">审批</el-button>
                    <el-text type="success" v-if="scope.row.status == '使用完毕'" disabeld>（已审批）</el-text>
                </template>
            </el-table-column>
        </el-table>
        </div>

        <div class="card">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :page-sizes="[5, 10, 15, 20, 30, 40, 50, 100, 200, 300, 400]"
            v-model:current-page.sync="data.current"
            v-model:page-size="data.size"
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total" />
        </div>

        <el-dialog title="学期信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
            <el-form :model="data.form" label-width="100px" style="padding-right: 50px" :rules="data.rules" ref="formRef">
                <el-form-item label="申请学期" prop="semesterId">
                    <el-select v-model="data.form.semesterId" style="width: 300%;" disabled />
                </el-form-item>
                <el-form-item label="姓名" prop="studentName">
                    <el-input v-model="data.form.studentName" disabled />
                </el-form-item>
                <el-form-item label="专业" prop="major">
                    <el-input v-model="data.form.major" disabled />
                </el-form-item>
                <el-form-item label="班级" prop="studentClass">
                    <el-input v-model="data.form.studentClass" disabled />
                </el-form-item>
                <el-form-item label="申请周次" prop="week">
                    <el-input v-model.number="data.form.week" disabled />
                </el-form-item>
                <el-form-item label="申请节次" prop="session">
                    <el-select v-model="data.form.session"  style="width: 300%;" disabled />
                </el-form-item>
                <el-form-item label="实验室编号" prop="labId">
                    <el-select v-model="data.form.labId" placeholder="请选择实验室编号" style="width: 300%;" disabled/>
                </el-form-item>
                <el-form-item label="申请理由" prop="reason">
                    <el-input v-model="data.form.reason" autocomplete="off" minlength="1" maxlength="128" show-word-limit 
                        type="textarea" autosize disabled/>
                </el-form-item>
                <el-form-item label="状态" prop="status" v-if="data.form.status != '使用完毕'">
                    <el-select v-model="data.form.status" placeholder="请选择状态" style="width: 300%;">
                        <el-option label="驳回" value="驳回" />
                        <el-option label="通过" value="通过" />
                    </el-select>
                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="data.formVisible = false">取 消</el-button>
                    <el-button type="primary" @click="save">保 存</el-button>
                </span>
            </template>
        </el-dialog>

    </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive, ref} from "vue";
import {Search} from '@element-plus/icons-vue'

const data = reactive({
    labId: '',
    status: '',

    records: [],
    total: 0,
    current: 1,
    size: 10,

    formVisible: false,
    form: {},

    rules: {

    },

})

const load = () => {  
    const requestData = {
        current: data.current,
        size: data.size,

        studentId: data.studentId,
        labId: data.labId,
        status: data.status,
    };

    request.get('/labBorrowRequest/page', { params: requestData })
        .then(res => {
            data.records = res.data?.records || [];
            data.total = res.data?.total || 0;
    });
}

load()

const save = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            request.request({
                url: '/labBorrowRequest/updateById',
                method:'PUT',
                data: data.form
            }).then(res => {
                if (res.code === "200") {
                    load()
                    data.formVisible = false
                    ElMessage.success("操作成功")
                } else {
                    ElMessage.error(res.message)
                }
            })
        } else {
            ElMessage.error('请检查输入项')
        }
    })
}

const handleCurrentChange = () => {
    load()
}

const handleSizeChange = (size) => {
    data.size = size
    load()
}

const reset = () => {
    data.labId = ''
    data.status = ''
    data.current = 1

    load()
}

const formRef = ref()

const handleEdit = (row) => {
    data.form = JSON.parse(JSON.stringify(row))
    data.formVisible = true
}

</script>

<style>

</style>