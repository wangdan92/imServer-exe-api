package com.im.interceptor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.im.TokenService;
import com.im.UserService;
import com.im.bean.MsgInfo;
import com.im.bean.TokenInfo;
import com.im.constant.Constants;
import com.im.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author ZhangPeng
 * @date 2017/12/8
 */
public class AuthHanlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果token存在，则返回成功，否则返回失败
        logger.info("token拦截校验");
        if(request.getMethod().equals(Constants.METHOD_OPTIONS)){
            logger.info("request.getMethod()"+request.getMethod());
            return true;
        }
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        //验证token
        boolean flag = true;
        logger.info("authorization: "+authorization);
        TokenInfo tokenInfo = tokenService.getTokenInfo(authorization);
        if (tokenInfo != null && tokenService.checkToken(tokenInfo)) {
            logger.info("登录校验成功");
             flag=true;
        }else{
            logger.info("登录校验失败");
            flag = false;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = response.getWriter();
            MsgInfo msgInfo = new MsgInfo(0,"登录校验失败");
            if (tokenInfo==null){
                //redis的token不存在
                logger.info("登录已过期,请重新登录");
                msgInfo.setMsg("登录已过期,请重新登录");
                JSONObject extra = new JSONObject();
                extra.put("type",1);
                msgInfo.setData(extra);
            }else {
                //token校验失败
                logger.info("登录校验失败");
                msgInfo.setMsg("登录校验失败");
                JSONObject extra = new JSONObject();
                extra.put("type",2);
                msgInfo.setData(extra);
            }
            out.println(JSON.toJSON(msgInfo));
            out.close();
        }
        return flag;
    }
}
