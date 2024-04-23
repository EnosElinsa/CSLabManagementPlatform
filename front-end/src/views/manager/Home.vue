<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div style="font-weight: bold; text-align: center; margin-top: 10px;"> {{ semester.semester }} 学期排课表 </div>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table border stripe :data="data.fixedContent" :span-method="spanMethod">
        <el-table-column fixed prop="day" align="center" width="70%"/>
        <el-table-column label="实验室" prop="labName" align="center" width="145%" />
        <el-table-column label="机房" prop="labId" align="center" width="55%" />
  
        <el-table-column label="1-2">
          <template #default="scope">
            <span v-html="getCourse(scope.row, '1-2')"></span>
          </template>
        </el-table-column>
        <el-table-column label="3-5">
          <template #default="scope">
            <span v-html="getCourse(scope.row, '3-5')"></span>
          </template>
        </el-table-column>
        <el-table-column label="6-7">
          <template #default="scope">
            <span v-html="getCourse(scope.row, '6-7')"></span>
          </template>
        </el-table-column>
        <el-table-column label="8-9">
          <template #default="scope">
            <span v-html="getCourse(scope.row, '8-9')"></span>
          </template>
        </el-table-column>
        <el-table-column label="10-12">
          <template #default="scope">
            <span v-html="getCourse(scope.row, '10-12')"></span>
          </template>
        </el-table-column>
        <el-table-column label="13-15">
          <template #default="scope">
            <span v-html="getCourse(scope.row, '13-15')"></span>
          </template>
        </el-table-column>

      </el-table>
    </div>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import router from '@/router'
import {reactive} from "vue";

const user = JSON.parse(localStorage.getItem('user') || '{}')
const semester = JSON.parse(localStorage.getItem('semester') || '{}')
if (Object.keys(user).length === 0) {
  ElMessage.error('请先登录');
  router.push("/login")
}

