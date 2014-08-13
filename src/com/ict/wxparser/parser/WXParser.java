package com.ict.wxparser.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ict.wxparser.util.FileReader;
import com.ict.wxparser.wxmsg.WxMsgContentItem;
import com.ict.wxparser.wxmsg.WxMsgItem;

public abstract class WXParser {
	
	private static Logger logger = Logger.getLogger(WXParser.class);
	/**
	 * 微信消息的文件路径
	 */
	private String wxMsgFilePath;
	/**
	 * 微信消息文件的编码
	 */
	private String charset;
	
	/**
	 * 用于判断是否已经初始化
	 */
	private boolean flag = false;
	
	private FileReader reader;
	/**
	 * WXParser构造函数
	 * @param _wxMsgFilePath 输入的微信消息路径
	 */
	protected WXParser(String wxMsgFilePath,String charset){
		this.wxMsgFilePath = wxMsgFilePath;
		this.charset = charset;
	}
	
	public boolean init(){
		reader = new FileReader(wxMsgFilePath,charset);
		try {
			reader.openStream();
			logger.info("Success to open the FileReader.");
			flag = true;
			return flag;
		}	catch (IOException e) {
			logger.info(e.getMessage(),e);
		}
		
		logger.info("Fail to open the FileReader.");
		return false;
	}
	/**
	 * 获取下一条微信消息
	 * @return
	 * @throws IOException 
	 */
	public WxMsgItem getNextWxMsgItem() throws IOException{
		if(reader == null){
			throw new IOException("WXparser hasn't been init");
		}
		String line = reader.getNextLine();
		if( line == null ){
			reader.closeStream();
			return null;
		}
		return parseLine(line);
	}
	/**
	 * 解析所读取到的行
	 * @param line
	 * @return
	 */
	public WxMsgItem parseLine(String line){
		
		int lidx = line.indexOf("[");
		int ridx = line.lastIndexOf("]");
		
		String contentString = line.substring(lidx + 1, ridx);
		lidx = line.lastIndexOf("Content", lidx);
		line = line.substring(0, lidx) + line.substring(ridx + 1);
		
		logger.info("The new line is: " + line) ;
		
		WxMsgItem item = new WxMsgItem();
		item.setContents( getMsgContent(contentString));
		item.setWxId( getValue("^.*\"WxId\"\\s*:\\s*\"(.*)\".*$", line).get(0));
		item.setSendTime( Long.parseLong( getValue("^.*\"SendTime\".*([0-9]+).*$", line).get(0)));
		item.setFansCount( Integer.parseInt( getValue("^.*\"FansCount\".*([0-9]+).*$", line).get(0)));
		item.setMsgType( Integer.parseInt(getValue("^.*\"MsgType\".*([0-9]+).*$", line).get(0)));
		
		logger.info(item);
		return item;
	}
	
	/**
	 * 获取微信消息内容
	 * @param contentString
	 * @return
	 */
	protected List<WxMsgContentItem> getMsgContent(String contentString){
		contentString = contentString.replace("\\\\\\", "");
		String htmlRegex = ".*\"TxtContent\":\"(.*<html.*</html\\s*>)\\s*\".*";
		
		Pattern p = Pattern.compile(htmlRegex);
		Matcher m = p.matcher(contentString);
		List<WxMsgContentItem> list = new ArrayList<WxMsgContentItem>();
		while(m.find()){
			WxMsgContentItem item = new WxMsgContentItem();
			item.setTxtContentHtmlString( m.group(1));
		}
		
		for(WxMsgContentItem item : list){
			contentString = contentString.replace(item.getTxtContentHtmlString(), "");
		}
		
		
	}
	
	
	
	/**
	 * 使用正则表达是解析字串
	 * @param regex 正则表达式
	 * @param line 字串
	 * @return 对应的值
	 */
	private List<String> getValue(String regex,String line){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		List<String> list = new ArrayList<String>();
		while( m.find() )
			list.add( m.group(1));
		return list;
	}
	
}
