package com.ict.wxparser.parser;

import java.util.List;

import com.ict.wxparser.wxmsg.WxMsgContentItem;
import com.ict.wxparser.wxmsg.WxMsgItem;

public class WXParserHtmlParser extends WXParser{

	/**
	 * ΢����Ϣ�ļ���·��
	 */
	private String wxMsgFilePath;
	
	/**
	 * WXParserHtmlParser���캯��
	 * @param _wxMsgFilePath �����΢����Ϣ·��
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
