<template>
  <section>
    <div class="header">
      {{ post.title }}
    </div>
    <div class="body">
      {{ post.text }}
    </div>
    <div class="footer">
      <a href="#" @click.prevent="toPost( 'FullPost')"><div @click.prevent="passPost(post)">View all</div></a>
    </div>
  </section>
</template>

<script>
export default {
  name: "SidebarPost",
  props: ["post"],
  methods: {
    toPost: function (page) {
      this.$root.$emit("toPost", page);
    },
    passPost: function (post) {
      this.$root.$emit("passPost", post);
    }
  },
}
</script>

<style scoped>
section {
  margin-bottom: 1rem;
}
</style>