package com.guavaStudy;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 人员
 * @Date 2019/4/2 0002 下午 4:59
 * @Created by Pengrenjun
 */
public class Person {

    private String lastName;
    private String firstName;
    private int zipCode;

    public static List<Person> personList=new ArrayList<>();

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    static {
        Person person1=new Person();
        person1.setFirstName("李");
        person1.setLastName("小明");
        person1.setZipCode(123789456);

        Person person2=new Person();
        person2.setFirstName("张");
        person2.setLastName("小强");
        person2.setZipCode(456798789);

        personList.add(person1);
        personList.add(person2);
    }


}
