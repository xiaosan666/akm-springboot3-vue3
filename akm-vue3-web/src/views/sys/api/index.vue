<template>
  <el-tabs v-model="activeName">
    <el-tab-pane name="first">
      <template #label>
        <span style="font-weight: bold; font-size: 15px">
          <el-icon><Link /></el-icon>
          <span v-margin-left="4">Swagger自动生成接口</span>
        </span>
      </template>
      <the-swagger-api></the-swagger-api>
    </el-tab-pane>
    <el-tab-pane name="second">
      <template #label>
        <span style="font-weight: bold; font-size: 15px">
          <el-icon><Plus /></el-icon>
          <span v-margin-left="4">手动添加接口</span>
        </span>
      </template>
      <the-manual-add></the-manual-add>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import TheSwaggerApi from '@/views/sys/api/TheSwaggerApi.vue'
import TheManualAdd from '@/views/sys/api/TheManualAdd.vue'

export default {
  name: 'SysApi',
  components: { TheManualAdd, TheSwaggerApi },
  data() {
    return {
      activeName: 'first',
    }
  },
}
</script>
