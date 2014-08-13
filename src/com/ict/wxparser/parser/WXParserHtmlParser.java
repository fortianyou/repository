package com.ict.wxparser.parser;

import java.util.List;

import com.ict.wxparser.wxmsg.WxMsgContentItem;
import com.ict.wxparser.wxmsg.WxMsgItem;

public class WXParserHtmlParser extends WXParser{

	/**
	 * 微信消息文件的路径
	 */
	private String wxMsgFilePath;
	
	/**
	 * WXParserHtmlParser构造函数
	 * @param _wxMsgFilePath 输入的微信消息路径
	 */
	protected WXParserHtmlParser(String _wxMsgFilePath){
		wxMsgFilePath = _wxMsgFilePath;
	}

	@Override
	protected List<WxMsgContentItem> getMsgContent(String contentString) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
