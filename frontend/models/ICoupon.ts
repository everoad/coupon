import MOBILE_OS from "./mobile_os"

interface Save {
  phoneNumber: string
  agree: boolean
  mobileOS: MOBILE_OS
}

interface Info {
  code: string
}

interface List {
  num: number
  code: string
  phoneNumber: string
  createdTime: string
  mobileOS: MOBILE_OS
}

export {
  Save,
  Info,
  List
}
