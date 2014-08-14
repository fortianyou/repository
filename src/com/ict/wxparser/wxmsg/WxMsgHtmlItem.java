package com.ict.wxparser.wxmsg;

import java.util.ArrayList;
import java.util.List;

public class WxMsgHtmlItem extends WxMsgItem{
	private List<WxMsgHtmlContent> content;
	public List<WxMsgHtmlContent> getContent() {
		return content==null?new ArrayList<WxMsgHtmlContent>(): content;
	}
	public void setContent(List<WxMsgHtmlContent> content) {
		this.content = content;
	}
	
	@Override
	public String toString(){
		return "WxId:" + getWxId() + ", SendTime:" + getSendTime()// + ", FansCount:" + getFansCount() 
		+", MsgType:" + getMsgType() + ", Content:" + getContent().toString();
	}
	@Override
	public String toDocument(){
		if( content == null || content.size() == 0){
			return "";
		}
		String retString = "";
		String domHead ="<msg ";
		domHead += "WxId="+getWxId() +" SendTime="+getSendTime() + " MsgType="+getMsgType();
		for(WxMsgHtmlContent cnt : content ){
			WxMsgHtmlContentItem itm = cnt.getTxtContent();
			String cntString =  itm.getContentString();
			if(  cntString == null || cntString.equals("") )
				continue;
			String dom = domHead + " TxtFile="+cnt.getTxtFile() + " PostDate="+ itm.getPostDateString()
			+ " PostUser=" + itm.getUserString()+" >\n";
			
			dom += itm.getContentString();
			retString += dom + "\n";
		}
		
		return retString;
	}
}
