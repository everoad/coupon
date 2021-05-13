<template>
  <main>
    <section>
      <header>
        {{ error.statusCode }}
      </header>
      <div v-if="error.statusCode === 404" class="message">
        페이지를 찾을 수 없습니다.
      </div>
      <div v-else class="message">
        ERROR
      </div>
      <footer>
        <router-link to="/">
          돌아가기
        </router-link>
      </footer>
    </section>
  </main>
</template>

<script>
export default {
  layout: 'error',
  props: ['error']
}
</script>
<style lang="scss" scoped>
section {
  text-align: center;
  width: 600px;
  height: 600px;
  margin: 5rem auto;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
header {
  font-weight: 600;
  font-size: 60px;
  color: #555;
}
div {
  font-weight: 600;
  font-size: 40px;
  color: #555;
}
footer {
  margin-top: 5rem;

  > a {
    text-decoration: none;
    color: #555;
    font-size: 20px;
  }

  > a:hover {
    text-decoration: underline;
  }
}
</style>
