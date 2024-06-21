package com.sp.common.util;


import com.alibaba.fastjson.JSONObject;
import com.sp.common.exception.CommonException;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 基本工具类
 * 
 * @author 印鲜刚
 * @version 1.0.0
 * @since 1.0.0
 */
public class ObjectHelper {

	static Logger logger = LoggerFactory.getLogger(ObjectHelper.class);
	public static  Date getNowAddMinutes(int iMinutes) {
		Date currentTime = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(currentTime);
        rightNow.add(Calendar.MINUTE,iMinutes);//日期加10天
        Date dt1=rightNow.getTime();
         return dt1;
	}
	public static void main(String[] p) throws Exception{
//		StringBuffer sb = new StringBuffer();
//		System.out.println(sb.length());
//		System.out.println(ObjectHelper.isEmpty(sb));
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("d", "dddddddddddd");
//		map.put("s", "dsdddddddddddd");
//		map.put("b", "aadddddddddddd");
//		JSONArray js = JSONArray.fromObject(map);
//		System.out.println(js.toString());
//		System.out.println(objectToJson(map, "ddd"));
		 Map<String, Object>  maps=getAllEnumByClassName("com.pg.scf.common.common.BusinessType");
		
		logger.info("aal:{}",JSONObject.toJSONString(maps));
	}
	/**
     * 根据枚举的字符串获取枚举的值
     *
     * @param className 包名+类名
     * @return
     * @throws Exception
     */
    public static  Map<String, Object>  getAllEnumByClassName(String className) throws Exception {
        // 1.得到枚举类对象
        Class<Enum> clz = (Class<Enum>) Class.forName(className);
        // 2.得到所有枚举常量
        Object[] objects = clz.getEnumConstants();
        Method getCode = clz.getMethod("getValue");
        Method getMessage = clz.getMethod("getText");
        
        Map<String, Object> map = new HashMap<String, Object>();
        Object obj1;
        for (Object obj : objects) {
        	obj1=getCode.invoke(obj);
        	if (obj1!=null && obj1.toString().equals("VisitRole"))
        	{
        		continue;
        	}
            map.put(getCode.invoke(obj).toString(),getMessage.invoke(obj));
        }
        return map;
    }
	public static boolean isEquals(Object object1, Object object2) {
		boolean ret = false;
		try {
			if (object1 == null && object2 == null) {
				ret = true;
				return ret;
			}
			ret = object1.equals(object2);
		} catch (NullPointerException e) {
			ret = false;
		}
		return ret;

	}

