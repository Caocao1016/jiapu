package com.yskj.daishuguan.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: StringUtil
 * @Description:
 */

public class StringUtil {

    public static String checkNull(String s) {
        return s == null ? "" : s;
    }

    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str.trim()) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    public static String[] splite(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(",");
    }

    public static String getValue(String str) {
        if (null == str || "".equals(str.trim()) || "null".equals(str)) {
            return "";
        }
        return str;
    }


    /**
     * 金额逗号
     */
    public static String formatTosepara(String data) {
        Double aDouble = Double.valueOf(data);
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(aDouble);
    }

    public static BigDecimal getActualNUmber(int money, String dayRate) {

        BigDecimal bigDecimal = new BigDecimal(money);

        return bigDecimal.multiply(new BigDecimal(StringUtil.isEmpty(dayRate)? "0" : dayRate).setScale(2,BigDecimal.ROUND_HALF_UP));
    }

    public static BigDecimal getALL(int money, BigDecimal dayRateMoney,String day) {

        BigDecimal bigDecimal = new BigDecimal(money);
        BigDecimal bigDecima = new BigDecimal(day);
        return bigDecimal.add(dayRateMoney.multiply(bigDecima));
    }
    public static BigDecimal getRateMoney(int money, String rate) {

        BigDecimal bigDecimal = new BigDecimal(money);
        BigDecimal bigDecima = new BigDecimal(rate);
        return bigDecimal.multiply(bigDecima.divide(new BigDecimal(100)));
    }

    public static int getNUmber(String max, String min) {

        BigDecimal mMax = new BigDecimal(max);
        BigDecimal mMin = new BigDecimal(min);
        BigDecimal subtract = mMax.subtract(mMin);
        BigDecimal divide = subtract.divide(new BigDecimal(500));
        ;
        BigDecimal add = divide.add(new BigDecimal(1));
        return add.intValue();
    }

    public static String trimStr(String str, String indexStr) {
        if (str == null) {
            return null;
        }
        StringBuilder newStr = new StringBuilder(str);
        if (newStr.indexOf(indexStr) == 0) {
            newStr = new StringBuilder(newStr.substring(indexStr.length())); //在开头

        } else if (newStr.indexOf(indexStr) == newStr.length() - indexStr.length()) {
            newStr = new StringBuilder(newStr.substring(0, newStr.lastIndexOf(indexStr)));//在结尾

        } else if (newStr.indexOf(indexStr) < (newStr.length() - indexStr.length())) {
            newStr = new StringBuilder(newStr.substring(0, newStr.indexOf(indexStr))
                    + newStr.substring(newStr.indexOf(indexStr) + indexStr.length(), newStr.length()));

        }
        return newStr.toString();
    }

    public static String formatToString(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        return decimalFormat.format(number);
    }


    public static String bigDecimalToString(BigDecimal number) {
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        return decimalFormat.format(number);
    }


    public static String URLEncoded(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 精确为两位小数
     */
    public static String setPrice(String money) {
        Double aDouble = Double.valueOf(money);
        return new DecimalFormat("#0.000000").format(aDouble);
    }

    public static <T> String formatMoneyType(T money) {

        String format = null;
        try {
            Double aDouble = Double.valueOf(String.valueOf(money));
            format = new DecimalFormat("#0.00").format(aDouble);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
        return format;
    }


    /**
     * textview设置其中某几个字体的颜色
     * Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终了下标，包括起始下标
     * Spanned.SPAN_INCLUSIVE_INCLUSIVE 从起始下标到终了下标，同时包括起始下标和终了下标
     * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 从起始下标到终了下标，但都不包括起始下标和终了下标
     * Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终了下标，包括终了下标
     *
     * @param msg   内容
     * @param color 颜色
     * @param start 开的位置
     * @param end   结束的位置
     */
    public static SpannableString setSpanStrColor(String msg, String msgLength, String color, int start, int end) {

        SpannableString spannableString = new SpannableString(msg);
        if (msgLength.length() > 1) {
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * textview设置其中某几个字体的大小
     *
     * @param msg   内容
     * @param size  大小
     * @param start 开始的位置
     * @param end   结束的位置
     */
    public static SpannableString setSpanStrSize(CharSequence msg, int size, int start, int end) {
        SpannableString spannableString = new SpannableString(msg);
        spannableString.setSpan(new AbsoluteSizeSpan(size), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 字段去除空格
     */
    public static String SetString(String str) {
        str = str.trim();
        str = str.replaceAll(" ", "");
        str = str.replaceAll(" +", "");
        str = str.replaceAll(" *", "");//正则表达式  *为0到无穷
        str = str.replaceAll("\\s*", "");
        return str;
    }

    // 验证手机号是否为正确手机号
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^(0|86|17951)?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /***
     * 手机号中间四位隐藏
     */
    public static String getString(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(7, 11);
    }


    /**
     * 获取缓存地址
     *
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        }
        if (cachePath == null)
            cachePath = context.getCacheDir().getPath();

        return cachePath;
    }

    /**
     * 递归创建文件夹
     *
     * @param dirPath
     */
    public static void mkDir(String dirPath) {
        String[] dirArray = dirPath.split("/");
        String pathTemp = "";
        for (int i = 1; i < dirArray.length; i++) {
            pathTemp = pathTemp + "/" + dirArray[i];
            File newF = new File(dirArray[0] + pathTemp);
            if (!newF.exists()) {
                newF.mkdir();
            }
        }
    }

}
