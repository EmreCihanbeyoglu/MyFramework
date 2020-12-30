package com.tester;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    // MMMM dd, yyy  --> February 17, 2019
    public static String todaysDate() {
        String today = new SimpleDateFormat("MMMM dd, yyy").format(new Date());
        return today;
    }

    // MM-dd-yy     --> 02-17-1999
    public static String todaysDate2() {
        String today = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        return today;
    }

    // dd-MM-yyyy    --> 17-02-1990
    public static String todaysDate3() {
        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        return today;
    }

    // yyyy-MM-dd hh:mm:ss   --> 2019-02-17 03:45:11
    public static String todaysDate4() {
        String today = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        return today;
    }

    public static String todaysDate5() {
        String today = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date());
        return today;
    }

    public static String todaysDate6() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return today;
    }
}
