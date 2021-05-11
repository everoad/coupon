<template>
  <main>
    <div class="page-container">
      <header>
        쿠폰 목록
      </header>
      <section>
        <el-form
          :inline="true"
          size="small"
          label-width="85px"
        >
          <el-form-item label="쿠폰번호">
            <el-input
              v-model="search.code"
              maxlength="12"
            />
          </el-form-item>
          <el-form-item label="휴대폰번호">
            <el-input
              v-model="search.phoneNumber"
              maxlength="11"
            />
          </el-form-item>
          <el-form-item label="휴대폰 OS">
            <el-select
              v-model="search.mobileOS"
              placeholder="선택"
            >
              <el-option
                label="선택"
                value
              />
              <el-option
                v-for="os in mobileOSList"
                :key="os"
                :label="os"
                :value="os"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="info"
            >
              검색
            </el-button>
          </el-form-item>
        </el-form>
      </section>
      <section>
        <el-table
          v-loading="loading"
          border
          empty-text="데이터가 없습니다."
          :default-sort="defaultSort"
          :data="couponList"
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
          :page-size="size"
          :current-page="page"
          :total="totalCount"
          @current-change="setPage"
        />
      </section>
    </div>
  </main>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import IResponse from '~/models/IResponse'
import * as ICoupon from '~/models/ICoupon'
import * as IPage from '~/models/IPage'
import IColumn from '~/models/IColumn'
import MOBILE_OS from '~/models/mobile_os'
import ISort from '~/models/ISort'

@Component
export default class Coupon extends Vue {
  private couponList: ICoupon.List[] = []
  private totalCount: number = 0
  private size: number = 0
  private page: number = 0
  private loading: boolean = false
  private defaultSort: ISort = { prop: 'createdTime', order: 'descending' }
  private search: ICoupon.Search = { code: '', phoneNumber: '', mobileOS: undefined }
  private columns: Array<IColumn> = [
    {
      prop: 'code',
      label: '쿠폰번호',
      formatter: this.couponCodeFormatter
    },
    {
      prop: 'phoneNumber',
      label: '휴대폰번호',
      formatter: this.phoneNumberFormatter
    },
    {
      prop: 'mobileOS',
      label: '휴대폰 OS'
    },
    {
      prop: 'createdTime',
      label: '신청일시'
    }
  ]

  async asyncData ({ $axios, query: { size = 10, page = 1, sort } }: any) {
    const params: IPage.Request = { size: Number(size), page: Number(page), sort }
    const res: IResponse<IPage.Response<ICoupon.List>> = await $axios.get('/api/coupons', { params })
    const { content, totalElements } = res.data.body
    return {
      couponList: content,
      totalCount: totalElements,
      ...params
    }
  }

  watchQuery (newQuery: any, oldQuery: any) {
    return newQuery.page !== oldQuery.page ||
      newQuery.size !== oldQuery.size ||
      newQuery.sort !== oldQuery.sort
  }

  get mobileOSList (): string[] {
    return Object.keys(MOBILE_OS)
  }

  setPage (page: number) {
    const query = this.getQueryString({ ...this.$route.query, page, size: this.size })
    this.$router.push(`${this.$route.path}?${query}`)
  }

  onSortChange ({ prop, order }: any) {
    const sort = order ? JSON.stringify({ prop, order }) : null
    const query = this.getQueryString({ ...this.$route.query, sort })
    this.$router.push(`${this.$route.path}?${query}`)
  }

  getIndex (index: number): number {
    return this.totalCount - this.size * (this.page - 1) - index
  }

  getQueryString (query: { [key: string]: any }): string {
    return Object.keys(query)
      .filter(key => query[key])
      .map(key => `${key}=${query[key]}`)
      .join('&')
  }

  phoneNumberFormatter (row: ICoupon.List) {
    const phoneNumber = row.phoneNumber
    const prefix = phoneNumber.substring(0, 3)
    const suffix = phoneNumber.substring(phoneNumber.length - 4)
    const center = phoneNumber.replace(prefix, '').replace(suffix, '')
    return `${prefix}-${center}-${suffix}`
  }

  couponCodeFormatter (row: ICoupon.List) {
    const code = row.code
    const prefix = code.substring(0, 4)
    const center = code.substring(4, 8)
    const suffix = code.substring(code.length - 4)
    return `${prefix}-${center}-${suffix}`
  }
}
</script>

<style lang="scss" scoped>
header {
  font-size: 1.2rem;
  font-weight: 600;
  width: 100%;
  padding: 0.5rem;
  color: #555;
  border-bottom: 2px solid #dcdfe6;
  margin-bottom: 2rem;
}
.page-container {
  margin: 2rem auto 4rem;
  max-width: 1140px;
  .search > form {
    display: flex;
  }
  section {
    margin-bottom: 1rem;
  }
  form > div:last-child {
    float: right;
  }
  .el-pagination {
    text-align: right;
  }
}
</style>
