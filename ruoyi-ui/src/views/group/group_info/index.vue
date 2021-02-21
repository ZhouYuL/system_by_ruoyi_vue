<template>
  <div class="app-container">
    <!-- 课程添加 -->
    <p style="text-align: center; margin: 0 0 20px;font-size:34px">添加课程</p>
    <el-form ref="form" :model="checked">
    <div style="text-align: center">
      <el-transfer
        style="text-align: left; display: inline-block"
        v-model="checked"
        filterable
        :left-default-checked="[]"
        :right-default-checked="[]"
        :titles="['可选', '已选']"
        :button-texts="['移出', '放入']"
        :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}'
        }"
        :data="transferData"
         @change="getObject">
        <span slot-scope="{ option }">{{ option.key+1 }} - {{ option.label }}</span>
        <el-button type="primary" class="transfer-footer" slot="right-footer" size="small" @click="submitForm">提  交</el-button>
      </el-transfer>
    </div>
    </el-form>

    <!-- 老师添加 -->
    <!-- <p style="text-align: center; margin: 50px 0 20px">添加老师</p>
    <div style="text-align: center">
      <el-transfer
        style="text-align: left; display: inline-block"
        v-model="value"
        filterable
        :left-default-checked="[]"
        :right-default-checked="[]"
        :render-content="renderFunc"
        :titles="['可选', '已选']"
        :button-texts="['移出', '放入']"
        :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}'
        }"
        @change="handleChange"
        :data="course">
      </el-transfer>
    </div> -->
  </div>
</template>

<script>
import { getCourse } from "@/api/group/group_index";

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
      // 课群管理首页表格数据
      group_infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 穿梭框
      transferData: [], //Transfer 的数据源	array[{ key, label, disabled }]
      checked: [], //穿梭框绑定的数据，选定到右侧框中的数据项的value组成的数组
      form: [],
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    handleChange(value, direction, movedKeys) {
      // console.log(value, direction, movedKeys);
    },
    //根据数据表名，从后台获取各个表的所有字段的相关信息
    fetchData() {
      var roleId = 103;
      getCourse(roleId).then(res => {
        // const allData = res.data.data;
        console.log(res.data)
        const allData = res.data;
        const data = [];
        console.log(allData.length);
        for(let i = 0; i < allData.length; i++) {
          data.push({
            key: i,
            label: allData[i],
          });
        }
        console.log(data)
        this.transferData = data;
      }).catch(error => {
        this.$message.error(error);
      })
    },
    
    //右侧列表元素变化时触发
    getObject() {
      console.log("选中的数据" + this.checked);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(1)
          addCourse(this.checked).then(response => {
            this.msgSuccess("提交成功");
            this.open = false;
            this.getList();
          });
        }
      });
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