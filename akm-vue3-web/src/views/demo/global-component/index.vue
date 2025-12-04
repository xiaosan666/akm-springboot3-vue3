<template>
  <div>
    <h2>akm-table</h2>
    <akm-table v-margin :config="tableConfig"></akm-table>
    <h2>akm-form</h2>
    <akm-form v-margin :config="formConfig"></akm-form>
    <h2>akm-form-view</h2>
    <akm-form-view v-margin :config="formViewConfig"></akm-form-view>
    <h2>akm-dialog</h2>
    <el-button type="primary" @click="dialogConfig.visible = true">打开dialog</el-button>
    <the-select-user></the-select-user>
    <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
      哈哈哈哈哈
    </akm-dialog>
    <h2>akm-select</h2>
    {{ selectData }}
    <akm-select
      :options="selectOptions"
      v-model:value="selectData"
      :disabled="false"
      :clearable="true"
      placeholder="请选择"
      :multiple="false"
      :collapseTags="false"
    ></akm-select>

    test
    <el-select v-model="selectData" placeholder="Select" style="width: 240px">
      <el-option
        v-for="item in selectOptions"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>

    <h2>akm-select-dict</h2>
    <p class="tips">根据数据字典，生成下拉选择框</p>
    <p class="tips">结果是Number类型还是String类型，取决于默认值类型</p>
    <p class="tips">根据 code 请求后台接口获取下拉数据会缓存，重新登录即可清空缓存，重新获取</p>
    {{ selectDictData }}
    <akm-select-dict
      code="menu_type"
      v-model:value="selectDictData"
      :disabled="false"
      :clearable="true"
      placeholder="请选择"
      :multiple="false"
      :collapseTags="false"
    ></akm-select-dict>
    <h2>akm-radio-dict</h2>
    <p class="tips">根据数据字典，生成单选按钮组</p>
    <p class="tips">结果是Number类型还是String类型，取决于默认值类型</p>
    <p class="tips">根据 code 请求后台接口获取下拉数据会缓存，重新登录即可清空缓存，重新获取</p>
    {{ selectDictData }}
    <akm-radio-dict
      code="menu_type"
      v-model:value="selectDictData"
      :disabled="false"
      :button="false"
    ></akm-radio-dict>
    <h2>akm-warn-btn</h2>
    <akm-warn-btn @confirm="del">删除</akm-warn-btn>
    <akm-warn-btn @confirm="disabled">禁用</akm-warn-btn>
    <akm-warn-btn @confirm="cancel">取消申请</akm-warn-btn>
  </div>
</template>