const data = reactive({
  fixedContent: [
    {
      day: '星期一',
      labName: '软件实验室',
      labId: '732',
    }, {
      day: '星期一',
      labName: '软件实验室',
      labId: '734',
    }, {
      day: '星期一',
      labName: '软件实验室',
      labId: '736',
    }, {
      day: '星期一',
      labName: '软件实验室',
      labId: '738',
    }, {
      day: '星期一',
      labName: '软件实验室',
      labId: '735',
    }, {
      day: '星期一',
      labName: '软件实验室',
      labId: '532',
    }, {
      day: '星期一',
      labName: '软件实验室',
      labId: '536',
    }, {
      day: '星期一',
      labName: '软件实验室',
      labId: '537',
    }, {
      day: '星期一',
      labName: '软件实验室',
      labId: '535',
    }, {
      day: '星期一',
      labName: '计算机系统实验室',
      labId: '801',
    }, {
      day: '星期一',
      labName: '计算机系统实验室',
      labId: '803',
    }, {
      day: '星期一',
      labName: '计算机硬件实验室',
      labId: '703',
    }, {
      day: '星期一',
      labName: '计算机硬件实验室',
      labId: '704',
    }, {
      day: '星期一',
      labName: '计算机硬件实验室',
      labId: '705',
    }, {
      day: '星期一',
      labName: '计算机硬件实验室',
      labId: '706',
    }, {
      day: '星期一',
      labName: '计算机硬件实验室',
      labId: '707',
    }, {
      day: '星期一',
      labName: '计算机硬件实验室',
      labId: '708',
    }, {
      day: '星期一',
      labName: '物联网实验室',
      labId: '806',
    }, {
      day: '星期一',
      labName: '物联网实验室',
      labId: '808',
    }, {
      day: '星期一',
      labName: '计算机网络实验室',
      labId: '832',
    }, {
      day: '星期一',
      labName: '计算机网络实验室',
      labId: '834',
    },
    {
      day: '星期二',
      labName: '软件实验室',
      labId: '732',
    }, {
      day: '星期二',
      labName: '软件实验室',
      labId: '734',
    }, {
      day: '星期二',
      labName: '软件实验室',
      labId: '736',
    }, {
      day: '星期二',
      labName: '软件实验室',
      labId: '738',
    }, {
      day: '星期二',
      labName: '软件实验室',
      labId: '735',
    }, {
      day: '星期二',
      labName: '软件实验室',
      labId: '532',
    }, {
      day: '星期二',
      labName: '软件实验室',
      labId: '536',
    }, {
      day: '星期二',
      labName: '软件实验室',
      labId: '537',
    }, {
      day: '星期二',
      labName: '软件实验室',
      labId: '535',
    }, {
      day: '星期二',
      labName: '计算机系统实验室',
      labId: '801',
    }, {
      day: '星期二',
      labName: '计算机系统实验室',
      labId: '803',
    }, {
      day: '星期二',
      labName: '计算机硬件实验室',
      labId: '703',
    }, {
      day: '星期二',
      labName: '计算机硬件实验室',
      labId: '704',
    }, {
      day: '星期二',
      labName: '计算机硬件实验室',
      labId: '705',
    }, {
      day: '星期二',
      labName: '计算机硬件实验室',
      labId: '706',
    }, {
      day: '星期二',
      labName: '计算机硬件实验室',
      labId: '707',
    }, {
      day: '星期二',
      labName: '计算机硬件实验室',
      labId: '708',
    }, {
      day: '星期二',
      labName: '物联网实验室',
      labId: '806',
    }, {
      day: '星期二',
      labName: '物联网实验室',
      labId: '808',
    }, {
      day: '星期二',
      labName: '计算机网络实验室',
      labId: '832',
    }, {
      day: '星期二',
      labName: '计算机网络实验室',
      labId: '834',
    },
    {
      day: '星期三',
      labName: '软件实验室',
      labId: '732',
    }, {
      day: '星期三',
      labName: '软件实验室',
      labId: '734',
    }, {
      day: '星期三',
      labName: '软件实验室',
      labId: '736',
    }, {
      day: '星期三',
      labName: '软件实验室',
      labId: '738',
    }, {
      day: '星期三',
      labName: '软件实验室',
      labId: '735',
    }, {
      day: '星期三',
      labName: '软件实验室',
      labId: '532',
    }, {
      day: '星期三',
      labName: '软件实验室',
      labId: '536',
    }, {
      day: '星期三',
      labName: '软件实验室',
      labId: '537',
    }, {
      day: '星期三',
      labName: '软件实验室',
      labId: '535',
    }, {
      day: '星期三',
      labName: '计算机系统实验室',
      labId: '801',
    }, {
      day: '星期三',
      labName: '计算机系统实验室',
      labId: '803',
    }, {
      day: '星期三',
      labName: '计算机硬件实验室',
      labId: '703',
    }, {
      day: '星期三',
      labName: '计算机硬件实验室',
      labId: '704',
    }, {
      day: '星期三',
      labName: '计算机硬件实验室',
      labId: '705',
    }, {
      day: '星期三',
      labName: '计算机硬件实验室',
      labId: '706',
    }, {
      day: '星期三',
      labName: '计算机硬件实验室',
      labId: '707',
    }, {
      day: '星期三',
      labName: '计算机硬件实验室',
      labId: '708',
    }, {
      day: '星期三',
      labName: '物联网实验室',
      labId: '806',
    }, {
      day: '星期三',
      labName: '物联网实验室',
      labId: '808',
    }, {
      day: '星期三',
      labName: '计算机网络实验室',
      labId: '832',
    }, {
      day: '星期三',
      labName: '计算机网络实验室',
      labId: '834',
    },
    {
      day: '星期四',
      labName: '软件实验室',
      labId: '732',
    }, {
      day: '星期四',
      labName: '软件实验室',
      labId: '734',
    }, {
      day: '星期四',
      labName: '软件实验室',
      labId: '736',
    }, {
      day: '星期四',
      labName: '软件实验室',
      labId: '738',
    }, {
      day: '星期四',
      labName: '软件实验室',
      labId: '735',
    }, {
      day: '星期四',
      labName: '软件实验室',
      labId: '532',
    }, {
      day: '星期四',
      labName: '软件实验室',
      labId: '536',
    }, {
      day: '星期四',
      labName: '软件实验室',
      labId: '537',
    }, {
      day: '星期四',
      labName: '软件实验室',
      labId: '535',
    }, {
      day: '星期四',
      labName: '计算机系统实验室',
      labId: '801',
    }, {
      day: '星期四',
      labName: '计算机系统实验室',
      labId: '803',
    }, {
      day: '星期四',
      labName: '计算机硬件实验室',
      labId: '703',
    }, {
      day: '星期四',
      labName: '计算机硬件实验室',
      labId: '704',
    }, {
      day: '星期四',
      labName: '计算机硬件实验室',
      labId: '705',
    }, {
      day: '星期四',
      labName: '计算机硬件实验室',
      labId: '706',
    }, {
      day: '星期四',
      labName: '计算机硬件实验室',
      labId: '707',
    }, {
      day: '星期四',
      labName: '计算机硬件实验室',
      labId: '708',
    }, {
      day: '星期四',
      labName: '物联网实验室',
      labId: '806',
    }, {
      day: '星期四',
      labName: '物联网实验室',
      labId: '808',
    }, {
      day: '星期四',
      labName: '计算机网络实验室',
      labId: '832',
    }, {
      day: '星期四',
      labName: '计算机网络实验室',
      labId: '834',
    },

    {
      day: '星期五',
      labName: '软件实验室',
      labId: '732',
    }, {
      day: '星期五',
      labName: '软件实验室',
      labId: '734',
    }, {
      day: '星期五',
      labName: '软件实验室',
      labId: '736',
    }, {
      day: '星期五',
      labName: '软件实验室',
      labId: '738',
    }, {
      day: '星期五',
      labName: '软件实验室',
      labId: '735',
    }, {
      day: '星期五',
      labName: '软件实验室',
      labId: '532',
    }, {
      day: '星期五',
      labName: '软件实验室',
      labId: '536',
    }, {
      day: '星期五',
      labName: '软件实验室',
      labId: '537',
    }, {
      day: '星期五',
      labName: '软件实验室',
      labId: '535',
    }, {
      day: '星期五',
      labName: '计算机系统实验室',
      labId: '801',
    }, {
      day: '星期五',
      labName: '计算机系统实验室',
      labId: '803',
    }, {
      day: '星期五',
      labName: '计算机硬件实验室',
      labId: '703',
    }, {
      day: '星期五',
      labName: '计算机硬件实验室',
      labId: '704',
    }, {
      day: '星期五',
      labName: '计算机硬件实验室',
      labId: '705',
    }, {
      day: '星期五',
      labName: '计算机硬件实验室',
      labId: '706',
    }, {
      day: '星期五',
      labName: '计算机硬件实验室',
      labId: '707',
    }, {
      day: '星期五',
      labName: '计算机硬件实验室',
      labId: '708',
    }, {
      day: '星期五',
      labName: '物联网实验室',
      labId: '806',
    }, {
      day: '星期五',
      labName: '物联网实验室',
      labId: '808',
    }, {
      day: '星期五',
      labName: '计算机网络实验室',
      labId: '832',
    }, {
      day: '星期五',
      labName: '计算机网络实验室',
      labId: '834',
    },
  ],

  records: [],

})
  
