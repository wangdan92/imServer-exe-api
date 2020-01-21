package com.im.websocket;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * @Author WD
 * @Description websocket信息封装类
 * @Date 14:43 2020/1/21
*/
public class Client implements Serializable {

    private static final long serialVersionUID = 8957107006902627635L;

    private int userId;

    private int chatGroupId;

    private Session session;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(int chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Client(int userId,int chatGroupId, Session session) {
        this.userId = userId;
        this.chatGroupId = chatGroupId;
        this.session = session;
    }

    public Client() {
    }
}
