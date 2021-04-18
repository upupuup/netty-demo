package juejin.second.netty.simplechat.attributes;

import io.netty.util.AttributeKey;
import juejin.second.netty.simplechat.session.Session;

/**
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 14:40
 */
public interface Attributes {
	AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
