package cn.fanzy.atfield.satoken.enums;

/**
 * <pre>
 * 功能点	     Simple 简单模式	          Mixin 混入模式	       Stateless 无状态模式
 * Token风格	         jwt风格	                 jwt风格	                 jwt风格
 * 登录数据存储	    Redis中存储	            Token中存储	            Token中存储
 * Session存储	    Redis中存储	            Redis中存储	             无Session
 * 注销下线	       前后端双清数据	           前后端双清数据	             前端清除数据
 * 踢人下线API	       支持	                  不支持	                   不支持
 * 顶人下线API	       支持	                  不支持	                   不支持
 * 登录认证	           支持	                   支持	                    支持
 * 角色认证	           支持	                   支持	                    支持
 * 权限认证	           支持	                   支持	                    支持
 * timeout      	   支持	                   支持	                    支持
 * active-timeout	   支持	                   支持	                   不支持
 * id反查Token	       支持	                   支持	                   不支持
 * 会话管理	           支持	                  部分支持	               不支持
 * 注解鉴权	           支持	                   支持	                    支持
 * 路由拦截鉴权	       支持	                   支持	                    支持
 * 账号封禁	           支持	                   支持	                   不支持
 * 身份切换	           支持	                   支持	                    支持
 * 二级认证	           支持	                   支持	                    支持
 * 模式总结	      Token风格替换	          jw与Redis逻辑混合	  完全舍弃Redis,只用jwt
 * </pre>
 *
 * @author fanzaiyang
 * @date 2024/11/15
 */
public enum SaTokenJwtEnum {
    /**
     * Simple 模式：Token 风格替换
     */
    simple,
    /**
     * Mixin 模式：混入部分逻辑
     */
    mixin,
    /**
     * Stateless 模式：服务器完全无状态
     */
    stateless
}
