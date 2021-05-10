
interface IData<T> {
  body: T
}

export default interface IResponse<T> {
  data: IData<T>
}
