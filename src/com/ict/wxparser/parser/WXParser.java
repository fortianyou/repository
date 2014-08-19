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
	
	protected WXParser() {
		
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
	 * @throws Exception 
	 */
	public WxMsgItem getNextWxMsgItem() throws IOException{
		if(reader == null){
			throw new IOException("WXparser hasn't been init");
		}
		String line = reader.getNextLine();
		WxMsgItem item = null;
		if( line == null ){
			reader.closeStream();
			return null;
		}
		try {
			item = parseLine(line);
			return item;
		} catch (Exception e) {
			logger.info(e.getMessage() + ", read next line",e);
			return getNextWxMsgItem();
		}
		
	}
	
	
	/**
	 * ��������ȡ������
	 * @param line
	 * @return
	 * @throws Exception 
	 */
	public WxMsgItem parseLine(String line) throws Exception{
		//�����ʼline̫���Ҳ�����ʹ�������ʾ�ƺ�����������ݲ�ʹ��
		//List<String > lists = getValue("^.*\"MsgType\":([0-9]+).*$", line);
		int lidx , ridx;
		lidx = line.indexOf("\"MsgType\"");
		lidx = line.indexOf(":",lidx + 1);
		if(lidx == -1)
			throw new Exception("Malformed WxMsg");
		ridx = line.indexOf(",",lidx + 1);
		if(ridx == -1)
			throw new Exception("Malformed WxMsg");
		int msgType = Integer.parseInt( line.substring(lidx + 1,ridx).trim());
		WxMsgItem item = null;
		if(msgType == 1){
			line = line.replace("\\\\\\", "");
			WxMsgTextItem txtItem = new WxMsgTextItem();
			List<String> list = getValue("^.*\"TxtContent\"\\s*:\\s*\"([^\"]*)\".*$", line);
			if( list == null || list.size() == 0)
				txtItem.setContent( "" );
			else {
				txtItem.setContent(list.get(0));
			}
			item = txtItem;
		}else{// if(msgType == 49){
			lidx = line.indexOf("[");
			ridx = line.lastIndexOf("]");
			
			String contentString = line.substring(lidx + 1, ridx);
			lidx = line.lastIndexOf("Content", lidx);
			line = line.substring(0, lidx) + line.substring(ridx + 1);
			logger.info("The new line is: " + line) ;
			WxMsgHtmlItem htmlItem = new WxMsgHtmlItem();
			List<WxMsgHtmlItemContent> list = getMsgContent(contentString);
			htmlItem.setContent( list);
			item = htmlItem;
		}
		List<String> list = getValue("^.*\"WxId\"\\s*:\\s*\"([^\"]*)\".*$", line);
		if( list == null || list.size() == 0)
			item.setWxId( "");
		else
			item.setWxId( list.get(0));
		
		list = getValue("^.*\"SendTime\".*([0-9]+).*$", line);
		if( list == null || list.size() == 0)
			item.setSendTime(0);
		else
			item.setSendTime( Long.parseLong( list.get(0)));
//		item.setFansCount( Integer.parseInt( getValue("^.*\"FansCount\".*([0-9]+).*$", line).get(0)));
		item.setMsgType( msgType);
		
		return item;
	}
	
	/**
	 * ��ȡ΢����Ϣ����
	 * @param contentString
	 * @return
	 */
	protected List<WxMsgHtmlItemContent> getMsgContent(String content){
		
		
		List<String> contentStringList = splitContentString(content);
		
		List<WxMsgHtmlItemContent> retList = new ArrayList<WxMsgHtmlItemContent>();
		for( String contentString : contentStringList)
		{
			List<String> list = getValue(".*\"TxtContent\":\"(.*<html.*</html\\s*>)\\s*\".*", contentString);
			if(list == null || list.size() == 0){
				continue;
			}else{
				WxMsgHtmlItemContent item = new WxMsgHtmlItemContent();
				item.setTxtContentHtmlString( list.get(0).replace("\\\\\\", ""));
				logger.info("Begin to analyze the html...");
				item.setTxtContent(getWxMsgContentItem(item.getTxtContentHtmlString()));
				logger.info("Analyze the html successfully.");
				retList.add(item);
				contentString = contentString.replace(item.getTxtContentHtmlString(), "");
				contentString = contentString.replace("\\\\\\\"", "\\\\\\#");
				list = getValue("^.*\"Title\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
				if(list == null || list.size() == 0){
					item.setTitle("");
				}else{
					item.setTitle(list.get(0).replace("\\\\\\#", "\""));
				}
				list = getValue("^.*\"Abstract\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
				if( list == null || list.size() == 0){
					item.setAbstract("");
				}else{
					item.setAbstract(list.get(0).replace("\\\\\\#", "\""));
				}
				list = getValue("^.*\"ArticleUrl\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
				if( list == null || list.size() == 0){
					item.setArticleUrl("");
				}else{
					item.setArticleUrl(list.get(0).replace("\\\\\\#", "\""));
				}
				list = getValue("^.*\"MediaUrl\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
				if( list == null || list.size() == 0){
					item.setMediaUrl("");
				}else{
					item.setMediaUrl(list.get(0).replace("\\\\\\#", "\""));
				}
				list = getValue("^.*\"TxtFile\"\\s*:\\s*\"([^\"]*)\".*$", contentString);
				
				if( list == null || list.size() == 0){
					item.setTxtFile("");
				}else{
					item.setTxtFile(list.get(0).replace("\\\\\\#", "\""));
				}
			}
			
			
		}
		
		return retList;
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
		if( m.find() )
			list.add( m.group(1).trim());
		return list;
	}
	
	private List<String> splitContentString(String contentString){
		int lastBraceIdx = contentString.lastIndexOf('}');
		
		int idx = contentString.indexOf("\"TxtContent\":\"");
		int lidx = contentString.lastIndexOf('{',idx);
		int ridx;
		List<String> retList = new ArrayList<String>();
		while( idx > 0 ){
			idx  = contentString.indexOf("\"TxtContent\":\"",idx + 1);
			if( idx > 0){
				ridx = contentString.lastIndexOf('}',idx);
				String tmp =  contentString.substring(lidx,ridx+1);
				retList.add(tmp);
				lidx = contentString.lastIndexOf('{',idx);
			}else{
				ridx = lastBraceIdx;
				String tmp =  contentString.substring(lidx,ridx+1);
				retList.add(tmp);
			}
			
		}
		return retList;
	}

	
}
