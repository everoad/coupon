const MOBILE_OS = {
  IOS: 'ios',
  AOS: 'aos'
} as const
// eslint-disable-next-line no-redeclare
type MOBILE_OS = typeof MOBILE_OS[keyof typeof MOBILE_OS]

export default MOBILE_OS
