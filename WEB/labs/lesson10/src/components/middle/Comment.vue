<template>
<div>
  {{ arrUserId_name[comment.userId] }}:    "{{ comment.text }}"
</div>
</template>

<script>
export default {
  name: "CommentList",
  props: ["comment", "arrUserId_name"]
}
</script>

<style scoped>

</style>