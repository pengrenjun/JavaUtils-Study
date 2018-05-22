package com.opslab.util;


import org.apache.log4j.Logger;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 提供一些常用的属性文件相关的方法
 */
public final class PropertiesUtil {
    public static Logger logger = Logger.getLogger(PropertiesUtil.class);

    /**
     * 从系统属性文件中获取相应的值
     *
     * @param key key
     * @return 返回value
     */
    public final static String key(String key) {
        return System.getProperty(key);
    }

    /**
     * 根据Key读取配置文件的Value
     *
     * @param filePath 属性文件
     * @param key      需要读取的属性
     */
    public final static String GetValueByKey(String filePath, String key) {
        Properties pps = new Properties();
        try (InputStream in = StreamUtil.resourceStream(filePath)) {
            pps.load(in);
            return pps.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将配置文件的键和值转为Map集合
     * @param in
     * @return
     */
    public final static Map<String,String> properties(InputStream in){
        Map<String,String> map = new HashMap<>();
        Properties pps = new Properties();
        try {
            pps.load(in);
        } catch (IOException e) {
            logger.error("load properties error:"+e.getMessage());
        }
        Enumeration en = pps.propertyNames();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            map.put(strKey,strValue);
        }
        return map;
    }
    /**
     * 读取Properties的全部信息(Map集合)
     *
     * @param filePath 读取的属性文件
     * @return 返回所有的属性 key:value<>key:value
     */
    public final static Map<String,String> GetAllProperties(String filePath) throws IOException {
        Map<String,String> map = new HashMap<>();
        Properties pps = new Properties();
        try (InputStream in =StreamUtil.resourceStream(filePath)) {
            return properties(in);
        }catch (IOException e){
            logger.error("load properties error");
        }
        return map;
    }


    /**
     * 传递键值对的Map，保存更新properties文件
     *
     * @param fileName
     *            文件名(放在resource源包目录下)，需要后缀
     * @param keyValueMap
     *            键值对Map
     */
    public static void updateProperties(String fileName,Map<String, String> keyValueMap) {
        // InputStream
        // inputStream=PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);//输入流
        // 文件的路径
        String filePath = PropertiesUtil.class.getClassLoader()
                .getResource(fileName).getFile();
        System.out.println("propertiesPath:" + filePath);
        Properties props = new Properties();
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            // 从输入流中读取属性列表（键和元素对）
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            props.load(br);
            br.close();

            // 写入属性文件
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
             //props.clear();// 清空旧的文件
            Map<String,String> oldMap=GetAllProperties(fileName);
            //sava
            for(String key:oldMap.keySet()){
                props.setProperty(key,oldMap.get(key));
            }
            //update
            for (String key : keyValueMap.keySet()){
                props.setProperty(key, keyValueMap.get(key));
            }

            props.store(bw, "");
            bw.close();
        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for updating " + ""+ " value error");
        } finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {

        Map<String,String> map=new HashMap<>();
        map.put("asd","asdasd");
        updateProperties("0opslab-default.properties",map);

        System.out.println(GetValueByKey("0opslab-default.properties","userName"));


    }

}
