package com.guavaStudy.base;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.guavaStudy.Person;

/**
 * @Description 常见 Object 方法
 * @Date 2019/4/2 0002 下午 4:34
 * @Created by Pengrenjun
 */
public class objects {

    public static void main(String[] args) {

        //当一个对象中的字段可以为 null 时，实现 Object.equals 方法会很痛苦，因为不得不分别对它们进行 null 检
        //查。使用 Objects.equal 帮助你执行 null 敏感的 equals 判断，从而避免抛出 NullPointerException。
        /**
         * Objects.equal("a", "a"); // returns true
         * Objects.equal(null, "a"); // returns false
         * Objects.equal("a", null); // returns false
         * Objects.equal(null, null); // returns true
         */
        testEquals();

        //好的 toString 方法在调试时是无价之宝，但是编写 toString 方法有时候却很痛苦。
        //使用 Objects.toStringHelper 可以轻松编写有用的 toString 方法。
        testToString();


        /**
         * 实现一个比较器[Comparator]，或者直接实现 Comparable 接口有时也伤不起
         * class Person implements Comparable<Person> {
         * private String lastName;
         * private String firstName;
         * private int zipCode;
         * public int compareTo(Person other) {
         * int cmp = lastName.compareTo(other.lastName);
         * if (cmp != 0) {
         * return cmp;
         * }
         * cmp = firstName.compareTo(other.firstName);
         * if (cmp != 0) {
         * return cmp;
         * }
         * return Integer.compare(zipCode, other.zipCode);
         * }
         * }
         *
         * 这部分代码太琐碎了，因此很容易搞乱，也很难调试。我们应该能把这种代码变得更优雅，为此，Guava 提供了
         * ComparisonChain。
         *
         * ComparisonChain 执行一种懒比较：它执行比较操作直至发现非零的结果，在那之后的比较输入将被忽略。
         */
        testCompareAndcompareTo();

    }

    private static void testCompareAndcompareTo() {

        Person person1=Person.personList.get(0);
        Person person2=Person.personList.get(1);

        int result = ComparisonChain.start()
                .compare(person1.getFirstName(), person2.getFirstName())
                .compare(person1.getLastName(), person2.getLastName())
                .compare(person1.getZipCode(), person2.getZipCode())
                .result();
        System.out.println(result);
    }



    private static void testToString() {
        String x = MoreObjects.toStringHelper(objects.class).add("x", 1).toString();
        System.out.println(x);
        String s = java.util.Objects.toString(new objects());
        System.out.println(s);
    }

    private static void testEquals() {

        String strNull=null;


        try {
            //null值比较会抛出异常
            strNull.equals("qwe");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * public static boolean equal(@Nullable Object a, @Nullable Object b) {
         *     return a == b || (a != null && a.equals(b));
         *   }
         */
        boolean b = Objects.equal(strNull, null);
        System.out.println(b);
    }
}
