package com.ict.wxparser.parser;

import java.util.List;

import com.ict.wxparser.wxmsg.WxMsgHtmlContent;
import com.ict.wxparser.wxmsg.WxMsgHtmlContentItem;
import com.ict.wxparser.wxmsg.WxMsgItem;

public class WXParserHtmlParser extends WXParser{

	
	/**
	 * WXParserHtmlParser构造函数
	 * @param _wxMsgFilePath 输入的微信消息路径
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
