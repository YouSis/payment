package com.wfj.pay.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * Created by wjg on 2017/6/26.
 */
public class CalendarUtil {
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 计算当前季度
     * 格式201701 代表2017的第一季度
     * @return
     */
    public static String getCurrentQuarter() {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int quarter = (month - 1) / 3 + 1;
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + "0" + String.valueOf(quarter);
    }

    /**
     * String(yyyyMMddHHmmSS)转Timestamp
     * @return
     * @throws ParseException
     */
    public static Timestamp getTimestampFromString(String strDate) throws ParseException{
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = formatDate.parse(strDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDate = format.format(date);
        Timestamp time = Timestamp.valueOf(newDate);
        return time;
    }

    /**
     * yyyy-MM-dd hh:mm:ss转long类型
     * @param timestamp
     * @return
     * @throws ParseException
     */
    public static Long getDateLong(Timestamp timestamp) throws ParseException {
        String dateStr = timestamp.toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = format.parse(dateStr);
        return date.getTime();
    }


    /**
     * date根据format转成String类型
     *
     * @param date
     * @return
     */
    public static String getsimpleDateFormat(Date date, String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        return formatDate.format(date);
    }


    /**
     * 日期字符串根据format转成STimestamp类型
     *
     * @return
     * @throws ParseException
     */
    public static Timestamp getTimestampFromString(String strDate,String format) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        Date date = formatDate.parse(strDate);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String newDate = sdf.format(date);
        Timestamp time = Timestamp.valueOf(newDate);
        return time;
    }
}
