<template>
  <div>
    <!-- <el-card class="mycard">
          <el-form ref="searchForm" :model="permissionSearch" label-position="right">
              <el-row :gutter="10">
                  <el-col :span="4">
                      <el-form-item prop="name" label="权限名" label-width="80px">
                          <el-input size="mini" v-model="permissionSearch.name" autocomplete="on" type="text" placeholder="角色名" class="form_input"></el-input>
                      </el-form-item>
                  </el-col>
                  <el-col :span="2">
                      <el-form-item>
                          <el-button size="mini" type="primary" class="commit-button" @click="getData">查询</el-button>
                      </el-form-item>
                  </el-col>
                  <el-col :span="1">
                      <el-form-item>
                          <el-button size="mini" @click="resetForm('searchForm')" plain type="warning">重置</el-button>
                      </el-form-item>
                  </el-col>
              </el-row>
          </el-form>
    </el-card> -->

    <el-card>
      <div class="line_box">
        <el-button
          size="mini"
          class="btn_primary"
          @click="addBtnClick"
          type="primary"

        >新增权限</el-button>
        <!-- <el-button size="medium" @click="addpermissionshow = true" type="success">分配权限</el-button> -->
      </div>
      <el-table
        highlight-current-row
        @current-change="handleCurrentChange"
        :data="permissions"
        style="width: 100%"
        row-key="id"
        @row-click="rowClick"
        @row-dblclick="addHandlerdbClick"
        :header-cell-style="{background: '#367fa98a',color:'white'}"
        :stripe="true"
        :default-expand-all="false"
      >
        <el-table-column prop="name" label="权限名"></el-table-column>
        <el-table-column prop="code" label="权限编码"></el-table-column>
        <el-table-column prop="url" label="路径"></el-table-column>
        <el-table-column prop="icon" label="图标" width="180">
          <template slot-scope="scope">
            <i :class="scope.row.icon"></i>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="类型" width="180">
          <template slot-scope="scope">
            <el-button size="mini" round v-if="scope.row.type == 'menu'" type="primary" plain>菜单</el-button>
            <el-button
              size="mini"
              round
              v-else-if="scope.row.type == 'button'"
              type="success"
              plain
            >按钮</el-button>
            <el-button size="mini" round v-else type="warning" plain>接口</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="listSort" label="排序" width="180"></el-table-column>

        <el-table-column ref="tt" label="操作" fixed="right" width="180">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              plain
              @click="showupdatePermission(scope.row)"
              v-has="'back:sysPermission:update'"
              v-if="scope.row.createBy!='sys'"
            >编辑</el-button>
            <el-button
              type="danger"
              size="mini"
              plain
              @click="deleteRole(scope.row)"
              v-has="'back:sysPermission:delete'"
              v-if="scope.row.createBy!='sys'"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog
      title="新增权限"
      :visible.sync="addpermissionshow"
      width="30%"
      center
      :show-close="false"
      :close-on-click-modal="false"
    >
      <el-form ref="addPermissionForm" :model="addPermission" :rules="addpermissionRule">
        <el-form-item label="父级权限" label-width="100px" style="margin-bottom:20px">
          <el-input
            type="text"
            v-model="rowClickItem.name"
            :placeholder="rowClickItem.name"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item
          label="权限名称"
          label-width="100px"
          style="margin-bottom:20px"
          prop="name"
        >
          <el-input type="text" v-model="addPermission.name" placeholder="权限名称"></el-input>
        </el-form-item>
        <el-form-item
          label="权限编码"
          label-width="100px"
          style="margin-bottom:20px"
          prop="name"
        >
          <el-input type="text" v-model="addPermission.permissionCode" placeholder="权限编码"></el-input>
        </el-form-item>
        <el-form-item label="路径" label-width="100px" style="margin-bottom:20px" prop="url">
          <el-input type="text" v-model="addPermission.url" placeholder="路径"></el-input>
        </el-form-item>
        <el-form-item label="类型" label-width="100px" style="margin-bottom:20px" prop="type">
          <el-select v-model="addPermission.type" placeholder="权限类型" style="width:100%">
            <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.name"
              :value="item.code"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图标" label-width="100px" style="margin-bottom:20px" prop="icon">
          <el-input type="text" v-model="addPermission.icon" placeholder="图标"></el-input>
        </el-form-item>
        <el-form-item label="排序" label-width="100px" style="margin-bottom:20px" prop="listSort">
          <el-input type="text" v-model="addPermission.listSort" placeholder="排序"></el-input>
        </el-form-item>
        <el-form-item label="工作流类型" label-width="100px" style="margin-bottom:20px" prop="jobType">
          <el-select v-model="addPermission.jobType" placeholder="工作流类型" style="width:100%">
            <el-option
              v-for="item in jobTypes"
              :key="item.value"
              :label="item.name"
              :value="item.code"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="流程选择"
          label-width="100px"
          style="margin-bottom:20px"
          prop="jobType"
          v-if="addPermission.jobType==1 || addPermission.jobType==2"
        >
          <el-select v-model="addPermission.processDefId" placeholder="工作流类型" style="width:100%">
            <el-option
              v-for="item in proDefs"
              :key="item.id"
              :label="item.processNm"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addpermissionshow = false">取 消</el-button>
        <el-button type="primary" @click="addpermissions()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="修改权限"
      :visible.sync="updatepermissionshow"
      width="30%"
      center
      :show-close="false"
      :close-on-click-modal="false"
    >
      <el-form ref="updatePermissionForm" :model="updatePermission" :rules="updateRule">
        <el-form-item label="父级权限" label-width="100px" style="margin-bottom:20px">
          <el-cascader
            :options="permissions"
            v-model="updatePermission.parentId"
            :show-all-levels="false"
            @change="handleChange"
            :props="{ value: 'id',label:'name',checkStrictly: true}"
          ></el-cascader>
        </el-form-item>
        <el-form-item
          label="权限名称"
          label-width="100px"
          style="margin-bottom:20px"
          prop="name"
        >
          <el-input type="text" v-model="updatePermission.name" placeholder="角色名称"></el-input>
        </el-form-item>
        <el-form-item
          label="权限编码"
          label-width="100px"
          style="margin-bottom:20px"
          prop="permissionCode"
        >
          <el-input
            type="text"
            v-model="updatePermission.permissionCode"
            placeholder="权限编码"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="路径" label-width="100px" style="margin-bottom:20px" prop="url">
          <el-input type="text" v-model="updatePermission.url" placeholder="路径"></el-input>
        </el-form-item>
        <el-form-item label="类型" label-width="100px" style="margin-bottom:20px" prop="type">
          <el-select v-model="updatePermission.type" placeholder="权限类型" style="width:100%">
            <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.name"
              :value="item.code"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图标" label-width="100px" style="margin-bottom:20px">
          <el-input type="text" v-model="updatePermission.icon" placeholder="图标"></el-input>
        </el-form-item>
        <el-form-item label="排序" label-width="100px" style="margin-bottom:20px">
          <el-input type="text" v-model="updatePermission.listSort" placeholder="请输入排序(越大越靠前)"></el-input>
        </el-form-item>
        <el-form-item label="工作流类型" label-width="100px" style="margin-bottom:20px" prop="jobType">
          <el-select v-model="updatePermission.jobType" placeholder="工作流类型" style="width:100%">
            <el-option
              v-for="item in jobTypes"
              :key="item.value"
              :label="item.name"
              :value="item.code"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="流程选择"
          label-width="100px"
          style="margin-bottom:20px"
          prop="jobType"
          v-if="updatePermission.jobType==1 || updatePermission.jobType==2"
        >
          <el-select v-model="updatePermission.processDefId" placeholder="工作流类型" style="width:100%">
            <el-option
              v-for="item in proDefs"
              :key="item.id"
              :label="item.processNm"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="updatepermissionshow = false">取 消</el-button>
        <el-button type="primary" @click="updatepermissions()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
  
  <script>
