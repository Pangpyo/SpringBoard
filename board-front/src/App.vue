<template>
  <header-nav></header-nav>
  <router-view></router-view>
</template>

<script>
import HeaderNav from "@/components/HeaderNav";

export default {
  name: 'App',
  components: {
    HeaderNav,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
