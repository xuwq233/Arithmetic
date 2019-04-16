<template>
    <div id="Test">
        <!-- 答题区 -->
        <el-card class="box-card" id="leftPart">
            <div class="text item">
                <el-table :data="dataFromBack2" border>
                    <el-table-column label="题目" prop="QUESTION"></el-table-column>
                    <el-table-column label="你的答案" prop="UANSWER">
                        <!-- 解决了input数据绑定问题 -->
                        <template slot-scope="scope">
                            <el-form :model="scope.row" :rules="rules">
                                <el-form-item prop="UANSWER">
                                    <el-input v-show="true" v-model.number="scope.row.UANSWER" placeholder="请输入答案"/>
                                </el-form-item>
                            </el-form>
                        </template>
                    </el-table-column>
                    <!-- <el-table-column label="答案" prop="ANSWER"></el-table-column> -->
                </el-table>

                <el-button id="startBtn" @click="getData();setTime()">开始作答</el-button>
                <el-button id="stopBtn" @click="getScore()">停止作答</el-button>
                <el-button id="backBtn" @click="getBack()">返回</el-button>
            </div>
        </el-card>
        <!-- 反馈区 -->
        <el-card class="box-card" id="rightPart" v-if="flagForC">
            <div class="text item">
                <!-- 计时器 -->
                <div>作答时间：{{callinTime}}</div>
                <!-- 得分 -->
                <div id="finalScore">
                    正确率：<h4>{{scorePer}}</h4>
                </div>
                <!-- 下载题目 -->
                <a href="http://localhost:8080/downloadFile" download="">
                    <el-button type="primary">下载题目<i class="el-icon-download el-icon--right"></i></el-button>
                </a>
            </div>
        </el-card>
    </div>
</template>

<script>

import Test from './Test.vue'
import Axios from 'axios'

    export default{
        name:'Test',
        data(){
            return{
                dataFromBack:[],//添加了ANSWER
                dataFromBack2:[],//添加了UANSWER
                scorePer:'',//百分数
                callinTime:'',//计时器
                callinTime:'',
                flagForC:false,//反馈区v-if
                rules: {
                    UANSWER:[
                        {required:true,message:'请注意作答！',trigger:'blur'}
                    ]
                },
            }
        },
        methods:{
            // 请求数据
            getData(){
                this.flagForC=true
                //本地存储获取数据
                var data= JSON.parse(localStorage.getItem('questions'))
                console.log(data)
                for (let index = 0; index < data.length; index++) {
                    data[index]=JSON.parse(data[index])
                }
                console.log(data)
                // console.log(typeof this.dataFromBack2)
                // 测试
                // let api="https://api.myjson.com/bins/74l63"
                // let api="http://localhost:8080/create"
                // Axios.get(api).then((response)=>{
                    // console.log(response);
                    
                    // 测试测试，正式删除下列代码,这里是一个json对象数组
                    // response.data=[{"ANSWER":"-1","QUESTION":"1+2-4=-1"},{"ANSWER":"1","QUESTION":"1+1-1=1"},{"ANSWER":"0","QUESTION":"1-1=0"}]
                    // this.dataFromBack=response.data
                    // 注意this指向
                    // let mydata=JSON.parse(dataFromBack)
                    // json对象无需转换
                    this.dataFromBack2=data
                    for (let index = 0; index < data.length; index++) {
                        // 由于 JavaScript 的限制，Vue 不能检测对象属性的添加或删除：
                        // this.dataFromBack2[index].UANSWER=''  
                        this.$set(this.dataFromBack2[index],'UANSWER','')
                        return this.dataFromBack2;
                        // 返回新数组
                    }
                    console.log(this.dataFromBack2)
                    // this.dataFromBack2.push(response.data)
                    // console.log(this.dataFromBack2)  
                // }).catch((error)=>{
                    // console.log(error);
                // })
                localStorage.clear()
            },

            // 得到成绩
            getScore(){
                clearInterval(this.timer)
                var j=0;
                let self=this
                console.log(self)
                console.log(self.dataFromBack2.length)
                //vue无法获取dataFromBack2的长度，以为异步方式执行，仅仅修改了值，未返回新数组
                for (let index = 0; index < self.dataFromBack2.length; index++) {
                    if (self.dataFromBack2[index].ANSWER==self.dataFromBack2[index].UANSWER) {
                        j=j+1;
                    }else{
                        j=j;//切记！没事不要瞎return
                    }
                }
                let fullScore=self.dataFromBack2.length;
                let score=eval(j/fullScore);
                // 转换成百分数
                let scoreMul=Number(score*100).toFixed(1)
                self.scorePer=scoreMul+'%'
                localStorage.clear()
            },

            // 返回选择页面
            getBack(){
                // if (true) {
                    this.$router.replace({
                        path:'/Exercises',
                        name:'Exercises',
                        query:{

                        }
                    })
                // }
                localStorage.clear()
            },

            //计时器
            // 1、无格式
            // timer(){
            //     let self=this
            //     this.setTime=setInterval(()=>{
            //         this.time++
            //     },1000)
            // }
            // 2、有格式
             setTime() {
                let self = this
                let hour, minute, second
                hour = minute = second = 0
                // if (bolean === true) {
                self.timer = setInterval(function () {
                    if (second >= 0) {
                    second = second + 1
                    }
                    if (second >= 60) {
                    second = 0
                    minute = minute + 1
                    }
                    if (minute >= 60) {
                    minute = 0
                    hour = hour + 1
                    }
                    self.callinTime = hour + '时' + minute + '分' + second + '秒'
                }, 1000)
                // } else {
                // window.clearInterval(_this.timer)
                // }
            },

            //阻止跳转
            // stop(){
            //     return flase;
            // }


        }//methods结束

    }//export结束
</script>

<style scoped>
.text {
    font-size: 14px;
    height:550px;
}
.item {
    padding: 18px 0;
}
#leftPart {
    width: 480px;
    margin: 0 auto;
    overflow: auto;
    display: inline-block;
}
#rightPart{
    width: 480px;
    height:300px;
    margin: 0 auto;
    margin-bottom: 325px;
    display: inline-block;
}
</style>
