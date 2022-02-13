<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <AllUsers v-if="page === 'AllUsers'" :users="viewUsers"/>
      <Index v-if="page === 'Index'" :posts="viewAllPosts" :arrPostId_comm="arrPostId_comm" :arrUserId_name="arrUserId_name"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <WritePost v-if="page === 'WritePost'"/>
      <EditPost v-if="page === 'EditPost'"/>
      <FullPost v-if="page === 'FullPost'" :post="post" :arrPostId_comm="arrPostId_comm" :arrUserId_name="arrUserId_name"/>

    </main>
  </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import Enter from "@/components/middle/Enter";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";
import Register from "@/components/middle/Register";
import AllUsers from "@/components/middle/AllUsers/AllUsers";
import FullPost from "@/components/middle/FullPost";

export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index",
      post: this.post
    }
  },
  components: {
    FullPost,
    AllUsers,
    Register,
    WritePost,
    Enter,
    Index,
    Sidebar,
    EditPost
  },
  props: ["posts", "users", "arrPostId_comm", "arrUserId_name"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    },
    viewAllPosts: function () {
      return Object.values(this.posts).sort().reverse();
    },
    viewUsers: function () {
      return Object.values(this.users).sort((a, b) => b.id - a.id);
    }

  },
  beforeCreate() {
    this.$root.$on("onChangePage", (page) => this.page = page);
    this.$root.$on("passPost", (post) => this.post = post);
    this.$root.$on("toPost", (page) => this.page = page);
  }
}
</script>

<style scoped>

</style>