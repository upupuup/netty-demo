package juejin.second.netty.simplechat.session;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * session类
 * @Author:         jiangzhihong
 * @CreateDate:     2021/4/18 14:39
 */
@Data
@AllArgsConstructor
public class Session {
	private String userId;
	private String userName;
}
