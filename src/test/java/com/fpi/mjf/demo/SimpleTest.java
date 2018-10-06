package com.fpi.mjf.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SimpleTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.parse("2018-09-01").getTime());
        System.out.println(sdf.parse("2018-09-30").getTime());
    }
}
