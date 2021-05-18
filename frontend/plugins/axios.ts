import { Context } from '@nuxt/types'
import { IError } from '~/models/IError'
import { IResponse } from '~/models/IResponse'

export default ({ $axios }: Context) => {
  $axios.onError((error) => {
    const { data: { body } } = error.response as IResponse<IError>
    return Promise.reject(new Error(body.messages[0]))
  })
}
