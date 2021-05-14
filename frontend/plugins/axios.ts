import { Message } from 'element-ui'
import { Context } from '@nuxt/types'
import { IResponse } from '~/models/IResponse'
import { IError } from '~/models/IError'

export default ({ $axios }: Context) => {
  $axios.onError((error) => {
    const { data: { body } } = error.response as IResponse<IError>
    Message(body.messages[0])
  })
}
