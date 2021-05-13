import Vue from 'vue'

declare module 'vue/types/vue' {
  interface Vue {
    $couponFormatter(code: string): string
    $phoneFormatter(phoneNumber: string): string
    $objectEquals(obj1: Object, obj2: Object): boolean
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

Vue.prototype.$objectEquals = function (obj1: Object, obj2: Object): boolean {
  const entries1 = Object.entries(obj1)
  const entries2 = Object.entries(obj2)
  if (entries1.length !== entries2.length) {
    return false
  }
  for (let i = 0; i < entries1.length; ++i) {
    if (entries1[i][0] !== entries2[i][0]) {
      return false
    }
    if (entries1[i][1] !== entries2[i][1]) {
      return false
    }
  }
  return true
}
