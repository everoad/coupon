import { Message } from 'element-ui'

export default ({ $axios }) => {
  $axios.onError((error) => {
    const { data: { body } } = error.response
    Message(body.messages[0])
  })
}
