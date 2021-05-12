
export interface Sort {
  prop: string
  order: string
}

export interface Column {
  prop: string
  label: string
  formatter?: Function
}

export interface Req {
  page: number | string
  size: number | string
  sort?: string
  total: number
}

export interface Res<T> {
  content: T[]
  totalElements: number
}
