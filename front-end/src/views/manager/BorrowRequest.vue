<template>
    <div>

        <div class="card" style="margin-bottom: 10px;">
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.labId" placeholder="请输入实验室编号查询"></el-input>
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.status" placeholder="请输入申请状态查询"></el-input>
            <el-button type="primary" @click="load">查询</el-button>
            <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
        </div>

        <div class="card" style="margin-bottom: 10px">
            <div style="margin-bottom: 10px">
                <el-button type="primary" @click="handleAdd">新增</el-button>
                <el-button type="danger" @click="handleDeleteBatch">批量删除</el-button>
            </div>

            <el-table :data="data.records" :header-cell-style="{background:'aliceblue'}" @selection-change="handleSelectionChange" table-layout="auto">
                <el-table-column type="selection" width="55" align="center" />

                <el-table-column label="编号" prop="requestId" align="center" width=80% sortable />
                <el-table-column label="学期" prop="semester" align="center" sortable />
                <el-table-column label="周次" prop="week" align="center" sortable />
                <el-table-column label="星期" prop="day" align="center" sortable />
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
        

                <el-table-column label="操作" align="center" width="160">
                    <template #default="scope">
                        <el-button type="primary" v-if="scope.row.status!='使用完毕'" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="danger"  @click="handleDelete(scope.row.requestId)">删除</el-button>
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
                    <el-select v-model="data.form.semesterId" placeholder="请选择学期" style="width: 300%;">
                        <el-option v-for="item in data.semesters" :key="item.semesterId" :label="item.semester" 
                            :value="item.semesterId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="申请周次" prop="week">
                    <el-input v-model.number="data.form.week" />
                </el-form-item>
                <el-form-item label="申请星期" prop="day">
                    <el-select v-model="data.form.day" placeholder="请选择节次" style="width: 300%;">
                        <el-option v-for="item in data.days" :key="item.value" :label="item.label" 
                            :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="申请节次" prop="session">
                    <el-select v-model="data.form.session" placeholder="请选择节次" style="width: 300%;">
                        <el-option v-for="item in data.sessions" :key="item.value" :label="item.label" 
                            :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="实验室编号" prop="labId">
                    <el-select v-model="data.form.labId" placeholder="请选择实验室编号" style="width: 300%;">
                        <el-option v-for="item in data.labs" :key="item.labId" :label="item.labId + ' - ' + item.name + ' - ' 
                            + item.category +  ' - ' + ' 设备数：' + item.equipmentCount" :value="item.labId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="申请理由" prop="reason">
                    <el-input v-model="data.form.reason" autocomplete="off" minlength="1" maxlength="128" show-word-limit 
                        type="textarea" autosize/>
                </el-form-item>
                <el-form-item label="状态" prop="status" v-if="data.form.status==='通过'">
                    <el-select v-model="data.form.status" placeholder="请选择状态" style="width: 300%;">
                        <el-option label="使用完毕（选择后无法恢复）" value="使用完毕"></el-option>
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
    studentId: JSON.parse(localStorage.getItem('user') || '{}').userId,
    labId: '',
    status: '',

    records: [],
    total: 0,
    current: 1,
    size: 10,

    formVisible: false,
    form: {},

    rules: {
        semesterId: [
            { required: true, message: '请选择申请学期', trigger: 'blur' },
        ],
        week: [
            { required: true, message: '请输入申请周次', trigger: 'blur' },
            { type: 'number', message: '申请周次必须为正整数', trigger: 'blur' }
        ],
        session: [
            { required: true, message: '请选择申请节次', trigger: 'blur' },
        ],
        labId: [
            { required: true, message: '请选择实验室编号', trigger: 'blur' },
        ],
        reason: [
            { required: true, message: '请输入申请理由', trigger: 'blur' },
        ],
    },

    ids: [],

    labs: [],
    semesters: [],
    sessions: [
        {
            value: '1-2',
            label: '第1-2节'
        },
        {
            value: '3-5',
            label: '第3-5节'
        },
        {
            value: '6-7',
            label: '第6-7节'
        },
        {
            value: '8-9',
            label: '第8-9节'
        },
        {
            value: '10-12',
            label: '第10-12节'
        },
        {
            value: '13-15',
            label: '第13-15节'
        },
    ],
    days: [
        {
            value: '星期一',
            label: '星期一'
        },
        {
            value: '星期二',
            label: '星期二'
        },
        {
            value: '星期三',
            label: '星期三'
        },
        {
            value: '星期四',
            label: '星期四'
        },
        {
            value: '星期五',
            label: '星期五'
        },
        {
            value: '星期六',
            label: '星期六'
        },
        {
            value: '星期日',
            label: '星期日'
        },
    ],
})

const initialize = () => {
    request.get('/semester/list').then(res => {
        data.semesters = res.data
    })
    request.get('/lab/list').then(res => {
        data.labs = res.data
    })
}

initialize()

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

const handleAdd = () => {
    data.form = {}
    data.formVisible = true
    data.form.semesterId = JSON.parse(localStorage.getItem('semester') || '{}').semesterId
}

const formRef = ref()

const save = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            data.form.studentId = data.studentId
            request.request({
                url: data.form.requestId ? '/labBorrowRequest/updateById' : '/labBorrowRequest/save',
                method: data.form.requestId ? 'PUT' : 'POST',
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

const handleEdit = (row) => {
    data.form = JSON.parse(JSON.stringify(row))
    data.formVisible = true
}

const handleDelete = (requestId) => {
    ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
        request.delete('/labBorrowRequest/removeById/' + requestId).then(res => {
            if (res.code === "200") {
                load()
                ElMessage.success("删除成功")
            } else {
                ElMessage.error(res.message)
            }
        })
    }).catch(err => {
        console.error(err)
    })
}

const handleDeleteBatch = () => {
    if (data.ids.length === 0) {
        ElMessage.warning('请选择要删除的申请')
        return
    }

    ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
        request.request({
        url: '/labBorrowRequest/removeBatchByIds',
        method: 'DELETE',
        data: data.ids
        }).then(res => {
        if (res.code === "200") {
            load()
            ElMessage.success("删除成功")
        } else {
            ElMessage.error(res.message)
        }
        })
    }).catch(err => {
        console.error(err)
    })  
}

const handleSelectionChange = (rows) => {
    data.ids = rows.map(item => item.requestId)
}

</script>

<style>

</style>