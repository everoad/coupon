<template>
  <div>
    <el-table
      border
      empty-text="데이터가 없습니다."
      :data="data"
      @sort-change="onSortChange"
    >
      <el-table-column
        type="index"
        align="center"
        :index="getIndex"
        label="No"
      />
      <el-table-column
        v-for="column in columns"
        :key="column.prop"
        :prop="column.prop"
        :formatter="column.formatter"
        :label="column.label"
        align="center"
        sortable="custom"
      />
    </el-table>
    <el-pagination
      layout="prev, pager, next"
      :hide-on-single-page="false"
      :page-size="paging.size"
      :current-page="paging.page"
      :total="total"
      @current-change="setPage"
    />
  </div>
</template>

<script lang="ts">
import { Vue, Component, Prop, Watch } from 'nuxt-property-decorator'
import * as ITable from '~/models/ITable'

@Component({
  name: 'base-table',
  watch: {
    '$route.query': '$fetch'
  }
})
export default class BaseTable extends Vue {
  private paging: ITable.Req = {
    page: 1,
    size: 10,
    sort: null
  }

  private total: number = 0
  private data: { [key: string]: any }[] = []

  @Prop() private columns!: ITable.Column[]
  @Prop() private getData!: Function
  // @Prop() private defaultSort!: ITable.Sort

  async fetch () {
    const { page = 1, size = 10 } = this.$route.query
    const params = { page: Number(page), size: Number(size), ...this.paging }
    const { data, total } = await this.getData(params)
    this.data = data
    this.total = total
  }

  @Watch('paging')
  changeUrl () {
    const query = this.getQueryString({ ...this.$route.query, ...this.paging })
    this.$router.push(`${this.$route.path}?${query}`)
  }

  filter (search: any) {
    const query = this.getQueryString({ ...this.$route.query, ...search })
    this.$router.push(`${this.$route.path}?${query}`)
  }

  setPage (page: number) {
    this.paging.page = page
  }

  private onSortChange ({ prop, order }: ITable.Sort) {
    this.paging.sort = { prop, order }
  }

  private getIndex (index: number): number {
    return this.total - this.paging.size * (this.paging.page - 1) - index
  }

  private getQueryString (query: { [key: string]: any }): string {
    return Object.keys(query)
      .filter(key => query[key])
      .map((key) => {
        if (typeof query[key] === 'string') {
          return `${key}=${query[key]}`
        } else {
          return `${key}=${JSON.stringify(query[key])}`
        }
      })
      .join('&')
  }
}
</script>

<style scoped>

</style>
