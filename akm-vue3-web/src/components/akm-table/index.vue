<template>
  <div class="akm-table-component">
    <div class="toolbar-warp">
      <div v-if="tableConfig.multipleSelect" class="selected-number">
        已选中
        <i>{{ selectedList.length }}</i>
        项
      </div>
      <div class="toolbar">
        <!--自定义列，如操作按钮-->
        <slot name="toolbar"></slot>
      </div>
    </div>
    <el-table
      :id="tableConfig.id"
      :ref="tableConfig.id"
      v-loading="tableConfig.loading"
      class="akm-table"
      border
      highlight-current-row
      :size="tableConfig.size"
      :data="tableConfig.data"
      :row-key="tableConfig.rowKey"
      :max-height="autoHeight || tableConfig.height"
      @selection-change="onCheckboxChange"
      @row-click="onRowClick"
      @sort-change="onSortChange"
    >
      <!--多选checkbox-->
      <el-table-column
        v-if="tableConfig.multipleSelect"
        type="selection"
        width="50"
        :selectable="checkboxDisable"
      ></el-table-column>

      <!--单选radio-->
      <el-table-column v-if="tableConfig.singleSelect" width="40">
        <template #default="scope">
          <el-radio
            v-model="radioValue"
            :label="scope.row[tableConfig.rowKey]"
            :disabled="scope.row._disabled"
            class="radio-box"
            @change="onRadioChange"
          ></el-radio>
        </template>
      </el-table-column>

      <el-table-column
        v-if="!(tableConfig.hideIndexColumn === true)"
        type="index"
        label="序号"
        width="60"
        :index="computedIndex"
      ></el-table-column>

      <!--列-->
      <template
        v-for="(item, index) in tableConfig.columns.filter(i => i.hide !== true)"
        :key="index"
      >
        <el-table-column
          v-if="item.type !== 'slot'"
          :prop="item.prop"
          :label="item.label"
          :width="item.width"
          :fixed="item.fixed"
          :show-overflow-tooltip="item.showOverflowTooltip"
          :sortable="item.sortable"
          :formatter="item.formatter"
          :class-name="item.className"
          :align="item.align"
        ></el-table-column>
        <el-table-column
          v-if="item.type === 'slot'"
          :prop="item.prop"
          :label="item.label"
          :width="item.width ? item.width : item.prop === 'op' && opColumnWidth"
          :fixed="item.fixed"
          :show-overflow-tooltip="item.showOverflowTooltip"
          :sortable="item.sortable"
          :formatter="item.formatter"
          :class-name="item.prop === 'op' ? 'op-buttons' : item.className"
          :align="item.align"
        >
          <template #default="scope">
            <slot :name="'column-' + item.prop" :row="scope.row" />
          </template>
        </el-table-column>
      </template>

      <!--自定义内容，如操作列-->
      <slot></slot>
    </el-table>
    <el-pagination
      v-if="tableConfig.pagination"
      v-text-right
      :current-page="tableConfig.pageNum"
      :page-size="tableConfig.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableConfig.total"
      @size-change="onPageSizeChange"
      @current-change="onPageNumChange"
    ></el-pagination>
  </div>
</template>