<script>
import TheSelectUser from '@/views/demo/global-component/TheSelectUser.vue'
export default {
  name: 'DemoGlobalComponent',
  components: { TheSelectUser },
  data() {
    return {
      editorContent:
        '<h2 style="text-align: center;"><span style="font-weight: bold;">hello world</span></h2>',

      tableConfig: {
        id: 'akmTable', // 默认为'akmTable'，当页面出现多个表格，务必指定不同的id，
        loading: false, // 是否显示loading
        height: 'auto', // 表格高度
        autoComputedHeight: false, // 是否自动计算表格高度，设为true则当页面出现滚动条时，自动把滚动条加到表格上，表格最大高度将填充页面
        multipleSelect: true, // 是否显示checkbox复选框
        singleSelect: false, // 是否显示radio单选框
        hideIndexColumn: false, // 是否隐藏序号列，默认显示
        pagination: false, // 是否显示分页组件
        pageNum: 1, // 当前页码，当pagination为true有效
        pageSize: 10, // 每页大小，当pagination为true有效
        total: 0, // 总数，当pagination为true有效
        rowKey: 'id', // 行数据的 Key，用来优化 Table 的渲染，默认为id
        // 表格数据
        data: [
          { id: 1, username: 'zhagnsan', name: '张三', phone: '13012344321' },
          { id: 2, username: 'lisi', name: '李四', phone: '13012344321' },
        ],
        // 表格列
        columns: [
          { prop: 'username', label: '用户名' },
          { prop: 'name', label: '姓名' },
          { prop: 'phone', label: '手机号码' },
        ],
      },

      formConfig: {
        loading: false, // 是否显示loading
        inline: false, // 表单内容是否一行显示
        showQuery: false, // 是否显示查询按钮
        showBorder: false, // 是否显示边框
        showMoreBtn: false, // 是否显示“更多搜索”查询按钮，为true则查询条件只显示一行
        className: '', // 自定义class
        labelPosition: 'right', // label显示位置， right/left/top
        labelWidth: 110, // label宽度
        // 表单绑定的数据对象
        data: {
          username: '',
          realname: '',
          sex: 1,
          birthday: '',
          age: 0,
          remark: '',
          enable: 1,
        },
        // 表单内容项
        items: [
          { type: 'input', prop: 'username', label: '用户名' },
          { type: 'input', prop: 'realname', label: '姓名' },
          {
            type: 'radioDict',
            code: 'sex',
            prop: 'sex',
            label: '性别',
          },
          {
            type: 'date',
            prop: 'birthday',
            label: '生日',
            className: 'width2X',
          },
          { type: 'inputNumber', prop: 'age', label: '年龄' },
          { type: 'textarea', prop: 'remark', label: '个人说明' },
          {
            type: 'switch',
            prop: 'enable',
            label: '启用状态',
            options: [
              {
                label: '启用',
                value: 1,
              },
              {
                label: '禁用',
                value: 0,
              },
            ],
          },
        ],
        // 表单验证规则
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            {
              validator: this.$validate.elementValidator(
                this.$validate.regex.username,
                '只能是字母或数字，4到16位字符'
              ),
              trigger: 'blur',
            },
          ],
          realname: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        },
      },

      formViewConfig: {
        loading: false, // 是否显示loading
        inline: false, // 表单内容是否一行显示
        className: '', // 自定义class
        labelPosition: 'right', // label显示位置， right/left/top
        labelWidth: 110, // label宽度
        // 表单绑定的数据对象
        data: {
          username: 'zhangsan',
          realname: '张三',
          sex: 1,
          birthday: new Date(),
          age: 22,
          remark: '我叫张三。',
          enable: 1,
        },
        // 表单内容项
        items: [
          { type: 'view', prop: 'username', label: '用户名' },
          { type: 'view', prop: 'realname', label: '姓名' },
          {
            type: 'dict',
            code: 'sex',
            prop: 'sex',
            label: '性别',
          },
          {
            type: 'date',
            prop: 'birthday',
            label: '生日',
            className: 'width2X',
          },
          { type: 'view', prop: 'age', label: '年龄' },
          { type: 'view', prop: 'remark', label: '个人说明' },
          {
            type: 'switch',
            prop: 'enable',
            label: '启用状态',
            options: [
              {
                label: '启用',
                value: 1,
              },
              {
                label: '禁用',
                value: 0,
              },
            ],
          },
        ],
      },

      dialogConfig: {
        loading: false, // 是否显示loading
        visible: false, // 是否打开dialog
        title: '我的dialog测试', // 标题
        width: 1000, // 初始化宽度
        footer: true, // 是否显示footer
        confirmLoading: false, // 确认按钮 loading，当footer为true有效
      },

      selectData: 1,
      selectOptions: [
        {
          label: '启用',
          value: 1,
        },
        {
          label: '禁用',
          value: 0,
        },
      ],

      selectDictData: '',
    }
  },
  methods: {
    confirm() {
      this.dialogConfig.visible = false
      this.$helper.successMessage('您点击了确定')
    },
    del() {
      this.$helper.successMessage('您点击了确定删除')
    },
    disabled() {
      this.$helper.successMessage('您点击了确定禁用')
    },
    cancel() {
      this.$helper.successMessage('您点击了确定取消申请')
    },
  },
}
</script>

<style lang="scss" scoped>
.tips {
  color: gray;
}
</style>
