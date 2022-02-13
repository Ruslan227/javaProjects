<template>
  <article>
    <div class="title">{{ post.title }}</div>
    <div class="information">By {{ post.user.name }} </div>
    <div class="body">{{ post.text }}</div>
    <ul class="attachment">
      <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
      <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
    </ul>
    <div class="footer">
      <div class="left">
        <img src="../../../public/img/voteup.png" title="Vote Up" alt="Vote Up"/>
        <span class="positive-score">+173</span>
        <img src="../../../public/img/votedown.png" title="Vote Down" alt="Vote Down"/>
      </div>
      <div class="right">
        <img src="../../../public/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
        no data
        <img src="../../../public/img/comments_16x16.png" title="Comments" alt="Comments"/>
        {{ 0 }}
      </div>
    </div>
  </article>
</template>

<script>

export default {
  name: "Post",
  methods: {
    toPost: function (page) {
      this.$root.$emit("toPost", page);
    },
    passPost: function (post) {
      this.$root.$emit("passPost", post);
    }
  },
  props: ["post"]
}
</script>

<style scoped>

</style>