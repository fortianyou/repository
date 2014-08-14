package com.ict.wxparser.wxmsg;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信消息：MsgType = 3、34、43、49，图片、语音、视频、图文等微信消息内容
 * @author Administrator
 *
 */
public class WxMsgHtmlItem extends WxMsgItem{
	private List<WxMsgHtmlItemContent> content;
	public List<WxMsgHtmlItemContent> getContent() {
		return content==null?new ArrayList<WxMsgHtmlItemContent>(): content;
	}
	public void setContent(List<WxMsgHtmlItemContent> content) {
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
		for(WxMsgHtmlItemContent cnt : content ){
			WxMsgHtmlItemContentItem itm = cnt.getTxtContent();
			if(itm == null ) continue;
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
