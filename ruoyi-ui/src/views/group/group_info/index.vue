<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="课程名称" prop="courseName">
        <el-input
          v-model="queryParams.courseName"
          placeholder="请输入课程名称"
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
          v-hasPermi="['group:groupInfo:add']"
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
          v-hasPermi="['group:groupInfo:edit']"
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
          v-hasPermi="['group:groupInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['group:groupInfo:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-view"
          size="mini"
          @click="handleManager"
          v-hasPermi="['group:groupInfo:manager']"
        >课程教师管理</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>


    <el-table v-loading="loading" :data="groupInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" prop="infoId" align="center" />
      <!-- <el-table-column label="课程序号" align="center" prop="courseId" /> -->
      <el-table-column label="课程名称" align="center" prop="groupCourse" />
      <el-table-column label="教师名称" align="center" prop="groupTeacher" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['group:groupInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['group:groupInfo:remove']"
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

  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程名称" prop="groupCourse">
        <el-select v-model="form.groupCourse" placeholder="请选择课程名称" clearable size="small" :disabled = "form.infoId != null">
          <el-option
            v-for="dict in groupCourseOptions"
            :key="dict.courseName"
            :label="dict.courseName"
            :value="dict.courseName"
          />
        </el-select>
      </el-form-item>
        <el-form-item label="教师名称" prop="groupTeacher">
        <el-select v-model="form.groupTeacher" placeholder="请选择教师名称" clearable size="small">
          <el-option
            v-for="dict in groupTeacherOptions"
            :key="dict.teacherName"
            :label="dict.teacherName"
            :value="dict.teacherName"
          />
        </el-select>
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
import { listGroupInfo, addGroupInfo, getGroupInfo, updateGroupInfo, delGroupInfo, exportGroupInfo } from "@/api/group/group_info";
import { listGroupTeacher } from "@/api/group/group_teacher";
import { listGroupCourse} from "@/api/group/group_course";

export default {
  name: "Group_info",
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
      // 课群信息管理表格数据
      groupInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      infoId:"",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        courseName: null
      },
      groupCourseOptions: [],
      groupTeacherOptions: [],
      // 表单参数
      form: {},
      rules: {
        groupCourse: [
          { required: true, message: "课程名称不能为空", trigger: "blur" }
        ],
        groupTeacher: [
          { required: true, message: "任课老师名称不能为空", trigger: "blur" }
        ]
      }

    };
  },
  created() {
    const groupId = this.$route.params && this.$route.params.groupId;
    this.groupId = groupId;
    this.getList(groupId);
    this.getTeacher(groupId);
    this.getCourse(groupId);
  },
  methods: {
    getList() {
      this.loading = true;
      this.queryParams.groupId = this.groupId;
      listGroupInfo(this.queryParams).then(response => {
        this.groupInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getTeacher(groupId){
      this.loading = true;
      this.queryParams.groupId = groupId;
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 9999;
      listGroupTeacher(this.queryParams).then(response => {
        this.groupTeacherOptions = response.rows;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList(this.groupId);
    },
    getCourse(groupId) {
      var queryParams1 = {
        groupId:groupId,
        pageNum:1,
        pageSize:9999
      }
      this.loading = true;
      listGroupCourse(queryParams1).then(response => {
        console.log(response)
        this.groupCourseOptions = response.rows;
        this.loading = false;
      });
      //this.getLastCourse();
    },
    handleManager() {
      this.$router.push({path:'/group/group_message/' + this.groupId})
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        groupId: null,
        groupCourse: null,
        groupTeacher:null
      };
      this.resetForm("form");
    },

    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加课程老师管理";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const infoId = row.infoId || this.ids
      getGroupInfo(infoId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改课程教师信息";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const infoIds = row.infoId || this.ids;
      this.$confirm('是否确认删除课程教师编号为"' + infoIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGroupInfo(infoIds);
        }).then(() => {
          this.getList(this.groupId);
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有课群信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportGroupInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
           if (this.form.infoId != null) {
              updateGroupInfo(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList(this.groupId);
            });
           }else {
              this.form.groupId = this.groupId;
              addGroupInfo(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList(this.groupId);
              });
           }
          
          
        }
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.infoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

  }
};
</script>

<style>
  .transfer-footer {
    margin-left: 20px;
    padding: 6px 5px;
  }
</style>