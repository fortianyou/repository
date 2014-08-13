package com.ict.wxparser.wxmsg;

import java.util.ArrayList;
import java.util.List;

public class WxMsgItem {

	private String wxId;
	private long sendTime;
	private int fansCount;
	private int msgType;
	private List<WxMsgContentItem> contents;
	public String getWxId() {
		return wxId;
	}
	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public long getSendTime() {
		return sendTime;
	}
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	public int getFansCount() {
		return fansCount;
	}
	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public List<WxMsgContentItem> getContents() {
		return contents==null?new ArrayList<WxMsgContentItem>(): contents;
	}
	public void setContents(List<WxMsgContentItem> contents) {
		this.contents = contents;
	}
	
	@Override
	public String toString(){
		return "WxId:" + wxId + ", SendTime:" + sendTime + ", FansCount:" + fansCount 
		+", MsgType:" + msgType + ", Content:" + contents.toString();
	}
	
}
