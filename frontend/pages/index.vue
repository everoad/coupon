<template>
  <main>
    <div class="bg-video">
      <video autoplay loop muted>
        <source src="/video/video_main.mp4" type="video/mp4">
      </video>
    </div>
    <el-header>
      <div class="logo" />
    </el-header>
    <el-container direction="column">
      <section v-in-viewport.once class="index viewport-target">
        <div class="season" />
        <div class="logo" />
        <div class="subtitle" />
        <div class="message" />
        <div class="scroll" />
      </section>
      <section class="event1">
        <div v-in-viewport.once class="title viewport-target" />
        <div v-in-viewport.once class="message viewport-target">
          <br>
          <p>로스트아크 시즌2 사전등록을 신청하세요.</p>
          <p>휴대폰 번호를 입력하시고, <span class="text-gradient">사전등록 쿠폰을 받아가세요!</span></p>
          <br>
          <p><span class="text-gradient">사전등록 기간 : 2021.05.12(수) ~ 06.30(수) 점검 전까지</span></p>
        </div>
        <div v-in-viewport.once class="form viewport-target">
          <div class="form-group">
            <form @submit.prevent="onSubmit">
              <div class="os">
                <el-radio-group v-model="couponSave.mobileOS" fill="#af8350">
                  <el-radio label="aos">
                    <div class="aos" />
                  </el-radio>
                  <el-radio label="ios">
                    <div class="ios" />
                  </el-radio>
                </el-radio-group>
              </div>
              <el-input
                ref="phoneInput"
                size="large"
                maxlength="11"
                class="text-center input-with-select btn-gold"
                placeholder="'-'를 제외 한 휴대폰 번호를 입력해 주세요"
                :value="couponSave.phoneNumber"
                @input="onPhoneNumberInput"
              >
                <el-button
                  slot="append"
                  native-type="submit"
                  :loading="loading"
                >
                  <span>신청하기</span>
                </el-button>
              </el-input>
              <div class="privacy">
                <el-checkbox
                  v-model="couponSave.agree"
                  class="checkbox-lg"
                  size="medium"
                  label="개인정보 수집 및 이용 동의"
                />
                <el-button
                  type="info"
                  size="mini"
                  @click="dialogVisible = true"
                >
                  자세히 보기
                </el-button>
              </div>
            </form>
            <div v-if="couponInfo.code" class="coupon">
              <div>쿠폰번호</div>
              <div ref="couponCode">
                {{ couponCode }}
              </div>
              <el-button
                type="text"
                @click="onCopyClipboardBtnClick"
              >
                <span class="text-copy">복사하기</span>
              </el-button>
            </div>
          </div>
        </div>
        <div v-in-viewport.once class="gift viewport-target" />
        <div v-in-viewport.once class="guide viewport-target" />
      </section>
      <section class="event2">
        <div v-in-viewport.once class="title viewport-target" />
        <div v-in-viewport.once class="subtitle viewport-target" />
        <div v-in-viewport.once class="reward-wrapper viewport-target">
          <div class="reward1" />
          <div class="reward2" />
          <div class="reward3" />
        </div>
        <div v-in-viewport.once class="message viewport-target" />
      </section>
    </el-container>
    <el-dialog
      title="개인정보 수집 및 이용 동의 안내"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <div class="privacy-content">
        <ol>
          <li>
            <strong>개인정보 수집 및 이용 목적</strong>
            <ul>
              <li>로스트아크 사전등록</li>
              <li>로스트아크 사전예약 등록 여부 및 쿠폰 번호 확인</li>
            </ul>
          </li>
          <li>
            <strong>개인정보 수집 항목</strong>
            <ul>
              <li>휴대전화번호</li>
            </ul>
          </li>
          <li>
            <strong>개인정보 보유 및 이용 기간</strong>
            <ul>
              <li>동의일로부터 1년 경과 후 삭제</li>
            </ul>
          </li>
        </ol>
        <p>
          귀하는 개인정보 수집 및 이용에 대한 동의를 거부하실 수 있으며, 동의 거부 시
          사전예약에 참여하실 수 없습니다.
        </p>
      </div>
    </el-dialog>
  </main>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import { Input } from 'element-ui'
