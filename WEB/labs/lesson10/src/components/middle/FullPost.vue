<template>
  <div>
    <Post :post="post" :arrPostId_comm="arrPostId_comm" :arrUserId_name="arrUserId_name"></Post>
    <Comment v-for="comment in getPostComments" :comment="comment" :arrUserId_name="arrUserId_name" :key="comment.id"></Comment>
  </div>
</template>

<script>
import Post from "@/components/middle/Post";
import Comment from "@/components/middle/Comment";
export default {
  name: "FullPost",
  components: {
    Comment,
    Post
  },
  props: ["post", "arrPostId_comm", "arrUserId_name"],
  computed: {
    getPostComments: function () {
      return this.arrPostId_comm[this.post.id];
    }
  }
}
</script>

<style scoped>

</style>