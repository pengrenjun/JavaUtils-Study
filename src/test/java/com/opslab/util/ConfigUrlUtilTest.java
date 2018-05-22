package com.opslab.util;

import junit.framework.TestCase;

import java.net.URL;


public class ConfigUrlUtilTest extends TestCase {

    public void testFindAsResource(){
        URL url = ConfigUrlUtil.findAsResource("log4j.properties");
        System.out.println(url);

        url = ConfigUrlUtil.findAsResource("0opslab-default.properties");
        System.out.println(url);

        url = ConfigUrlUtil.findAsResource("ali.gif");
        System.out.println(url);
    }

    public void testResourcePath(){
        System.out.println(ConfigUrlUtil.resourcePath(""));
        System.out.println(ConfigUrlUtil.resourcePath("log4j.properties"));

    }


}