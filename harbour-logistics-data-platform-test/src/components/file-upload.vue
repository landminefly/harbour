<script>
import axios from 'axios';
export default {
    methods: {
        uploadFile() {
            var fileSelect = document.getElementById('select');
            var file = fileSelect.files[0];
            if (file === undefined) {
                alert("您尚未选择文件！");
                return;
            }
            var progress = document.getElementById('progress')

            var formData = new FormData();
            formData.append('file', file);

            axios({
                method: 'post',
                url: '/api/HLDP/com/admin/upload_file',
                data: formData,
                // 计算进度条长度
                onUploadProgress: function (progressEvent) {
                    var percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
                    progress.style.width = percentCompleted + '%';
                }
            })
                .then(function (response) {
                    var responseData = response.data;
                    // 在这里处理后端返回的数据
                    alert(responseData);
                })
                .catch(function (error) {
                    // 处理请求错误
                    alert(error);
                });

        }
    },

    mounted() {
        var fileSelect = document.getElementById('select');
        var selected = document.getElementById("selected");
        fileSelect.addEventListener("change", function (e) {
            if (e.target.files[0]) {
                selected.innerText = e.target.files[0].name;
            } else {
                selected.innerText = "无";
            }
        })
    }
}
</script>

<template>
    <div id="file-upload">
        <label for="select" id="select-label">选择文件</label>
        <input type="file" id="select">
        当前选择的文件：
        <div id="selected">无</div>
        <button @click="uploadFile">上传</button>
        <!--  进度条  -->
        <div id="progressBar">
            <div id="progress"></div>
        </div>
    </div>
</template>

<style>
#progressBar {
    width: 300px;
    height: 20px;
    border: 1px solid #000;
    border-radius: 10px;
    overflow: hidden;
}

#progress {
    width: 0;
    height: 100%;
    background-color: #4CAF50;
    transition: all 0.3s;
}

#select {
    display: none;
}

#select-label {
    display: block;
    height: 30px;
    width: 80px;
    background-color: transparent;
    border-radius: 10px;
    border: 2px #808080 solid;
    font-size: 15px;
    line-height: 30px;
    text-align: center;
    transition: all 0.1s;
}

#select-label:hover {
    background-color: rgba(128, 128, 128,0.3);
}
</style>