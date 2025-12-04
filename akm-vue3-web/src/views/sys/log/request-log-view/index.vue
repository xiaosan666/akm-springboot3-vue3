<template>
  <div>
    <akm-form-view :config="formConfig"></akm-form-view>
  </div>
</template>

<script>
export default {
  name: 'RequestLogView',
  data() {
    return {
      formConfig: {
        loading: false,
        labelWidth: 150,
        data: {},
        items: [
          { prop: 'id', label: 'id' },
          { prop: 'requestId', label: '请求id' },
          { prop: 'apiDescription', label: '接口名称' },
          { prop: 'uri', label: 'uri' },
          { prop: 'method', label: 'method' },
          { prop: 'code', label: 'code' },
          { prop: 'message', label: 'message' },
          { prop: 'parameter', label: '请求入参' },
          { prop: 'result', label: '响应结果' },
          { prop: 'success', label: 'success' },
          { prop: 'startTime', label: '请求时间', type: 'datetime' },
          { prop: 'spendTime', label: '请求消耗时间' },
          { prop: 'ip', label: 'IP地址' },
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
          { type: 'datetime', prop: 'createTime', label: '创建时间' },
          { type: 'datetime', prop: 'updateTime', label: '更新时间' },
        ],
      },
    }
  },
  created() {
    this.formConfig.loading = true
    this.$http
      .post('/auth/sys/log/view/request/detail', this.$route.params.id)
      .then(res => {
        this.formConfig.data = res
      })
      .finally(() => {
        this.formConfig.loading = false
      })
  },
}
</script>

<style scoped></style>
