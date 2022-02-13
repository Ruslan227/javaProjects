<template>
  <div id="app">
    <Header :userId="userId" :users="users"/>
    <Middle :posts="posts" :users="users" :arrPostId_comm="countComments" :arrUserId_name="countUsers"/>
    <Footer :loggedUsersAmount="Object.keys(users).length" :postsAmount="Object.keys(posts).length"/>
  </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";

export default {
  name: 'App',
  components: {
    Footer,
    Middle,
    Header
  },
  data: function () {
    return this.$root.$data;
  },
  computed: {

    countComments: function () {
      const res = {};
      const arrComments = Object.values(this.comments);
      const arrPosts = Object.values(this.posts);
      for (let j = 0; j < arrPosts.length; j++) {
        for (let i = 0; i < arrComments.length; i++) {
          if (arrComments[i].postId === arrPosts[j].id) {
            // eslint-disable-next-line no-unused-vars
            let t = arrPosts[j].id;
            if (Object.keys(res).indexOf(t.toString()) === -1) {
              res[t] = [];
            }
            res[t].push(arrComments[i]);
          }
        }
      }
      return res;
    },

    countUsers: function () {
      const res = {};
      const arrUsers = Object.values(this.users);
      for (let i = 0; i < arrUsers.length; i++) {
        res[arrUsers[i].id] = arrUsers[i].name;
      }
      return res;
    }

  },
  beforeCreate() {
    this.$root.$on("onEnter", (login, password) => {
      if (password === "") {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      const users = Object.values(this.users).filter(u => u.login === login);
      if (users.length === 0) {
        this.$root.$emit("onEnterValidationError", "No such user");
      } else {
        this.userId = users[0].id;
        this.$root.$emit("onChangePage", "Index");
      }
    });

    this.$root.$on("onLogout", () => this.userId = null);

    this.$root.$on("onWritePost", (title, text) => {
      if (this.userId) {
        if (!title || title.length < 5) {
          this.$root.$emit("onWritePostValidationError", "Title is too short");
        } else if (!text || text.length < 10) {
          this.$root.$emit("onWritePostValidationError", "Text is too short");
        } else {
          const id = Math.max(...Object.keys(this.posts)) + 1;
          this.$root.$set(this.posts, id, {
            id, title, text, userId: this.userId
          });
        }
      } else {
        this.$root.$emit("onWritePostValidationError", "No access");
      }
    });


    this.$root.$on("onRegister", (login, name) => {
      if (Object.values(this.users).filter(u => u.login === login).length === 0) {
        if (login.length > 16 || login.length < 3) {
          this.$root.$emit("onRegisterValidationError", "Login should be 3-16 characters");
        } else if (!login.match(/^[a-z]*$/i)) {
          this.$root.$emit("onRegisterValidationError", "a-z only required");
        } else if (name.length < 1 || name.length > 32) {
          this.$root.$emit("onRegisterValidationError", "Name should be 1-32 characters");
        } else {
          const id = Math.max(...Object.keys(this.users)) + 1;
          this.$root.$set(this.users, id, {
            id, login, name, admin: false
          });
          this.$root.$emit("onChangePage", "Enter");
        }
      } else {
        this.$root.$emit("onRegisterValidationError", "Login is already in use");
      }
    });


    this.$root.$on("onEditPost", (id, text) => {
      if (this.userId) {
        if (!id) {
          this.$root.$emit("onEditPostValidationError", "ID is invalid");
        } else if (!text || text.length < 10) {
          this.$root.$emit("onEditPostValidationError", "Text is too short");
        } else {
          let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
          if (posts.length) {
            posts.forEach((item) => {
              item.text = text;
            });
          } else {
            this.$root.$emit("onEditPostValidationError", "No such post");
          }
        }
      } else {
        this.$root.$emit("onEditPostValidationError", "No access");
      }
    });
  }
}
</script>

<style>
#app {

}
</style>
