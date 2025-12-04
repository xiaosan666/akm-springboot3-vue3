<script setup>
// import TheWelcome from '../components/TheWelcome.vue'
</script>

<template>
  <main>
    <el-button type="primary" size="default">default</el-button>
    <el-button type="primary" size="small">small</el-button>
    <el-button type="primary" size="large">large</el-button>
    111
    <el-icon :size="22"><Search /></el-icon>
    <el-icon color="red"><Edit /></el-icon>
    <Share style="width: 2em; margin-right: 8px" />
    <!-- <TheWelcome /> -->
  </main>
</template>
