<template>
  <div class="datatable">
    <div class="caption">Users</div>
    <table>
      <thead>
      <tr>
        <th>id</th>
        <th>login</th>
        <th>name</th>
      </tr>
      </thead>
      <tbody>
      <User v-for="user in users" :user="user" :key="user.id"/>
      </tbody>
    </table>
  </div>
</template>

<script>
import User from "@/components/middle/User";
export default {
  name: "AllUsers",
  components: {
    User
  },
  props: ["users"]
}
</script>

<style scoped>

</style>