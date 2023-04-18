<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'HomeworkGit',
  data() {
    return {
      url: 'https://github.com/Xiang-S/homework'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
