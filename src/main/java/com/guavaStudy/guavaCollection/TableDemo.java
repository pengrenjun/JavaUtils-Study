package com.guavaStudy.guavaCollection;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Created by Administrator on 2019/4/5.
 */
public class TableDemo {
    /**
     * ? HashBasedTable���������� HashMap<R, HashMap<C, V>>ʵ�֣�
     * ? TreeBasedTable���������� TreeMap<R, TreeMap<C,V>>ʵ�֣�
     * ? ImmutableTable���������� ImmutableMap<R, ImmutableMap<C, V>>ʵ�֣�ע��ImmutableTable��ϡ����ܼ������ݼ������Ż���
     * ? ArrayTable��Ҫ���ڹ���ʱ��ָ���к��еĴ�С����������һ����ά����ʵ�֣������������ٶȺ��ܼ� Table ���ڴ������ʡ�ArrayTable ������ Table �Ĺ���ԭ���е㲻ͬ����μ� Javadoc �˽����顣
     */

    @Test
    public void testTable(){
        TreeBasedTable<Integer, Integer, Integer> table = TreeBasedTable.create();

        table.put(2, 0, 6);
        table.put(3, 2, 4);
        table.put(0, 0, 5);
        table.put(0, 3, 2);
        table.put(4, 1, 2);
        table.put(4, 4, 9);


        System.out.println("=================== ����table ===============================");
        // ����table
        // cellSet()����Ԫ������Ϊ Table.Cell<R, C, V>�� Set ���� Table<R, C, V>��Cell ������ Map.Entry����
        // �������к������������ֵ�
        for (Table.Cell<Integer, Integer, Integer> cell : table.cellSet()) {

                System.out.println(String.format("%d->%d = %d", cell.getRowKey(), cell.getColumnKey(), cell.getValue()));
        }

        System.out.println("=================== rowMap rowKeySet ===============================");
        //rowMap()���� Map<R, Map<C, V>>���� Table<R, C, V>��ͬ���ģ� rowKeySet()���ء��С��ļ���Set
        SortedMap<Integer, Map<Integer, Integer>> integerMapSortedMap = table.rowMap();

        System.out.println(integerMapSortedMap.toString());

        SortedSet<Integer> integers = table.rowKeySet();
        System.out.println(integers.toString());


        integers.forEach(integer -> System.out.println("rowKey��"+integer+"-> "+integerMapSortedMap.get(integer).toString()));

        System.out.println("=================== columnMap rowKeySet ===============================");

        Map<Integer, Map<Integer, Integer>> integerMapMap = table.columnMap();
        System.out.println(integerMapMap.toString());

        Set<Integer> integers1 = table.columnKeySet();
        System.out.println(integers1.toString());

        System.out.println("=================== row(r) ===============================");
        //row(r) ���� Map<C, V>���ظ������С��������У������ map ���е�д����Ҳ��д�� Table �С�
        //���Ƶ��з��ʷ�����columnMap()��columnKeySet()��column(c)���������еķ��ʻ�Ȼ��ڵ��з�����΢��Ч�㣩
        SortedMap<Integer, Integer> row = table.row(0);
        row.put(1,2);
        System.out.println(table.toString());




    }


}
