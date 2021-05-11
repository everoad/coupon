import Vue from 'vue'
import {
  Button, Card, Container, Header,
  FormItem,
  Input, Checkbox, Select, Option, Dialog, Radio, RadioGroup,
  Table, TableColumn,
  Message, Pagination, Form
} from 'element-ui'

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

Vue.prototype.$message = function (options) {
  if (typeof options === 'string') {
    options = { message: options }
  }
  Message({
    ...options,
    offset: 150,
    customClass: 'el-custom-message'
  })
}
