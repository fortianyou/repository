package com.ict.wxparser.parser;

import java.util.List;

import com.ict.wxparser.wxmsg.WxMsgHtmlContent;
import com.ict.wxparser.wxmsg.WxMsgHtmlContentItem;
import com.ict.wxparser.wxmsg.WxMsgItem;

public class WXParserHtmlParser extends WXParser{

	
	/**
	 * WXParserHtmlParser���캯��
	 * @param _wxMsgFilePath �����΢����Ϣ·��
	 */
	protected WXParserHtmlParser(String wxMsgFilePath,String charset){
		super(wxMsgFilePath,charset);
	}

	@Override
	protected WxMsgHtmlContentItem getWxMsgContentItem(String html) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
