package com.ict.wxparser.parser;

import java.util.List;

import com.ict.wxparser.wxmsg.WxMsgHtmlItemContent;
import com.ict.wxparser.wxmsg.WxMsgHtmlItemContentItem;
import com.ict.wxparser.wxmsg.WxMsgItem;
/**
 * ����HtmlParser��΢�����ݽ�����:��δʵ��
 * @author Administrator
 *
 */
public class WXParserHtmlParser extends WXParser{

	
	/**
	 * WXParserHtmlParser���캯��
	 * @param _wxMsgFilePath �����΢����Ϣ·��
	 */
	public WXParserHtmlParser(String wxMsgFilePath,String charset){
		super(wxMsgFilePath,charset);
	}

	@Override
	protected WxMsgHtmlItemContentItem getWxMsgContentItem(String html) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
