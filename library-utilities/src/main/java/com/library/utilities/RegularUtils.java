package com.library.utilities;

import android.text.TextUtils;

public class RegularUtils {
  //身份证
  public static final String REGEX_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
  //验证邮箱
  public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
  //手机号
  public static final String REGEX_PHONE = "0?(13|14|15|17|18)[0-9]{9}";
  //汉字
  public static final String REGEX_TRUENAME = "^[\\u4e00-\\u9fa5]*$";
  //银行卡
  public static final String BANKNUMBER="^([0-9]{16}|[0-9]{19})$";
  //固话电话正则
  public static final String TELE= "([0-9]{3,4}-)?[0-9]{7,8}";
  //密码强度验证
  public static final String REGEX_PWS="^(((?=.*[0-9])(?=.*[a-zA-Z])|(?=.*[0-9])(?=.*[^\\s0-9a-zA-Z])|(?=.*[a-zA-Z])(?=.*[^\\s0-9a-zA-Z]))[^\\s]+)$";
  public static boolean isPhone(String mobiles){
    if (TextUtils.isEmpty(mobiles)){
      return false;
    }else{
      return mobiles.matches(REGEX_PHONE);
    }
  }
  public static boolean isTel(String mobiles) {
    if (TextUtils.isEmpty(mobiles)) {
      return false;
    } else {
      return mobiles.matches(TELE);
    }
  }
  public static boolean isIDacard(String idcard){
    if (TextUtils.isEmpty(idcard))
      return false;
    else
      return idcard.matches(REGEX_ID_CARD);
  }
  public static boolean isBankCard(String bankcard){
    if (TextUtils.isEmpty(bankcard))
      return false;
    else
      return bankcard.matches(BANKNUMBER);
  }
  public static boolean isEmail(String email){
    if (TextUtils.isEmpty(email))
      return false;
    else
      return email.matches(REGEX_EMAIL);
  }
  public static boolean isTrueName(String name){
    if (TextUtils.isEmpty(name))
      return false;
    else
      return name.matches(REGEX_TRUENAME);
  }
  //验证密码强度
  public static boolean isPsw(String psw) {
    //包含数字，字母，特殊符号其中至少两种
    if (TextUtils.isEmpty(psw)) {
      return false;
    } else {
      //限制长度
      if(psw.length()<6||psw.length()>16){
        return false;
      }
      //matches():字符串是否在给定的正则表达式匹配
      return psw.matches(REGEX_PWS);
    }
  }
}
