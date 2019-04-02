package com.guavaStudy;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 人员
 * @Date 2019/4/2 0002 下午 4:59
 * @Created by Pengrenjun
 */
public class Person implements Comparable<Person> {

    private String lastName;
    private String firstName;
    private int zipCode;
    private Date workDate;

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

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    static {
        Person person1=new Person();
        person1.setFirstName("李");
        person1.setLastName("小明");
        person1.setZipCode(1);
        try {
            person1.setWorkDate(DateUtils.parseDate("2019-01-01","yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Person person2=new Person();
        person2.setFirstName("张");
        person2.setLastName("小强");
        person2.setZipCode(2);
        try {
            person2.setWorkDate(DateUtils.parseDate("2019-01-03","yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Person person3=new Person();
        person3.setFirstName("宋");
        person3.setLastName("小英");
        person3.setZipCode(3);
        try {
            person3.setWorkDate(DateUtils.parseDate("2019-01-02","yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
    }


    @Override
    public int compareTo(Person o) {
        if(o.getZipCode()==this.getZipCode()){
            return 0;
        }

        if(o.getZipCode()>this.getZipCode()){
            return 1;
        }

            return -3;

    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", zipCode=" + zipCode +
                ", workDate=" + DateFormatUtils.format(workDate,"yyyy-MM-dd") +
                '}';
    }
}
