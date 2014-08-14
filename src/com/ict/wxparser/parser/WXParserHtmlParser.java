package com.ict.wxparser.parser;

import java.util.List;

import com.ict.wxparser.wxmsg.WxMsgHtmlItemContent;
import com.ict.wxparser.wxmsg.WxMsgHtmlItemContentItem;
import com.ict.wxparser.wxmsg.WxMsgItem;
/**
 * 采用HtmlParser的微信内容解析器:暂未实现
 * @author Administrator
 *
 */
public class WXParserHtmlParser extends WXParser{

	
	/**
	 * WXParserHtmlParser构造函数
	 * @param _wxMsgFilePath 输入的微信消息路径
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