import { mapState } from "vuex";
import { getPermission,addPermission } from "@/api/accountApi";

export default {
  data() {
    return {
      //可选的流程
      proDefs: [],
      //工作流类型
      jobTypes: [
        {
          name: "一般菜单",
          code: 0
        },
        {
          name: "手动任务",
          code: 1
        },
        {
          name: "自动任务",
          code: 2
        }
      ],
      //权限类型
      types: [
        {
          name: "菜单",
          code: "menu"
        },
        {
          name: "按钮",
          code: "button"
        },
        {
          name: "接口",
          code: "backApi"
        }
      ],
      //当前选中的行
      rowClickItem: {},
      //是否显示修改权限的对话框
      updatepermissionshow: false,
      //修改权限数据dto
      updatePermission: {
        id: "",
        permissionCode: "",
        name: ""
      },
      //是否显示新增权限对话框
      addpermissionshow: false,
      //新增权限数据dto
      addPermission: {
        permissionCode: "",
        name: "",
        type: "",
        icon: "",
        listSort: "",
        url: "",
        jobType: ""
      },
      //权限表格列表
      permissions: [],
      //筛选条件dto-暂时为用到
      permissionSearch: {
        name: ""
      },
      //修改权限的表单校验规则
      updateRule: {
        name: [
          {
            required: true,
            message: "请输入权限名称",
            trigger: "blur"
          }
        ],
        permissionCode: [
          {
            required: true,
            message: "请输入权限编码",
            trigger: "blur"
          }
        ],
        url: [
          {
            required: true,
            message: "请输入权限路径",
            trigger: "blur"
          }
        ],
        type: [
          {
            required: true,
            message: "请输入权限编码",
            trigger: "blur"
          }
        ],
        jobType: [
          {
            required: true,
            message: "请选择工作流类型",
            trigger: "blur"
          }
        ]
      },
      //新增权限的表单校验规则
      addpermissionRule: {
        name: [
          {
            required: true,
            message: "请输入权限名称",
            trigger: "blur"
          }
        ],
        permissionCode: [
          {
            required: true,
            message: "请输入权限编码",
            trigger: "blur"
          }
        ],
        url: [
          {
            required: true,
            message: "请输入权限路径",
            trigger: "blur"
          }
        ],
        type: [
          {
            required: true,
            message: "请选择权限类型",
            trigger: "blur"
          }
        ],
        jobType: [
          {
            required: true,
            message: "请选择工作流类型",
            trigger: "blur"
          }
        ]
        // icon: [{
        //     required: true,
        //     message: "请输入图标",
        //     trigger: "blur",
        // }, ],
        // listSort: [{
        //     required: true,
        //     message: "请输入排序(越大越靠前)",
        //     trigger: "blur",
        // }, ],
      }
    };
  },
  methods: {
    //获取部署的流程列表
    getProDef() {
      this.$http({
        method: "post",
        url: this.$url + "/bpm/processdef/get", //这里是发送给后台的数据
        params: {
          page: 1,
          row: 1000000
        }
      }).then(response => {
        const res = response.data;
        this.proDefs = res.data.data;
      });
    },
    // 判断是否为对象
    isObject(o) {
      return (typeof o === "object" || typeof o === "function") && o !== null;
    },
    deepClone(obj) {
      if (!this.isObject(obj)) {
        throw new Error("obj 不是一个对象！");
      }

      let isArray = Array.isArray(obj);
      let cloneObj = isArray ? [] : {};
      for (let key in obj) {
        cloneObj[key] = this.isObject(obj[key])
          ? deepClone(obj[key])
          : obj[key];
      }

      return cloneObj;
    },
    addBtnClick() {
      console.log(this.rowClickItem);
      if (
        this.rowClickItem.id == null ||
        this.rowClickItem.id == "" ||
        this.rowClickItem.id == undefined
      ) {
        this.$message.error("请选择父节点");
        return;
      }
      this.addpermissionshow = true;
    },
    handleCurrentChange(val) {
      console.log(val);
      this.rowClickItem = val;
    },
    addHandlerdbClick() {
      if (this.addCheck()) {
        this.addpermissionshow = true;
      }
      this.addpermissionshow = true;
    },
    addCheck() {
      for (let item of this.$store.state.permissions) {
        if (item.permissionCode === "back:permission:add") {
          return true;
        }
      }
      return false;
    },
    handleChange(val) {
      this.updatePermission.parentId = val[0];
    },
    rowClick(row, column, event) {
      this.rowClickItem = row;
      console.log(this.rowClickItem);
    },
    showupdatePermission(row) {
      this.updatePermission = this.deepClone(row);
      this.updatepermissionshow = true;
    },
    updatepermissions() {
      console.log(this.updatePermission.parentId);
      this.$refs["updatePermissionForm"].validate(valid => {
        if (valid) {
          this.$http({
            method: "post",
            url: this.$url + "/sys/sysPermission/update", //这里是发送给后台的数据
            params: {
              id: this.updatePermission.id,
              name: this.updatePermission.name,
              parentId: this.updatePermission.parentId,
              url: this.updatePermission.url,
              type: this.updatePermission.type,
              icon: this.updatePermission.icon,
              listSort: this.updatePermission.listSort,
              jobType: this.updatePermission.jobType,
              processDefId: this.updatePermission.processDefId
            }
          }).then(response => {
            var res = response.data;
            console.log(res);
            if (res.code == 200) {
              this.updatePermission.id = "";
              this.updatePermission.name = "";
              this.updatePermission.permissionCode = "";
              this.updatepermissionshow = false;
              this.getData();
            }
          });
        } else {
          return false;
        }
      });
    },
    deleteRole(row) {
      console.log(row);

      this.$http({
        method: "post",
        url: this.$url + "/sys/sysPermission/delete", //这里是发送给后台的数据
        params: {
          id: row.id
        }
      }).then(response => {
        var res = response.data;
        console.log(res);
        if (res.code == 200) {
          this.addpermissionshow = false;
          this.getData();
        }
      });
    },
    addpermissions() {
      addPermission({
              name: this.addPermission.name,
              code: this.addPermission.permissionCode,
              parentId: this.rowClickItem.id,
              icon: this.addPermission.icon,
              listSort: this.addPermission.listSort,
              url: this.addPermission.url,
              type: this.addPermission.type,
              sourcePlat: this.rowClickItem.sourcePlat,
              jobType: this.addPermission.jobType,
              processDefId: this.addPermission.processDefId
            }).then(res => {
              console.log(res)
        this.permissions = res.data;
        this.total = res.total;
      });


      // console.log(this.addPermission);
      // this.$refs["addPermissionForm"].validate(valid => {
      //   if (valid) {
      //     this.$http({
      //       method: "post",
      //       url: this.$url + "/permission/add", //这里是发送给后台的数据
      //       params: {
      //         name: this.addPermission.name,
      //         code: this.addPermission.permissionCode,
      //         parentId: this.rowClickItem.id,
      //         icon: this.addPermission.icon,
      //         listSort: this.addPermission.listSort,
      //         url: this.addPermission.url,
      //         type: this.addPermission.type,
      //         sourcePlat: this.rowClickItem.sourcePlat,
      //         jobType: this.addPermission.jobType,
      //         processDefId: this.addPermission.processDefId
      //       }
      //     }).then(response => {
      //       var res = response.data;
      //       console.log(res);
      //       if (res.code == 200) {
      //         this.addPermission.name = "";
      //         this.addPermission.permissionCode = "";
      //         this.addpermissionshow = false;
      //         this.getData();
      //       }
      //     });
      //   } else {
      //     return false;
      //   }
      // });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getData() {

      getPermission().then(res => {
        this.permissions = res.data;
        this.total = res.total;
      });
    }
  },
  mounted: function() {
    this.getData();
    // this.getProDef();
  }
};
</script>
  
  <style scoped>
.table_header {
  background: red;
}

.el-input.is-disabled >>> .el-input__inner {
  background-color: #f5f7fa;
  border-color: #e4e7ed;
  color: #a1a5ad;
  cursor: not-allowed;
}

.line_box {
  text-align: left;
  margin-bottom: 10px;
}

.el-main >>> .el-dialog__footer {
  text-align: center !important;
}

.el-form-item {
  margin-bottom: 0px;
}

.commit-button {
  background: #367fa98a;
  width: 100%;
}

.el-button--primary.is-plain {
  color: #367fa9d7;
  background: #ecf5ff;
  border-color: #b3d8ff;
}

.card-magin-top {
  margin-top: 10px;
}
</style>
  