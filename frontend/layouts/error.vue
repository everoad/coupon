<template>
  <main>
    <section>
      <h1 v-if="error.statusCode === 404">
        Not Found
      </h1>
      <h1 v-else>
        ERROR
      </h1>
      <NuxtLink to="/">
        돌아가기
      </NuxtLink>
    </section>
  </main>
</template>

<script>
export default {
  layout: 'error',
  props: ['error']
}
</script>
<style scoped>
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
</style>
