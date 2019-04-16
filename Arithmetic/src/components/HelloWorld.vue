<template>
  <div class="hello">
    <div id="words">
      <p>{{ msg1 }}</p>
      <router-link to="/Exercises">
        <el-button type="warning" v-if="flag">进入系统<i class="el-icon-edit el-icon--right"></i></el-button>
      </router-link>
    </div>
    <img src="../assets/TESTSONLINE.png" :style="{transform:'rotateY('+deg+'deg)'}">
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      deg:0,
      msg1: '',
      flag:false,
    }
  },
  // 页面加载时触发
  mounted:function(){
    this.trans()
    this.oneByOne()
  },

  methods:{
    //旋转效果
    trans(){
      let self=this
      self.timer=setInterval(() => {
        self.deg+=10
      }, 200);
    },

    //字体效果
    oneByOne(){
      let i=0
      let self=this
      // 实现字符串换行
      let str1='自主选择，在线答题\n欢迎使用四则运算在线训练系统'
      let num1=str1.length
      // let str2="欢迎使用四则运算在线训练系统"
      self.timerForW1=setInterval(() => {
        self.msg1=str1.substring(0,i)
        i++
        if (this.msg1==str1) {
          clearInterval(self.timerForW1)
          self.flag=true
        }
      }, 200);
  
    },

  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@font-face{
  font-family: handwrite;
  src: url('../assets/字魂62号-瘦金体.ttf')
}
#hello{
  position: relative;
}
h1, h2 {
  font-weight: normal;
}
img{
  position: absolute;
  bottom: 20%;
  left: 60%;
}
#words{
  text-align: left;
  font-weight: normal;
  white-space: pre-wrap;
/* 解决插值表达式不换行 */
}
#words p{
  font-size: 30px;
  font-family: handwrite;
  font-weight: 800;
  width: auto;
}
#words{
  position: absolute;
  left: 200px;
  top: 150px;
  width: auto;
}
</style>
