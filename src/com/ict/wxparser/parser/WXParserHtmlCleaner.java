package com.ict.wxparser.parser;

import java.io.IOException;
import java.util.List;

import javax.swing.text.html.parser.TagElement;

import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import com.ict.wxparser.util.FileReader;
import com.ict.wxparser.wxmsg.WxMsgHtmlContentItem;

public class WXParserHtmlCleaner extends WXParser{
	
	private static Logger logger = Logger.getLogger(WXParserHtmlCleaner.class);
	private static HtmlCleaner cleaner = new HtmlCleaner();
	
	/**
	 * WXParserHtmlParser构造函数
	 * @param _wxMsgFilePath 输入的微信消息路径
	 */
	public WXParserHtmlCleaner(String _wxMsgFilePath,String charset){
		super(_wxMsgFilePath, charset);
	}

	@Override
	protected  WxMsgHtmlContentItem getWxMsgContentItem(String html) {
		WxMsgHtmlContentItem item = new WxMsgHtmlContentItem();
		TagNode dom = cleaner.clean(html);
		TagNode [] nodes = dom.getElementsByAttValue("id","page-content", true, true);
		if(nodes == null || nodes.length == 0){
			logger.error("The id page-content wasn't found, return null");
			return null;
		}
		
		TagNode rootNode = nodes[0];
		nodes = dom.getElementsByAttValue("id","js_content", true, true);
		String contentString = null;
		if( nodes == null || nodes.length == 0){
			contentString = (rootNode.getText()).toString();
		}else {
			TagNode node = nodes[0];
			contentString = (node.getText()).toString();
		}
		
		item.setContentString(contentString);
		
		nodes = dom.getElementsByAttValue("id","activity-name", true, true);
		String str = null;
		if(nodes == null || nodes.length == 0){
			logger.warn("The id activity-name wasn't foudn.");
		}else{
			TagNode node = nodes[0];
			str = (node.getText()).toString().trim();
			item.setTitleString(str);
		}
		
		nodes = dom.getElementsByAttValue("id","post-date", true, true);
		str = null;
		if(nodes == null || nodes.length == 0){
			logger.warn("The id post-date wasn't foudn.");
		}else{
			TagNode node = nodes[0];
			str = (node.getText()).toString().trim();
			item.setPostDateString(str);
		}
		
		nodes = dom.getElementsByAttValue("id","post-user", true, true);
		str = null;
		if(nodes == null || nodes.length == 0){
			logger.warn("The id post-user wasn't foudn.");
		}else{
			TagNode node = nodes[0];
			str = (node.getText()).toString().trim();
			item.setUserString(str);
		}
		
		
		return item;
	}
	
	protected static  String getWxMsgContentString(String html) {
		TagNode dom = cleaner.clean(html);
		TagNode [] nodes = dom.getElementsByAttValue("id","page-content", true, true);
		TagNode rootNode = nodes[0];
		nodes = dom.getElementsByAttValue("id","js_content", true, true);
		String titleString = null;
		if( nodes == null || nodes.length == 0){
			titleString = (rootNode.getText()).toString();
		}else {
			TagNode node = nodes[0];
			titleString = (node.getText()).toString();
		}
		
		return titleString.trim();
	}
	
	

}
