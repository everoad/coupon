import Vue from 'vue'

declare module 'vue/types/vue' {
  interface Vue {
    $couponFormatter(code: string): string
    $phoneFormatter(phoneNumber: string): string
  }
}

Vue.prototype.$couponFormatter = function (code: string): string {
  const prefix = code.substring(0, 4)
  const center = code.substring(4, 8)
  const suffix = code.substring(code.length - 4)
  return `${prefix}-${center}-${suffix}`
}

Vue.prototype.$phoneFormatter = function (phoneNumber: string): string {
  const prefix = phoneNumber.substring(0, 3)
  const suffix = phoneNumber.substring(phoneNumber.length - 4)
  const center = phoneNumber.replace(prefix, '').replace(suffix, '')
  return `${prefix}-${center}-${suffix}`
}
