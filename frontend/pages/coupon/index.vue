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
              placeholder="'-' 제외"
              @keydown.enter.native.prevent="onSubmit"
            />
          </el-form-item>
          <el-form-item label="휴대폰번호">
            <el-input
              v-model="search.phoneNumber"
              maxlength="11"
              placeholder="'-' 제외"
              @keydown.enter.native.prevent="onSubmit"
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
              @click.prevent="onSubmit"
            >
              검색
            </el-button>
          </el-form-item>
        </el-form>
      </section>
      <section>
        <base-table
          ref="tableRef"
          :index="true"
          :default-sort="defaultSort"
          :get-data="getData"
          :set-data="setData"
        >
          <el-table-column
            v-for="column in columns"
            :key="column.prop"
            :prop="column.prop"
            :formatter="column.formatter"
            :label="column.label"
            align="center"
            sortable="custom"
          />
        </base-table>
      </section>
    </div>
  </main>
</template>

<script lang="ts">
import { Vue, Component, Ref } from 'nuxt-property-decorator'
import * as ICoupon from '~/models/ICoupon'
import { IResponse } from '~/models/IResponse'
import * as ITable from '~/models/ITable'
import MOBILE_OS from '~/models/mobile_os'
import { BaseTable } from '~/components/base'

@Component
export default class Coupon extends Vue {
  @Ref() private tableRef!: BaseTable

  private search: ICoupon.Search = {
    code: '',
    phoneNumber: '',
    mobileOS: undefined
  }

  private defaultSort: ITable.Sort = {
    prop: 'createdTime',
    order: 'descending'
  }

  private columns: Array<ITable.Column> = [
    {
      prop: 'code',
      label: '쿠폰번호',
      formatter: (row: ICoupon.List): string => {
        return this.$couponFormatter(row.code)
      }
    },
    {
      prop: 'phoneNumber',
      label: '휴대폰번호',
      formatter: (row: ICoupon.List): string => {
        return this.$phoneFormatter(row.phoneNumber)
      }
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

  get mobileOSList (): string[] {
    return Object.keys(MOBILE_OS)
  }

  async getData (params: { [key: string]: any }) {
    const res: IResponse<ITable.Res<ICoupon.List>> = await this.$axios.get('/api/coupons', { params })
    const { content, totalElements } = res.data.body
    return {
      data: content,
      total: totalElements
    }
  }

  setData (search: ICoupon.Search) {
    this.search = { ...search }
  }

  onSubmit () {
    this.tableRef.filter({ ...this.search })
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
