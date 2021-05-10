const MOBILE_OS = {
  IOS: 'ios',
  AOS: 'aos'
} as const
type MOBILE_OS = typeof MOBILE_OS[keyof typeof MOBILE_OS]

export default MOBILE_OS
