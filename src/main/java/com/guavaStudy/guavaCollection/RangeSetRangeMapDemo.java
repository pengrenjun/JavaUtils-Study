package com.guavaStudy.guavaCollection;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2019/4/5.
 */
public class RangeSetRangeMapDemo {

    @Test
    public  void testRangeSet(){

        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        //开闭区间的添加
        rangeSet.add(Range.closed(1, 10)); // {[1,10]}
        rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
        rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
        rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
        rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}

        System.out.println(rangeSet);

        System.out.println("========================== complement ========================= ");
        RangeSet<Integer> complement = rangeSet.complement();
        System.out.println(complement.toString());

        System.out.println("========================= asRanges====================");

        Set<Range<Integer>> ranges = rangeSet.asRanges();
        System.out.println(ranges.toString());

        //是否包含元素 RangeSet 最基本的操作，判断 RangeSet 中是否有任何区间包含给定元素
        boolean contains = rangeSet.contains(30);
        System.out.println(contains);

        //返回包含给定元素的区间；若没有这样的区间，则返回 null
        Range<Integer> integerRange = rangeSet.rangeContaining(10);
        System.out.println(integerRange);

        //span()：返回包括 RangeSet 中所有区间的最小区间
        Range<Integer> span = rangeSet.span();
        System.out.println(span);
    }


    @Test
    public void testRangeMap(){
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo"); //{[1,10] => "foo"}
        rangeMap.put(Range.open(3, 6), "bar"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); //{[1,3] => "foo", (3,6) => "bar", [6,10] => "foo", (10,20) => "foo"}
        rangeMap.remove(Range.closed(5, 11)); //{[1,3] => "foo", (3,5) => "bar", (11,20) => "foo"}

        System.out.println(rangeMap);

        System.out.println("================asMapOfRanges()=======");
        //用 Map<Range, V>表现 RangeMap。这可以用来遍历 RangeMap
        Map<Range<Integer>, String> rangeStringMap = rangeMap.asMapOfRanges();
        System.out.println(rangeStringMap.toString());

        String str = rangeStringMap.get(Range.closed(1, 3));
        System.out.println(str);

        System.out.println("================subRangeMap(Range)==========");
        //用 RangeMap 类型返回 RangeMap 与给定 Range 的交集视图
        RangeMap<Integer, String> integerStringRangeMap = rangeMap.subRangeMap(Range.open(12, 30));
        System.out.println(integerStringRangeMap);

    }


}
