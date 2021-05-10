import Vue from 'vue'
import {
  Button, Card, Container, Header,
  Input, Checkbox, Dialog, Radio, RadioGroup, Message
} from 'element-ui'

Vue.component(Button.name, Button)
Vue.component(Card.name, Card)
Vue.component(Container.name, Container)
Vue.component(Header.name, Header)
Vue.component(Input.name, Input)
Vue.component(Checkbox.name, Checkbox)
Vue.component(Dialog.name, Dialog)
Vue.component(Radio.name, Radio)
Vue.component(RadioGroup.name, RadioGroup)


Vue.prototype.$message = function (options) {
  if (typeof options === 'string') {
    options = {message: options}
  }
  Message({
    ...options,
    offset: 150,
    customClass: 'el-custom-message'
  })
}
