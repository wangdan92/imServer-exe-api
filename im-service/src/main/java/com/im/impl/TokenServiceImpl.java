package com.im.impl;


import com.im.RedisService;
import com.im.TokenService;
import com.im.bean.TokenInfo;
import com.im.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 * @author ScienJus
 * @date 2015/7/31.
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisService redisService;


    @Override
    public TokenInfo createTokenApp(TokenInfo tokenInfo) {
        //使用uuid作为源token
        String key = "";
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String encodeUserName = Base64Utils.encodeToUrlSafeString(tokenInfo.getUserName().getBytes());

        if(tokenInfo.getType() == 0){
            key = encodeUserName + Constants.SPLIT_UNDERLINE + uuid +Constants.SPLIT_LINE_THROUGH+Constants.TOKEN_OA;
        }else if(tokenInfo.getType() == 1){
            key = encodeUserName + Constants.SPLIT_UNDERLINE + uuid +Constants.SPLIT_LINE_THROUGH+Constants.TOKEN_APP;
        }else if(tokenInfo.getType() == 2){
            key = encodeUserName + Constants.SPLIT_UNDERLINE + uuid +Constants.SPLIT_LINE_THROUGH+Constants.TOKEN_SERVICE;
        }else if(tokenInfo.getType() == 3){
            key = encodeUserName + Constants.SPLIT_UNDERLINE + uuid +Constants.SPLIT_LINE_THROUGH+Constants.TOKEN_PC;
        }else {
            key = encodeUserName;
        }
        tokenInfo.setToken(key);
        if(null!=tokenInfo.getRole()){
            redisService.addData(key,key+"_role_"+tokenInfo.getRole(), Constants.TOKEN_EXPIRES_8, TimeUnit.DAYS);
        }else{
            redisService.addData(key,key, Constants.TOKEN_EXPIRES_8, TimeUnit.DAYS);
        }
        return tokenInfo;
    }

    @Override
    public TokenInfo getTokenInfo(String authorization) {
        if (authorization == null || authorization.length() == 0) {
            return null;
        }
        String[] param = authorization.split("_");
        int needLength = 2;
        if (param.length != needLength) {
            return null;
        }
        String userName = new String(Base64Utils.decodeFromUrlSafeString(param[0]));
        String token = authorization;
        String value = redisService.getData(token);
        String role="";
        if(null!=value&&value.contains("_role_")){
            role = value.split("_role_")[1];
            return new TokenInfo(userName, token,Short.valueOf(role));
        }
        return new TokenInfo(userName, token);
    }

    @Override
    public TokenInfo getTokenInfo(HttpServletRequest request){
        String authorization = request.getHeader(Constants.AUTHORIZATION);
        TokenInfo tokenInfo = this.getTokenInfo(authorization);
        return tokenInfo;
    }

    @Override
    public boolean checkToken(TokenInfo tokenInfo) {
        String value;
        String valueP;
        if (tokenInfo == null) {
            return false;
        }
        String key = tokenInfo.getToken();
        value = redisService.getData(key);
        valueP = redisService.getData(key);
        //去除附加的用户类型
        if(null!=value){
            valueP = value.split("_role_")[0];
        }
        if (valueP == null || !valueP.equals(tokenInfo.getToken())) {
            return false;
        }
        if (key.contains(Constants.TOKEN_OA)){
            tokenInfo.setType(0);
        }else if (key.contains(Constants.TOKEN_APP)){
            tokenInfo.setType(1);
        }else if (key.contains(Constants.TOKEN_SERVICE)){
            tokenInfo.setType(2);
        }else if (key.contains(Constants.TOKEN_PC)){
            tokenInfo.setType(3);
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisService.addData(key,value, Constants.TOKEN_EXPIRES_8, TimeUnit.DAYS);
        return true;
    }

    @Override
    public void deleteToken(TokenInfo tokenInfo) {
        String key = tokenInfo.getToken();
        redisService.deleteData(key);
    }

    @Override
    public void deleteToken(String token) {
        redisService.deleteData(token);
    }

    @Override
    public TokenInfo createTokenOA(TokenInfo tokenInfo) {
        //使用uuid作为源token
        String key = "";
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String encodeUserName = Base64Utils.encodeToUrlSafeString(tokenInfo.getUserName().getBytes());
        if(tokenInfo.getType() == 0){
            key = encodeUserName + Constants.SPLIT_UNDERLINE + uuid +Constants.SPLIT_LINE_THROUGH+Constants.TOKEN_OA;
        }else if(tokenInfo.getType() == 1){
            key = encodeUserName + Constants.SPLIT_UNDERLINE + uuid +Constants.SPLIT_LINE_THROUGH+Constants.TOKEN_APP;
        }else if(tokenInfo.getType() == 2){
            key = encodeUserName + Constants.SPLIT_UNDERLINE + uuid +Constants.SPLIT_LINE_THROUGH+Constants.TOKEN_SERVICE;
        }else if(tokenInfo.getType() == 3){
            key = encodeUserName + Constants.SPLIT_UNDERLINE + uuid +Constants.SPLIT_LINE_THROUGH+Constants.TOKEN_PC;
        }else {
            key = encodeUserName;
        }
        tokenInfo.setToken(key);
        if(null!=tokenInfo.getRole()){
            redisService.addData(key,key+"_role_"+tokenInfo.getRole(), Constants.TOKEN_EXPIRES_OA, TimeUnit.DAYS);
        }else{
            redisService.addData(key,key, Constants.TOKEN_EXPIRES_OA, TimeUnit.DAYS);
        }
        return tokenInfo;
    }

    @Override
    public boolean checkTokenOA(TokenInfo tokenInfo) {
        String value;
        String valueP;
        if (tokenInfo == null) {
            return false;
        }
        String key = tokenInfo.getToken();
        value = redisService.getData(key);
        valueP = redisService.getData(key);
        //去除附加的用户类型
        if(null!=value){
            valueP = value.split("_role_")[0];
        }
        if (valueP == null || !valueP.equals(tokenInfo.getToken())) {
            return false;
        }
        if (key.contains(Constants.TOKEN_OA)){
            tokenInfo.setType(0);
        }else if (key.contains(Constants.TOKEN_APP)){
            tokenInfo.setType(1);
        }else if (key.contains(Constants.TOKEN_SERVICE)){
            tokenInfo.setType(2);
        }else if (key.contains(Constants.TOKEN_PC)){
            tokenInfo.setType(3);
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redisService.addData(key,value, Constants.TOKEN_EXPIRES_OA, TimeUnit.DAYS);
        return true;
    }
}
