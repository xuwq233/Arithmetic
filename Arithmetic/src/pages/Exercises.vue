<template>
    <div id="Exercises">
        <!-- 卡片 -->
        <el-card class="box-card">
            <div class="text item">
                <!-- ：model是绑定form表单中提交给后台的一个对象，ref是我们绑定的对象 -->
                <el-form ref="form" :rules="rules" :model="form" label-width="110px" class="demo-ruleForm">
                    <el-form-item prop="num1" label="数值范围下限值">
                        <el-input-number v-model="form.num1"  :min="1" :max="100" label="描述文字" @change="limitNum2"></el-input-number>
                    </el-form-item>
                     <el-form-item prop="num2" label="数值范围上限值">
                        <el-input-number 
                        v-model="form.num2" :min="form.num1+1"  :max="100" label="描述文字"></el-input-number>
                    </el-form-item>
                    <!-- prop和v-model一致!! -->
                    <el-form-item prop="number" label="题目数目">
                        <el-input v-model.number="form.number" placeholder="题目数目在1-100之间"></el-input>
                    </el-form-item>
                    <el-form-item prop="value" label="运算符上限">
                        <el-select v-model="form.value" placeholder="请选择">
                            <el-option
                            v-for="item in form.options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item prop="" label="是否包含乘除号">
                        <el-switch
                        v-model="form.value1"
                        active-color="#13ce66"
                        inactive-color="#909399">
                        </el-switch>
                    </el-form-item>
                    <el-form-item prop="" label="是否包含括号">
                        <el-switch
                        v-model="form.value2"
                        active-color="#13ce66"
                        inactive-color="#909399">
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

    export default{
        name:'Exercises',
        data(){
            return {
                // form:[],
                form:{
                    num1:1,
                    num2:'',
                    number:'',
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
                    value: '',
                    value1:'',
                    value2:'',
                },
                // question:[],
                // answer:[],
                rules: {
                    number: [
                        // {type:'number',message:'题目数目必须为数字',trigger:'blur'},
                        { required: true, message: '请输入题目数量', trigger: 'blur' },
                        // number is not a string!注意number验证放的位置
                        { min:1,max:100,type:'number',message: '题目数量是在1-100之间的整数', trigger: 'blur' },
                    ],
                    value:[
                        {required:true,message:'请选择运算符上限',trigger:'change'}
                    ],
                },

            }/*return结束 */
        },/*data结束 */

        // created() {
        //     if ( this.timeOut ) {
        //         clearTimeout(this.timeOut);
        //     }
        //     this.getListIng();
        // },


//         computed:{
//             timeOut: {
//                 set (val) {
//                     this.$store.state.timeout.compileTimeout = val;
//                 },
//                 get() {
//                 return this.$store.state.timeout.compileTimeout;
//                 }
//             },
//         },

        // methods带s，并不是数组而是对象
        methods:{
            //自定义验证上下限
            limitNum2(){
                // 注意数据绑定！
                if (this.form.num1>=this.form.num2) {
                    this.form.num2=this.form.num1+1
                }else{
                    this.form.num2=this.form.num2
                }
            },
            // 验证表单
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // this.$options.methods.getData() 错
                        // 注意this指向
                        // this.getData()

                        // 路由跳转
                        this.$router.replace({
                            path:'/Test',
                            name:'Test',
                            query:{

                            }
                        })
                        console.log('successful submit!')
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
    position: absolute;
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