<template>
  <div>
    <akm-form-view :config="formConfig">
      <template #form-exceptionContent>
        <div v-for="(item, index) in exceptionContentArr" :key="index" style="line-height: 24px">
          {{ item }}
        </div>
      </template>
    </akm-form-view>
  </div>
</template>

<script>
export default {
  name: 'ExceptionLogView',
  data() {
    return {
      formConfig: {
        loading: false,
        labelWidth: 150,
        data: {},
        items: [
          { prop: 'id', label: 'id' },
          { prop: 'requestId', label: '请求id' },
          { prop: 'apiDescription', label: '请求描述' },
          { prop: 'uri', label: 'uri' },
          { prop: 'method', label: '请求类型' },
          { prop: 'parameter', label: '请求入参' },
          { prop: 'result', label: '响应结果' },
          { prop: 'startTime', label: '请求时间', type: 'datetime' },
          { prop: 'spendTime', label: '请求消耗时间' },
          { prop: 'ip', label: 'IP地址' },
          { prop: 'url', label: 'URL' },
          { prop: 'userAgent', label: '浏览器标识' },
          { prop: 'userId', label: '用户id' },
          { prop: 'username', label: '用户名' },
          { prop: 'name', label: '用户姓名' },
          {
            type: 'dict',
            code: 'client_type',
            prop: 'clientType',
            label: '应用类型',
          },
          {
            type: 'dict',
            code: 'exception_type',
            prop: 'type',
            label: '异常类型',
          },
          {
            type: 'slot',
            prop: 'exceptionContent',
            label: '异常堆栈信息',
          },
          { type: 'datetime', prop: 'createTime', label: '创建时间' },
          { type: 'datetime', prop: 'updateTime', label: '更新时间' },
        ],
      },
      exceptionContentArr: '',
    }
  },
  created() {
    this.formConfig.loading = true
    this.$http
      .post('/auth/sys/log/view/exception/detail', this.$route.params.id)
      .then(res => {
        this.formConfig.data = res
        let exceptionContent = res.content || ''
        this.exceptionContentArr = exceptionContent.split('at ')
      })
      .finally(() => {
        this.formConfig.loading = false
      })
  },
}
</script>

<style scoped></style>
