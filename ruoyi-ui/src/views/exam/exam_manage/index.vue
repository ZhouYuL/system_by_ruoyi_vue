<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学期" prop="examSemester">
        <el-input
          v-model="queryParams.examSemester"
          placeholder="请输入学期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课程名" prop="examProject">
        <el-input
          v-model="queryParams.examProject"
          placeholder="请输入课程名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入学生ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入学生姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable size="small">
          <el-option
            v-for="dict in sexOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否缺考" prop="isMiss">
        <el-select v-model="queryParams.isMiss" placeholder="请选择是否缺考" clearable size="small">
          <el-option
            v-for="dict in isMissOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="成绩" prop="record">
        <el-input
          v-model="queryParams.record"
          placeholder="请输入成绩"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="考试时间" prop="examTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.examTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择考试时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="是否及格" prop="isFail">
        <el-select v-model="queryParams.isFail" placeholder="请选择是否及格" clearable size="small">
          <el-option
            v-for="dict in isFailOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['exam:exam_manage:add']"
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
          v-hasPermi="['exam:exam_manage:edit']"
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
          v-hasPermi="['exam:exam_manage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['exam:exam_manage:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['exam:exam_manage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="exam_manageList" @selection-change="handleSelectionChange" :default-sort = "{prop: 'userId', order: 'ascending'}">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" align="center" width="100" />
      <!-- <el-table-column label="序号" align="center" prop="examId" /> -->
      <el-table-column label="学期" align="center" prop="examSemester" :formatter="examSemesterFormat" />
      <el-table-column label="课程名" align="center" prop="examProject" />
      <el-table-column label="学生ID" align="center" prop="userId" sortable	/>
      <el-table-column label="学生姓名" align="center" prop="userName" />
      <el-table-column label="性别" align="center" prop="sex" :formatter="sexFormat" />
      <el-table-column label="是否缺考" align="center" prop="isMiss" :formatter="isMissFormat" />
      <el-table-column label="成绩" align="center" prop="record" />
      <el-table-column label="考试时间" align="center" prop="examTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.examTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否及格" align="center" prop="isFail" :formatter="isFailFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['exam:exam_manage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['exam:exam_manage:remove']"
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

    <!-- 添加或修改考试信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学期" prop="examSemester">
          <el-input v-model="form.examSemester" placeholder="请输入学期" />
        </el-form-item>
        <el-form-item label="课程名" prop="examProject">
          <el-input v-model="form.examProject" placeholder="请输入课程名" />
        </el-form-item>
        <el-form-item label="学生ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入学生ID" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in sexOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否缺考" prop="isMiss">
          <el-select v-model="form.isMiss" placeholder="请选择是否缺考">
            <el-option
              v-for="dict in isMissOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="成绩" prop="record">
          <el-input v-model="form.record" placeholder="请输入成绩" />
        </el-form-item>
        <el-form-item label="考试时间" prop="examTime">
          <el-date-picker clearable size="small"
            v-model="form.examTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择考试时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否及格" prop="isFail">
          <el-select v-model="form.isFail" placeholder="请选择是否及格">
            <el-option
              v-for="dict in isFailOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
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
import { listExam_manage, getExam_manage, delExam_manage, addExam_manage, updateExam_manage, exportExam_manage } from "@/api/exam/exam_manage";
import { getToken } from "@/utils/auth";
import { listExam_index, getExam_index } from "@/api/exam/exam_index"

