package com.ict.wxparser.util;

public class StringUtils {
	   /**
     * �滻һ���ַ����е�ĳЩָ���ַ�
     * @param strData String ԭʼ�ַ���
     * @param regex String Ҫ�滻���ַ���
     * @param replacement String ����ַ���
     * @return String �滻����ַ���
     */
    public static String replaceString(String strData, String regex,
            String replacement)
    {
        if (strData == null)
        {
            return null;
        }
        int index;
        index = strData.indexOf(regex);
        String strNew = "";
        if (index >= 0)
        {
            while (index >= 0)
            {
                strNew += strData.substring(0, index) + replacement;
                strData = strData.substring(index + regex.length());
                index = strData.indexOf(regex);
            }
            strNew += strData;
            return strNew;
        }
        return strData;
    }
 
    /**
     * �滻�ַ����������ַ�
     */
  public static String encodeString(String strData)
    {
        if (strData == null)
        {
            return "";
        }
        strData = replaceString(strData, "&", "&amp;");
        strData = replaceString(strData, "<", "&lt;");
        strData = replaceString(strData, ">", "&gt;");
        strData = replaceString(strData, "'", "&apos;");
        strData = replaceString(strData, "\"", "&quot;");
        
        return strData;
    }
 
    /**
     * ��ԭ�ַ����������ַ�
     */
   public static String decodeString(String strData)
    {
        strData = replaceString(strData, "&lt;", "<");
        strData = replaceString(strData, "&gt;", ">");
        strData = replaceString(strData, "&apos;", "'");
        strData = replaceString(strData, "&quot;", "\"");
        strData = replaceString(strData, "&amp;", "&");
//        strData = strData.replaceAll("&[a-zA-Z]+;", "");
        return strData;
    }
}
