<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 300px; margin-right: 10px" :prefix-icon="Search" v-model="data.semester" placeholder="请输入学期查询"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="handleDeleteBatch">批量删除</el-button>
        <el-button type="info" @click="handleSetCurrentSemester">设置当前学期</el-button>
      </div>

      <el-table :data="data.records" :header-cell-style="{background:'aliceblue'}" @selection-change="handleSelectionChange"
        :row-class-name="highlightRow">
        <el-table-column type="selection" width="55" align="center" />

        <el-table-column label="编号" prop="semesterId" align="center" width=80% sortable />
        <el-table-column label="学期" prop="semester" align="center" sortable />
        <el-table-column label="周数" prop="weekCount" align="center" sortable />
        <el-table-column label="是否当前学期" prop="isCurrentSemester" align="center">
          <template #default="scope">{{ scope.row.isCurrentSemester ? '是' : '否' }}</template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger"  @click="handleDelete(scope.row.semesterId)">删除</el-button>
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
        <el-form-item label="学期" prop="semester">
          <el-input v-model="data.form.semester" />
        </el-form-item>
        <el-form-item label="周数" prop="weekCount">
          <el-input v-model.number="data.form.weekCount" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog title="选择当前学期" width="40%" v-model="data.setSemesterVisible" :close-on-click-modal="false" destroy-on-close>
        <el-select v-model="value" placeholder="选择当前学期" style="width: 100%; ">
          <el-option
            v-for="item in data.options"
            :label="item.label"
            :value="item.value"
            :disabled="item.disabled"
            @click="data.selectedSemesterId = item.value"
          />
        </el-select>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="data.setSemesterVisible = false">取 消</el-button>
            <el-button type="primary" @click="setCurrentSemester">设置</el-button>
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

const value = ref('')

const data = reactive({
  semesterId: '',
  semester: '',
  weekCount: '',
  isCurrentSemester: '',

  records: [],
  total: 0,
  current: 1,
  size: 10,

  formVisible: false,
  form: {},
  setSemesterVisible: false,

  rules: {
    semester: [
      { required: true, message: '请输入学期信息', trigger: 'blur' },
      { length: 11, message: '学期信息长度为11位', trigger: 'blur' },
      { pattern: /^(19|20)\d{2}-(19|20)\d{2}-(1|2)$/, message: '形式为2022-2023-1, 前2个4位整数表示学年, 第3个只能是1(秋季)或2(春季)', trigger: 'blur' },
    ],
    weekCount: [
      { required: true, message: '请输入周数', trigger: 'blur' },
      { type: 'number', message: '周数必须为正整数', trigger: 'blur' }
    ],
  },

  ids: [],

  options: [], 

  selectedSemesterId: '',

})
  
const load = () => {  
  const requestData = {
      current: data.current,
      size: data.size,
      semesterId: data.semesterId,
      semester: data.semester,
      weekCount: data.weekCount,
      isCurrentSemester: data.isCurrentSemester,
  };

  request.get('/semester/page', { params: requestData })
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
  data.semesterId = ''
  data.semester = ''
  data.weekCount = ''
  data.isCurrentSemester = ''
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
      request.request({
        url: data.form.semesterId ? '/semester/updateById' : '/semester/save',
        method: data.form.semesterId ? 'PUT' : 'POST',
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
  
const handleDelete = (semesterId) => {
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/activation/removeById/' + semesterId).then(res => {
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
    ElMessage.warning('请选择要删除的学期')
    return
  }
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.request({
      url: '/semester/removeBatchByIds',
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
  data.ids = rows.map(item => item.activationId)
}

const handleSetCurrentSemester = () => {
  data.setSemesterVisible = true
  data.options = data.records.map(item => {
    return {
      value: item.semesterId,
      label: item.semester,
      disabled: item.isCurrentSemester,
    }
  })
}

const setCurrentSemester = () => {
  request.request({
    url: '/semester/setCurrentSemester/' + data.selectedSemesterId,
    method: 'PUT',
  }).then(res => {
    if (res.code === "200") {
      load()
      data.setSemesterVisible = false
      ElMessage.success("设置成功")
    } else {
      ElMessage.error(res.message)
    }
  })
}

const highlightRow = ({ row, rowIndex }) => {
  if (row.isCurrentSemester) {
    return 'highlight-row'
  }
  return ''
}

</script>

<style>
.el-table .highlight-row {
  --el-table-tr-bg-color: lightyellow;
}
</style>