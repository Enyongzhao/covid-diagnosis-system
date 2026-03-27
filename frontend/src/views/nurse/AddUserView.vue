<template>
  <div style="height: 100%">
    <el-container style="height: 100%">


      <el-main>

        <div style="padding: 10px 0">
          <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>
          <el-button class="ml-5" type="primary" @click ="load">搜索</el-button>
          <el-button class="ml-5" type="warning" @click ="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
          <el-button type="primary" @click="handleadd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe>
          <el-table-column prop="id" label="ID" width="80"></el-table-column>
          <el-table-column prop="userId" label="身份证" width="200"></el-table-column>
          <el-table-column prop="name" label="姓名" width="140"></el-table-column>
          <el-table-column prop="age" label="年龄" width="120"></el-table-column>
          <el-table-column prop="sex" label="性别"></el-table-column>
          <el-table-column label="操作" width="200" align="center">
            <template v-slot:default="scope">
              <el-button type="primary" @click ="handleedit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
              <el-button type="danger" @click = "del(scope.row.id)">删除<i class="el-icon-remove-outline"></i></el-button>
            </template>
          </el-table-column>
        </el-table>

        <div style="padding: 10px 0">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[5, 10, 15, 20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </div>

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
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
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
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection:[]
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },

  methods: {
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    load(){
      //加载数据到表格
      request.get("/user/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name
        }
      }).then(res =>{
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })

    },
    reset(){
      this.name = ""
      this.load() //重置表单
    },
    handleadd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    validateID(id) {
      // 假设中国身份证为18位
      const re = /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(10|11|12))((0[1-9])|([12]\d)|3[01])\d{3}([0-9]|X)$/;
      return re.test(id);
    },
    validateGender(gender) {
      // 验证性别为男或女
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
      // 如果通过验证，则发送请求
      request.post("/user", this.form).then(res => {
        if (res) {
          this.$message.success("保存成功");
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error("保存失败");
        }
      })
    },
    handleedit(Row){
      this.form = Row
      this.dialogFormVisible = true
    },
    del(id) { //删除前二次确认
      this.$confirm('您确定要删除这条记录吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete("/user/" + id).then(res => {
          if (res) {
            this.$message.success("删除成功");
            this.dialogFormVisible = false;
            this.load();
          } else {
            this.$message.error("删除失败");
          }
        }).catch(err => {
          this.$message.error('删除操作失败：' + err.message);
        });
      }).catch(() => {
        this.$message.info('取消删除');
      });
    },
  }
}
</script>

