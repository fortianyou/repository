package com.ict.wxparser.wxmsg;

import java.util.List;
/**
 * Î¢ÐÅÏûÏ¢ÄÚÈÝ£ºÍ¼¡¢ÊÓÆµ¡¢ÒôÆµ¡¢Í¼ÎÄ
 * @author Administrator
 *
 */
public class WxMsgHtmlItemContent {
	private String title;
	private String Abstract;
	private String articleUrl;
	private String mediaUrl;
	private WxMsgHtmlItemContentItem txtContent;
	private String txtContentHtmlString;
	private String txtFile;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract_) {
		Abstract = abstract_;
	}
	public String getArticleUrl() {
		return articleUrl;
	}
	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public WxMsgHtmlItemContentItem getTxtContent() {
		return txtContent;
	}
	public void setTxtContent(WxMsgHtmlItemContentItem txtContent) {
		this.txtContent = txtContent;
	}
	public String getTxtFile() {
		return txtFile;
	}
	public void setTxtFile(String txtFile) {
		this.txtFile = txtFile;
	}
	public String getTxtContentHtmlString() {
		return txtContentHtmlString;
	}
	public void setTxtContentHtmlString(String txtContentHtmlString) {
		this.txtContentHtmlString = txtContentHtmlString;
	}
	
	
}
