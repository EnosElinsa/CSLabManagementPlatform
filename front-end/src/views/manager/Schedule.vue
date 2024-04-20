<template>
    <div>
        <div class="card" style="margin-bottom: 10px;">
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.courseName" placeholder="请输入课程名称查询"></el-input>
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.labCategory" placeholder="请输入实验室类型查询"></el-input>
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.studentClass" placeholder="请输入学生班级查询"></el-input>
            <el-button type="primary" @click="load">查询</el-button>
            <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
        </div>

        <div class="card" style="margin-bottom: 10px">
            <el-table stripe :data="data.records" :header-cell-style="{background:'aliceblue'}" table-layout="auto">
                <el-table-column label="编号" prop="requestId" align="center" sortable/>
                <el-table-column label="学期" prop="semester" align="center" sortable />
                <el-table-column label="任课老师" prop="fullName" align="center" />
                <el-table-column label="课程名" prop="courseName" align="center" />
                <el-table-column label="实验室类型" prop="labCategory" align="center" />
                <el-table-column label="学生班级" prop="studentClass" align="center" />
                <el-table-column label="学生人数" prop="studentCount" align="center" />
                <el-table-column label="开始周" prop="startWeek" align="center" />
                <el-table-column label="结束周" prop="endWeek" align="center" />
                <el-table-column label="星期" prop="day" align="center" />
                <el-table-column label="节次" prop="session" align="center" />
                <el-table-column label="实验室" prop="labId" align="center" />
                <el-table-column label="排课状态" prop="status" align="center" sortable>
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.status === '已排课'">已排课</el-tag>
                        <el-tag type="warning" v-if="scope.row.status === '未排课'">未排课</el-tag>
                    </template>
                </el-table-column>
                
                <el-table-column label="操作" align="center">
                    <template #default="scope">
                        <el-button type="primary" v-if="scope.row.status === '未排课'" @click="handleSchedule(scope.row)">排课</el-button>
                        <el-button type="danger" v-if="scope.row.status === '已排课'" @click="handleDelete(scope.row)">撤销</el-button>
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

        <el-dialog title="排课" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
            <el-form :model="data.form" label-width="100px" style="padding-right: 50px" :rules="data.rules" ref="formRef">
                <el-form-item label="学期" prop="semester">
                    <el-input v-model="data.currentRequest.semester" disabled />
                </el-form-item>
                <el-form-item label="开始周" prop="startWeek">
                    <el-input v-model="data.currentRequest.startWeek" disabled />
                </el-form-item>
                <el-form-item label="结束周" prop="endWeek">
                    <el-input v-model="data.currentRequest.endWeek" disabled />
                </el-form-item>
                <el-form-item label="星期" prop="day">
                    <el-input v-model="data.currentRequest.day" disabled />
                </el-form-item>
                <el-form-item label="节次" prop="session">
                    <el-input v-model="data.currentRequest.session" disabled />
                </el-form-item>
                <el-form-item label="课程名" prop="courseName">
                    <el-input v-model="data.currentRequest.courseName" disabled />
                </el-form-item>
                <el-form-item label="实验室类型" prop="labCategory">
                    <el-input v-model="data.currentRequest.labCategory" disabled />
                </el-form-item>
                <el-form-item label="任课老师" prop="fullName">
                    <el-input v-model="data.currentRequest.fullName" disabled />
                </el-form-item>
                <el-form-item label="学生班级" prop="studentClass">
                    <el-input v-model="data.currentRequest.studentClass" disabled />
                </el-form-item>
                <el-form-item label="学生人数" prop="studentCount">
                    <el-input v-model="data.currentRequest.studentCount" disabled />
                </el-form-item>
                <el-form-item label="实验室">
                    <el-select v-model="data.form.labId" placeholder="请选择实验室" style="width: 300%;">
                        <el-option v-for="item in data.labs" :key="item.labId" :label="item.labId + ' - ' + item.name + ' - ' 
                            + item.category +  ' - ' + ' 设备数：' + item.equipmentCount" :value="item.labId" />
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
import {ElMessage, ElMessageBox} from "element-plus";
import {Search} from '@element-plus/icons-vue'

const data = reactive({
    courseName: '',
    labCategory: '',
    studentClass: '',

    records: [],
    total: 0,
    current: 1,
    size: 10,

    formVisible: false,
    form: {},

    rules: {
        labId: [
            { required: true, message: '请选择实验室', trigger: 'blur' }
        ],
    },

    currentRequest: {},

    labs: [],
})
  
const load = () => {  
    const requestData = {
        current: data.current,
        size: data.size,

        courseName: data.courseName,
        labCategory: data.labCategory,
        studentClass: data.studentClass,
    };

    request.get('/labScheduleRequest/page', { params: requestData }).then(res => {
        data.records = res.data?.records || [];
        data.total = res.data?.total || 0;
    });
}
  
load()

const reset = () => {
    data.courseName = ''
    data.labCategory = ''
    data.studentClass = ''
    data.current = 1
    
    load()
}

const formRef = ref()

const handleSchedule = (row) => {
    data.currentRequest = JSON.parse(JSON.stringify(row))
    data.formVisible = true
    request.request({
      url: '/lab/listByCategory/' + data.currentRequest.labCategory,
      method: 'GET'
    }).then(res => {
        if (res.code === "200") {
            data.labs = res.data
        }
    })
}

const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
        data.form.requestId = data.currentRequest.requestId
        data.form.semesterId = data.currentRequest.semesterId
        data.form.startWeek = data.currentRequest.startWeek
        data.form.endWeek = data.currentRequest.endWeek
        data.form.session = data.currentRequest.session
        data.form.courseName = data.currentRequest.courseName
        data.form.teacherId = data.currentRequest.teacherId
        data.form.studentClass = data.currentRequest.studentClass
        request.request({
            url: '/schedule/save',
            method: 'POST',
            data: data.form
        }).then(res => {
            if (res.code === "200") {
                data.currentRequest.status = '已排课'
                data.currentRequest.labId = data.form.labId
                request.request({
                    url: '/labScheduleRequest/updateById',
                    method: 'PUT',
                    data: data.currentRequest
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
                ElMessage.error(res.message)
            }
        })
    } else {
      ElMessage.error('请检查输入项')
    }
  })
}

const handleDelete = (row) => {
    data.currentRequest = JSON.parse(JSON.stringify(row))
    data.currentRequest.status = '未排课'
    data.currentRequest.labId = ''
    ElMessageBox.confirm('撤销后数据无法恢复，您确定撤销吗?', '撤销确认', { type: 'warning' }).then(res => {
        request.request({
            url: '/schedule/removeByRequestId/' + data.currentRequest.requestId,
            method: 'DELETE',
        }).then(res => {
            if (res.code === "200") {
                request.request({
                    url: '/labScheduleRequest/updateById',
                    method: 'PUT',
                    data: data.currentRequest
                }).then(res => {
                    if (res.code === "200") {
                        load()
                        ElMessage.success("操作成功")
                    } else {
                        ElMessage.error(res.message)
                    }
                })
            } else {
                ElMessage.error(res.message)
            }
        })
    }).catch(err => {
        console.error(err)
    })
}

const handleCurrentChange = () => {
    load()
}

const handleSizeChange = (size) => {
    data.size = size
    load()
}
  
</script>