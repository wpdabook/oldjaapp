
package com.a21zhewang.constructionapp.download;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/*
 * This is used to deal with strings.
 */
public class StringUtils
{

	private final static String EMAIL_REGULAR_EXPRESSION = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	/*private final static String PHONE_REGULAR_EXPRESSION = "^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$";*/
	private final static String PHONE_REGULAR_EXPRESSION = "^1[0-9]{10}$";
	
	private final static String EMPTY = "";

	/**
	 * To determine whether a string is empty.
	 * 
	 * @return
	 */
	public static boolean isEmpty(Object input)
	{

		return input == null || input.toString().trim().length() == 0 || "null"
				.equals(input.toString().trim());
	}

	/**
	 * To determine whether a string is email.
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
	{

		if (isEmpty(email))
		{
			return false;
		}
		Pattern emailer = Pattern.compile(EMAIL_REGULAR_EXPRESSION);
		return emailer.matcher(email).matches();
	}

	/**
	 * To determine whether a string is number of phone.
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneNumber(String phone)
	{

		if (isEmpty(phone))
		{
			return false;
		}
		Pattern emailer = Pattern.compile(PHONE_REGULAR_EXPRESSION);
		return emailer.matcher(phone).matches();
	}

	/**
	 * Use the clearer named.
	 * 
	 * @param str
	 * @return
	 */
	public static String clean(Object str)
	{

		return isEmpty(str) ? EMPTY : str.toString();
	}

	/**
	 * encoded in utf-8.
	 * 
	 * @param str
	 * @return
	 */
	public static String utf8Encode(String str)
	{

		if (!isEmpty(str) && str.getBytes().length != str.length())
		{
			try
			{
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * str compare to md5Str.
	 * 
	 * @param org
	 * @param md5Str
	 * @return
	 */
	public static boolean md5StrCompare(Object org, String md5Str)
	{

		String md5_org = MD5Utils.getMD5Str(org);
		return md5_org.equals(md5Str);
	}

	/**
	 * 获取码或书籍的Number
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static String getNumber(String str, String format)
	{

        if (str == null)
            return "";

		if (str.indexOf(format) < 0)
		{
			return null;
		}
		return str.substring(str.indexOf(format) + format.length(), str
				.length());
	}
	
	/**
	 * 取整  去除小数点后两位
	 * @param str
	 * @return
	 */
    @SuppressLint("NewApi")
    public static String ToPrice(double str)
	{
	    NumberFormat nf = new DecimalFormat("#.##");
	 	nf.setRoundingMode(RoundingMode.DOWN);

	    return nf.format(str);
	}
	
    /**
	 * 检测是否有emoji表情
	 * @param source
	 * @return
	 */
   public static boolean containsEmoji(String source) {
   	int len = source.length();        
   	for (int i = 0; i < len; i++) {            
   		char codePoint = source.charAt(i);            
   		if (!isEmojiCharacter(codePoint)) { 
   			//如果不能匹配,则该字符是Emoji表情
               return true;
           }
       }        
   	return false;
   }    
   
   /**
    * 判断是否是Emoji
    *
    * @param codePoint 比较的单个字符
    * @return
    */
   private static boolean isEmojiCharacter(char codePoint) {        
   	return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
               (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
               ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
               && (codePoint <= 0x10FFFF));
   }
	
   //判断是否为纯数字
   public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();  
   }
   
   /**
    * 判断是否为中文
    * @param str
    * @return
    */
   public static boolean isChinese(String str)
   {
	   Pattern pattern= Pattern.compile("[\u4e00-\u9fa5]");
	   return pattern.matcher(str).matches();  
   }
   
   /**
    * 判断是否为英文
    * @param str
    * @return
    */
   public static boolean isEnglish(String str)
   {
	   Pattern pattern= Pattern.compile("[a-zA-Z]");
	   return pattern.matcher(str).matches();  
   }
   
   /**
	 * 判断密码是否是数字英文及普通符号
	 * @param str
	 * @return
	 */
	public static boolean checkPassword(String str)
	{
		Pattern password = Pattern.compile("^[A-Za-z0-9]+$");
		return password.matcher(str).matches();
	}
	
	/**
	 * 转换时间格式 分：秒 mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
		String dateString = formatter.format(d);
		return dateString;
	}
	
	/**
     * 将2002-1-1格式日期转换为毫秒数
     * @param str 带转化字符串
     * @return 毫秒数
     */
    public static long formatDate(String str) {
        if(!isEmpty(str))
        {
            if (str.contains("."))
            {
                str = str.substring(0 , str.indexOf("."));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);
            Date d = null;
            try {
                d = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d.getTime();
        }
        else
        {
            return 0;
        }
    }
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue ){
		float density = context.getResources().getDisplayMetrics().density;
		return ((int) (dpValue * density + 0.5f));
	}

	/**
	 *  根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int px2dip(Context context, float dpValue ){
		float density = context.getResources().getDisplayMetrics().density;
		return ((int) (dpValue /density + 0.5f));
	}
}
