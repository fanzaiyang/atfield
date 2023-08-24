package me.chanjar.weixin.cp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.xml.XStreamReplaceNameConverter;

/**
 * The type Wx cp xml out update btn message.
 *
 * @author nickname263  created on  2021-09-23
 */
@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxCpXmlOutUpdateBtnMessage extends WxCpXmlOutMessage {
  private static final long serialVersionUID = 976182367423048138L;
  @XStreamAlias("Button")
  @XStreamConverter(value = XStreamReplaceNameConverter.class)
  private String replaceName;

  /**
   * Instantiates a new Wx cp xml out update btn message.
   */
  public WxCpXmlOutUpdateBtnMessage() {
    this.msgType = WxConsts.XmlMsgType.UPDATE_BUTTON;
  }


}
