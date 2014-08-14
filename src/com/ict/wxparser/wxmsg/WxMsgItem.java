package com.ict.wxparser.wxmsg;


public abstract class WxMsgItem {

	protected String wxId;
	protected long sendTime;
//	protected int fansCount;
	protected int msgType;
	
	public String getWxId() {
		return wxId  == null? "": wxId;
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
//	public int getFansCount() {
//		return fansCount;
//	}
//	public void setFansCount(int fansCount) {
//		this.fansCount = fansCount;
//	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	
	public abstract String toDocument();
}