	/**
	 * 比较两字符串是否相等，忽略大小写
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equalsIgnorecase(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 != null && s2 != null) {
			if (s1.toLowerCase().equals(s2.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *            -参数对象
	 * @return boolean -true:表示对象为空;false:表示对象为非空 集合： Collection.isEmpty()
	 *         数组：判断数组每个元素，所有元素都为空，即判定数组为空
	 *         字符串：判断字符串等于"null"，或去除两端""字窜后返回String.isEmpty()的结果 其它类型返回 false
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).entrySet().isEmpty();
		}

		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}

		if (obj instanceof String) {
			return ((String) obj).equalsIgnoreCase("null") | ((String) obj).trim().isEmpty();
		}

		if (obj instanceof StringBuffer) {
			return ((StringBuffer) obj).length() == 0;
		}

		if (obj.getClass().isArray()) {
			try {
				Object[] a = (Object[]) obj;

				boolean b = true;
				for (Object o : a) {
					b = b & ObjectHelper.isEmpty(o);

					if (!b) {
						break;
					}
				}

				return b;
			} catch (ClassCastException e) {
			}
		}

		return false;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 校验邮箱是否合规
	 * 
	 * @param email
	 *            邮箱
	 * @return true 合规 false 不合规
	 */
	public static boolean isEmailFormat(String email) {

		Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	public static String DateToStr(Date date,String dateformat) { 
		   if (date==null)
		   {
			   return "";
		   }
		   SimpleDateFormat format = new SimpleDateFormat(dateformat); 
		   String str = format.format(date); 
		   return str; 
	} 
	/***
	 * 计算天数
	 * @param startDate
	 * @param endDate
	 * @param adjustmentDays
	 * @return
	 */
	public static int getGapCount(Date startDate, Date endDate, Integer adjustmentDays) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);
		if (adjustmentDays != null) {
			toCalendar.add(Calendar.HOUR_OF_DAY, adjustmentDays.intValue());
		}
		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}
    public static Date strToDate(String sDate,String dateFormat)
    {
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dateFormat);
        
        try {
            Date date=simpleDateFormat.parse(sDate);
            System.out.println(date);
            return date;
        } catch(ParseException px) {
            px.printStackTrace();
            throw new CommonException("日期格式错误："+sDate);
        }
    }
	public static boolean isEmailAdressFormat(String email) {
		boolean isExist = false;
		if (isEmpty(email)) {
			return isExist;
		}
		Pattern p = Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
		Matcher m = p.matcher(email);
		boolean b = m.matches();
		if (b) {
			isExist = true;
		}
		return isExist;
	}
	
	public static String getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static String getNowDate(String format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static String getNowDate(String format,int iDays) {
		Date currentTime = new Date();
		 Calendar rightNow = Calendar.getInstance();
         rightNow.setTime(currentTime);
         rightNow.add(Calendar.DAY_OF_YEAR,iDays);//日期加10天
         Date dt1=rightNow.getTime();
         SimpleDateFormat formatter = new SimpleDateFormat(format);
		 String dateString = formatter.format(dt1);
		 return dateString;
 	}
 	public static String getDate(Date date,String format) {
 		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		return dateString;
	}
 	
 	
 	public static String getAddDate(Date date,String format,int iDays) {
         Calendar rightNow = Calendar.getInstance();
         rightNow.setTime(date);
         rightNow.add(Calendar.DAY_OF_YEAR,iDays);//日期加10天
         Date dt1=rightNow.getTime();
         SimpleDateFormat formatter = new SimpleDateFormat(format);
		 String dateString = formatter.format(dt1);
		 return dateString;
	}
 	
 	
 	public static  Date getAddMonthsDate(Date date,String format,int imonth) {
         Calendar rightNow = Calendar.getInstance();
         rightNow.setTime(date);
         rightNow.add(Calendar.MONTH,imonth);//日期加10天
         Date dt1=rightNow.getTime();
         /*SimpleDateFormat formatter = new SimpleDateFormat(format);
		 String dateString = formatter.format(dt1);
		 return dateString;*/
         return dt1;
	}
 	public static  Date convertStringToDate(String sDate,String format)
 	{
 	 	try  
 		{  
 		    SimpleDateFormat sdf = new SimpleDateFormat(format);  
 		    Date date = sdf.parse(sDate);  
 		    return date;
 		}  
 		catch (ParseException e)  
 		{  
 		    e.printStackTrace();
 		    return null;
 		}  
 	}
	public static String getAddMonthsString(Date date,String format,int imonth) {
         Calendar rightNow = Calendar.getInstance();
         rightNow.setTime(date);
         rightNow.add(Calendar.MONTH,imonth);//日期加10天
         Date dt1=rightNow.getTime();
         SimpleDateFormat formatter = new SimpleDateFormat(format);
		 String dateString = formatter.format(dt1);
		 return dateString; 
          
	}
	
	
	public static String getDateAddMonthsString(String sdate,String format,int imonth) {
		Date date=convertStringToDate(sdate,format);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH,imonth);//日期加10天
        Date dt1=rightNow.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(dt1);
		return dateString; 
         
	}

	/**
	 * 在obj上获取指定属性(expr, 形如 field.attr)的值，filed是obj上的属性，并对应有getField()方法
	 * 
	 * @param obj
	 * @param expr
	 * @return
	 */
	public static Object getFieldValue(Object obj, String expr) throws CommonException {
		if (obj == null) {
			throw new CommonException(expr);
		}

		Object value = null;
		if (ObjectHelper.isEmpty(expr)) {
			value = obj;
		} else {
			value = resolveVariableEL(obj, expr);
		}

		return value;
	}

	/**
	 * 从objs中获取指定属性(expr,形如
	 * arg.field.attr)的值，arg对应Map中的key,field是key对应的value对象上的属性
	 * ，value上并对应有getField()方法
	 * @param objs
	 * @param expr
	 * @return
	 * @throws CommonException
	 */
	public static Object getFieldValue(Map<String, Object> objs, String expr) throws CommonException {
		// 查找第一个值对象
		String targetNm = null;
		if (ObjectHelper.isNotEmpty(expr)) {
			int pos = expr.indexOf(".");
			if (pos > -1) {
				targetNm = expr.substring(0, pos);
				expr = expr.substring(pos + 1);
			} else {
				targetNm = expr;
				expr = null;
			}
		}

		Object targetObject = objs.get(targetNm);
		if (targetObject == null) {
			throw new CommonException(targetNm);
		}

		Object value = null;
		if (ObjectHelper.isEmpty(expr)) {
			value = targetObject;
		} else {
			value = resolveVariableEL(targetObject, expr);
		}

		return value;
	}

	private static Object resolveVariableEL(Object target, String el) {
		int pos = el.indexOf(".");

		if (pos > -1) {
			String field = el.substring(0, pos);
			logger.debug("field" + field);
			el = el.substring(pos + 1);
			logger.debug("el = " + el);

			Object value = getValue(target, field);
			if (ObjectHelper.isNotEmpty(el)) {
				value = resolveVariableEL(value, el);
			}

			return value;
		} else {
			return getValue(target, el);
		}
	}

	private static Object getValue(Object target, String field) {
		Method m;
		try {
			m = target.getClass().getMethod("get" + StringUtils.capitalize(field));

			return m.invoke(target, new Object[] {});
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	// /**
	// * 判断Map<String,Object>里指定key的value值是否为空
	// *
	// * @param params
	// * @param key
	// * @return boolean -true:表示value为空;false:表示value为非空
	// */
	// public static boolean isEmpty(Map<String, Object> params, String key) {
	// if (isEmpty(params))
	// return true;
	// else {
	// if (params.containsKey(key) && !isEmpty(params.get(key)))
	// return false;
	// return true;
	// }
	// }
	//
	//
	// /**
	// * 使用模型驱动的时候将模型驱动vo进行字符串解码
	// *
	// * @param obj
	// * 待转码的vo
	// * @throws IllegalArgumentException
	// * 参数错误！参数不能为空。。。
	// * @throws IllegalAccessException
	// * 参数错误！
	// * @throws UnsupportedEncodingException
	// * 不支持的编码方式
	// */
	// public static void decodeObject(Object obj)
	// throws IllegalArgumentException, IllegalAccessException,
	// UnsupportedEncodingException {
	// if (StringUtil.isEmpty(obj)) {
	// return;
	// }
	// // 取得该对象里面所有定义的字段,并对每个字段进行转码
	// for (Field field : obj.getClass().getDeclaredFields()) {
	// // 将此对象的 accessible 标志设置为指示的布尔值。(即,当该字段为private时,也可以访问)
	// field.setAccessible(true);
	// // 回指定对象上此 Field 表示的字段的值。(即,取得传入对象中改字段的值)
	// Object fieldObj = field.get(obj);
	// if (!StringUtil.isEmpty(fieldObj)) {
	// // 只有在字段为String类型的时候才有中文乱码,因为如果是其他类型的话,在类型转换的时候就出错了
	// if (field.getType() == String.class) {
	// // 将指定对象变量上此 Field 对象表示的字段设置为指定的新值。(即,将传入的对象里面的这个字段设置为转码后的值)
	// field.set(
	// obj,
	// !fieldObj.toString().trim().isEmpty() ? URLDecoder
	// .decode(fieldObj.toString(),
	// CommonConstant.UTF8) : null);
	// }
	// }
	// }
	// }
	//
	// /**
	// * 字符串解码
	// *
	// * @param str
	// * 待转码的字符串
	// * @throws UnsupportedEncodingException
	// * 不支持的编码方式
	// */
	// public static void decodeString(String str)
	// throws UnsupportedEncodingException {
	// str = URLDecoder.decode(str, CommonConstant.UTF8);
	// }
	//
	// /**
	// * 将指定字符串的后4个字符替换成*
	// *
	// * @param source
	// * @param len
	// * @return
	// */
	// public static String replacementPartString(String source) {
	// return StringUtil.replacementPartString(source, 4, '*');
	// }
	//
	// /**
	// * 将指定字符串的后Len个字符替换成*
	// *
	// * @param source
	// * @param len
	// * @return
	// */
	// public static String replacementPartString(String source, int len) {
	// return StringUtil.replacementPartString(source, len, '*');
	// }
	//
	// /**
	// * 将指定字符串的后Len个字符替换成replaceStr
	// *
	// * @param source
	// * @param len
	// * @param replaceStr
	// * @return
	// */
	// public static String replacementPartString(String source, int len,
	// char replaceStr) {
	// if (StringUtil.isEmpty(source))
	// return "";
	// else {
	// if (source.length() < 4)
	// return source;
	// else {
	// String dest = source.substring(0, source.length() - 4);
	// for (int i = 0; i < len; i++) {
	// dest += replaceStr;
	// }
	// return dest;
	// }
	// }
	//
	// }
	//
	// /**
	// * 用于处理编号（应用与公文等）
	// *
	// */
	// public static String idCode(String profix, String type, String year,
	// String seq, int len) {
	// String idCode = profix + type + year;
	// for (int i = seq.length(); i < len; i++) {
	// seq = "0" + seq;
	// }
	// return idCode + seq;
	// }
	//
	// /**
	// * 将一个空的object 转换成""
	// *
	// * @param obj
	// * @return
	// */
	// public static String toChangeString(Object obj) {
	// if (obj == null) {
	// obj = "";
	// }
	// return String.valueOf(obj);
	// }
	//
	// /**
	// * 将验证码转换成小写
	// *
	// * @param str
	// * @return
	// */
	// public static String validateToLow(Object str) {
	// String validate = "";
	// if (str != null) {
	// validate = str.toString().toLowerCase();
	// }
	// return validate;
	// }
	//
	//
	// /**
	// * 验证传入的字符串是否是数字
	// *
	// * @Title: PartternValidateNm
	// * @Description:
	// * @param @return
	// * @return boolean
	// * @throws
	// */
	// public static boolean PartternValidateNm(String str) {
	// String pattern = "[0-9]*";
	// Pattern p = Pattern.compile(pattern);
	// Matcher m = p.matcher(str);
	// boolean b = m.matches();
	// return b;
	// }
	//
	// /**
	// * 将一个int数组转换成String
	// *
	// * @param count
	// * @return
	// */
	// public static String intsToString(int[] count) {
	// String str = "";
	// for (int value : count) {
	// str = str + String.valueOf(value) + "@";
	// }
	// return str;
	// }

	/**
	 * 将string 转换成 回调函数+名称
	 * 
	 * @param str
	 * @param jsonCallBack
	 * @return
	 */
	public static String objectToJson(String str, String jsonCallBack) {
		return jsonCallBack + "('" + str + "')";
	}

	/**
	 * 将string 转换成 回调函数+名称
	 * @param flag
	 * @param jsonCallBack
	 * @return
	 */
	public static String objectToJson(Boolean flag, String jsonCallBack) {
		return jsonCallBack + "(" + flag + ")";
	}

	/**
	 * 将list 转换成 回调函数+名称
	 * 
	 * @param list
	 * @param jsonCallBack
	 * @return
	 */
	public static String objectToJson(List<?> list, String jsonCallBack) {
		String temp = JSONObject.toJSONString(list);
		return jsonCallBack + "(" + temp + ")";
	}

	/**
	 * 将list 转换成 回调函数+名称
	 * 
	 * @param set
	 * @return
	 */
	public static String objectToJson(List<?> set) {
		return JSONObject.toJSONString(set);
	}

	/**
	 * 将collection转换成 回调函数+名称
	 * 
	 * @param coll
	 * @return
	 */
	public static String objectToJson(Set<?> coll) {
		return JSONObject.toJSONString(coll);
	}

	/**
	 * 将hashmap 转换成 回调函数+名称
	 * 
	 * @param map
	 * @param jsonCallBack
	 * @return
	 */
	public static String objectToJson(Map<?, ?> map, String jsonCallBack) {
		String res = "";
		res = JSONObject.toJSONString(map);
		return jsonCallBack + "(" + res + ")";
	}

	/**
	 * 将hashmap 转换成 回调函数+名称
	 * 
	 * @param obj
	 * @param jsonCallBack
	 * @return
	 */
	public static String objectToJson(Object obj, String jsonCallBack) {
		String res = "";
		res = JSONObject.toJSONString(obj);
		return jsonCallBack + "(" + res + ")";
	}

	/**
	 * 将对象转换成json
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj) {
		String res = "";
		res = JSONObject.toJSONString(obj);
		return res;
	}

	/**
	 * 实例化HibernateProxy到具体的实体对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Object initializeProxy(Object obj) {
		if (obj instanceof HibernateProxy) {
			obj = ((HibernateProxy) obj).getHibernateLazyInitializer().getImplementation();
		}

		return obj;
	}

	/**
	 * 判断obj是否实现了type接口,如果type不是接口类型，直接返回false
	 * 
	 * @param obj
	 *            要检测的对象
	 * @param type
	 *            接口的类型
	 * @return
	 */
	public static boolean isImplement(Object obj, Class<?> type) {
		if (!type.isInterface()) {
			return false;
		}

		Class<?>[] clzz = obj.getClass().getInterfaces();

		for (Class<?> clz : clzz) {
			if (clz.getName().equals(type.getName())) {
				return true;
			}
		}

		return false;
	}
	 public static String getRemoteAddress(HttpServletRequest request) {
         String ip ="" ;
         try
         {
        	 
        	// proxy_set_header x-client $http_x_connecting_ip;
        	 ip = request.getHeader("x-client");
        	 logger.info("ip:{}",ip);
        	 if(ObjectHelper.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
        		 return ip;
        	 }
        	 
        	 ip = request.getHeader("X-Forwarded-For");
       	  
	          if(ObjectHelper.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
	              //多次反向代理后会有多个ip值，第一个ip才是真实ip
	        	  logger.info("ip:"+ip);
	              int index = ip.indexOf(",");
	              if(index != -1){
	                  return ip.substring(0,index);
	              }else{
	                  return ip;
	              }
	          }
	          ip = request.getHeader("X-Real-IP");
	          logger.info("ip:"+ip);
	          if(ObjectHelper.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
	        	  logger.info("ip:"+ip);
	        	  return ip;
	          }
	          ip = request.getRemoteAddr();
	          logger.info("ip:"+ip);
	          if(ObjectHelper.isNotEmpty(ip) )
	   		  {
		        	  logger.info("ip:"+ip);
	   		  }
	           
         }
         catch(Exception ex)
         {
       	 ex.printStackTrace();
         }
         
         return ip;
     }
	
	 
}
