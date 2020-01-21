package com.im;


import com.im.bean.TokenInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 对Token进行操作的接口
 * @author ScienJus
 * @date 2015/7/31.
 */
public interface TokenService {

    /**
     * 创建一个token关联上指定用户
     * @param model 指定用户的账号
     * @return 生成的token
     */
    TokenInfo createTokenApp(TokenInfo model);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    boolean checkToken(TokenInfo model);

    /**
     * 从字符串中解析token
     * @param authorization 加密后的字符串
     * @return
     */
    TokenInfo getTokenInfo(String authorization);

    /**
     * 根据request请求获取tokent
     * @param request HttpServletRequest
     * @return TokenInfo token对象
     */
    TokenInfo getTokenInfo(HttpServletRequest request);

    /**
     * 清除token
     * @param model 登录用户的账号
     */
    void deleteToken(TokenInfo model);

    /**
     * 清除token
     * @param token
     */
    void deleteToken(String token);

    /**
     * 创建一个token关联上指定用户
     * @return 生成的token
     */
    TokenInfo createTokenOA(TokenInfo tokenInfo);

    /**
     * 检查token是否有效
     * @return 是否有效
     */
    boolean checkTokenOA(TokenInfo tokenInfo);
}
