<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.openId" placeholder="请输入Open Id查询"></el-input>
      <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.nickname" placeholder="请输入微信昵称查询"></el-input>
      <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.bookId" placeholder="请输入激活的书本编号查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="handleDeleteBatch">批量删除</el-button>
        <el-button type="info" @click="exportBatch">批量导出</el-button>
        <el-upload
          :action=data.importUrl
          :headers="{token: data.token}"
          :limit="1"
          accept="['.xls', '.xlsx']"
          :show-file-list="false"
          :on-success="handleImportSuccess"
          style="display: inline-block; margin-left: 10px;">
          <el-button type="primary">批量导入</el-button>
        </el-upload>
        <el-button type="success" @click="handleDownloadTemplate" style="display: inline-block; margin-left: 10px;" >下载导入模板</el-button>
      </div>
      <el-table stripe :data="data.records" :header-cell-style="{background:'aliceblue'}" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />

        <el-table-column label="编号" prop="activationId" align="center" width=80% sortable />
        <el-table-column label="Open Id" prop="openId" align="center" sortable />
        <el-table-column label="微信昵称" prop="nickname" align="center" sortable />
        <el-table-column label="激活的书本编号" prop="bookId" align="center" sortable />
        <el-table-column label="激活时间" prop="activationDateTime" align="center" sortable />
        <el-table-column label="过期时间" prop="expirationDateTime" align="center" sortable />

        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger"  @click="handleDelete(scope.row.activationId)">删除</el-button>
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

    <el-dialog title="激活信息" width="40%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px" :rules="data.rules" ref="formRef">
        <el-form-item label="Open Id" prop="openId">
          <el-input v-model="data.form.openId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="微信昵称" prop="nickname">
          <el-input v-model="data.form.nickname" autocomplete="off" />
        </el-form-item>
        <el-form-item label="书本编号" prop="bookId">
          <el-input v-model.number="data.form.bookId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="过期时间" prop="expirationDateTime">
          <el-date-picker type="datetime" format="YYYY-MM-DD HH:mm:ss" v-model="data.form.expirationDateTime" placeholder="选择过期的日期时间" />
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
  activationId: '', 
  openId: '',
  nickname: '',
  bookId: '',
  activationDateTime: '',
  expirationDateTime: '',

  records: [],
  total: 0,
  current: 1,
  size: 10,

  formVisible: false,
  form: {},

  rules: {
    openId: [
      { required: true, message: '请输入用户Open Id', trigger: 'blur' },
      { min: 1, max: 28, message: '长度在 1 到 28 个字符之间', trigger: 'blur' }
    ],
    nickname: [
      { min: 1, max: 64, message: '长度在 1 到 64 个字符之间', trigger: 'blur' }
    ],
    bookId: [
      { required: true, message: '请输入书本编号', trigger: 'blur' },
      { type: 'number', message: '书本编号必须为正整数', trigger: 'blur' }
    ],
    expirationDateTime: [
      { required: true, message: '请选择过期时间', trigger: 'blur' }
    ],
  },

  ids: [],

  token: JSON.parse(localStorage.getItem('admin')).token,

  importUrl: import.meta.env.VITE_BASE_URL + '/activation/import',
})

const load = () => {  
  const requestData = {
      current: data.current,
      size: data.size,
      activationId: data.activationId,
      openId: data.openId,
      nickname: data.nickname,
      bookId: data.bookId,
      activationDateTime: data.activationDateTime,
      expirationDateTime: data.expirationDateTime,
  };

  const requestUrl = !isNaN(parseInt(data.bookId)) ? '/activation/pageByBookId' : '/activation/page';

  request.get(requestUrl, { params: requestData })
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
  data.activationId = ''
  data.openId = ''
  data.nickname = ''
  data.bookId = ''
  data.activationDateTime = ''
  data.expirationDateTime = ''
  data.current = 1
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
      const date = new Date(data.form.expirationDateTime)
      date.setHours(date.getHours() + 8)
      
      data.form.expirationDateTime = date.toISOString().slice(0, 19).replace('T', ' ')
      request.request({
        url: data.form.activationId ? '/activation/updateById' : '/activation/save',
        method: data.form.activationId ? 'PUT' : 'POST',
        data: data.form
      }).then(res => {
        if (res.code === 1) {
          load()
          data.formVisible = false
          ElMessage.success("操作成功")
        } else {
          ElMessage.error(res.msg)
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

const handleDelete = (activationId) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/activation/removeById/' + activationId).then(res => {
      if (res.code === 1) {
        load()
        ElMessage.success("删除成功")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

const handleDeleteBatch = () => {
  if (data.ids.length === 0) {
    ElMessage.warning('请选择要删除的章节')
    return
  }
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.request({
      url: '/activation/removeBatchByIds',
      method: 'DELETE',
      data: data.ids
    }).then(res => {
      if (res.code === 1) {
        load()
        ElMessage.success("删除成功")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })  
}

const handleSelectionChange = (rows) => {
  data.ids = rows.map(item => item.activationId)
}

const exportBatch = () => {
  let url = import.meta.env.VITE_BASE_URL + '/activation/export' + '?token=' + data.token
  if (!data.ids.length) {
    url = url + '&openId=' + data.openId + '&nickname=' + data.nickname + '&bookId=' + data.bookId
  } else {
    const idsString = data.ids.join(',')
    url = url + '&activationIds=' + idsString
  }
  window.open(url)
}

const handleImportSuccess = (res, file, fileList) => {
  if (res.code === 1) {
    ElMessage.success('导入成功')
    load()
  } else {
    ElMessage.error(res.msg)
  }
}

const handleDownloadTemplate = () => {
  const url = import.meta.env.VITE_BASE_URL + '/file/download/' + '激活信息导入模板.xlsx'
  window.open(url)
}

</script>