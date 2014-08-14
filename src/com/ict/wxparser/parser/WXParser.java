package com.ict.wxparser.parser;

import java.awt.event.ItemEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ict.wxparser.util.FileReader;
import com.ict.wxparser.wxmsg.WxMsgHtmlItemContent;
import com.ict.wxparser.wxmsg.WxMsgHtmlItemContentItem;
import com.ict.wxparser.wxmsg.WxMsgHtmlItem;
import com.ict.wxparser.wxmsg.WxMsgItem;
import com.ict.wxparser.wxmsg.WxMsgTextItem;
/**
 * ΢���ļ�������
 * @author Administrator
 *
 */
public abstract class WXParser {
	
	private static Logger logger = Logger.getLogger(WXParser.class);
	/**
	 * ΢����Ϣ���ļ�·��
	 */
	private String wxMsgFilePath;
	/**
	 * ΢����Ϣ�ļ��ı���
	 */
	private String charset;
	
	/**
	 * �����ж��Ƿ��Ѿ���ʼ��
	 */
	private boolean flag = false;
	
	private FileReader reader;
	/**
	 * WXParser���캯��
	 * @param _wxMsgFilePath �����΢����Ϣ·��
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
	 * ��ȡ��һ��΢����Ϣ
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
	 * ��������ȡ������
	 * @param line
	 * @return
	 */
	public WxMsgItem parseLine(String line){
		int msgType = Integer.parseInt(getValue("^.*\"MsgType\".*([0-9]+).*$", line).get(0));
		WxMsgItem item = null;
		if(msgType == 1){
			line = line.replace("\\\\\\", "");
			WxMsgTextItem txtItem = new WxMsgTextItem();
			txtItem.setContent( getValue("^.*\"TxtContent\"\\s*:\\s*\"([^\"]*)\".*$", line).get(0));
			item = txtItem;
		}else{// if(msgType == 49){
			int lidx = line.indexOf("[");
			int ridx = line.lastIndexOf("]");
			
			String contentString = line.substring(lidx + 1, ridx);
			lidx = line.lastIndexOf("Content", lidx);
			line = line.substring(0, lidx) + line.substring(ridx + 1);
			logger.info("The new line is: " + line) ;
			WxMsgHtmlItem htmlItem = new WxMsgHtmlItem();
			List<WxMsgHtmlItemContent> list = getMsgContent(contentString);
			htmlItem.setContent( list);
			item = htmlItem;
		}
		
		item.setWxId( getValue("^.*\"WxId\"\\s*:\\s*\"([^\"]*)\".*$", line).get(0));
		item.setSendTime( Long.parseLong( getValue("^.*\"SendTime\".*([0-9]+).*$", line).get(0)));
//		item.setFansCount( Integer.parseInt( getValue("^.*\"FansCount\".*([0-9]+).*$", line).get(0)));
		item.setMsgType( msgType);
		
		return item;
	}
	
	/**
	 * ��ȡ΢����Ϣ����
	 * @param contentString
	 * @return
	 */
	protected List<WxMsgHtmlItemContent> getMsgContent(String contentString){
		
		String htmlRegex = ".*\"TxtContent\":\"(.*<html.*</html\\s*>)\\s*\".*";
		
		Pattern p = Pattern.compile(htmlRegex);
		Matcher m = p.matcher(contentString);
		List<WxMsgHtmlItemContent> list = new ArrayList<WxMsgHtmlItemContent>();
		while(m.find()){
			WxMsgHtmlItemContent item = new WxMsgHtmlItemContent();
			item.setTxtContentHtmlString( m.group(1).replace("\\\\\\", ""));
			logger.info("Begin to analyze the html...");
			item.setTxtContent(getWxMsgContentItem(item.getTxtContentHtmlString()));
			logger.info("Analyze the html successfully.");
			list.add(item);
		}
		
		for(WxMsgHtmlItemContent item : list){
			contentString = contentString.replace(item.getTxtContentHtmlString(), "");
		}
		
		contentString = contentString.replace("\\\\\\\"", "\\\\\\#");
		
		List<String> valueStrings = getValue("^.*\"Title\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
		int idx = 0;
		for( String value: valueStrings){
			WxMsgHtmlItemContent item = list.get(idx++);
			item.setTitle(value.replace("\\\\\\#", "\""));
		}
		valueStrings = getValue("^.*\"Abstract\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
		idx = 0;
		for( String value: valueStrings){
			WxMsgHtmlItemContent item = list.get(idx++);
			item.setAbstract(value.replace("\\\\\\#", "\""));
		}
		valueStrings = getValue("^.*\"ArticleUrl\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
		idx = 0;
		for( String value: valueStrings){
			WxMsgHtmlItemContent item = list.get(idx++);
			item.setArticleUrl(value.replace("\\\\\\#", "\""));
		}
		valueStrings = getValue("^.*\"MediaUrl\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
		idx = 0;
		for( String value: valueStrings){
			WxMsgHtmlItemContent item = list.get(idx++);
			item.setMediaUrl(value.replace("\\\\\\#", "\""));
		}
		valueStrings = getValue("^.*\"TxtFile\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
		idx = 0;
		for( String value: valueStrings){
			WxMsgHtmlItemContent item = list.get(idx++);
			item.setTxtFile(value);
		}
		
		return list;
	}
	
	/**
	 * ���ڽ���html�ı�
	 * @param html
	 * @return
	 */
	protected abstract WxMsgHtmlItemContentItem getWxMsgContentItem(String html);
	
	/**
	 * ʹ���������ǽ����ִ�
	 * @param regex ������ʽ
	 * @param line �ִ�
	 * @return ��Ӧ��ֵ
	 */
	private List<String> getValue(String regex,String line){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		List<String> list = new ArrayList<String>();
		while( m.find() )
			list.add( m.group(1).trim());
		return list;
	}
	
	
	public static void main(String []args) throws IOException{
		WXParser wxParser = new WXParserHtmlCleaner("C:/Users/Administrator/Desktop/����Run/data/pa00", "utf-8");
		wxParser.init();
		WxMsgItem item = null;
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream("C:/Users/Administrator/Desktop/����Run/data/pa00.trans"),"utf-8"));
		while( ( item = wxParser.getNextWxMsgItem())!= null ){
			writer.append(item.toDocument());
		}

		writer.close();
	}
	
}
