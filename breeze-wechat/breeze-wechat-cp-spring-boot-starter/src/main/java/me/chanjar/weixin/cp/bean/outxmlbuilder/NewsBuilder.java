package me.chanjar.weixin.cp.bean.outxmlbuilder;

import me.chanjar.weixin.cp.bean.message.WxCpXmlOutNewsMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutNewsMessage.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 图文消息builder
 *
 * @author Daniel Qian
 */
public final class NewsBuilder extends BaseBuilder<NewsBuilder, WxCpXmlOutNewsMessage> {
  private List<Item> articles = new ArrayList<>();

  /**
   * Add article news builder.
   *
   * @param items the items
   * @return the news builder
   */
  public NewsBuilder addArticle(Item... items) {
    Collections.addAll(this.articles, items);
    return this;
  }

  /**
   * Articles news builder.
   *
   * @param articles the articles
   * @return the news builder
   */
  public NewsBuilder articles(List<Item> articles) {
    this.articles = articles;
    return this;
  }

  @Override
  public WxCpXmlOutNewsMessage build() {
    WxCpXmlOutNewsMessage m = new WxCpXmlOutNewsMessage();
    for (Item item : this.articles) {
      m.addArticle(item);
    }
    setCommon(m);
    return m;
  }

}
