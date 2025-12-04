<template>
  <div>
    <demo-dynamic-condition1></demo-dynamic-condition1>
    <demo-dynamic-condition2></demo-dynamic-condition2>
  </div>
</template>

<script>
import DemoDynamicCondition1 from '@/views/demo/dynamic-condition/TheDemo1.vue'
import DemoDynamicCondition2 from '@/views/demo/dynamic-condition/TheDemo2.vue'

export default {
  name: 'DynamicCondition',
  components: { DemoDynamicCondition2, DemoDynamicCondition1 },
}
</script>

<style scoped></style>
