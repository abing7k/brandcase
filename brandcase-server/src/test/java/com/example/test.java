package com.example;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @PROJECT_NAME: brandcase
 * @DESCRIPTION:
 * @USER: 韩冰
 * @DATE: 2022/10/30 0030 18:04
 */
@SpringBootTest
public class test {
    @Test
    public void test01(){
        String s= "abc";
        String[] strings = new String[s.length()];
        for (int i = 0; i < s.length(); i++) {
            strings[i] = s.substring(i,i+1);
        }
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void test02(){
        String s= "[1,3]";
    }

}
