package com.ict.wxparser.wxmsg;

/**
 * MsgType = 1，纯文本微信内容
 * @author Administrator
 *
 */
public class WxMsgTextItem extends WxMsgItem{

	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content == null? "":content;
	}
	
	@Override
	public String toString(){
		return "WxId:" + getWxId() + ", SendTime:" + getSendTime()  
		+", MsgType:" + getMsgType() + ", Content:" + getContent().toString();
	}
	@Override
	public String toDocument(){
		if( content == null || content.equals("")){
			return "";
		}
		String dom ="<msg ";
		dom += "WxId="+getWxId() +" SendTime="+getSendTime() + " MsgType="+getMsgType() + " >\n";;
		dom += content +"\n";
		
		return dom;
	}
}
