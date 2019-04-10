import Vue from 'vue'
import Router from 'vue-router'
import layout from '@/components/layout'
import Exercises from '@/pages/Exercises'
import HelloWorld from '@/components/HelloWorld'
import Test from '@/pages/Test'

// 注册
Vue.use(Router)

export default new Router({
  routes:[
    {
      path:'/',
      name:'layout',
      component:layout,
      children:[
        {path:'Exercises',name:'Exercises',component:Exercises},
        {path:'Test',name:'Test',component:Test}
      ]
    }
  ]
})