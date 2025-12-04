import { usePermissionStore } from '@/stores/permission'

export const has = {
  mounted: function (el, binding) {
    const permissionStore = usePermissionStore()
    let codeList = permissionStore.permissionMenu.buttonCodeList
    let data = binding.value

    // 数组取或者关系，即包含任何一个权限code都算拥有权限
    if (Array.isArray(data)) {
      let has = data.some(item => codeList.includes(item))
      if (!has) {
        el.remove()
      }
    } else {
      if (!data || !codeList.includes(data)) {
        el.remove()
      }
    }
  },
}
