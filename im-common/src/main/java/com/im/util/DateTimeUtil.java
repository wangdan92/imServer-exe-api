package com.im.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ZhangPeng
 * @date 2010-11-18
 */
public class DateTimeUtil {
    private static final String DEFAULTFORMATSTR = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将日期类型转成特定格式字符串，用默认的"yyyy-MM-dd HH:mm:ss"进行format
     *
     * @return
     */
    public static String format() {
        return format(new Date(), DEFAULTFORMATSTR);
    }

    /**
     * 将日期类型转成特定格式字符串
     *
     * @param formatStr 格式化类型
     * @return
     */
    public static String format(String formatStr) {
        return format(new Date(), formatStr);
    }

    /**
     * 将日期类型转成特定格式字符串
     *
     * @param formatStr 格式化类型
     * @param field     日历类型
     * @param offset    偏移量
     * @return
     */
    public static String format(String formatStr, int field, int offset) {
        return format(new Date(), formatStr, field, offset);
    }

    /**
     * 将日期类型转成特定格式字符串
     *
     * @param dateTime  传入的时间
     * @param formatStr 格式化类型
     * @return
     */
    public static String format(Date dateTime, String formatStr) {
        return format(dateTime, formatStr, -1, 0);
    }

    /**
     * 将long类型转换成特定格式字符串
     *
     * @param millis    传入的时间毫秒
     * @param formatStr 格式化类型
     * @return
     */
    public static String format(long millis, String formatStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.format(cal.getTime());
    }

