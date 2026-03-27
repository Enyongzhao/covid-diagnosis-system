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
          <el-table-column prop="id" label="ID" width="80"></el-table-column>
          <el-table-column prop="userId" label="身份证" width="160"></el-table-column>
          <el-table-column prop="name" label="姓名" width="80"></el-table-column>
          <el-table-column prop="age" label="年龄" width="60"></el-table-column>
          <el-table-column prop="sex" label="性别" width="60"></el-table-column>
          <el-table-column prop="description" label="病情描述" width="230"></el-table-column>
          <el-table-column prop="status" label="辅助诊断结果" ></el-table-column>
          <el-table-column  label="CT图像">
            <template v-slot="scope">
              <div class="demo-image__preview">
                <el-image
                    style="width: 100px; height: 100px"
                    :src="'http://localhost:9090/files/'+ scope.row.img"
                    :preview-src-list="['http://localhost:9090/files/'+ scope.row.img]">
                </el-image>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="result" label="医生诊断结果" ></el-table-column>
          <el-table-column label="操作" width="500" align="center">
            <template v-slot:default="scope">
              <el-button type="primary" @click ="handleedit(scope.row)">诊断<i class="el-icon-edit"></i></el-button>
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
            <el-form-item label="病情描述">
              <el-input
                  v-model="form.description"
                  autocomplete="off"
                  @input="checkDescriptionLength"
                  maxlength="50">
              </el-input>
            </el-form-item>
            <el-form-item label="最终诊断结果">
              <el-input v-model="form.result" autocomplete="off"></el-input>
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
  name: 'DiagnosisListComponent',
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
    checkDescriptionLength() {
      if (this.form.description.length > 50) {
        this.$message.warning('病情描述不能超过50个字');
        // 截断多余的文字
        this.form.description = this.form.description.substring(0, 50);
      }
    },
    save(){
      if (this.form.description.length > 50) {
        this.$message.error('病情描述不能超过50个字');
        return;
      }
      request.post("/user", this.form).then(res =>{
        if (res){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else{
          this.$message.success("保存失败")
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

