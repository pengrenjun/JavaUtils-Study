package com.opslab.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置相关的一些辅助类
 * classloder(path)-> url -> InputStream(File) ->propertities
 */
public class ConfigUrlUtil {

    private static Logger logger = Logger.getLogger(ConfigUrlUtil.class);

    /**
     * 获取配置文件资源的url
     * 通过类加载器 获取资源的具体路径
     * @return
     */
    public static URL findAsResource(final String path) {
        URL url = null;

        ClassLoader contextClassLoader = ClassUtil.getContextClassLoader();
        if (contextClassLoader != null) {
            url = contextClassLoader.getResource(path);
        }
        if (url != null)
            return url;

        url = ConfigUrlUtil.class.getClassLoader().getResource(path);
        if (url != null)
            return url;

        url = ClassLoader.getSystemClassLoader().getResource(path);

        return url;
    }

    /**
     * 获取相对路径的绝对路径
     * @param path
     * @return
     */
    public static String resourcePath(final String path) {
        URL asResource = findAsResource(path);
        return new File(asResource.getFile()).getPath();
    }




    /**
     * 获取配置资源属性列表
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Properties getConfigProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(StreamUtil.resourceStream(path));
        return properties;
    }

    /**
     * 获取配置资源属性某一具体值
     * @param path
     * @param proName
     * @return
     * @throws IOException
     */
    public static Object getConfigProperty( final String path, final String proName) throws IOException {
       return getConfigProperties(path).getProperty(proName);
    }


}