<script>
export default {
  name: 'AkmTable',
  props: {
    config: {
      type: Object,
      required: true,
      default: () => {},
    },
    data: {
      type: Array,
      required: false,
    },
  },
  data() {
    return {
      selectedList: [],
      radioValue: '', // 当singleSelect为true有效
      autoHeight: '',
      defaultConfig: {
        id: 'akmTable', // 默认为'akmTable'，当页面出现多个表格，务必指定不同的id，
        uri: '', // 指定uri则可以自动发起请求
        data: [], // 表格数据
        loading: false, // 是否显示loading
        height: 'auto', // 表格高度
        autoComputedHeight: false, // 是否自动计算表格高度，设为true则当页面出现滚动条时，自动把滚动条加到表格上，表格最大高度将填充页面
        multipleSelect: false, // 是否显示checkbox复选框
        singleSelect: false, // 是否显示radio单选框
        hideIndexColumn: false, // 是否隐藏序号列，默认显示
        checkOnRowClick: false, // 如果设置为 true，当用户点击某一行时，则会选中/取消选中该行，若有复选框则会选中/取消勾线。
        pagination: false, // 是否显示分页组件
        pageNum: 1, // 当前页码，当pagination为true有效
        pageSize: 10, // 每页大小，当pagination为true有效
        total: 0, // 总数，当pagination为true有效
        rowKey: 'id', // 行数据的 Key，用来优化 Table 的渲染，默认为id
        size: 'default',
        // 表格列
        columns: [
          /*
                这里是demo
                {
                  prop: 'id',
                  primaryKey: true,
                  hidden: true
                },
                {
                  prop: 'name',
                  label: '名称'，
                  width: 200,
                  showOverflowTooltip: false,
                },
                {
                  prop: 'time',
                  label: '时间',
                  width: 200,
                  formatter: (row, column, cellValue, home) => {
                    return this.$utils.formatDate(cellValue, 'yyyy-MM-dd HH:mm')
                  }
                } */
        ],
      },
      condition: {}, // 查询条件

      opColumnWidth: '', // 操作列宽度
    }
  },
  computed: {
    tableConfig() {
      let config = { ...this.defaultConfig, ...this.config }
      if (this.data) {
        config.data = this.data
      }
      return config
    },
  },
  watch: {
    'tableConfig.data'(list) {
      this.computedTableColumnWidth()
      if (list && list.length !== 0) {
        // 监听table data变化，根据数据变化计算合适的高度，使表格出现滚动条，而不是页面出现滚动条
        if (this.config.autoComputedHeight === true) {
          let table = document.getElementById(this.tableConfig.id)
          if (table) {
            // 延迟等待页面数据渲染完成后计算
            setTimeout(() => {
              this.computedTableHeight(false)
            }, 0)
          }
        }
        // 校验数据必须包含rowKey属性
        if (!list[0][this.tableConfig.rowKey]) {
          window.DEBUG && alert('请指定数据表格"rowKey"属性, 默认值为id')
        }
      }
    },
  },
  created() {
    if (this.tableConfig.autoComputedHeight) {
      this.$eventBus.off(this.$eventBus.keys.heightChange)
      this.$eventBus.on(this.$eventBus.keys.heightChange, () => {
        this.computedTableHeight(true)
      })
    }
  },
  methods: {
    async fetchTableData(condition) {
      const config = this.tableConfig
      if (!config.uri) {
        return
      }
      if (condition) {
        this.condition = condition
      }
      this.config.loading = true
      let body = this.condition
      if (config.pagination) {
        body = {
          pageSize: config.pageSize,
          pageNum: config.pageNum,
          condition: body,
        }
      }

      try {
        const res = await this.$http.post(this.config.uri, body)
        if (this.config.pagination) {
          this.config.data = res.list
          this.config.total = res.total
        } else {
          this.config.data = res
        }
      } catch (error) {
        console.error('获取表格数据失败:', error)
      } finally {
        // this.defaultConfig.loading = false
        this.config.loading = false
      }
    },
    // 获取选中的数据
    getSelected() {
      return this.selectedList
    },
    // 清空选中的数据
    clearSelected() {
      this.$refs[this.tableConfig.id].clearSelection()
      this.$refs[this.tableConfig.id].setCurrentRow(null)
      this.radioValue = ''
      this.selectedList = []
    },
    onCheckboxChange(selectedList) {
      this.selectedList = selectedList
      this.$emit('selectedChange', selectedList)
    },
    onRadioChange() {
      let selectedList = []
      if (this.radioValue) {
        selectedList = this.tableConfig.data.filter(
          item => item[this.tableConfig.rowKey] === this.radioValue
        )
      }
      this.selectedList = selectedList
      this.$emit('selectedChange', selectedList)
    },
    onRowClick(row) {
      // 判断是否需要选中
      if (!row._disabled && this.tableConfig.checkOnRowClick) {
        // 单选情况
        if (this.tableConfig.singleSelect) {
          this.radioValue = row[this.tableConfig.rowKey]
          this.selectedList = [row]
          this.$emit('selectedChange', this.selectedList)
        } else {
          // 多选情况，使用toggleRowSelection后会调用onCheckboxChange方法
          this.$refs[this.tableConfig.id].toggleRowSelection(row)
        }
      }
      this.$emit('row-click', row)
    },
    onSortChange(sortData) {
      this.$emit('sort-change', sortData)
    },
    onPageSizeChange(pageSize) {
      this.clearSelected()
      this.$emit('pageSizeChange', pageSize)
      if (this.config.uri) {
        this.config.pageSize = pageSize
        this.fetchTableData(this.condition)
      }
    },
    onPageNumChange(pageNum) {
      this.clearSelected()
      this.$emit('pageNumChange', pageNum)
      if (this.config.uri) {
        this.config.pageNum = pageNum
        this.defaultConfig.pageNum = pageNum
        this.fetchTableData(this.condition)
      }
    },
    // 计算table序号列
    computedIndex(index) {
      if (this.tableConfig.pagination) {
        let pageNum = this.tableConfig.pageNum
        let pageSize = this.tableConfig.pageSize
        return index + 1 + (pageNum - 1) * pageSize
      }
      return index + 1
    },
    // 表格复选框是否禁用状态
    checkboxDisable(row) {
      return !row._disabled
    },
    computedTableHeight(pageHeightChange) {
      let height = this.$helper.computedTableHeight(
        this.tableConfig.id,
        'akmMain',
        pageHeightChange
      )
      if (height) {
        this.autoHeight = height
      }
    },
    /**
     * 计算表格操作列宽度
     * @param tableRef
     * @param colName
     */
    computedTableColumnWidth(tableRef = 'akmTable', colName = '操作') {
      setTimeout(() => {
        let table = document.getElementById(tableRef)
        if (!table) return
        let ths = table.querySelectorAll('.el-table__header-wrapper thead th')
        // 操作列位置
        let opIndex
        for (let i = 0; i < ths.length; i++) {
          let th = ths[i]
          if (th.classList.contains('op-buttons') || th.innerHTML.includes(colName)) {
            opIndex = i
          }
        }
        if (opIndex === undefined) return
        // 获取所有数据行，找出每行操作列最宽的
        let trs = table.querySelectorAll('.el-table__body-wrapper tbody tr')
        if (trs.length === 0) return
        let opColumnWidth = 0
        trs.forEach(tr => {
          let tds = tr.childNodes
          let td = tds[opIndex]
          let cell = td.querySelector('.cell')
          let childNodes = cell.childNodes

          if (!childNodes || childNodes.length === 0) {
            return
          }
          let width = 0
          childNodes.forEach(node => {
            // 输出8，注释节点
            if (node.nodeType !== 8) {
              width += node.offsetWidth
            }
          })
          // *10是因为每个按钮都有margin 10
          width = width === 0 ? 0 : width + (childNodes.length + 1) * 10
          opColumnWidth = Math.max(width, opColumnWidth)
        })
        this.opColumnWidth = opColumnWidth
      }, 0)
    },
  },
}
</script>

<style lang="scss" scoped>
.akm-table-component {
  .toolbar-warp {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .toolbar {
      padding-right: 12px;
      padding-bottom: 2px;
      width: 100%;
      text-align: right;
    }
    .selected-number {
      color: var(--el-text-color-regular);
      width: 120px;
      i {
        font-style: normal;
        font-size: 16px;
        color: var(--el-text-color-primary);
        padding: 0 2px;
      }
    }
  }
}
</style>

<style lang="scss">
.akm-table-component {
  :deep(.radio-box) {
    .el-radio__label {
      display: none;
    }
  }
}
</style>
