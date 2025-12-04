<template>
  <the-message :message-type="1"></the-message>
</template>

<script>
import TheMessage from '@/views/user-message/common/TheMessage.vue'
export default {
  name: 'MyMessage',
  components: { TheMessage },
  data() {
    return {}
  },
  created() {},
  methods: {},
}
</script>

<style scoped lang="scss"></style>
