<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学期" prop="examSemester">
        <el-select v-model="queryParams.examSemester" placeholder="请选择学期" clearable size="small">
          <el-option
            v-for="dict in examSemesterOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任课老师" prop="examTeacher">
        <!-- <el-input
          v-model="queryParams.examTeacher"
          placeholder="请输入任课老师"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        /> -->

        <el-select v-model="queryParams.examTeacher" placeholder="请选择任课老师" clearable size="small">
          <el-option
            v-for="dict in examTeacherOptions"
            :key="dict.teacherName"
            :label="dict.teacherName"
            :value="dict.teacherName"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程名称" prop="examName">
        <!-- <el-input
          v-model="queryParams.examName"
          placeholder="请输入课程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.examName" placeholder="请选择课程名称" clearable size="small">
          <el-option
            v-for="dict in examNameOptions"
            :key="dict.courseName"
            :label="dict.courseName"
            :value="dict.courseName"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考试时间" prop="examTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.examTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择考试时间">
        </el-date-picker>
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
          v-hasPermi="['exam:exam_index:add']"
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
          v-hasPermi="['exam:exam_index:edit']"
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
          v-hasPermi="['exam:exam_index:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['exam:exam_index:import']"
            >导入</el-button>
          </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['exam:exam_index:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exam_indexList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" width="100" />
      <!-- <el-table-column label="考试信息表编号" align="center" prop="indexId" /> -->
      <el-table-column label="学期" align="center" prop="examSemester" :formatter="examSemesterFormat" />
      <el-table-column label="任课老师" align="center" prop="examTeacher" />
      <el-table-column label="课程名称" align="center" prop="examName" :show-overflow-tooltip="true">
        <!-- <template slot-scope="scope">
          <router-link :to="'/exam/exam_manage/' + scope.row.indexId" class="link-type">
            <span>{{ scope.row.examName }}</span>
          </router-link>
        </template> -->
      </el-table-column>
      <el-table-column label="不及格人数" align="center" prop="failNum" />
      <el-table-column label="班级总人数" align="center" prop="classTotal" />
      <el-table-column label="及格率" align="center" prop="passingRate" />
      <el-table-column label="考试时间" align="center" prop="examTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.examTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['exam:exam_index:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['exam:exam_index:remove']"
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

    <!-- 添加或修改考试信息管理首页对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学期" prop="examSemester">
          <el-select v-model="form.examSemester" placeholder="请选择学期">
            <el-option
              v-for="dict in examSemesterOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任课老师" prop="examTeacher">
          <el-select v-model="form.examTeacher" placeholder="请选择任课老师" clearable size="small">
          <el-option
            v-for="dict in examTeacherOptions"
            :key="dict.teacherName"
            :label="dict.teacherName"
            :value="dict.teacherName"
          />
        </el-select>
          <!-- <el-input v-model="form.examTeacher" placeholder="请输入任课老师" /> -->
        </el-form-item>
        <el-form-item label="课程名称" prop="examName">
          <el-select v-model="form.examName" placeholder="请选择课程名称" clearable size="small">
          <el-option
            v-for="dict in examNameOptions"
            :key="dict.courseName"
            :label="dict.courseName"
            :value="dict.courseName"
          />
        </el-select>
          <!-- <el-input v-model="form.examName" placeholder="请输入考试课程名称" /> -->
        </el-form-item>
        <el-form-item label="不及格人数" prop="failNum">
          <el-input type="number" v-model="form.failNum" placeholder="请输入不及格人数" />
        </el-form-item>
        <el-form-item label="班级总人数" prop="classTotal">
          <el-input v-model="form.classTotal" type="number" placeholder="请输入班级总人数" />
        </el-form-item>
        <el-form-item label="及格率" prop="passingRate">
          <el-input v-model="form.passingRate" placeholder="请输入及格率"/>
        </el-form-item>
        <el-form-item label="考试时间" prop="examTime">
          <el-date-picker clearable size="small"
            v-model="form.examTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择考试时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的考试数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listExam_index, getExam_index, delExam_index, addExam_index, updateExam_index, exportExam_index,importTemplate } from "@/api/exam/exam_index";
import { getToken } from "@/utils/auth";
import {listAllTeacher} from "@/api/group/teacher";
import {listAllCourse} from "@/api/group/course";

export default {
  name: "Exam_index",
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
      // 考试信息管理首页表格数据
      exam_indexList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 学期字典
      examSemesterOptions: [],
      examTeacherOptions: [],
      examNameOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        examSemester: null,
        examTeacher: null,
        examName: null,
        examTime: null
      },

      upload: {
        // 是否显示弹出层（考试信息导入）
        open: false,
        // 弹出层标题（考试信息导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的考试信息数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/exam/exam_index/importData"
      },
      
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        examSemester: [
          { required: true, message: "学期不能为空", trigger: "change" }
        ],
        examName: [
          { required: true, message: "考试课程名称不能为空", trigger: "blur" }
        ],
        examTime: [
          { required: true, message: "考试时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getListTeacher();
    this.getListCourse();
    this.getDicts("exam_semester").then(response => {
      this.examSemesterOptions = response.data;
    });
  },
  methods: {
    /** 查询考试信息管理首页列表 */
    getList() {
      this.loading = true;
      // window.setTimeout(function() {
        listExam_index(this.queryParams).then(response => {
          // console.log(1)
          this.exam_indexList = response.rows;
          this.total = response.total;
          this.loading = false;
          // console.log(this.$data)
        });  
      // }, 500);
          
    },

    getListTeacher() {
      listAllTeacher(this.queryParams).then(response => {
        this.examTeacherOptions = response.data;
      })
    },
    getListCourse() {
      listAllCourse(this.queryParams).then(response => {
        this.examNameOptions = response.data;
      })
    },
    // 考试信息状态字典翻译
    // statusFormat(row, column) {
    //   return this.selectDictLabel(this.statusOptions, row.status);
    // },
    // 学期字典翻译
    examSemesterFormat(row, column) {
      var name = this.selectDictLabel(this.examSemesterOptions, row.examSemester);
      if(name != '') {
        return name;
      }
      return row.examSemester;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        indexId: null,
        examSemester: null,
        examTeacher: null,
        examName: null,
        examTime: null
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
      this.ids = selection.map(item => item.indexId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考试信息管理首页";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const indexId = row.indexId || this.ids
      getExam_index(indexId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考试信息管理首页";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.indexId != null) {
            updateExam_index(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addExam_index(this.form).then(response => {
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
      const indexIds = row.indexId || this.ids;
      this.$confirm('是否确认删除考试信息管理首页编号为"' + indexIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delExam_index(indexIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有考试信息管理首页数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportExam_index(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "考试信息导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
