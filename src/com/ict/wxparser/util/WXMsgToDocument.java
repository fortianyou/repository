package com.ict.wxparser.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.ict.wxparser.parser.WXParser;
import com.ict.wxparser.parser.WXParserHtmlCleaner;
import com.ict.wxparser.wxmsg.WxMsgItem;

/**
 * 对一个WxMsg文件进行分析，并输出指定格式的文本到指定文件
 * @author Administrator
 *
 */
public class WXMsgToDocument {
	private static Logger logger = Logger.getLogger(WXMsgToDocument.class);
	private String inFilename;
	private String outFilename;
	private String charset;
	public WXMsgToDocument(String inFilename,String outFilename,String charset){
		this.inFilename = inFilename;
		this.outFilename = outFilename;
		this.charset = charset;
	}

	/**
	 * 输出指定格式的文本到指定文件
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws IOException
	 */
	public void transToDocument() throws UnsupportedEncodingException, FileNotFoundException{
		WXParser wxParser = new WXParserHtmlCleaner(inFilename,charset);
		wxParser.init();
		WxMsgItem item = null;
		BufferedWriter writer;
		
		writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(outFilename),charset));
	
		try {
			while( ( item = wxParser.getNextWxMsgItem())!= null ){
				writer.append(item.toDocument());
			}
		} catch (IOException e) {
			logger.error(e.getMessage()+", close BufferedWriter",e);
			try {
				writer.close();
			} catch (IOException e1) {
				logger.error(e.getMessage(),e);
			}
		}

		
	}
	
	public static void main(String []args) throws IOException{
		WXMsgToDocument d = new WXMsgToDocument("C:/Users/Administrator/Desktop/仲由Run/data/file", "C:/Users/Administrator/Desktop/仲由Run/data/file.trans", "UTF-8");
		d.transToDocument();

	}
}
