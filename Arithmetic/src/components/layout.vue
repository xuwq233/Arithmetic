<template>
    <!-- <div id="layout"> -->
        <el-container>
            <el-header>
                <h1>Welcome!</h1>
            </el-header>
            <el-main>
                <router-view></router-view>
            </el-main>
            <el-footer>
                <p>版权所有：孔一言 徐文晴</p>
            </el-footer>
        </el-container>
    <!-- </div> -->
</template>

<script>
    export default{
        name:'layout'
    }
</script>

<style scoped>
.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    /* line-height: 60px; */
  }
.el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    /* line-height: 160px; */
  }
</style>