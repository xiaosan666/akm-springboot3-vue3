<template>
  <div>
    <h2>自定义查询条件demo2</h2>
    <div v-for="(item, index) in conditions" :key="index">
      <el-checkbox v-model="item.checked"></el-checkbox>
      <el-cascader
        v-model="item.columnName"
        :options="columnTree"
        :props="{ emitPath: false }"
        :show-all-levels="false"
        @change="onColumnNameChange(item)"
      ></el-cascader>
      <el-select v-model="item.relation" placeholder="请选择">
        <el-option
          v-for="(item, index) in relations[item.columnType]"
          :key="index"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-input
        v-if="item.columnType === 'string'"
        v-model.trim="item.value"
        style="width: 200px; display: inline-block"
      ></el-input>
      <el-input
        v-if="item.columnType === 'num'"
        v-model.number="item.value"
        style="width: 200px; display: inline-block"
      ></el-input>
      <el-select v-if="item.columnType === 'dict'" v-model="item.value" placeholder="请选择">
        <el-option
          v-for="(it, index) in dict[item.dictCode]"
          :key="index"
          :label="it.label"
          :value="it.value"
        ></el-option>
      </el-select>
      <template>
        <el-button v-if="item.andOr === 'and'" @click="item.andOr = 'or'">and</el-button>
        <el-button v-if="item.andOr === 'or'" @click="item.andOr = 'and'">or</el-button>
      </template>
      <template v-if="index === conditions.length - 1">
        <el-button @click="addCondition">+</el-button>
      </template>
    </div>

    <h4>动态生成sql：{{ sql }}</h4>
  </div>
</template>

<script>
export default {
  name: 'DemoDynamicCondition2',
  data() {
    return {
      // 查询条件级联树
      columnTree: [
        {
          value: 'role',
          label: '角色',
          children: [
            {
              value: 't1.name',
              label: '角色名称',
              type: 'string',
            },
            {
              value: 't1.order',
              label: '角色排序',
              type: 'num',
            },
          ],
        },
        {
          value: 'menu',
          label: '菜单查询',
          children: [
            {
              value: 't2.name',
              label: '菜单名称',
              type: 'string',
            },
            {
              value: 't2.uri',
              label: '菜单地址',
              type: 'string',
            },
            {
              value: 't2.order',
              label: '菜单排序',
              type: 'num',
            },
            {
              value: 't2.type',
              label: '菜单类型',
              type: 'dict',
              dictCode: 'menuType',
            },
          ],
        },
      ],

      // 数据字典下拉选项
      dict: {
        menuType: [
          {
            label: '目录',
            value: '1',
          },
          {
            label: '菜单',
            value: '2',
          },
          {
            label: '按钮',
            value: '3',
          },
        ],
      },

      // 字段与参数关系
      relations: {
        string: [
          { label: '等于', value: '=' },
          { label: '不等于', value: '!=' },
          { label: '包含', value: 'like' },
          { label: '不包含', value: 'not like' },
        ],
        num: [
          { label: '等于', value: '=' },
          { label: '不等于', value: '!=' },
          { label: '小于', value: '<' },
          { label: '小于等于', value: '<=' },
          { label: '大于', value: '>' },
          { label: '大于等于', value: '>=' },
        ],
        dict: [
          { label: '等于', value: '=' },
          { label: '不等于', value: '!=' },
        ],
      },

      // 默认查询条件数组
      conditions: [
        {
          checked: true,
          columnName: 't1.name',
          columnType: 'string',
          relation: '=',
          value: '管理员',
          andOr: 'and',
        },
        {
          checked: true,
          columnName: 't2.name',
          columnType: 'string',
          relation: 'like',
          value: '管理',
          andOr: 'and',
        },
        {
          checked: true,
          columnName: 't2.uri',
          columnType: 'string',
          relation: '=',
          value: 'sys',
          andOr: '',
        },
      ],
    }
  },
  computed: {
    // 根据查询条件 this.conditions 生成sql
    sql() {
      let sql = ''
      let validConditions = this.conditions.filter(it => it.checked && it.columnName)
      if (validConditions.length === 0) {
        return ''
      }
      validConditions.forEach((it, index) => {
        // 最后一个条件，andOr设置为''
        let andOr = index === validConditions.length - 1 ? '' : it.andOr
        let value = it.columnType === 'num' ? `${it.value}` : `'${it.value}'`
        if (it.relation.includes('like')) {
          value = `'%${it.value}%'`
        }
        sql += ` ${it.columnName} ${it.relation} ${value} ${andOr}`
      })
      return sql
    },
    // 将查询条件树拉平
    columnList() {
      return this.$utils.treeToList(this.columnTree, 'children', 'false')
    },
  },
  methods: {
    // 当选中字段，则清空value，并设置选中的列类型
    onColumnNameChange(item) {
      item.value = ''
      let column = this.columnList.find(it => it.value === item.columnName)
      item.columnType = column.type
      item.dictCode = column.dictCode
    },
    // 添加条件
    addCondition() {
      let lastConditions = this.conditions[this.conditions.length - 1]
      lastConditions.andOr = 'and'
      this.conditions.push({
        checked: true,
        columnName: '',
        columnType: '',
        relation: '=',
        value: '',
        andOr: '',
      })
    },
  },
}
</script>
