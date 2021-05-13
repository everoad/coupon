import { Message } from 'element-ui'

export default ({ $axios }) => {
  $axios.onError((error) => {
    const { status, data: { body } } = error.response
    Message(body.messages[0])
    return true
  })
}
