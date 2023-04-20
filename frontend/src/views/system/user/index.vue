<template>
  <div class="app-container">
    <el-table
      :data="tableData.filter(data => !search || data.nickName.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
    >
      <el-table-column
        label="CreateTime"
        prop="createTime"
      >
      </el-table-column>
      <el-table-column
        label="UserName"
        prop="userName"
      >
      </el-table-column>
      <el-table-column
        label="NickName"
        prop="nickName"
      >
      </el-table-column>
      <el-table-column
        label="Sex"
        v-slot="scope"
      >
        {{ scope.row.sex == 0 ? '男' : '女' }}
      </el-table-column>
      <el-table-column
        label="IsAdmin"
        v-slot="scope"
      >
        {{ scope.row.admin ? '是' : '否' }}
      </el-table-column>
      <el-table-column
        align="right"
      >

        <template v-slot:header="scope">
          <el-input
            v-model="search"
            size="mini"
            placeholder="Type nickName to search"
          />
        </template>
        <template v-slot="scope">
          <el-button
            v-if="!scope.row.admin"
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >Edit
          </el-button>
          <el-button
            v-if="!scope.row.admin"
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="Edit" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="UserName" :label-width="formLabelWidth">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="NickName" :label-width="formLabelWidth">
          <el-input v-model="form.nickName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Sex" :label-width="formLabelWidth">
          <el-radio v-model="radio" label="0">男</el-radio>
          <el-radio v-model="radio" label="1">女</el-radio>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">Cancel</el-button>
        <el-button type="primary" @click="confirm">Confirm</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {
  listUser,
  delUser,
  addUser,
  updateUser
} from '@/api/system/user'

export default {
  name: 'User',
  data() {
    return {
      tableData: [],
      search: '',
      gridData: [],
      dialogFormVisible: false,
      form: {},
      formLabelWidth: '150px',
      radio: ''
    }
  },
  watch: {},
  computed:{},
  created() {
    this.getList()
  },
  methods: {
    getList() {
      listUser().then(res => {
        const { rows } = res
        this.tableData = rows
      })
    },
    handleEdit(index, row) {
      this.dialogFormVisible = true
      this.radio = row.sex
      let { userName, nickName, sex, userId } = row
      this.form = {
        userName,
        nickName,
        userId
      }
    },
    handleDelete(index, row) {
      this.$confirm('你确定要删除吗？', '提示框', {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'error'
      }).then(() => {
        delUser(row.userId).then(res => {
          if (res.code === 200) {
            this.$message.success('删除成功')
            this.getList()
          }
        })
      })
    },
    cancel() {
      this.dialogFormVisible = false
      this.form = {}
    },
    confirm() {
      this.dialogFormVisible = false
      this.form.sex = this.radio
      updateUser(this.form).then(res => {
        if (res.code === 200) {
          this.$message.success('修改成功')
          this.getList()
        }
      })

    }
  }
}
</script>
