<template>
  <div class="page-content">
    <div class="error-page">
      <h1>🚧</h1>
      <p>页面开发中</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ThePageInDevelopment',
}
</script>

<style lang="scss" scoped>
.page-content {
  background-color: #f5f5f5;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;

  .error-page {
    h1 {
      font-size: 120px;
      color: var(--el-color-info);
      margin: 0;
    }
    p {
      font-size: 24px;
      color: var(--el-text-color-regular);
      margin: 20px 0;
    }
  }
}
</style>
