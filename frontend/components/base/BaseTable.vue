<template>
  <div>
    <el-table
      ref="elTableRef"
      border
      empty-text="데이터가 없습니다."
      :data="data"
      :default-sort="paging.sort"
      @sort-change="onSortChange"
    >
      <el-table-column
        v-if="index"
        type="index"
        align="center"
        label="No"
        :index="getIndex"
      />
      <slot />
    </el-table>
    <el-pagination
      layout="prev, pager, next"
      :hide-on-single-page="false"
      :page-size="paging.size"
      :current-page="paging.page"
      :total="total"
      @current-change="onPageChange"
    />
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop, Ref, Watch } from 'nuxt-property-decorator'
import { Table } from 'element-ui'
import * as ITable from '~/models/ITable'

@Component({
  name: 'base-table'
})
export default class BaseTable extends Vue {
  private paging: ITable.Req = {
    page: 1,
    size: 10,
    sort: null
  }

  private total: number = 0
  private data: { [key: string]: any }[] = []
  private search: { [key: string]: any } = {}

  @Ref() private elTableRef!: Table
  @Prop({ default: false }) private index!: boolean
  @Prop() private defaultSort!: ITable.Sort
  @Prop() private getData!: Function
  @Prop() private setData!: Function

  private created () {
    const { page, size, sort, search } = this.getQuery()
    this.paging.page = page
    this.paging.size = size
    this.paging.sort = sort
    this.search = search
    this.setData(search)
  }

  private async fetch () {
    await this.callAPI()
  }

  @Watch('$route.query')
  private async watchQueryString (newQuery: { [key: string]: any }, oldQuery: { [key: string]: any }) {
    const { page, size, sort, search } = this.getQuery()
    this.paging.page = page
    this.paging.size = size
    this.paging.sort = sort
    if (!this.$objectEquals(this.search, search)) {
      this.search = search
      this.setData(search)
    }
    if (newQuery?.sort !== oldQuery?.sort) {
      this.elTableRef.sort(sort.prop, sort.order)
    }
    await this.callAPI()
  }

  private async callAPI () {
    const { data, total } = await this.getData({ ...this.paging, ...this.search })
    this.data = data
    this.total = total
  }

  private onPageChange (page: number) {
    this.changeUrl({ page })
  }

  private onSortChange ({ prop, order }: ITable.Sort) {
    const { elTableRef, paging: { sort } } = this
    if (sort?.prop === prop) {
      if (order === null) {
        elTableRef.sort(prop, sort.order === 'ascending' ? 'descending' : 'ascending')
        return
      }
      if (sort.order === order) {
        return
      }
    }
    this.changeUrl({ sort: { prop, order } })
  }

  private changeUrl (params: Object) {
    const tempQuery = { ...this.paging, ...this.search, ...params } as { [key: string]: any }
    tempQuery.sort = JSON.stringify(tempQuery.sort)
    const query = {} as { [key: string]: any }
    Object.keys(tempQuery).filter(key => tempQuery[key]).forEach((key: string) => {
      query[key] = tempQuery[key]
    })
    this.$router.push({ path: this.$route.path, query })
  }

  private getQuery (): any {
    const { page, size, sort, ...search } = this.$route.query
    return {
      page: (page && Number(page)) || 1,
      size: (size && Number(size)) || 10,
      sort: (sort && JSON.parse(String(sort))) || this.defaultSort,
      search
    }
  }

  private getIndex (index: number): number {
    return this.total - this.paging.size * (this.paging.page - 1) - index
  }

  filter (search: { [key: string]: any } = {}) {
    this.changeUrl({ page: 1, ...search })
  }
}
</script>
<style scoped>
.el-pagination {
  text-align: right;
}
</style>
