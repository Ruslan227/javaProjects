<template>
  <article>
    <div class="title" @click.prevent="toPost( 'FullPost')">
      <a href="#" @click.prevent="passPost(post)">{{ post.title }}</a></div>
    <div class="information">By {{ getName }} </div>
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
        {{ getCommentsAmount }}
      </div>
    </div>
  </article>
</template>

<script>

export default {
  name: "Post",
  computed: {
    getCommentsAmount: function () {
      if (Object.keys(this.arrPostId_comm).indexOf(this.post.id.toString()) === -1) {
        return 0;
      }
      return this.arrPostId_comm[this.post.id].length;
    },
    getName: function () {
      return this.arrUserId_name[this.post.userId];
    }
  },
  methods: {
    toPost: function (page) {
      this.$root.$emit("toPost", page);
    },
    passPost: function (post) {
      this.$root.$emit("passPost", post);
    }
  },
  props: ["post", "arrPostId_comm", "arrUserId_name"]
}
</script>

<style scoped>

</style>