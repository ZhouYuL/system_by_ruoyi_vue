<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课群名称" prop="groupType">
        <el-input
          v-model="queryParams.groupType"
          placeholder="请输入课群名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课群负责人" prop="groupLeader" style="width:300px" label-width="95px">
        <el-input
          v-model="queryParams.groupLeader"
          placeholder="请输入课群负责人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学期" prop="groupSemester">
        <el-select v-model="queryParams.groupSemester" placeholder="请选择学期" clearable size="small">
          <el-option
            v-for="dict in groupSemesterOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="专业" prop="groupProfession">
        <el-input
          v-model="queryParams.groupProfession"
          placeholder="请输入专业"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['group:group_index:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['group:group_index:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['group:group_index:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['group:group_index:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="group_indexList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="课群序号" align="center" prop="groupId" />
      <el-table-column label="课群名称" align="center" prop="groupType" />
      <el-table-column label="课群负责人" align="center" prop="groupLeader" />
      <el-table-column label="学期" align="center" prop="groupSemester" :formatter="groupSemesterFormat" />
      <el-table-column label="专业" align="center" prop="groupProfession" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['group:group_index:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['group:group_index:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改课群管理首页对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="95px">
        <el-form-item label="课群名称" prop="groupType">
          <el-input v-model="form.groupType" placeholder="请输入课群名称" />
        </el-form-item>
        <el-form-item label="课群负责人" prop="groupLeader">
          <el-input v-model="form.groupLeader" placeholder="请输入课群负责人" />
        </el-form-item>
        <el-form-item label="学期" prop="groupSemester">
          <el-select v-model="form.groupSemester" placeholder="请选择学期">
            <el-option
              v-for="dict in groupSemesterOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专业" prop="groupProfession">
          <el-input v-model="form.groupProfession" placeholder="请输入专业" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGroup_index, getGroup_index, delGroup_index, addGroup_index, updateGroup_index, exportGroup_index } from "@/api/group/group_index";

export default {
  name: "Group_index",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 课群管理首页表格数据
      group_indexList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 学期字典
      groupSemesterOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupType: null,
        groupLeader: null,
        groupSemester: null,
        groupProfession: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        groupType: [
          { required: true, message: "课群名称不能为空", trigger: "blur" }
        ],
        groupLeader: [
          { required: true, message: "课群负责人不能为空", trigger: "blur" }
        ],
        groupSemester: [
          { required: true, message: "学期不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("exam_semester").then(response => {
      this.groupSemesterOptions = response.data;
    });
  },
  methods: {
    /** 查询课群管理首页列表 */
    getList() {
      this.loading = true;
      listGroup_index(this.queryParams).then(response => {
        this.group_indexList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 学期字典翻译
    groupSemesterFormat(row, column) {
      return this.selectDictLabel(this.groupSemesterOptions, row.groupSemester);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        groupId: null,
        groupType: null,
        groupLeader: null,
        groupSemester: null,
        groupProfession: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.groupId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加课群管理首页";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const groupId = row.groupId || this.ids
      getGroup_index(groupId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改课群管理首页";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.groupId != null) {
            updateGroup_index(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGroup_index(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const groupIds = row.groupId || this.ids;
      this.$confirm('是否确认删除课群管理首页编号为"' + groupIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGroup_index(groupIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有课群管理首页数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportGroup_index(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
