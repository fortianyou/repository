package com.ict.wxparser.util;

public class StringUtils {
	   /**
     * Ìæ»»Ò»¸ö×Ö·û´®ÖÐµÄÄ³Ð©Ö¸¶¨×Ö·û
     * @param strData String Ô­Ê¼×Ö·û´®
     * @param regex String ÒªÌæ»»µÄ×Ö·û´®
     * @param replacement String Ìæ´ú×Ö·û´®
     * @return String Ìæ»»ºóµÄ×Ö·û´®
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
     * Ìæ»»×Ö·û´®ÖÐÌØÊâ×Ö·û
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
     * »¹Ô­×Ö·û´®ÖÐÌØÊâ×Ö·û
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