export default {
  name: "Exam_manage",
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
      // 考试信息管理表格数据
      exam_manageList: [],
      // 默认考试信息课程
      defaultExamProject: "",
      // 默认考试学期
      defaultExamSemeste: "",
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 学期字典
      examSemesterOptions: [],
      // 性别字典
      sexOptions: [],
      // 是否缺考字典
      isMissOptions: [],
      // 是否及格字典
      isFailOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        indexId: null,
        examSemester: null,
        examProject: null,
        userId: null,
        userName: null,
        sex: null,
        isMiss: null,
        record: null,
        examTime: null,
        isFail: null
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/exam/exam_manage/importData"
      },
      // 列信息
      columns: [
        { key: 0, label: `序号`, visible: true },
        { key: 1, label: `学期`, visible: true },
        { key: 2, label: `课程名`, visible: true },
        { key: 3, label: `学生ID`, visible: true },
        { key: 4, label: `学生姓名`, visible: true },
        { key: 5, label: `性别`, visible: true },
        { key: 6, label: `是否缺考`, visible: true },
        { key: 7, label: `成绩`, visible: true },
        { key: 8, label: `考试时间`, visible: true },
        { key: 9, label: `是否及格`, visible: true }
      ],
      // 表单参数
      form: {
        indexId: null
      },
      // 表单校验
      rules: {
        examSemester: [
          { required: true, message: "学期不能为空", trigger: "change" }
        ],
        examProject: [
          { required: true, message: "课程名不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "学生ID不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "学生姓名不能为空", trigger: "blur" }
        ],
        sex: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    const indexId = this.$route.params && this.$route.params.indexId;
    // console.log(indexId)
    this.getExam_index(indexId);
    this.getList();
    this.getDicts("exam_semester").then(response => {
      this.examSemesterOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getDicts("exam_isMiss").then(response => {
      this.isMissOptions = response.data;
    });
    this.getDicts("exam_isFail").then(response => {
      this.isFailOptions = response.data;
    });
  },
  methods: {
    /** 查询考试信息详细 */
    getExam_index(indexId) {
      getExam_index(indexId).then(response => {
        // console.log(indexId)
        // console.log(response.data)
        this.queryParams.examSemester = response.data.examSemester;
        this.queryParams.indexId = response.data.indexId;
        this.queryParams.examProject = response.data.examName;
        this.defaultExamProject = response.data.examName;
        this.defaultExamSemeste = response.data.examSemester;
        this.getList();
      });
    },
    /** 查询考试信息首页列表 */
    // getTypeList() {
    //   listExam_index().then(response => {
    //     console.log(1)
    //     this.examSemesterOptions = response.rows;
    //   });
    // },
    /** 查询考试信息管理列表 */
    getList() {
      this.loading = true;
      // console.log(1)
      listExam_manage(this.queryParams).then(response => {
        // console.log(response.rows)
        this.exam_manageList = response.rows;
        this.total = response.total;
        this.loading = false;
      }).catch(reject => {
        //有选择性的在此处抛出错误或不抛出
        console.log(reject);
    });
    },
    // 学期字典翻译
    examSemesterFormat(row, column) {
      return this.selectDictLabel(this.examSemesterOptions, row.examSemester);
    },
    // 性别字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex);
    },
    // 是否缺考字典翻译
    isMissFormat(row, column) {
      return this.selectDictLabel(this.isMissOptions, row.isMiss);
    },
    // 是否及格字典翻译
    isFailFormat(row, column) {
      return this.selectDictLabel(this.isFailOptions, row.isFail);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        examId: null,
        userId: null,
        userName: null,
        sex: null,
        isMiss: null,
        record: null,
        examTime: null,
        isFail: null
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
      this.queryParams.examProject = this.defaultExamProject;
      this.queryParams.examSemester = this.defaultExamSemeste;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.examId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考试信息数据";
      this.form.examProject = this.queryParams.examProject;
      this.form.examSemester = this.queryParams.examSemester;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const examId = row.examId || this.ids
      getExam_manage(examId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考试信息数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        this.form.indexId = this.queryParams.indexId;
        if (valid) {
          if (this.form.examId != null) {
            updateExam_manage(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addExam_manage(this.form).then(response => {
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
      const examIds = row.examId || this.ids;
      this.$confirm('是否确认删除考试信息管理编号为"' + examIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delExam_manage(examIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有考试信息管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportExam_manage(queryParams);
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
