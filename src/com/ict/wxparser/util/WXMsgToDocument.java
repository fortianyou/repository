package com.ict.wxparser.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.ict.wxparser.parser.WXParser;
import com.ict.wxparser.parser.WXParserHtmlCleaner;
import com.ict.wxparser.wxmsg.WxMsgItem;

public class WXMsgToDocument {

	private String inFilename;
	private String outFilename;
	private String charset;
	public WXMsgToDocument(String inFilename,String outFilename,String charset){
		this.inFilename = inFilename;
		this.outFilename = outFilename;
		this.charset = charset;
	}

	public void transToDocument() throws IOException{
		WXParser wxParser = new WXParserHtmlCleaner(inFilename,charset);
		wxParser.init();
		WxMsgItem item = null;
		BufferedWriter writer;
		
		writer = new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(outFilename),charset));
	
		while( ( item = wxParser.getNextWxMsgItem())!= null ){
			writer.append(item.toDocument());
		}

		writer.close();
	}
}