const initialize = () => {
  request.get('/schedule/list').then(res => {
    data.records = res.data
  })
}

initialize()

const getCourse = (row, session) => {
  const matchedSchedules = data.records.filter(record => record.day === row.day && record.labId === row.labId && record.session === session);
  let display = '';
  matchedSchedules.forEach(schedule => {
    display += `课程: ${schedule.courseName}<br>教师: ${schedule.fullName}<br>班级: ${schedule.studentClass}<br>周次: ${schedule.startWeek}-${schedule.endWeek}<br>`;
  });
  return display;
}


const spanMethod = ({ row, column, rowIndex, columnIndex }) => {
  if (columnIndex === 0 && rowIndex % 21 === 0) {
    return { rowspan: 21, colspan: 1 };
  } else if (columnIndex === 1) {
    switch (rowIndex % 21) {
      case 0:
        return { rowspan: 9, colspan: 1 };
      case 9:
        return { rowspan: 2, colspan: 1 };
      case 11:
        return { rowspan: 6, colspan: 1 };
      case 17:
        return { rowspan: 2, colspan: 1 };
      case 19:
        return { rowspan: 2, colspan: 1 };
      default:
        return { rowspan: 0, colspan: 0 };
    }
  } else if (columnIndex >= 2 && columnIndex <= 8) {
    return { rowspan: 1, colspan: 1 };
  } else {
    return { rowspan: 0, colspan: 0 };
  }
};

</script>