import * as ICoupon from '~/models/ICoupon'
import { IResponse } from '~/models/IResponse'
import MOBILE_OS from '~/models/mobile_os'

@Component
export default class Index extends Vue {
  private couponSave: ICoupon.Save = { phoneNumber: '', agree: false, mobileOS: MOBILE_OS.AOS }
  private couponInfo: ICoupon.Info = { code: '' }
  private dialogVisible: boolean = false
  private loading: boolean = false

  get couponCode (): string {
    return this.$couponFormatter(this.couponInfo.code)
  }

  onPhoneNumberInput (value: string) {
    if (this.isNotInteger(value)) {
      value = value.substring(0, value.length - 1)
    }
    this.couponSave.phoneNumber = value
  }

  async onSubmit () {
    if (this.checkForm()) {
      return
    }
    this.loading = true
    const res: IResponse<ICoupon.Info> = await this.$axios.post('/api/coupons', this.couponSave)
    this.couponInfo = res.data.body
    this.loading = false
  }

  checkForm (): boolean {
    const { phoneNumber, agree, mobileOS } = this.couponSave
    if (!phoneNumber) {
      (this.$refs.phoneInput as Input).focus()
      alert('휴대폰 번호를 입력해 주세요.')
      return true
    }
    if (phoneNumber.length < 10 || this.isNotInteger(phoneNumber)) {
      (this.$refs.phoneInput as Input).focus()
      alert('휴대폰 번호를 확인해 주세요.')
      return true
    }
    if (!agree) {
      alert('개인정보 수집 및 이용 동의를 해주세요.')
      return true
    }
    if (!mobileOS) {
      alert('사용하시는 핸드폰 OS를 선택해 주세요.')
      return true
    }
    return false
  }

  isNotInteger (value: string): boolean {
    return isNaN(value as any) || value.lastIndexOf('.') > -1
  }

  onCopyClipboardBtnClick () {
    const couponCodeElement = this.$refs.couponCode as HTMLSpanElement
    const range = document.createRange() as Range
    const select = window.getSelection() as Selection
    range.selectNode(couponCodeElement.childNodes[0])
    select.removeAllRanges()
    select.addRange(range)
    document.execCommand('copy')
    select.removeRange(range)
    this.$message('클립보드에 복사했습니다.')
  }
}
</script>

<style lang="scss" scoped>
main {
  background-color: rgba(0, 0, 0, 0.5);
}

