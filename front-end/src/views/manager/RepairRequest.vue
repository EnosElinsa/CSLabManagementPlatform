<template>
    <div>

        <div class="card" style="margin-bottom: 10px;">
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.labId" placeholder="请输入实验室编号查询"></el-input>
            <el-button type="primary" @click="load">查询</el-button>
            <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
        </div>

        <div class="card" style="margin-bottom: 10px">
            <div style="margin-bottom: 10px">
                <el-button type="primary" @click="handleAdd">新增</el-button>
                <el-button type="danger" @click="handleDeleteBatch">批量删除</el-button>
            </div>

            <el-table stripe :data="data.records" :header-cell-style="{background:'aliceblue'}" @selection-change="handleSelectionChange" table-layout="auto">
                <el-table-column type="selection" width="55" align="center" />

                <el-table-column label="编号" prop="requestId" align="center" sortable />
                <el-table-column label="教师" prop="fullName" align="center" />
                <el-table-column label="实验室编号" prop="labId" align="center" sortable />
                <el-table-column label="实验室名称" prop="name" align="center" />
                <el-table-column label="描述" prop="description" align="center" />
                <el-table-column label="报修日期" prop="date" align="center" sortable />
                <el-table-column label="报修状态" prop="status" align="center" sortable>
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.status === '已维修'">已维修</el-tag>
                        <el-tag type="warning" v-if="scope.row.status === '未维修'">未维修</el-tag>
                        <el-tag type="info" v-if="scope.row.status === '维修中'">维修中</el-tag>
                    </template>
                </el-table-column>
                
                <el-table-column label="操作" align="center" >
                    <template #default="scope">
                        <el-button type="primary" v-if="scope.row.status === '未维修'" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="danger" v-if="scope.row.status === '未维修' || scope.row.status === '已维修'" @click="handleDelete(scope.row.requestId)">删除</el-button>
                        <el-text type="success" v-if="scope.row.status === '维修中'">（维修中）</el-text>
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

        <el-dialog title="报修申请" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
            <el-form :model="data.form" label-width="100px" style="padding-right: 50px" :rules="data.rules" ref="formRef">
                <el-form-item label="实验室" prop="labId">
                    <el-select v-model="data.form.labId" placeholder="请选择实验室" style="width: 300%;">
                        <el-option v-for="item in data.labs" :key="item.labId" :label="item.labId + ' - ' + item.name + ' - ' 
                            + item.category" :value="item.labId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="故障描述" prop="description">
                    <el-input v-model="data.form.description" placeholder="请输入故障描述" autocomplete="off" minlength="1" maxlength="256" show-word-limit 
                        type="textarea" autosize />
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
    teacherId: JSON.parse(localStorage.getItem('user')).userId,
    labId: '',

    records: [],
    total: 0,
    current: 1,
    size: 10,

    formVisible: false,
    form: {},
    ids: [],

    rules: {
        labId: [
            { required: true, message: '请选择实验室', trigger: 'blur' }
        ],
        description: [
            { required: true, message: '请输入故障描述', trigger: 'blur' }
        ],
    },

    labs: [],

})

const initialize = () => {
    request.get('/lab/list').then(res => {
        data.labs = res.data
    })
}

initialize()
  
const load = () => {  
    const requestData = {
        current: data.current,
        size: data.size,

        teacherId: data.teacherId,
        labId: data.labId,
    };

    request.get('/repairRequest/page', { params: requestData }).then(res => {
        data.records = res.data?.records || [];
        data.total = res.data?.total || 0;
    });
}
  
load()

const reset = () => {
    data.labId = ''

    load()
}

const handleAdd = () => {
    data.form = {}
    data.formVisible = true
}

const handleEdit = (row) => {
    data.form = JSON.parse(JSON.stringify(row))
    data.formVisible = true
}

const formRef = ref()

const save = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            data.form.teacherId = data.teacherId
            request.request({
                url: data.form.requestId ? '/repairRequest/updateById' : '/repairRequest/save',
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

const handleDelete = (requestId) => {
    ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
        request.delete('/repairRequest/removeById/' + requestId).then(res => {
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
        url: '/repairRequest/removeBatchByIds',
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

const handleCurrentChange = () => {
    load()
}

const handleSizeChange = (size) => {
    data.size = size
    load()
}
  
const handleSelectionChange = (rows) => {
    data.ids = rows.map(item => item.requestId)
}

</script>