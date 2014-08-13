package com.ict.wxparser.parser;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;

import com.ict.wxparser.util.FileReader;
import com.ict.wxparser.wxmsg.WxMsgContentItem;
import com.ict.wxparser.wxmsg.WxMsgItem;

public class WXParserHtmlCleaner extends WXParser{
	
	private static HtmlCleaner cleaner = new HtmlCleaner();
	
	/**
	 * WXParserHtmlParser构造函数
	 * @param _wxMsgFilePath 输入的微信消息路径
	 */
	protected WXParserHtmlCleaner(String _wxMsgFilePath,String charset){
		super(_wxMsgFilePath, charset);
	}

	@Override
	protected List<WxMsgContentItem> getMsgContent(String contentString) {
		
	}



}