.text-gradient {
  background: linear-gradient(to bottom, #fbcac9, #F7B153);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 4rem 4rem;

  > .logo {
    background-image: url('/img/header_logo.png');
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    width: 150px;
    height: 40px;
  }
}

video {
  background-color: #000;
}

.bg-video {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  max-width: 2560px;
  margin: 0 auto;
  z-index: -1;
  background: #000;
}

section {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-direction: column;
  flex-direction: column;
  -ms-flex-pack: center;
  justify-content: center;
  align-items: center;
  position: relative;
  width: 100%;
  margin-top: 3rem;
}

section.index {
  > .season {
    background: url('/img/season.png') 0 0 no-repeat;
    width: 571px;
    height: 34px;
    z-index: 0;
  }

  > .logo {
    background: url('/img/index_logo.png') 0 0 no-repeat;
    width: 741px;
    height: 180px;
    z-index: 1;
  }

  > .subtitle {
    background: url('/img/index_subtitle.png') 0 0 no-repeat;
    width: 563px;
    height: 44px;
    margin-top: 7px;
  }

  > .message {
    margin-top: 160px;
    background: url('/img/index_message.png') 0 0 no-repeat;
    width: 465px;
    height: 86px;
  }

  > .scroll {
    background: url('/img/index_scroll.png') 0 0 no-repeat;
    width: 16px;
    height: 134px;
    content: "";
    animation: bounce 1s ease-in-out infinite;
    transition: transform .5s, opacity .5s;
    transition-delay: 1s;
  }

  @keyframes bounce {
    0% {
      transform: translateY(-10px);
    }

    50% {
      transform: translateY(0);
    }
    100% {
      transform: translateY(-10px);
    }
  }
}

section.event1 {
  > .title {
    background: url('/img/event1_title.png') 0 0 no-repeat;
    width: 292px;
    height: 114px;
  }

  > .message {
    margin: 1rem 0;
    text-align: center;
    color: #fff;
    font-size: 20px;

    > p > span {
      font-weight: 700;
    }
  }

  > .form {
    background: url('/img/event1_form.webp') 0 0 no-repeat;
    width: 1251px;
    height: 579px;
    text-align: right;

    > .form-group {
      width: 50%;
      margin-left: 50%;
      margin-top: 120px;
      padding: 3rem 3.7rem 0 0;
    }
    .os {
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .ios {
      margin: 1rem 0;
      background: url('/img/icon_ios.png') 0 0 no-repeat;
      width: 192px;
      height: 48px;
    }
    .aos {
      margin: 1rem 0;
      background: url('/img/icon_aos.png') 0 0 no-repeat;
      width: 192px;
      height: 48px;
    }

    .el-radio {
      text-align: center;
    }

    .coupon {
      margin-top: 1.1rem;
      width: 100%;
      height: 80px;
      background: #fff;
      border: 1px solid #32345D;
      box-sizing: border-box;
      background: url('/img/coupon_bg.webp') 0 0 repeat;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-right: 1.7rem;
      > div:first-child {
        padding: 1.7rem 1rem;
        color: #8091BD;
        font-size: 1.1rem;
        border-right: 1px solid #32345D;
      }
      > div {
        color: #ecb165;
        font-size: 2rem;
        font-weight: 700;
      }
    }
    .text-copy {
      font-size: 1.2rem;
      color: #8091BD;
      font-weight: 600;
    }

    .privacy {
      margin-top: 1rem;
    }
  }

  > .gift {
    margin-top: 1rem;
    background: url('/img/event1_gift.png') 0 0 no-repeat;
    width: 1251px;
    height: 517px;
  }

  > .guide {
    margin-top: 1rem;
    background: url('/img/event1_guide.png') 0 0 no-repeat;
    width: 1251px;
    height: 384px;
  }
}

section.event2 {
  margin: 10rem 0 10rem;
  > .title {
    background: url('/img/event2_title.png') 0 0 no-repeat;
    width: 592px;
    height: 122px;
  }

  > .subtitle {
    margin-top: 4rem;
    background: url('/img/event2_subtitle.png') 0 0 no-repeat;
    width: 723px;
    height: 24px;
  }

  > .reward-wrapper {
    margin: 2rem 0;
    display: flex;
    justify-content: space-between;
    > div {
      width: 376px;
      height: 376px;
    }
    > .reward1 {
      background: url('/img/reward1.png') 0 0 no-repeat;
    }
    > .reward2 {
      background: url('/img/reward2.png') 0 0 no-repeat;
    }
    > .reward3 {
      background: url('/img/reward3.png') 0 0 no-repeat;
    }
  }
  > .message {
    margin-top: 1rem;
    background: url('/img/event2_message.png') 0 0 no-repeat;
    width: 753px;
    height: 127px;
  }
}

.viewport-target {
  opacity: 0;
  transition: transform .5s, opacity .5s;
  transform: translateY(60px);
}

.in-viewport {
  opacity: 1;
  transform: translateY(0);
}

.privacy-content {
  ul {
    list-style: disc;
    padding-left: 1rem;
    padding-bottom: 1rem;
  }
}
</style>
