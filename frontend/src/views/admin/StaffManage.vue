<template>
  <div style="height: 100%">
    <el-container style="height: 100%">
      <el-main>

        <div style="padding: 10px 0">
          <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>
          <el-button class="ml-5" type="primary" @click ="load">搜索</el-button>
          <el-button class="ml-5" type="warning" @click ="reset">重置</el-button>
        </div>


        <el-table :data="tableData" border stripe >
          <el-table-column prop="staffId" label="工号" width="80"></el-table-column>
          <el-table-column prop="name" label="姓名" width="200"></el-table-column>
          <el-table-column prop="role" label="职务" width="140"></el-table-column>
          <el-table-column prop="password" label="密码" width="120"></el-table-column>
          <el-table-column prop="age" label="年龄"></el-table-column>
          <el-table-column label="操作" width="200" align="center">
            <template v-slot:default="scope">
              <el-button type="primary" @click="handleedit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
              <el-button type="danger" @click="del(scope.row.staffId)">删除<i class="el-icon-remove-outline"></i></el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
          <el-form label-width="100px">
            <el-form-item label="身份证">
              <el-input v-model="form.userId" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="年龄">
              <el-input v-model="form.age" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-input v-model="form.sex" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="save">确定</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>



<script>

import request from "@/utils/request";


export default {
  name: 'AddUserView',
  data() {
    return {
      tableData: [],
      name: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request.get("/auth/staff")
          .then(res => {
            console.log(res); // 确认接收到的数据结构
            this.tableData = res; // 假设返回的是一个staff列表
          })
          .catch(error => {
            console.error("Error loading data:", error);
          });
    },
    reset() {
      this.name = "";
      this.load();
    },
    handleadd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    validateID(id) {
      const re = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(10|11|12))((0[1-9])|([12]\d)|3[01])\d{3}([0-9]|X)$/;
      return re.test(id);
    },
    validateGender(gender) {
      return gender === '男' || gender === '女';
    },
    save() {
      if (!this.validateID(this.form.userId)) {
        this.$message.error('身份证格式不正确，应为18位数字，最后一位可为X。');
        return;
      }
      if (!this.validateGender(this.form.sex)) {
        this.$message.error('性别格式不正确，应为“男”或“女”。');
        return;
      }
      request.post("/user", this.form).then(res => {
        this.$message.success("保存成功");
        this.dialogFormVisible = false;
        this.load();
      }).catch(error => {
        this.$message.error("保存失败: " + error.message);
      });
    },
    handleedit(Row) {
      this.form = Row;
      this.dialogFormVisible = true;
    },
    del(id) {
      this.$confirm('您确定要删除这条记录吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete("/user/" + id).then(res => {
          this.$message.success("删除成功");
          this.load();
        }).catch(err => {
          this.$message.error('删除操作失败：' + err.message);
        });
      }).catch(() => {
        this.$message.info('取消删除');
      });
    }
  }
}
</script>

