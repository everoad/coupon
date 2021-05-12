
interface IData<T> {
  body: T
}

interface IResponse<T> {
  data: IData<T>
}

export {
  IData,
  IResponse
}
