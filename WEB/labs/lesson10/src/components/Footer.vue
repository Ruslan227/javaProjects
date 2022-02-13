<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov<br>
      Logged users amount: {{ loggedUsersAmount }}  Posts amount: {{ postsAmount }}
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: [ "loggedUsersAmount", "postsAmount"]
}
</script>

<style scoped>

</style>