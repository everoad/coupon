
export interface Sort {
  prop: string
  order: string | null
}

export interface Column {
  prop: string
  label: string
  formatter?: Function
}

export interface Req {
  page: number
  size: number
  sort: Sort | null
}

export interface Res<T> {
  content: T[]
  totalElements: number
}
