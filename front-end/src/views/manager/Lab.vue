<template>
    <div>

        <div class="card" style="margin-bottom: 10px;">
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.labId" placeholder="请输入实验室编号查询"></el-input>
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.name" placeholder="请输入实验室名称查询"></el-input>
            <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.category" placeholder="请输入实验室类型查询"></el-input>
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

                <el-table-column label="编号" prop="labId" align="center" width=80% sortable />
                <el-table-column label="实验室名称" prop="name" align="center" sortable />
                <el-table-column label="实验室类型" prop="category" align="center" sortable />
                <el-table-column label="设备数" prop="equipmentCount" align="center" sortable />
                <el-table-column label="实验员" prop="technicianName" align="center" sortable />

                <el-table-column label="操作" align="center" width="160">
                    <template #default="scope">
                        <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="danger"  @click="handleDelete(scope.row.labId)">删除</el-button>
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

        <el-dialog title="实验室信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
            <el-form :model="data.form" label-width="100px" style="padding-right: 50px" :rules="data.rules" ref="formRef">
                <el-form-item label="实验室编号" prop="labId" v-if="data.isAdd === true">
                    <el-input v-model="data.form.labId" autocomplete="off" minlength="1" maxlength="8" show-word-limit />
                </el-form-item>
                <el-form-item label="实验室名称" prop="name">
                    <el-input v-model="data.form.name" autocomplete="off" minlength="1" maxlength="32" show-word-limit />
                </el-form-item>
                <el-form-item label="实验室类别" prop="category">
                    <el-select v-model="data.form.category" placeholder="请选择实验室类别" style="width: 300%;">
                        <el-option v-for="item in data.categories" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="设备数" prop="equipmentCount">
                    <el-input v-model.number="data.form.equipmentCount" />
                </el-form-item>
                <el-form-item label="实验员" prop="technicianId">
                    <el-select v-model="data.form.technicianId" placeholder="请选择实验员" style="width: 300%;">
                        <el-option v-for="item in data.technicians" :key="item.userId" :label="item.fullName" 
                            :value="item.userId" />
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
    labId: '',
    name: '',
    category: '',

    records: [],
    total: 0,
    current: 1,
    size: 10,

    formVisible: false,
    isAdd: false,
    form: {},

    rules: {
        labId: [
            { required: true, message: '请选择实验室编号', trigger: 'blur' },
        ],
        name: [
            { required: true, message: '请输入实验室名称', trigger: 'blur' },
        ],
        category: [
            { required: true, message: '请输选择实验室类别', trigger: 'blur' },
        ],
        equipmentCount: [
            { required: true, message: '请输入设备数', trigger: 'blur' },
            { type: 'number', message: '设备数必须为正整数', trigger: 'blur' },
            {
                validator: (rule, value, callback) => { 
                    if (value <= 0) { 
                        callback(new Error('人数必须为大于0的正整数')); 
                    } else { 
                        callback(); 
                    } 
                } 
            }
        ],
        technicianId: [
            { required: true, message: '请选择实验员', trigger: 'blur' },
        ],
    },

    ids: [],

    technicians: [],
    categories: [
        {
            value: '软件',
            label: '软件'
        },
        {
            value: '硬件',
            label: '硬件'
        },
        {
            value: '网络',
            label: '网络',
        },
    ]
})

const initialize = () => {
    request.get('/user/listTechnicians').then(res => {
        data.technicians = res.data
    })
}

initialize()

const load = () => {  
    const requestData = {
        current: data.current,
        size: data.size,

        labId: data.labId,
        name: data.name,
        category: data.category,
    };

    request.get('/lab/page', { params: requestData })
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
    data.name = ''
    data.category = ''
    data.current = 1

    load()
}

const handleAdd = () => {
    data.form = {}
    data.formVisible = true
    data.isAdd = true
}

const formRef = ref()

const save = () => {
    formRef.value.validate((valid) => {
        if (valid) {
            request.request({
                url: data.isAdd ? '/lab/save' : '/lab/updateById',
                method: data.isAdd ? 'POST' : 'PUT',
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
    data.isAdd = false
}

const handleDelete = (labId) => {
    ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
        request.delete('/lab/removeById/' + labId).then(res => {
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
        url: '/lab/removeBatchByIds',
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
    data.ids = rows.map(item => item.labId)
}

</script>

<style>

</style>