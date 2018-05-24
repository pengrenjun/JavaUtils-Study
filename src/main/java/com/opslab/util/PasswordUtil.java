package com.opslab.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供密码相关的工具类
 */
public final class PasswordUtil {

    /**
     * 必选包含数字、大写字母、小写字母、特殊字符，长度在8到15位
     */

    private static final String SEC_PASSWORD =
            "^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[@!#$%^&*()_+\\.\\-\\?<>'\"|=]+).{8,15}$";

    /**
     * 判断一个密码是否健壮
     * 必选包含数字、大写字母、小写字母、特殊字符，长度在8到15位
     *
     * @param password
     * @return
     */

    public final static boolean isSec(String password) {
        return RegUtil.isMatche(password, SEC_PASSWORD);
    }

    /**
     * Base64加密技术
     *
     * @Description 采用Base64编码具有不可读性，即所编码的数据不会被人用肉眼所直接看到。
     * Base64编码一般用于url的处理，或者说任何你不想让普通人一眼就知道是啥的东西都可以用Base64编码处理后再发布在网络上
     * @return
     */
    static class Base64enCrypt {
        /**
         * Base64加密
         *
         * @param pwstr
         * @return
         */
        public static String encrypt(String pwstr) {
            byte[] encodeBytes = Base64.getEncoder().encode(pwstr.getBytes());
            return new String(encodeBytes);
        }

        /**
         * Base64解密
         *
         * @param pwCode
         * @return
         */
        public static String decode(String pwCode) {
            byte[] decodeBytes = Base64.getDecoder().decode(pwCode.getBytes());
            return new String(decodeBytes);
        }

    }


    /**
     * 字符串加密函数MD5实现 消息摘要算法（Message Digest）
     * 消息摘要（Message Digest）又称为数字摘要(Digital Digest)。
     * 它是一个唯一对应一个消息或文本的固定长度的值，它由一个单向Hash加密函数对消息进行作用而产生
     */
    static class md5 {

        public final static String encrypt(String password) {
            MessageDigest md;
            try {
                // 生成一个MD5加密计算摘要
                md = MessageDigest.getInstance("MD5");
                // 计算md5函数
                md.update(password.getBytes());
                // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
                // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
                String pwd = new BigInteger(1, md.digest()).toString(16);
                return pwd;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return password;
        }

    }

    /**
     * 安全散列算法
     */
    static class SHA {
        public final static String encrypt(String password) {

            MessageDigest md;
            try {
                // 生成一个MD5加密计算摘要
                md = MessageDigest.getInstance("SHA");
                // 计算md5函数
                md.update(password.getBytes());
                // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
                // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
                String pwd = new BigInteger(1, md.digest()).toString(16);
                return pwd;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return password;

        }
    }


    /**
     * 对称加密解密 DES 密码及密钥暂且存在项目文件中(密钥保存在text文件中,密码与加密后的密码,密钥所在文件路径保存在配置文件中)
     */
    static class DES {

        /**
         * DES加密
         */
        public static String encrypt(String password) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

            Cipher cipher;
            SecretKey generateKey;
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            //size
            keyGenerator.init(56);
            //创建key密钥需要进行保存进行解密
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            generateKey = secretKeyFactory.generateSecret(desKeySpec);
            //创建cipher对象实例
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            //初始化
            cipher.init(Cipher.ENCRYPT_MODE, generateKey);
            byte[] resultBytes = cipher.doFinal(password.getBytes());

            //进行DES加密
            String pwCode = HexBin.encode(resultBytes);
            //保存密钥及原密码和加密处理的密码
            savakeyAndpw(secretKey, password, pwCode);
            System.out.println(password + " 加密后的编码为: " + pwCode);
            return pwCode;
        }

        /**
         * DES解密
         */
        public static String decode(String pwCode) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, ClassNotFoundException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

            Cipher cipher = Cipher.getInstance("DES");

            //通过配置文件获取唯一标识
            String onlyFlag = null;
            String filePathName = null;

            Map<String, String> flagAndpwCodeMap = PropertiesUtil.GetAllProperties("flagAndpwCode.properties");
            for (String flag : flagAndpwCodeMap.keySet()) {
                String val = flagAndpwCodeMap.get(flag);
                if (val.equals(pwCode)) {
                    onlyFlag = flag;
                }
            }

            //读取密钥所在文件名称
            Map<String, String> flagAndfileNameMap = PropertiesUtil.GetAllProperties("flagAndfilePath.properties");
            for (String key : flagAndfileNameMap.keySet()) {
                if (key.equals(onlyFlag)) {
                    filePathName = flagAndfileNameMap.get(key);
                }
            }
            //获取密钥
            Map<String, SecretKey> keyMap = (Map<String, SecretKey>) FileUtil.readDate(filePathName);

            cipher.init(Cipher.DECRYPT_MODE, keyMap.get(onlyFlag));
            byte[] result = cipher.doFinal(HexBin.decode(pwCode));
            String password = new String(result);
            System.out.println(pwCode + " 解密后的数据：" + password);
            return password;
        }
    }


    /**
     * 保存key(密钥)和pw以Map形式保存到三个文件中(唯一标识-key密钥, 唯一标识-pw(保存到配置文件中), 唯一标识-pwCode(保存到配置文件中))
     *
     * @param secretKey 密钥
     * @param password  密码
     * @param pwCode    处理后的加密数据
     */
    private static void savakeyAndpw(SecretKey secretKey, String password, String pwCode) throws IOException {

        //唯一标识
        String onlyFlag = RandomUtil.UUID();
        HashMap<String, String> mapA = new HashMap<String, String>() {
            {
                put(onlyFlag, password);
            }
        };
        //保存唯一标识-pw(保存到配置文件中)
        PropertiesUtil.updateProperties("flagAndpw.properties", mapA);

        HashMap<String, String> mapB = new HashMap<String, String>() {
            {
                put(onlyFlag, pwCode);
            }
        };
        //保存唯一标识-pw(保存到配置文件中)
        PropertiesUtil.updateProperties("flagAndpwCode.properties", mapB);


        //保存唯一标识-密钥所在文件名称到配置文件中
        String filePathName = "flagAndSecretKey_" + onlyFlag + ".text";
        HashMap<String, String> mapC = new HashMap<String, String>() {
            {
                put(onlyFlag, filePathName);
            }
        };

        PropertiesUtil.updateProperties("flagAndfilePath.properties", mapC);


        HashMap<String, SecretKey> mapD = new HashMap<String, SecretKey>() {
            {
                put(onlyFlag, secretKey);
            }
        };


        //保存唯一标识-key 到磁盘文件中
        FileUtil.writeDate(filePathName, mapD);
    }

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {
        PasswordUtil.DES.encrypt("12312341");
    }


}








