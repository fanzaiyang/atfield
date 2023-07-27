package me.chanjar.weixin.cp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.XStreamMediaIdConverter;

/**
 * The type Wx cp xml out image message.
 */
@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxCpXmlOutImageMessage extends WxCpXmlOutMessage {
  private static final long serialVersionUID = -1099446240667237313L;

  @XStreamAlias("Image")
  @XStreamConverter(value = XStreamMediaIdConverter.class)
  private String mediaId;

  /**
   * Instantiates a new Wx cp xml out image message.
   */
  public WxCpXmlOutImageMessage() {
    this.msgType = WxConsts.XmlMsgType.IMAGE;
  }

}
