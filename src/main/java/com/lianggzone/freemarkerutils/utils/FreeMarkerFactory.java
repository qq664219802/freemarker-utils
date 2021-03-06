/*
 * Copyright 2013-2015 lianggzone all rights reserved.
 * @license http://www.lianggzone.com/about
 */
package com.lianggzone.freemarkerutils.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * FreeMarker生产工厂
 * @author 粱桂钊
 * @since 
 * <p>更新时间: 2015年9月11日  v0.1</p><p>版本内容: 创建</p>
 */
public class FreeMarkerFactory {

    /**
     * 根据ftl模板文件,生成静态HTML文件
     * @param ftlPath   FTL模板文件路径,例如["c:/liang/template.ftl"]
     * @param filePath  生成HMTL文件路径，例如["d:/liang/lianggzone.html"]
     * @param data      Map数据
     * @return
     */
    public static boolean createHTML(String ftlPath, String filePath, Map<String, Object> data) throws IOException{    
        return createHTML(ftlPath, filePath, data, true);
    }
    
    /**
     * 根据ftl模板文件,生成静态HTML文件
     * @param ftlPath   FTL模板文件路径,例如["c:/liang/template.ftl"]
     * @param filePath  生成HMTL文件路径，例如["d:/liang/lianggzone.html"]
     * @param data      Map数据
     * @param isCreate4NoExists      是否文件夹不存在的时候创建
     * @return
     */
    public static boolean createHTML(String ftlPath, String filePath, Map<String, Object> data, boolean isCreate4NoExists) throws IOException{
        String fileDir = StringUtils.substringBeforeLast(filePath, "/");  // 获取HMTL文件目录
//      String fileName = StringUtils.substringAfterLast(filePath, "/");  // 获取HMTL文件名
        String ftlDir = StringUtils.substringBeforeLast(ftlPath, "/");    // 获取FTL文件目录
        String ftlName = StringUtils.substringAfterLast(ftlPath, "/");    // 获取FTL文件名 
        
        //文件递归创建生成文件目录
        if(isCreate4NoExists){
            File realDirectory = new File(fileDir);
            if (!realDirectory.exists()) {
                realDirectory.mkdirs();
            }
        }
        
        // step1 获取freemarker的配置
        Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_23);
        // step2 设置freemarker模板所放置的位置(文件夹)
        freemarkerCfg.setDirectoryForTemplateLoading(new File(ftlDir));
        // step3 设置freemarker模板编码
        freemarkerCfg.setEncoding(Locale.getDefault(), CharEncoding.UTF_8);
        // step4 找到对应freemarker模板并实例化
        Template template = freemarkerCfg.getTemplate(ftlName, CharEncoding.UTF_8); 
        // step5 初始化一个IO流
        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), CharEncoding.UTF_8))) {
            writer.flush();
            // step6 模板渲染出所要的内容
            template.process(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
