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
            <div style="margin-bottom: 10px">
                <el-button type="primary" @click="handleAdd">新增</el-button>
                <el-button type="danger" @click="handleDeleteBatch">批量删除</el-button>
            </div>

            <el-table stripe :data="data.records" :header-cell-style="{background:'aliceblue'}" @selection-change="handleSelectionChange" table-layout="auto">
                <el-table-column type="selection" width="55" align="center" />

                <el-table-column label="编号" prop="requestId" align="center" sortable />
                <el-table-column label="学期" prop="semester" align="center" sortable />
                <el-table-column label="课程名" prop="courseName" align="center" />
                <el-table-column label="实验室类型" prop="labCategory" align="center" />
                <el-table-column label="学生班级" prop="studentClass" align="center" />
                <el-table-column label="学生人数" prop="studentCount" align="center" />
                <el-table-column label="开始周" prop="startWeek" align="center" />
                <el-table-column label="结束周" prop="endWeek" align="center" />
                <el-table-column label="节次" prop="session" align="center"  />
                <el-table-column label="排课状态" prop="status" align="center" sortable>
                    <template #default="scope">
                        <el-tag type="success" v-if="scope.row.status === '已排课'">已排课</el-tag>
                        <el-tag type="warning" v-if="scope.row.status === '未排课'">未排课</el-tag>
                    </template>
                </el-table-column>
                
                <el-table-column label="操作" align="center" >
                    <template #default="scope">
                        <el-button type="primary" v-if="scope.row.status === '未排课'" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="danger" v-if="scope.row.status === '未排课'" @click="handleDelete(scope.row.requestId)">删除</el-button>
                        <el-text type="success" v-if="scope.row.status === '已排课'">（已排课）</el-text>
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

        <el-dialog title="排课申请" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
            <el-form :model="data.form" label-width="100px" style="padding-right: 50px" :rules="data.rules" ref="formRef">
                <el-form-item label="学期" prop="semesterId">
                    <el-select v-model="data.form.semesterId" placeholder="请选择学期" style="width: 300%;">
                        <el-option v-for="item in data.semesters" :key="item.semesterId" :label="item.semester" 
                            :value="item.semesterId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="开始周" prop="startWeek">
                    <el-input v-model.number="data.form.startWeek" />
                </el-form-item>
                <el-form-item label="结束周" prop="endWeek">
                    <el-input v-model.number="data.form.endWeek" />
                </el-form-item>
                <el-form-item label="节次" prop="session">
                    <el-select v-model="data.form.session" placeholder="请选择节次" style="width: 300%;">
                        <el-option v-for="item in data.sessions" :key="item.value" :label="item.label" 
                            :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="课程名" prop="courseName">
                    <el-input v-model="data.form.courseName" autocomplete="off" minlength="1" maxlength="32" show-word-limit />
                </el-form-item>
                <el-form-item label="实验室类型" prop="labCategory">
                    <el-select v-model="data.form.labCategory" placeholder="请选择实验室类型" style="width: 300%;">
                        <el-option v-for="item in data.labCategories" :key="item.value" :label="item.label" 
                            :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="学生班级" prop="studentClass">
                    <el-input v-model="data.form.studentClass" autocomplete="off" minlength="1" maxlength="32" show-word-limit />
                </el-form-item>
                <el-form-item label="学生人数" prop="studentCount">
                    <el-input v-model.number="data.form.studentCount" />
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
    courseName: '',
    labCategory: '',
    studentClass: '',

    records: [],
    total: 0,
    current: 1,
    size: 10,

    formVisible: false,
    form: {},
    ids: [],

    rules: {
        semesterId: [
            { required: true, message: '请选择学期', trigger: 'blur' }
        ],
        startWeek: [
            { required: true, message: '请输入开始周', trigger: 'blur' },
            { type: 'number', message: '周数必须为大于0的正整数', trigger: 'blur'},
            {
                validator: (rule, value, callback) => { 
                    if (value <= 0) { 
                        callback(new Error('周数必须为大于0的正整数')); 
                    } else { 
                        callback(); 
                    } 
                } 
            }
        ],
        endWeek: [
            { required: true, message: '请输入结束周', trigger: 'blur' },
            { type: 'number', message: '周数必须为正整数', trigger: 'blur' },
            {
                validator: (rule, value, callback) => {
                    if (value < data.form.startWeek) {
                        callback(new Error('结束周必须大于等于开始周'));
                    } else {
                        callback();
                    }
                },
                trigger: 'blur'
            }
        ],
        session: [
            { required: true, message: '请选择节次', trigger: 'blur' }
        ],
        courseName: [
            { required: true, message: '请输入课程名', trigger: 'blur' }
        ],
        labCategory: [
            { required: true, message: '请输入实验室类型', trigger: 'blur' }
        ],
        studentClass: [
            { required: true, message: '请输入学生班级', trigger: 'blur' }
        ],
        studentCount: [
            { required: true, message: '请输入学生人数', trigger: 'blur' },
            { type: 'number', message: '人数必须为正整数', trigger: 'blur' },
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
    },

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
    labCategories: [
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
            label: '网络'
        },
    ],
})

const initialize = () => {
    request.get('/semester/list').then(res => {
        data.semesters = res.data
    })
}

initialize()
  
const load = () => {  
    const requestData = {
        current: data.current,
        size: data.size,

        teacherId: data.teacherId,
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
                url: data.form.requestId ? '/labScheduleRequest/updateById' : '/labScheduleRequest/save',
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
        request.delete('/labScheduleRequest/removeById/' + requestId).then(res => {
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
        url: '/labScheduleRequest/removeBatchByIds',
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