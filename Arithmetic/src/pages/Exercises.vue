<template>
    <div id="Exercises">
        <!-- 卡片 -->
        <el-card class="box-card">
            <div class="text item">
                <!-- ：model是绑定form表单中提交给后台的一个对象，ref是我们绑定的对象 -->
                <el-form ref="form" :rules="rules" :model="form" label-width="110px" class="demo-ruleForm">
                    <el-form-item prop="min" label="数值范围下限值">
                        <el-input-number v-model="form.min"  :min="1" :max="100" label="描述文字" @change="limitNum2"></el-input-number>
                    </el-form-item>
                     <el-form-item prop="max" label="数值范围上限值">
                        <el-input-number 
                        v-model="form.max" :min="form.min+1"  :max="100" label="描述文字"></el-input-number>
                    </el-form-item>
                    <!-- prop和v-model一致!! -->
                    <el-form-item prop="num" label="题目数目">
                        <el-input v-model.number="form.num" placeholder="题目数目在1-100之间"></el-input>
                    </el-form-item>
                    <el-form-item prop="maxOper" label="运算符上限">
                        <el-select v-model="form.maxOper" placeholder="请选择">
                            <el-option
                            v-for="item in form.options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item prop="isMul" label="是否包含乘除号">
                        <el-switch
                        v-model="form.isMul"
                        active-color="#13ce66"
                        inactive-color="#909399"
                        active-value="4"
                        inactive-val="2"
                        >
                        </el-switch>
                    </el-form-item>
                    <el-form-item prop="isBrac" label="是否包含括号">
                        <el-switch
                        v-model="form.isBrac"
                        active-color="#13ce66"
                        inactive-color="#909399"
                        active-value="0"
                        inactive-value="1"
                        >
                        </el-switch>
                    </el-form-item>
                    <el-form-item prop="">
                        <el-button type="primary" @click="submitForm('form')" >开始出题</el-button>
                        <el-button @click="resetForm('form')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

<script>

import exercisesVue from './Exercises.vue'
import { required, between, sameAs } from 'vuelidate/lib/validators'
import Axios from 'axios'
import qs from 'qs'

    export default{
        name:'Exercises',
        data(){
            return {
                // form:[],
                form:{
                    min:1,
                    max:'',
                    num:'',
                    options: [{
                        value: '1',
                        label: '1'
                    },{
                        value: '2',
                        label: '2'
                    },{
                        value: '3',
                        label: '3'
                    },{
                        value: '4',
                        label: '4'
                    },{
                        value: '5',
                        label: '5'
                    }],
                    maxOper: '',
                    isBrac:'',
                    isMul:'',
                },
                rules: {
                    num: [
                        // {type:'number',message:'题目数目必须为数字',trigger:'blur'},
                        { required: true, message: '请输入题目数量', trigger: 'blur' },
                        // number is not a string!注意number验证放的位置
                        { min:1,max:100,type:'number',message: '题目数量是在1-100之间的整数', trigger: 'blur' },
                    ],
                    maxOper:[
                        {required:true,message:'请选择运算符上限',trigger:'change'}
                    ]
                },

            }/*return结束 */
        },/*data结束 */

        // methods带s，并不是数组而是对象
        methods:{
            //自定义验证上下限
            limitNum2(){
                // 注意数据绑定！
                if (this.form.min>=this.form.max) {
                    this.form.max=this.form.min+1
                }else{
                    this.form.max=this.form.max
                }
            },
            // 提交、验证表单、传值
            submitForm(formName) {
                let self=this
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // this.$options.methods.getData() 错
                        // 注意this指向
                        // this.getData()

                        //传值
                        let postData=qs.stringify({
                            "_num":self.form.num,
                            "_max":self.form.max,
                            "_min":self.form.min,
                            "_maxOper":self.form.maxOper,
                            "_isBrac":self.form.isBrac,
                            "_isMul":self.form.isMul,
                        })
                        // let api='http://localhost:8080/create'
                        Axios({
                            method:'post',
                            url:'http://localhost:8080/create',
                            data:postData
                        })
                        .then(function (response) {
                            console.log(response);
                            console.log('success post!')
                            //本地存储数据
                            localStorage.clear
                            var dataStorage=[]
                            for (let index = 0; index < response.data.length; index++) {
                                dataStorage.push(JSON.stringify(response.data[index]))//数组里面粗放字符串
                            }
                            localStorage.questions = JSON.stringify(dataStorage);
                            //将dataStorage存放进localStorage中,注意！！！
                            console.log(dataStorage)
                            console.log(localStorage.questions)
                            // 路由跳转
                            self.$router.replace({
                                path:'/Test',
                                name:'Test',
                                query:{

                                }
                            })

                        })
                        .catch(function (error) {
                            console.log(error);
                        })
                        console.log('success submit!')
                     } else {
                        console.log('error submit!!');
                        return false;
                    }
                });


            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            

        }

    }
</script>

<style scoped>
.text {
    font-size: 14px;
    height:550px;
    text-align: left;
}
.item {
    padding: 18px 0;
}
.box-card {
    width: 480px;
    margin: 0 auto;
}
#stopBtn{
    bottom: 15px;
    right: 15px;
    background-color: #F56C6C;
    color: white;
}
#finalScore{
    position: absolute;
    right: 250px;
    bottom: 10px;
}
</style>