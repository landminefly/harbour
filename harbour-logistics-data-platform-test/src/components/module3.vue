<script>

</script>

<template>
  <n-upload
    action="/api/HLDP/com/admin/upload_file"
    :headers="{
      'naive-info': 'hello!'
    }"
    :data="{
      'naive-data': 'cool! naive!'
    }"
  >
    <n-button>上传文件</n-button>
  </n-upload>
</template>

<style>
#module3{
    height: 100%;
    width: 100%;
    /* background-color:chartreuse; */
}
</style>