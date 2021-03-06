import Vue from 'vue'
import {
  Button, Container, Header,
  FormItem, Form,
  Input, Checkbox, Select, Option, Radio, RadioGroup,
  Table, TableColumn, Pagination,
  Message, Dialog
} from 'element-ui'
import { ElMessageOptions } from 'element-ui/types/message'

import lang from 'element-ui/lib/locale/lang/ko'
import locale from 'element-ui/lib/locale'

locale.use(lang)

Vue.component(Header.name, Header)
Vue.component(Container.name, Container)

Vue.component(Form.name, Form)
Vue.component(FormItem.name, FormItem)
Vue.component(Input.name, Input)
Vue.component(Select.name, Select)
Vue.component(Option.name, Option)
Vue.component(Checkbox.name, Checkbox)
Vue.component(Radio.name, Radio)
Vue.component(RadioGroup.name, RadioGroup)

Vue.component(Button.name, Button)
Vue.component(Dialog.name, Dialog)

Vue.component(Table.name, Table)
Vue.component(TableColumn.name, TableColumn)
Vue.component(Pagination.name, Pagination)

Vue.prototype.$message = function (options: string | ElMessageOptions) {
  if (typeof options === 'string') {
    options = { message: options }
  }
  Message({
    ...options,
    offset: 150,
    customClass: 'el-custom-message'
  } as ElMessageOptions)
}
