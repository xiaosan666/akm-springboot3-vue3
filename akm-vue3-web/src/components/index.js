import AkmDialog from '@/components/akm-dialog/index.vue'
import AkmSelect from '@/components/akm-select/index.vue'
import AkmSelectDict from '@/components/akm-select-dict/index.vue'
import AkmRadioDict from '@/components/akm-radio-dict/index.vue'
import AkmTable from '@/components/akm-table/index.vue'
import AkmWarnBtn from '@/components/akm-warn-btn/index.vue'
import AkmForm from '@/components/akm-form/index.vue'
import AkmFormView from '@/components/akm-form-view/index.vue'

const Components = {
  AkmDialog,
  AkmSelect,
  AkmSelectDict,
  AkmRadioDict,
  AkmTable,
  AkmWarnBtn,
  AkmForm,
  AkmFormView,
}


// 如果需要在全局注册，可以使用以下方式
export function registerGlobalComponents(app) {
  Object.keys(Components).forEach(name => {
    app.component(name, Components[name])
  })
}
