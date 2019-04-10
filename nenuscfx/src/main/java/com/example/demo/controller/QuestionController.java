package com.example.demo.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.QuestionService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/")
public class QuestionController {

    @Resource
    QuestionService questionService;

//    @RequestMapping("/create")
//    public String creatFile(@RequestParam("num") int n,  @RequestParam("max") int max, @RequestParam("min") int min, @RequestParam("maxOper") int maxOper, @RequestParam("isBrac") int isBrac, @RequestParam("isMul") int isMul)throws IOException{
//            File file = new File("../../../../../result.txt");
//            if (file.exists()) { //如果文件已存在，则删除文件
//                file.delete();
//            }
//            JSONArray jsonArray = new JSONArray();
//            if(file.createNewFile()){
//
//                FileOutputStream txtfile = new FileOutputStream(file);
//                PrintStream p = new PrintStream(txtfile);
////                p.println("2017012842");
//                for(int i=0;i<n;i++){
//                    String s =  questionService.create(max, min, maxOper, isBrac, isMul);
//                    int answer = questionService.algorithm(s);
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("QUESTION", s);
//                    jsonObject.put("ANSWER",  answer);
//                    jsonArray.add(jsonObject);
//                    p.print(s);
//                    p.println(answer);
//                }
//                txtfile.close();
//                p.close();
//                System.out.println("文件创建成功！");
//            }
//            return jsonArray.toJSONString();
//    }

    @RequestMapping("/downloadFile")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "result.txt";
        if (fileName != null) {
            String realPath = "C:\\Users\\lenovo\\Desktop";
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }


    @RequestMapping("/create")
    public String createFile()throws IOException{
        File file = new File("../result.txt");
        if (file.exists()) { //如果文件已存在，则删除文件
            file.delete();
        }
        JSONArray jsonArray = new JSONArray();
        if(file.createNewFile()){

            FileOutputStream txtfile = new FileOutputStream(file);
            PrintStream p = new PrintStream(txtfile);
//                p.println("2017012842");
            for(int i=0;i<10;i++){
                String[] s =  questionService.create();
                //将式子和答案分开
                String que = s[0];
                String answer = s[1];
                //用阿里巴巴开发的fastjson将式子和答案分别转成JSON，传给前端
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("QUESTION", que);
                jsonObject.put("ANSWER", answer);
                jsonArray.add(jsonObject);
                p.print(que);
                p.println(answer);
            }
            txtfile.close();
            p.close();
            System.out.println("文件创建成功！");
        }
        return jsonArray.toJSONString();
    }

}
