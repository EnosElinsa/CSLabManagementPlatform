<template>
    <div>
  
      <div class="card" style="margin-bottom: 10px;">
        <el-input style="width: 200px; margin-right: 10px" :prefix-icon="Search" v-model="data.username" placeholder="请输入学号查询"></el-input>
        <el-input style="width: 200px; margin-right: 10px" :prefix-icon="Search" v-model="data.fullName" placeholder="请输入姓名查询"></el-input>
        <el-input style="width: 200px; margin-right: 10px" :prefix-icon="Search" v-model="data.major" placeholder="请输入专业查询"></el-input>
        <el-input style="width: 200px; margin-right: 10px" :prefix-icon="Search" v-model="data.studentClass" placeholder="请输入班级查询"></el-input>
        <el-button type="primary" @click="load">查询</el-button>
        <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
      </div>
  
      <div class="card" style="margin-bottom: 10px">
        <div style="margin-bottom: 10px">
          <el-button type="primary" @click="handleAdd">新增</el-button>
          <el-button type="danger" @click="handleDeleteBatch">批量删除</el-button>
          <el-upload
                :action=data.importUrl
                :headers="{token: data.token}"
                :limit="1"
                accept=".xls,.xlsx"
                :show-file-list="false"
                :on-success="handleImportSuccess"
                style="display: inline-block; margin-left: 10px;">
                <el-button type="info">批量导入</el-button>
          </el-upload>
          <el-button type="success" @click="handleDownloadTemplate" style="display: inline-block; margin-left: 10px;" >下载导入模板</el-button>
        </div>
        <el-table stripe :data="data.records" :header-cell-style="{background:'aliceblue'}" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
  
          <el-table-column label="编号" prop="userId" align="center" width=105% sortable/>
          <el-table-column label="学号" prop="username" align="center" sortable />
          <el-table-column label="姓名" prop="fullName" align="center" sortable />
          <el-table-column label="专业" prop="major" align="center" sortable />
          <el-table-column label="班级" prop="studentClass" align="center" sortable />
            
          <el-table-column label="操作" align="center" width="300%">
            <template #default="scope">
              <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button type="danger"  @click="handleDelete(scope.row.userId)">删除</el-button>
              <el-button type="info" @click="handleResetPassword(scope.row.userId)">重置密码</el-button>
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
  
      <el-dialog title="用户信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
        <el-form :model="data.form" label-width="100px" style="padding-right: 50px" :rules="data.rules" ref="formRef">
          <el-form-item label="角色" prop="role">
            <el-select v-model="data.form.role">
                <el-option label="实验员" value="实验员"></el-option>
                <el-option label="教师" value="教师"></el-option>
                <el-option label="学生" value="学生"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学号" prop="username">
            <el-input v-model="data.form.username" autocomplete="off" minlength="4" maxlength="20" show-word-limit />
          </el-form-item>
          <el-form-item label="姓名" prop="fullName">
            <el-input v-model="data.form.fullName" autocomplete="off" minlength="1" maxlength="128" show-word-limit />
          </el-form-item>
          <el-form-item label="专业" prop="major">
            <el-input v-model="data.form.major" autocomplete="off" minlength="1" maxlength="32" show-word-limit />
          </el-form-item>
          <el-form-item label="班级" prop="studentClass">
            <el-input v-model="data.form.studentClass" autocomplete="off" minlength="1" maxlength="32" show-word-limit />
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
    userId: '', 
    role: '学生',
    username: '',
    password: '',
    fullName: '',
    major: '',
    studentClass: '',
  
    records: [],
    total: 0,
    current: 1,
    size: 10,
  
    formVisible: false,
    form: {},
  
    rules: {
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 4, max: 20, message: '长度在 4 到 20 个字符之间', trigger: 'blur' }
      ],
      fullName: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
        { min: 1, max: 128, message: '长度在 1 到 128 个字符之间', trigger: 'blur' }
      ],
    },
  
    ids: [],
  
    token: JSON.parse(localStorage.getItem('user')).token,
  
    importUrl: import.meta.env.VITE_BASE_URL + '/user/import'
  })
  
  const load = () => {  
    const requestData = {
        current: data.current,
        size: data.size,

        userId: data.userId,
        role: data.role,
        username: data.username,
        password: data.password,
        fullName: data.fullName,
        major: data.major,
        studentClass: data.studentClass
    };
  
    request.get('/user/pageStudents', { params: requestData }).then(res => {
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
    data.userId = ''
    data.username = ''
    data.password = ''
    data.fullName = ''
    data.major = ''
    data.studentClass = ''
    load()
  }
  
  const handleAdd = () => {
    data.form = {}
    data.formVisible = true
  }
  
  const formRef = ref()
  
  const save = () => {
    formRef.value.validate((valid) => {
      if (valid) {
        request.request({
          url: data.form.userId ? '/user/updateById' : '/user/save',
          method: data.form.userId ? 'PUT' : 'POST',
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
  
  const handleDelete = (userId) => {
    ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
      request.delete('/user/removeById/' + userId).then(res => {
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
      ElMessage.warning('请选择要删除的用户')
      return
    }
    ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
      request.request({
        url: '/user/removeBatchByIds',
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
    data.ids = rows.map(item => item.userId)
  }

  const handleImportSuccess = (res, file, fileList) => {
    if (res.code === "200") {
      ElMessage.success('导入成功')
      load()
    } else {
      ElMessage.error(res.message)
    }
  }
  
  const handleDownloadTemplate = () => {
    const url = import.meta.env.VITE_BASE_URL + '/file/download/' + '用户导入模板.xlsx'
    window.open(url)
  }

  const handleResetPassword = (userId) => {
    ElMessageBox.confirm('确定重置密码吗?', '重置密码确认', { type: 'warning' }).then(res => {
      request.request({
        url: '/user/resetPassword/' + userId,
        method: 'PUT',
      }).then(res => {
        if (res.code === "200") {
          ElMessage.success("密码重置成功")
        } else {
          ElMessage.error(res.message)
        }
      })
    }).catch(err => {
      console.error(err)
    })
  }
  
  </script>