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

    <!--批量删除,新增-->
    <div>
      <el-button type="danger" plain @click="deleteByIds()">批量删除</el-button>
      <el-button type="primary" plain @click="addBrand()">新增</el-button>
    </div>

    <!--展示-->
    <el-table
        :data="tableData"
        style="width: 100%"
        @selection-change="tableRowChange"
    >
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
        <template slot-scope="scope">
          <el-button type="primary" @click="updateBrand(scope.row);">修改</el-button>
          <el-button type="danger" @click="deleteBrand(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--修改表格-->
    <el-dialog title="收货地址" :visible.sync="updateFormVisible">
      <el-form :model="updateForm">
        <el-form-item label="品牌名称" :label-width="formLabelWidth" prop="brandName">
          <el-input v-model="updateForm.brandName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="企业名称" :label-width="formLabelWidth" prop="companyName">
          <el-input v-model="updateForm.companyName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="订单量" :label-width="formLabelWidth" prop="ordered">
          <el-input v-model="updateForm.ordered" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth" prop="description">
          <el-input v-model="updateForm.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-switch :active-value=1
                     :inactive-value=0
                     v-model="updateForm.status">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateFormVisible  = false">取 消</el-button>
        <el-button type="primary" @click="updateExecute">确 定</el-button>
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
      id: Number,
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
      updateForm: {
        id: "",
        brandName: "",
        companyName: "",
        ordered: "",
        description: "",
        status: ""
      },
      formInline: {
        status: "",
        companyName: '',
        brandName: ''
      },
      tableData: [{
        brandName: '华为',
        id: 1,
        companyName: '华为科技有限公司',
        ordered: '100',
        status: "1"
      }],
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
      rowId: [],
      updateFormVisible: false,
      formLabelWidth: '120px'

    }
  },

  methods: {
    // 展示列表数据
    selectAll() {
      let _this = this;
      axios({
        method: "get",
        url: "http://localhost:8081/selectAll"
      }).then(function (resp) {
        console.log(resp.data)
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
        data: this.formInline,
      }).then(resp => {
        console.log(resp, "多条件查询...")
        this.tableData = resp.data;
      })
    },

    deleteBrand(id) {
      this.id = id
      console.log(this.id)
      axios({
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        },
        method: "delete",
        url: "http://localhost:8081/deleteById",
        data: this.id
      }).then(() => {
        this.selectAll();
      })
    },

    deleteByIds() {
      let _this = this
      this.$confirm('此操作将永久删除这些记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios({
          url: "http://localhost:8081/deleteByIds",
          method: "delete",
          headers: {
            'Content-Type': 'application/json;charset=UTF-8'
          },
          data: this.rowId
        }).then(()=>{
          this.selectAll()
        })
      })
    },


    tableRowChange(rowArr) {
      let _this = this
      _this.rowId = []
      if (rowArr.length > 0) {
        for (let item of rowArr) {
          if (item.id) {
            // console.log(_this.rowId)
            _this.rowId.push(item.id)
          }
        }
      }
    },


    //修改
    updateBrand(row) {
      this.updateForm = JSON.parse(JSON.stringify(row))
      this.updateFormVisible = true
    },
    updateExecute() {
      console.log(this.updateForm)
      axios({
        method: "post",
        url: "http://localhost:8081/updateById",
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        },
        data: this.updateForm
      }).then((resp) => {
        this.updateForm = resp.data;
        this.updateFormVisible = false
        this.selectAll();
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
    this.selectAll()
  }

}
</script>