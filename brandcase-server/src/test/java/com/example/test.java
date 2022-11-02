package com.example;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @PROJECT_NAME: brandcase
 * @DESCRIPTION:
 * @USER: 韩冰
 * @DATE: 2022/10/30 0030 18:04
 */
@SpringBootTest
public class test {
    @Test
    public void test01() {
        String s = "abc";
        String[] strings = new String[s.length()];
        for (int i = 0; i < s.length(); i++) {
            strings[i] = s.substring(i, i + 1);
        }
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void test02() {
        String str = "[1, 2, 3]";
//        String data = str.substring(str.indexOf("["),str.length());
        ArrayList<Integer> idList = JSON.parseObject(str, ArrayList.class);
        System.out.println(idList);
        //        strings.
//        Integer[] integers = new Integer[strings.length];
//        for (int i = 0; i < strings.length; i++) {
//            integers[i] = Integer.valueOf(strings[i]).intValue();
//        }
//        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(integers));
//        System.out.println(list);
    }

}
