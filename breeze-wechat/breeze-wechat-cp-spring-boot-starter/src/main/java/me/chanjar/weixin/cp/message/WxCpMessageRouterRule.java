package me.chanjar.weixin.cp.message;

import lombok.Data;
import me.chanjar.weixin.common.api.WxErrorExceptionHandler;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.message.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.message.WxCpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * The type Wx cp message router rule.
 *
 * @author Daniel Qian
 */
@Data
public class WxCpMessageRouterRule {
  private final WxCpMessageRouter routerBuilder;

  private boolean async = true;

  private String fromUser;

  private String msgType;

  private String event;

  private String eventKey;

  private String eventKeyRegex;

  private String content;

  private String rContent;

  private WxCpMessageMatcher matcher;

  private boolean reEnter = false;

  private Integer agentId;

  private List<WxCpMessageHandler> handlers = new ArrayList<>();

  private List<WxCpMessageInterceptor> interceptors = new ArrayList<>();

  /**
   * Instantiates a new Wx cp message router rule.
   *
   * @param routerBuilder the router builder
   */
  protected WxCpMessageRouterRule(WxCpMessageRouter routerBuilder) {
    this.routerBuilder = routerBuilder;
  }

  /**
   * 设置是否异步执行，默认是true
   *
   * @param async the async
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule async(boolean async) {
    this.async = async;
    return this;
  }

  /**
   * 如果agentId匹配
   *
   * @param agentId the agent id
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule agentId(Integer agentId) {
    this.agentId = agentId;
    return this;
  }

  /**
   * 如果msgType等于某值
   *
   * @param msgType the msg type
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule msgType(String msgType) {
    this.msgType = msgType;
    return this;
  }

  /**
   * 如果event等于某值
   *
   * @param event the event
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule event(String event) {
    this.event = event;
    return this;
  }

  /**
   * 如果eventKey等于某值
   *
   * @param eventKey the event key
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule eventKey(String eventKey) {
    this.eventKey = eventKey;
    return this;
  }

  /**
   * 如果eventKey匹配该正则表达式
   *
   * @param regex the regex
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule eventKeyRegex(String regex) {
    this.eventKeyRegex = regex;
    return this;
  }

  /**
   * 如果content等于某值
   *
   * @param content the content
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule content(String content) {
    this.content = content;
    return this;
  }

  /**
   * 如果content匹配该正则表达式
   *
   * @param regex the regex
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule rContent(String regex) {
    this.rContent = regex;
    return this;
  }

  /**
   * 如果fromUser等于某值
   *
   * @param fromUser the from user
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule fromUser(String fromUser) {
    this.fromUser = fromUser;
    return this;
  }

  /**
   * 如果消息匹配某个matcher，用在用户需要自定义更复杂的匹配规则的时候
   *
   * @param matcher the matcher
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule matcher(WxCpMessageMatcher matcher) {
    this.matcher = matcher;
    return this;
  }

  /**
   * 设置微信消息拦截器
   *
   * @param interceptor the interceptor
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule interceptor(WxCpMessageInterceptor interceptor) {
    return interceptor(interceptor, (WxCpMessageInterceptor[]) null);
  }

  /**
   * 设置微信消息拦截器
   *
   * @param interceptor       the interceptor
   * @param otherInterceptors the other interceptors
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule interceptor(WxCpMessageInterceptor interceptor,
                                           WxCpMessageInterceptor... otherInterceptors) {
    this.interceptors.add(interceptor);
    if (otherInterceptors != null && otherInterceptors.length > 0) {
      Collections.addAll(this.interceptors, otherInterceptors);
    }
    return this;
  }

  /**
   * 设置微信消息处理器
   *
   * @param handler the handler
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule handler(WxCpMessageHandler handler) {
    return handler(handler, (WxCpMessageHandler[]) null);
  }

  /**
   * 设置微信消息处理器
   *
   * @param handler       the handler
   * @param otherHandlers the other handlers
   * @return the wx cp message router rule
   */
  public WxCpMessageRouterRule handler(WxCpMessageHandler handler, WxCpMessageHandler... otherHandlers) {
    this.handlers.add(handler);
    if (otherHandlers != null && otherHandlers.length > 0) {
      Collections.addAll(this.handlers, otherHandlers);
    }
    return this;
  }

  /**
   * 规则结束，代表如果一个消息匹配该规则，那么它将不再会进入其他规则
   *
   * @return the wx cp message router
   */
  public WxCpMessageRouter end() {
    this.routerBuilder.getRules().add(this);
    return this.routerBuilder;
  }

  /**
   * 规则结束，但是消息还会进入其他规则
   *
   * @return the wx cp message router
   */
  public WxCpMessageRouter next() {
    this.reEnter = true;
    return end();
  }

  /**
   * Test boolean.
   *
   * @param wxMessage the wx message
   * @return the boolean
   */
  protected boolean test(WxCpXmlMessage wxMessage) {
    return
      (this.fromUser == null || this.fromUser.equals(wxMessage.getFromUserName()))
        &&
        (this.agentId == null || this.agentId.equals(Integer.valueOf(wxMessage.getAgentId())))
        &&
        (this.msgType == null || this.msgType.equalsIgnoreCase(wxMessage.getMsgType()))
        &&
        (this.event == null || this.event.equalsIgnoreCase(wxMessage.getEvent()))
        &&
        (this.eventKey == null || this.eventKey.equalsIgnoreCase(wxMessage.getEventKey()))
        &&
        (this.eventKeyRegex == null || Pattern.matches(this.eventKeyRegex,
          StringUtils.trimToEmpty(wxMessage.getEventKey())))
        &&
        (this.content == null || this.content.equals(StringUtils.trimToNull(wxMessage.getContent())))
        &&
        (this.rContent == null || Pattern.matches(this.rContent, StringUtils.trimToEmpty(wxMessage.getContent())))
        &&
        (this.matcher == null || this.matcher.match(wxMessage))
      ;
  }

  /**
   * 处理微信推送过来的消息
   *
   * @param wxMessage        the wx message
   * @param context          the context
   * @param wxCpService      the wx cp service
   * @param sessionManager   the session manager
   * @param exceptionHandler the exception handler
   * @return true 代表继续执行别的router，false 代表停止执行别的router
   */
  protected WxCpXmlOutMessage service(WxCpXmlMessage wxMessage,
                                      Map<String, Object> context,
                                      WxCpService wxCpService,
                                      WxSessionManager sessionManager,
                                      WxErrorExceptionHandler exceptionHandler) {

    if (context == null) {
      context = new HashMap<>();
    }

    try {
      // 如果拦截器不通过
      for (WxCpMessageInterceptor interceptor : this.interceptors) {
        if (!interceptor.intercept(wxMessage, context, wxCpService, sessionManager)) {
          return null;
        }
      }

      // 交给handler处理
      WxCpXmlOutMessage res = null;
      for (WxCpMessageHandler handler : this.handlers) {
        // 返回最后handler的结果
        res = handler.handle(wxMessage, context, wxCpService, sessionManager);
      }
      return res;

    } catch (WxErrorException e) {
      exceptionHandler.handle(e);
    }

    return null;

  }


}