    /**
     * 将日期类型转成特定格式字符串
     *
     * @param dateTime  传入的时间
     * @param formatStr 格式化类型
     * @param field     日历类型
     * @param offset    偏移量
     * @return
     */
    public static String format(Date dateTime, String formatStr, int field, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        if (field != -1) {
            cal.add(field, offset);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.format(cal.getTime());
    }

    /**
     * 将字符串类型转成日期类型
     *
     * @param dateStr   传入的时间
     * @param formatStr 格式化类型
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateStr, String formatStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.parse(dateStr);
    }

    public static long parseToLong(String dateStr, String formatStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = dateFormat.parse(dateStr);
        return date.getTime();
    }

    /**
     * 日的偏移运算
     *
     * @param day 日偏移量
     * @return
     */
    public static Date offsetDay(int day) {
        return offsetTime(new Date(), 0, 0, day, 0, 0, 0);
    }

    /**
     * 月的偏移运算
     *
     * @param month 月偏移量
     * @return
     */
    public static Date offsetMonth(int month) {
        return offsetTime(new Date(), 0, month, 0, 0, 0, 0);
    }

    /**
     * 日的偏移运算
     *
     * @param dateTime 传入的时间
     * @param day      日偏移量
     * @return
     */
    public static Date offsetDay(Date dateTime, int day) {
        return offsetTime(dateTime, 0, 0, day, 0, 0, 0);
    }

    /**
     * 月的偏移运算
     *
     * @param dateTime 传入的时间
     * @param month    月偏移量
     * @return
     */
    public static Date offsetMonth(Date dateTime, int month) {
        return offsetTime(dateTime, 0, month, 0, 0, 0, 0);
    }

    /**
     * 秒的偏移运算
     *
     * @param second 秒偏移量
     * @return
     */
    public static Date offsetTime(int second) {
        return offsetTime(new Date(), 0, 0, 0, 0, 0, second);
    }

    /**
     * 分秒的偏移运算
     *
     * @param minute 分偏移量
     * @param second 秒偏移量
     * @return
     */
    public static Date offsetTime(int minute, int second) {
        return offsetTime(new Date(), 0, 0, 0, 0, minute, second);
    }

    /**
     * 时分秒的偏移运算
     *
     * @param hour   时偏移量
     * @param minute 分偏移量
     * @param second 秒偏移量
     * @return
     */
    public static Date offsetTime(int hour, int minute, int second) {
        return offsetTime(new Date(), 0, 0, 0, hour, minute, second);
    }

    /**
     * 日时分秒的偏移运算
     *
     * @param day    日偏移量
     * @param hour   时偏移量
     * @param minute 分偏移量
     * @param second 秒偏移量
     * @return
     */
    public static Date offsetTime(int day, int hour, int minute, int second) {
        return offsetTime(new Date(), 0, 0, day, hour, minute, second);
    }

    /**
     * 月日时分秒的偏移运算
     *
     * @param month  月偏移量
     * @param day    日偏移量
     * @param hour   时偏移量
     * @param minute 分偏移量
     * @param second 秒偏移量
     * @return
     */
    public static Date offsetTime(int month, int day, int hour, int minute, int second) {
        return offsetTime(new Date(), 0, month, day, hour, minute, second);
    }

    /**
     * 年月日时分秒的偏移运算
     *
     * @param year   年偏移量
     * @param month  月偏移量
     * @param day    日偏移量
     * @param hour   时偏移量
     * @param minute 分偏移量
     * @param second 秒偏移量
     * @return
     */
    public static Date offsetTime(int year, int month, int day, int hour, int minute, int second) {
        return offsetTime(new Date(), year, month, day, hour, minute, second);
    }

    /**
     * 时间的偏移运算函数
     *
     * @param dateTime 传入的时间
     * @param second   秒偏移量
     * @return 偏移后的时间
     */
    public static Date offsetTime(Date dateTime, int second) {
        return offsetTime(dateTime, 0, 0, 0, 0, 0, second);
    }

    /**
     * 时间的偏移运算函数
     *
     * @param dateTime 传入的时间
     * @param minute   分偏移量
     * @param second   秒偏移量
     * @return 偏移后的时间
     */
    public static Date offsetTime(Date dateTime, int minute, int second) {
        return offsetTime(dateTime, 0, 0, 0, 0, minute, second);
    }

    /**
     * 时间的偏移运算函数
     *
     * @param dateTime 传入的时间
     * @param hour     时偏移量
     * @param minute   分偏移量
     * @param second   秒偏移量
     * @return 偏移后的时间
     */
    public static Date offsetTime(Date dateTime, int hour, int minute, int second) {
        return offsetTime(dateTime, 0, 0, 0, hour, minute, second);
    }

    /**
     * 时间的偏移运算函数
     *
     * @param dateTime 传入的时间
     * @param day      日偏移量
     * @param hour     时偏移量
     * @param minute   分偏移量
     * @param second   秒偏移量
     * @return 偏移后的时间
     */
    public static Date offsetTime(Date dateTime, int day, int hour, int minute, int second) {
        return offsetTime(dateTime, 0, 0, day, hour, minute, second);
    }

    /**
     * 时间的偏移运算函数
     *
     * @param dateTime 传入的时间
     * @param month    月偏移量
     * @param day      日偏移量
     * @param hour     时偏移量
     * @param minute   分偏移量
     * @param second   秒偏移量
     * @return 偏移后的时间
     */
    public static Date offsetTime(Date dateTime, int month, int day, int hour, int minute, int second) {
        return offsetTime(dateTime, 0, month, day, hour, minute, second);
    }

    /**
     * 时间的偏移运算函数
     *
     * @param dateTime 传入的时间
     * @param year     年偏移量
     * @param month    月偏移量
     * @param day      日偏移量
     * @param hour     时偏移量
     * @param minute   分偏移量
     * @param second   秒偏移量
     * @return 偏移后的时间
     */
    public static Date offsetTime(Date dateTime, int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.add(Calendar.YEAR, year);
        cal.add(Calendar.MONTH, month);
        cal.add(Calendar.DATE, day);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        cal.add(Calendar.MINUTE, minute);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    public static Date specifyDay(Date dateTime, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date specifyTime(int second) {
        return specifyTime(new Date(), -1, -1, -1, -1, -1, second);
    }

    public static Date specifyTime(int minute, int second) {
        return specifyTime(new Date(), -1, -1, -1, -1, minute, second);
    }

    public static Date specifyTime(int hour, int minute, int second) {
        return specifyTime(new Date(), -1, -1, -1, hour, minute, second);
    }

    public static Date specifyTime(int day, int hour, int minute, int second) {
        return specifyTime(new Date(), -1, -1, day, hour, minute, second);
    }

    public static Date specifyTime(int month, int day, int hour, int minute, int second) {
        return specifyTime(new Date(), -1, month, day, hour, minute, second);
    }

    public static Date specifyTime(int year, int month, int day, int hour, int minute, int second) {
        return specifyTime(new Date(), year, month, day, hour, minute, second);
    }

    public static Date specifyTime(Date dateTime, int second) {
        return specifyTime(dateTime, -1, -1, -1, -1, -1, second);
    }

    public static Date specifyTime(Date dateTime, int minute, int second) {
        return specifyTime(dateTime, -1, -1, -1, -1, minute, second);
    }

    public static Date specifyTime(Date dateTime, int hour, int minute, int second) {
        return specifyTime(dateTime, -1, -1, -1, hour, minute, second);
    }

    public static Date specifyTime(Date dateTime, int day, int hour, int minute, int second) {
        return specifyTime(dateTime, -1, -1, day, hour, minute, second);
    }

    public static Date specifyTime(Date dateTime, int month, int day, int hour, int minute, int second) {
        return specifyTime(dateTime, -1, month, day, hour, minute, second);
    }

    /**
     * 根据传入的年月日时分秒，指定时间
     *
     * @param dateTime 传入的时间
     * @param year     设置的年
     * @param month    设置的月
     * @param day      设置的天
     * @param hour     设置的时
     * @param minute   设置的分
     * @param second   设置的秒
     * @return 设置后的时间
     */
    public static Date specifyTime(Date dateTime, int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        if (year != -1) {
            cal.set(Calendar.YEAR, year);
        }
        if (month != -1) {
            cal.set(Calendar.MONTH, month);
        }
        if (day != -1) {
            cal.set(Calendar.DATE, day);
        }
        if (hour != -1) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != -1) {
            cal.set(Calendar.MINUTE, minute);
        }
        if (second != -1) {
            cal.set(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

    /**
     * 获得指定日期的天开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date = (Date) cal.getTime();
        return date;
    }

    /**
     * 获得指定日期的天开始时间，并转换为字符串
     *
     * @param date
     * @param formatStr 格式化类型
     * @return
     */
    public static String getStartTimeOfDayStr(Date date, String formatStr) {
        date = getStartTimeOfDay(date);
        return format(date, formatStr);
    }

    /**
     * 获得指定日期的天开始时间，并转换为字符串，用默认的"yyyy-MM-dd HH:mm:ss"进行format
     *
     * @param date
     * @return
     */
    public static String getStartTimeOfDayStr(Date date) {
        return format(getStartTimeOfDay(date), DEFAULTFORMATSTR);
    }

    /**
     * 获得指定日期的天开始时间，并转换为字符串，用默认当前时间的"yyyy-MM-dd HH:mm:ss"进行format
     *
     * @return
     */
    public static String getStartTimeOfDayStr() {
        return format(getStartTimeOfDay(new Date()), DEFAULTFORMATSTR);
    }

    /**
     * 获得指定日期的天结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        date = (Date) cal.getTime();
        return date;
    }

    /**
     * 获得指定日期的天结束时间，并转换为字符串
     *
     * @param date
     * @param formatStr 格式化类型
     * @return
     */
    public static String getEndTimeOfDayStr(Date date, String formatStr) {
        date = getEndTimeOfDay(date);
        return format(date, formatStr);
    }

    /**
     * 获得指定日期的天结束时间，并转换为字符串，用默认的"yyyy-MM-dd HH:mm:ss"进行format
     *
     * @param date
     * @return
     */
    public static String getEndTimeOfDayStr(Date date) {
        return format(getEndTimeOfDay(date), DEFAULTFORMATSTR);
    }

    /**
     * 获得指定日期的天结束时间，并转换为字符串，用默认当前时间的"yyyy-MM-dd HH:mm:ss"进行format
     *
     * @return
     */
    public static String getEndTimeOfDayStr() {
        return format(getEndTimeOfDay(new Date()), DEFAULTFORMATSTR);
    }

    public static Date getStartTimeOfWeek(Date dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static String getStartTimeOfWeekStr(Date date, String formatStr) {
        date = getStartTimeOfWeek(date);
        return format(date, formatStr);
    }

    public static Date getStartTimeOfWeek(String dateStr, String formatStr) throws ParseException {
        Date date = parse(dateStr, formatStr);
        return getStartTimeOfWeek(date);
    }

    public static Date getEndTimeOfWeek(Date dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 7);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date getEndTimeOfWeek(String dateStr, String formatStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = dateFormat.parse(dateStr);
        return getEndTimeOfWeek(date);
    }

    public static String getEndTimeOfWeekStr(Date date, String formatStr) {
        date = getEndTimeOfWeek(date);
        return format(date, formatStr);
    }

    /**
     * 根据传入的时间，获得月开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartTimeOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date = (Date) cal.getTime();
        return date;
    }

    /**
     * 根据传入的时间，获得月结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndTimeOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        date = (Date) cal.getTime();
        return date;
    }

    /**
     * 根据传入的年月字符串，获得月开始时间
     *
     * @param dateStr
     * @param formatStr
     * @return
     * @throws ParseException
     */
    public static Date getStartTimeOfMonth(String dateStr, String formatStr) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = dateFormat.parse(dateStr);
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date = (Date) cal.getTime();
        return date;
    }

    /**
     * 根据传入的年月字符串，获得月结束时间
     *
     * @param dateStr
     * @param formatStr
     * @return
     * @throws ParseException
     */
    public static Date getEndTimeOfMonth(String dateStr, String formatStr) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = dateFormat.parse(dateStr);
        cal.setTime(date);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        date = (Date) cal.getTime();
        return date;
    }

    /**
     * 根据传入的时间，获得年开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartTimeOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date = (Date) cal.getTime();
        return date;
    }

    /**
     * 根据传入的时间，获得年结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndTimeOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        date = (Date) cal.getTime();
        return date;
    }

    /**
     * 返回两个日期相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDistDates(Date startDate, Date endDate) {
        long totalDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
        return totalDate;
    }

    /**
     * 返回两个日期相差的天数 可为负数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDistDate(Date startDate, Date endDate) {
        long totalDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        totalDate = (timeend - timestart) / (1000 * 60 * 60 * 24);
        return totalDate;
    }

    /**
     * 返回两个日期相差的小时数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDistHours(Date startDate, Date endDate) {
        long dist = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        dist = Math.abs((timeend - timestart)) / (1000 * 60 * 60);
        return dist;
    }

    /**
     * 返回两个日期相差的分钟数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDistMinutes(Date startDate, Date endDate) {
        long dist = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        dist = Math.abs((timeend - timestart)) / (1000 * 60);
        return dist;
    }

    /**
     * 返回两个日期相差的秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDistSeconds(Date startDate, Date endDate) {
        long dist = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        dist = Math.abs((timeend - timestart)) / 1000;
        return dist;
    }

    /**
     * 根据传入的日期返回周几
     *
     * @param dateTime
     * @return
     */
    public static String dayForWeek(Date dateTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

    /**
     * 根据传入的开始时间和结束时间，返回中间的时间列表（默认不包含头尾时间）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<Date> listDateBetweenTwoDate(Date startTime, Date endTime) {
        return listDateBetweenTwoDate(startTime, endTime, false);
    }

    /**
     * 根据传入的开始时间和结束时间，返回中间的时间列表
     *
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param includeStartEnd 是否包含头尾时间
     * @return
     */
    public static List<Date> listDateBetweenTwoDate(Date startTime, Date endTime, boolean includeStartEnd) {
        List<Date> dateList = new ArrayList<>();
        if (includeStartEnd) {
            dateList.add(startTime);
        }
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(startTime);
        boolean needContinue = true;
        while (needContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endTime.after(cal.getTime())) {
                dateList.add(cal.getTime());
            } else {
                break;
            }
        }
        if (includeStartEnd) {
            dateList.add(endTime);
        }
        return dateList;
    }

    /**
     * 根据开始时间和结束时间字符串，获取中间的日期字符串列表（默认不包含头尾，默认yyyy-MM-dd格式）
     *
     * @param startTimeStr
     * @param endTimeStr
     * @return
     * @throws ParseException
     */
    public static List<String> listDateStrBetweenTwoDateStr(String startTimeStr, String endTimeStr) throws ParseException {
        return listDateStrBetweenTwoDateStr(startTimeStr, endTimeStr, false, "yyyy-MM-dd");
    }

    /**
     * 根据开始时间和结束时间字符串，获取中间的日期字符串列表（默认不包含头尾）
     *
     * @param startTimeStr
     * @param endTimeStr
     * @param formatStr
     * @return
     * @throws ParseException
     */
    public static List<String> listDateStrBetweenTwoDateStr(String startTimeStr, String endTimeStr, String formatStr) throws ParseException {
        return listDateStrBetweenTwoDateStr(startTimeStr, endTimeStr, false, formatStr);
    }

    /**
     * 根据开始时间和结束时间字符串，获取中间的日期字符串列表
     *
     * @param startTimeStr
     * @param endTimeStr
     * @param includeStartEnd
     * @param formatStr
     * @return
     * @throws ParseException
     */
    public static List<String> listDateStrBetweenTwoDateStr(String startTimeStr, String endTimeStr, boolean includeStartEnd, String formatStr) throws ParseException {
        List<String> dateList = new ArrayList<>();
        if (includeStartEnd) {
            dateList.add(startTimeStr);
        }
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(parse(startTimeStr, formatStr));
        boolean needContinue = true;
        while (needContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            Date endTime = parse(endTimeStr, formatStr);
            if (endTime.after(cal.getTime())) {
                dateList.add(format(cal.getTime(), formatStr));
            } else {
                break;
            }
        }
        if (includeStartEnd) {
            dateList.add(endTimeStr);
        }
        return dateList;
    }

    /**
     * 当前日期加上天数后的日期
     *
     * @param num 为增加的天数
     * @return
     */
    public static String plusDay(int num) {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);
        d = ca.getTime();
        String endDate = format.format(d);
        return endDate;
    }

    /**
     * 判断日期是否是当天
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 判断日期是否是本周
     *
     * @param date
     * @return
     */
    public static boolean isThisWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(date);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    /**
     * 格式化成类似qq的时间显示格式，当天显示时分、最近七天显示周几，其他显示年月日
     *
     * @param date
     * @return
     */
    public static String formatQQ(Date date) {
        String str = "";
        if (isToday(date)) {
            str = format(date, "HH:mm");
        } else if (getDistDates(date, new Date()) <= 7) {
            str = dayForWeek(date);
        } else {
            str = format(date, "yyyy-MM-dd");
        }
        return str;
    }

    /**
     * 获取指定日期是月中的第几天
     *
     * @param date 时间
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取指定时间所在月份
     *
     * @param date 时间
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        //老外计算月份从0开始，转换为国内月份需要加1
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间是否是本月
     *
     * @param date 时间
     */
    public static boolean isThisMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        //老外计算月份从0开始，转换为国内月份需要加1
        calendar.setTime(date);
        int paramMonth = calendar.get(Calendar.MONTH) + 1;
        return currentMonth == paramMonth;
    }

    /**
     * 获取指定时间所在月份天数
     *
     * @param date 时间
     */
    public static int getDaysByYearMonth(Date date) {
        Calendar a = Calendar.getInstance();
        //a.set(Calendar.YEAR,year); 
        a.set(Calendar.MONTH, getMonth(date) - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    /**
     * 获取指定时间所在季度
     *
     * @param date
     */
    public static int getDuarterDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int quarter = 0;
        //判断季度
        if (month <= 3) {
            quarter = 1;
        } else if (month <= 6) {
            quarter = 2;
        } else if (month <= 9) {
            quarter = 3;
        } else {
            quarter = 4;
        }
        return quarter;
    }

    /**
     * 时间是否在同一季度
     *
     * @param one ,tow
     */
    public static boolean isEqualDuarterDay(Date one, Date tow) {
        return getDuarterDay(one) == getDuarterDay(tow);
    }

    /**
     * 时间是否在同一年
     *
     * @param date1 ,date2
     */
    public static boolean isSameYear(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
    }

    public static void main(String[] args) throws ParseException {
        int num = getDaysByYearMonth(new Date());
        System.out.println(isThisMonth(parse("2019-11-01", "yyyy-MM-dd")));
        System.out.println(isSameYear(parse("2015-11-01", "yyyy-MM-dd"),new Date()));
        System.out.println(num);
    }
}