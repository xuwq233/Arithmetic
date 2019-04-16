package com.example.demo.service.impl;

import com.example.demo.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

//    @Resource
//    QuestionMapper questionMapper;
    @Override
    public int algorithm(String s) {
        //放数字
        Stack<Integer> stack1 = new Stack<>();
        //放操作符
        Stack<String> stack2 = new Stack<>();
        //存放运算符优先级
        HashMap<String, Integer> hashmap = new HashMap<>();
        hashmap.put("(", 0);
        hashmap.put("+", 1);
        hashmap.put("-", 1);
        hashmap.put("*", 2);
        hashmap.put("÷", 2);

        for (int i = 0; i < s.length();) {
            //设置可变长的字符串
            StringBuffer digit = new StringBuffer();
            //将式子字符串切割为c字符
            char c = s.charAt(i);
            //判断字符是否为10进制数字,将一个数加入digit
            while (Character.isDigit(c)) {
                digit.append(c);
                i++;
                c = s.charAt(i);
            }
            //当前digit里面已经无数字，即当前处理符号
            if (digit.length() == 0){
                switch (c) {
                    case '(': {
                        stack2.push(String.valueOf(c));
                        break;
                    }
                    //遇到右括号了计算，因为（的优先级最高
                    case ')': {
                        String stmp = stack2.pop();
                        while (!stack2.isEmpty() && !stmp.equals("(")) {
                            int a = stack1.pop();
                            int b = stack1.pop();
                            int sresulat = calculate(b, a, stmp);
                            if(sresulat<0)
                                return  -1;
                            stack1.push(sresulat);
                            //符号指向下一个计算符号
                            stmp = stack2.pop();
                        }
                        break;
                    }
                    case '=': {
                        String stmp;
                        while (!stack2.isEmpty()) {
                            stmp = stack2.pop();
                            int a = stack1.pop();
                            int b = stack1.pop();
                            int sresulat = calculate(b, a, stmp);
                            if(sresulat<0)
                                return  -1;
                            stack1.push(sresulat);
                        }
                        break;
                    }
                    default: {
                        String stmp;
                        while (!stack2.isEmpty()) {
                            stmp = stack2.pop();
                            //比较优先级
                            if (hashmap.get(stmp) >= hashmap.get(String.valueOf(c))) {
                                int a = stack1.pop();
                                int b = stack1.pop();
                                int sresulat =calculate (b, a, stmp);
                                if(sresulat<0)
                                    return  -1;
                                stack1.push(sresulat);
                            }
                            else {
                                stack2.push(stmp);
                                break;
                            }
                        }
                        //将符号压入符号栈
                        stack2.push(String.valueOf(c));
                        break;
                    }
                }
            }
            else {
                //处理数字
                stack1.push(Integer.valueOf(digit.toString()));
                continue;
            }
            i++;
        }
        //返回栈底得到答案
        return stack1.peek();
    }

    public int calculate(int a, int b, String stmp) { //计算a stmp b的值
        int res = 0;
        char s = stmp.charAt(0);
        switch (s) {
            case '+': {
                res = a + b;
                break;
            }
            case '-': {
                //判断是否产生负数
                res = a - b;
                break;
            }
            case '*': {
                res = a * b;
                break;
            }
            case '÷': {
                if(b==0)
                    return -1;
                    //判断是否产生小数
                else if(a%b!=0)
                    return -2;
                else
                    res = a / b;
                break;
            }
        }
        return res;
    }

    public int[] index(int n, int m){ //产生操作符的下标数组
        Random random = new Random();
        int similar=0;
        int[] a = new int[n];
        for(int j=0;j<n;j++){
            a[j] = random.nextInt(m);
        }
        for(int j=1;j<n;j++){
            if(a[0]==a[j]) similar++;
        }
        //保证一个式子里至少有2个不同的操作符
        if(similar==n-1){
            while(a[n-1] == a[0]){
                a[n-1] = random.nextInt(m);
            }
        }
        return a;
    }

    public String[] create(int max, int min, int maxOper,  int isBrac, int isMul){
//        int max=10 ;
//        int min=0 ;
//        int maxOper=5;
//        int isBrac=1;
//        int isMul=4;
        String[] string = new String[2];
        Random r = new Random();
        String[] opertor = {"+","-","*","÷"};
        //操作符的个数
        int operatorNum = maxOper;
        //新建数组来保存操作数
        int[] number = new  int[operatorNum+1];
        //操作符的下标,若isMul等于2，则没有乘除法，若isMul等于4，则含有乘除法
        int[] arr = index(operatorNum, isMul);
        String s = new String();

        for(int j=0;j<operatorNum+1;j++){
            number[j] = min + r.nextInt(max-min);
        }

        //如果flag=0，则该式子加括号，如果flag=1，则该式子不加括号（自己设定）

        switch (operatorNum){
            case 3:{
                if(isBrac == 0){
                    s = "(" + number[0] + opertor[arr[0]] + number[1] + ")" +opertor[arr[1]]+ "(" + number[2] + opertor[arr[2]] + number[3] + ")";
                }
                else{
                    s = number[0] + opertor[arr[0]] + number[1]+opertor[arr[1]]+number[2] + opertor[arr[2]] + number[3] ;
                }
                break;
            }
            case 4:{
                if(isBrac ==0){
                    s = "(" + "(" + number[0] + opertor[arr[0]] + number[1] + ")" + opertor[arr[1]] + number[2] + ")" + opertor[arr[2]] + "(" + number[3] + opertor[arr[3]] + number[4] + ")";
                }
                else{
                    s = number[0] + opertor[arr[0]] + number[1] +opertor[arr[1]] + number[2] + opertor[arr[2]] + number[3] + opertor[arr[3]] + number[4] ;
                }
                break;
            }
            case 5:{
                if(isBrac ==0){
                    s = "(" + "(" + number[0] + opertor[arr[0]]+number[1]+")" + opertor[arr[1]] + number[2] + ")" +opertor[arr[2]]+ "(" + "(" + number[3] + opertor[arr[3]] + number[4] + ")" + opertor[arr[4]] + number[5] + ")";
                }
                else{
                    s = number[0] + opertor[arr[0]]+number[1]+ opertor[arr[1]] + number[2] +opertor[arr[2]] + number[3] + opertor[arr[3]] + number[4] + opertor[arr[4]] + number[5] ;
                }
                break;
            }
        }
        s+="=";
        int answer = algorithm(s);

        //判断式子是否符合要求，凡是返回负数的就是不合格的
        if(answer>min && answer<max){
            string[0] = s;
            string[1] = String.valueOf(answer);
            return string;

        }else{
            return create(max, min, maxOper, isBrac, isMul);
        }
    }
}
