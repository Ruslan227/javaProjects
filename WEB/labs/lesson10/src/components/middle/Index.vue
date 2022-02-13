<template>
  <div>
    <Post v-for="post in posts" :post="post" :arrPostId_comm="arrPostId_comm" :arrUserId_name="arrUserId_name" :key="post.id"/>
  </div>
</template>

<script>
import Post from "@/components/middle/Post";
export default {
  name: "Index",
  components: {
    Post
  },
  props: ["posts", "arrPostId_comm", "arrUserId_name"],
  computed: {

  }
}

</script>

<style scoped>

</style>