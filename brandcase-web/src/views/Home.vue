<template>
  <div>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="当前状态">
        <el-select v-model="formInline.status" placeholder="当前状态">
          <el-option label="" value=""></el-option>
          <el-option label="启用" value=1></el-option>
          <el-option label="禁用" value=0></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="企业名称">
        <el-input v-model="formInline.companyName" placeholder="企业名称"></el-input>
      </el-form-item>
      <el-form-item label="品牌名称">
        <el-input v-model="formInline.brandName" placeholder="品牌名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="selectQuery()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="tableData"
        style="width: 100%">
      <el-table-column
          type="selection"
          prop="id"
          label="序号">
      </el-table-column>
      <el-table-column
          type="index"
          prop="id"
          label="序号">
      </el-table-column>
      <el-table-column
          prop="brandName"
          label="品牌名称">
      </el-table-column>
      <el-table-column
          prop="companyName"
          label="企业名称">
      </el-table-column>
      <el-table-column
          prop="ordered"
          label="订单量">
      </el-table-column>

      <el-table-column
          prop="status"
          :formatter="statusShow"
          label="当前状态">
      </el-table-column>

      <el-table-column prop="description" label="描述" width="180">
      </el-table-column>

      <el-table-column label="操作">
        <el-button type="primary" @click="dialogFormVisible = true">修改</el-button>
        <el-button type="danger">删除</el-button>
      </el-table-column>

    </el-table>

    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="addForm">
        <el-form-item label="品牌名称" :label-width="formLabelWidth" prop="brandName">
          <el-input v-model="addForm.brandName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="企业名称" :label-width="formLabelWidth" prop="companyName">
          <el-input v-model="addForm.companyName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="订单量" :label-width="formLabelWidth" prop="ordered">
          <el-input v-model="addForm.ordered" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth" prop="description">
          <el-input v-model="addForm.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-switch v-model="addForm.status"></el-switch>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>

    <!--分页-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage4"
        :page-sizes="[100, 200, 300, 400]"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="400">
    </el-pagination>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Home",
  data() {
    return {
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,
      addForm: {
        brandName: "",
        companyName: "",
        ordered: "",
        description: "",
        status: ""
      },
      formInline: {
        status: '',
        companyName: '',
        brandName: ''
      },
      tableData: [],

      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      dialogFormVisible: false,
      formLabelWidth: '120px'

    }
  },

  methods: {
    // 展示列表数据
    pagedata() {
      var _this = this;
      axios({
        method: "get",
        url: "http://localhost:8081/selectAll"
      }).then(function (resp) {
        _this.tableData = resp.data;
      })
    },

    // 多条件查询
    selectQuery() {
      console.log(this.formInline.status)
      console.log(this.formInline.companyName)
      axios({
        method: "post",
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        },
        url: "http://localhost:8081/selectQuery",
        data: {
          stauts: Number(this.formInline.status),
          companyName: this.formInline.companyName,
          brandName: this.formInline.brandName,
          id: "",
          ordered: "",
          description: "",
        },
      }).then(resp => {
        console.log(resp, "多条件查询...")
        this.tableData = resp.data;
      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    statusShow(row, column, cellValue, index) {
      // console.log(row, column, cellValue, index);
      if (cellValue === 1) {
        return "启用"
      } else {
        return "禁用"
      }
    }
  },

  mounted() {
    this.pagedata()
  }

}
</script>