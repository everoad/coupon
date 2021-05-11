
interface Request {
  page: number
  size: number
  sort: string
}

interface Response<T> {
  content: T[]
  totalElements: number
}

export {
  Request,
  Response
}
