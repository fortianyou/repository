package com.ict.wxparser.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class FileReader {
	
	private static Logger logger = Logger.getLogger(FileReader.class);
	/**
	 * �ȴ���ʱʱ�䣬����
	 */
	private static int AWAIT_TIME_OUT = 100;
	/**
	 * ����ȡ���ļ���
	 */
	private String filename;
	/**
	 * �ļ�����
	 */
	private String charset;
	private BufferedReader reader;
	/**
	 * ������
	 * @param filename ������ļ���
	 */
	public FileReader(String filename,String charset){
		this.filename = filename;
		this.charset = charset;
	}
	
	public void openStream() throws IOException{
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),charset));
	
		while( !reader.ready()){
			logger.info("The BufferedReader isn't ready, waitting...");
			try {
				Thread.sleep(AWAIT_TIME_OUT);
			} catch (InterruptedException e) {
				logger.info(e.getMessage(),e);
			}
		}
	}
	
	public void closeStream() throws IOException{
		if( reader != null ){
			reader.close();
			reader = null;
		}
	}
	
	public String getNextLine() throws IOException{
		if( reader != null )
			this.openStream();
		return reader.readLine();
	}
}
