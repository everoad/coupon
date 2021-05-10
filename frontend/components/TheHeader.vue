<template>
  <header>
    <ul>
      <li>
        <router-link to="/">
          사전등록
        </router-link>
      </li>
      <li>
        <router-link to="/coupon">
          예약목록
        </router-link>
      </li>
    </ul>
  </header>
</template>

<style lang="scss" scoped>
header {
  height: 68px;
  padding: 0 5rem;
  background-color: #fff;
  text-align: right;
  box-shadow: 0 0 14px 0 rgba(0, 0, 0, 0.13);
  > ul {
    list-style: none;
    padding-left: 0;
    > li {
      display: inline-block;
      vertical-align: top;
      padding: 1.5rem 0.5rem;
      > a {
        color: #333;
        text-decoration: none;
        font-size: 0.9rem;
        font-weight: 600;

        &:hover {
          text-decoration: underline;
        }
      }
    }
  }
}

</style>
