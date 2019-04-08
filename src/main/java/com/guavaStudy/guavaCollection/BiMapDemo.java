package com.guavaStudy.guavaCollection;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;


/**
 * Created by Administrator on 2019/4/5.
 */
public class BiMapDemo {

    /**
     * BiMap<K, V>������� Map��
     ? ������ inverse()��ת BiMap<K, V>�ļ�ֵӳ��
     ? ��ֵ֤��Ψһ�ģ���� values()���� Set ��������ͨ�� Collection

     �� BiMap �У��������Ѽ�ӳ�䵽�Ѿ����ڵ�ֵ�����׳� IllegalArgumentException �쳣��������ض�
     ֵ������Ҫǿ���滻���ļ�����ʹ�� BiMap.forcePut(key, value)
     */
    @Test
    public void testBiMap(){



        BiMap<Integer,String> biMap= HashBiMap.create();

        biMap.put(1,"1a");
        biMap.put(2,"2b");
        biMap.put(3,"3c");

        //java.lang.IllegalArgumentException: value already present: 3c
        //biMap.put(7,"3c");

        //ǿ��ִ��
        biMap.forcePut(7,"3c");

        System.out.println(biMap.toString());

        BiMap<String, Integer> inverse = biMap.inverse();

        System.out.println(inverse.toString());

    }

    /**
     * ö����Map
     */
    @Test
    public void testEnumHashBiMap(){

        EnumHashBiMap<Color,String> enumHashBiMap=EnumHashBiMap.create(Color.class);

        enumHashBiMap.put(Color.YELLOW, "��ɫ");
        enumHashBiMap.put(Color.BLUE, null);
        enumHashBiMap.put(Color.BLACK,"��ɫ");
        enumHashBiMap.put(Color.RED, "��ɫ");
        enumHashBiMap.put(Color.GREEN, "��ɫ");

        System.out.println(enumHashBiMap.toString());

        System.out.println(enumHashBiMap.inverse().toString());



    }

     enum Color{//Ĭ�ϼ̳�  extends Enum�࣬����ö��Ҳ�Ǹ��࣬��Ȼ������й��캯����������������
        RED, BLUE, BLACK, YELLOW, GREEN;//�±�Ϊ0,1,2,3,4

        @Override
        public String toString() {
            return super.toString()+"..."+super.ordinal();//ordinal()������ȡ�±�
        }
    }







}
