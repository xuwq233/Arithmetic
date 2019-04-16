<template>
    <!-- <div id="layout"> -->
        <el-container>
            <el-header>
                <img src="../assets/TESTSONLINE.png">
                <h2>四则运算在线训练</h2>
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
// import Header from '@/components/header'
    export default{
        name:'layout',
        components:{
            // Header,
        },
        data(){
            return{
            }
        }
    }
</script>

<style scoped>
.el-header{
    background-color: #B3C0D1;
    color: #333;
    text-align: left;
    width: 100%;
    /* position: absolute; */
    top:0;
    /* line-height: 60px; */
  }
.el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    width: 100%;
    position: absolute;
    bottom:0;
    /* line-height: 60px; */
  }
.el-main {
    background-color: #ffffff;
    color: #333;
    text-align: center;
    line-height: 80px;
    height: auto;
  }
  img{
      width: 80px;
      height:80px;
      float: left;
  }
</style>