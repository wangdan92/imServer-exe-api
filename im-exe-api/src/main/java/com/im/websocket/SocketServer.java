package com.im.websocket;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.im.*;
import com.im.bean.BaseConfig;
import com.im.entity.ImChatGroupMessage;
import com.im.entity.ImChatGroupUser;
import com.im.entity.ImMessage;
import com.im.entity.User;
import com.im.util.ContextHolder;
import com.im.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @Author WD
 * @Description webSocket核心类
 * @Date 9:51 2019/9/27
 */
@ServerEndpoint(value = "/ws/{userId}/{chatGroupId}")
@Component
public class SocketServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    /**
     * 设置上下文对象
     */
    private static ImChatGroupService imChatGroupService;
    private static ImChatGroupMessageService imChatGroupMessageService;
    private static ImChatGroupUserService imChatGroupUserService;
    private static ImMessageService imMessageService;
    private static UserService userService;
    private BaseConfig baseConfig;

    @Autowired
    public void setImChatGroupMessageService(ImChatGroupMessageService imChatGroupMessageService) {
        SocketServer.imChatGroupMessageService = imChatGroupMessageService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        SocketServer.userService = userService;
    }

    @Autowired
    public void setUserService(ImChatGroupService imChatGroupService) {
        SocketServer.imChatGroupService = imChatGroupService;
    }


    @Autowired
    public void setImChatGroupUserService(ImChatGroupUserService imChatGroupUserService) {
        SocketServer.imChatGroupUserService = imChatGroupUserService;
    }

    @Autowired
    public void setImMessageService(ImMessageService imMessageService) {
        SocketServer.imMessageService = imMessageService;
    }

    /**
     * 用线程安全的CopyOnWriteArraySet来存放客户端连接的信息
     */
    private static CopyOnWriteArraySet<Client> socketServers = new CopyOnWriteArraySet<>();

    /**
     * websocket封装的session,信息推送，就是通过它来信息推送
     */
    private Session session;

    /**
     * 服务端的userName,因为用的是set，每个客户端的username必须不一样，否则会被覆盖。
     * 要想完成ui界面聊天的功能，服务端也需要作为客户端来接收后台推送用户发送的信息
     */
    private final static String SYS_USERNAME = "administrator";


    /**
     * 用户连接时触发，我们将其添加到
     * 保存客户端连接信息的socketServers中
     *
     * @param session
     * @param userId
     */
    @OnOpen
    public void open(Session session, @PathParam(value = "userId") int userId, @PathParam(value = "chatGroupId") int chatGroupId) {

        this.session = session;
        socketServers.add(new Client(userId, chatGroupId, session));

        logger.info("客户端:【{}】连接成功", userId);

    }

    /**
     * 收到客户端发送信息时触发
     * 我们将其推送给客户端(niezhiliang9595)
     * 其实也就是服务端本身，为了达到前端聊天效果才这么做的
     *
     * @param message 客户端发送的信息
     *                {
     *                msgType:'user',           			信息类型 ==>  user:用户信息  system:系统信息
     *                userId:'',      			 			用户id
     *                chatGroupId:'', 			 			群组id
     *                contentType:'text' ,  			   信息格式 ==> text:文本 ,img:图片 ,voice:语音,file:文件,redEnvelope:红包
     *                content:								信息内容
     *                {
     *                text:'xxx',						文本内容,
     *                url:''				 			语音/图片/文件的路径
     *                //其余参数:可能会涉及
     *                length:''						信息为语音时的语音时长
     *                w:''								信息为图片时的图片宽度
     *                h:''								信息为图片时的图片高度
     *                blessing:''						信息为红包时的备注信息
     *                rid:								红包id,
     *                isReceived:true/false			信息为红包时,红包是否被领取的状态
     *                }
     *                }
     */
    @OnMessage
    public void onMessage(String message) {
        Client client = socketServers.stream().filter(cli -> cli.getSession() == session).collect(Collectors.toList()).get(0);
        if (StringUtils.isNotEmpty(message)) {
            JSONObject jsonObject = JSON.parseObject(message);
            int userId = jsonObject.getInteger("userId");
            int chatGroupId = jsonObject.getInteger("chatGroupId");
            short messageType = jsonObject.getShort("messageType");
            short contentType = jsonObject.getShort("contentType");
            String content = jsonObject.getString("content");
            JSONObject contentObject = JSON.parseObject(content);
            JSONArray atArray = contentObject.getJSONArray("atUserList");
            ImChatGroupMessage imChatGroupMessage = new ImChatGroupMessage();
            imChatGroupMessage.setChatGroupId(chatGroupId);
            imChatGroupMessage.setFromId(userId);
            imChatGroupMessage.setContentType(contentType);
            imChatGroupMessage.setMessageType(messageType);
            imChatGroupMessage.setSendTime(new Date());
            imChatGroupMessage.setContent(content);
            imChatGroupMessageService.addImChatGroupMessage(imChatGroupMessage);
            JSONObject returnObject = new JSONObject();
            if (messageType == 1) {
                returnObject.put("type", "user");
            } else {
                returnObject.put("type", "system");
            }
            User user = userService.getUserById(userId);
            /*String multipartUrl = "http://114.242.34.137:39998/tz-multipart";*/
            if (baseConfig==null){
                baseConfig = ContextHolder.getBean(BaseConfig.class);
            }
            if (null != user.getPhotoPath() && !user.getPhotoPath().equals("")) {
                String base64Str = Base64Utils.encodeToUrlSafeString(user.getPhotoPath().getBytes());
                String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                user.setPhotoPath(url);
            } else if (null == user.getPhotoPath() || user.getPhotoPath().equals("")) {
                String base64Str = Base64Utils.encodeToUrlSafeString("face.jpg".getBytes());
                String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
                user.setPhotoPath(url);
            }
            JSONObject msgObject = new JSONObject();
            String tmpType = "";
            if (contentType == 1) {
                tmpType = "text";
            } else if (contentType == 2) {
                tmpType = "img";
            } else if (contentType == 3) {
                tmpType = "voice";
            } else if (contentType == 4) {
                tmpType = "video";
            } else if (contentType == 5) {
                tmpType = "file";
            } else {
                tmpType = "other";
            }
            msgObject.put("type", tmpType);
            msgObject.put("time", DateTimeUtil.formatQQ(imChatGroupMessage.getSendTime()));
            msgObject.put("userinfo", user);
            msgObject.put("chatGroupId", chatGroupId);
            msgObject.put("userId", userId);
            msgObject.put("content", JSONObject.parseObject(imChatGroupMessage.getContent()));
            msgObject.put("id", imChatGroupMessage.getId());
            returnObject.put("msg", msgObject);


            //这里对接收到的信息进行存储和处理,并转换为对应的格式发送给前端
            sendChatGroup(userId, chatGroupId, returnObject,atArray);
            logger.info("客户端:【{}】发送信息:{}", client.getUserId(), message);
        }

    }

    /**
     * 连接关闭触发，通过sessionId来移除
     * socketServers中客户端连接信息
     */
    @OnClose
    public void onClose(@PathParam(value = "userId") int userId, @PathParam(value = "chatGroupId") int chatGroupId) {
        socketServers.forEach(client -> {
            if (client.getSession().getId().equals(session.getId())) {
                logger.info("客户端:【{}】断开连接", client.getUserId());
                socketServers.remove(client);

            }
        });
    }

    /**
     * 发生错误时触发
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        socketServers.forEach(client -> {
            if (client.getSession().getId().equals(session.getId())) {
                socketServers.remove(client);
                logger.error("客户端:【{}】发生异常", client.getUserId());
                error.printStackTrace();
            }
        });
    }

    /**
     * 信息发送的方法，通过客户端的userName
     * 拿到其对应的session，调用信息推送的方法
     *
     * @param message
     * @param userId
     */
    public synchronized static void sendMessage(String message, int userId) {
        System.out.println("userId:" + userId);
        socketServers.forEach(client -> {
            if (userId == client.getUserId()) {
                try {

                    client.getSession().getBasicRemote().sendText(message);

                    logger.info("服务端推送给客户端 :【{}】", client.getUserId(), message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取服务端当前客户端的连接数量，
     * 因为服务端本身也作为客户端接受信息，
     * 所以连接总数还要减去服务端
     * 本身的一个连接数
     * <p>
     * 这里运用三元运算符是因为客户端第一次在加载的时候
     * 客户端本身也没有进行连接，-1 就会出现总数为-1的情况，
     * 这里主要就是为了避免出现连接数为-1的情况
     *
     * @return
     */
    public synchronized static int getOnlineNum() {
        return socketServers.stream().filter(client -> client.getUserId() != 0)
                .collect(Collectors.toList()).size();
    }

	/**
	 * 根据用户编号和群组编号判断用户是否在线
	 * @param userId
	 * @param chatGroupId
	 * @return
	 */
	public synchronized static boolean isOnline(int userId,int chatGroupId) {
    	boolean isOnline = false;
		Iterator<Client> iterator = socketServers.iterator();
		while(iterator.hasNext()) {
			Client client = iterator.next();
			if(client.getUserId()==userId && chatGroupId == client.getChatGroupId()){
				isOnline = true;
				break;
			}
		}
		return isOnline;
	}

	/**
	 * 根据用户名和群组编号获取在线用户的连接对象
	 * @param userId
	 * @param chatGroupId
	 * @return
	 */
	public synchronized static Client getOnlineUser(int userId,int chatGroupId) {
    	Client onlineClient = null;
		Iterator<Client> iterator = socketServers.iterator();
		while(iterator.hasNext()) {
			Client client = iterator.next();
			if(client.getUserId()==userId && chatGroupId == client.getChatGroupId()){
				onlineClient = client;
				break;
			}
		}
		return onlineClient;
	}

    /**
     * 获取在线用户名，前端界面需要用到
     *
     * @return
     */
    public synchronized static List<Integer> listOnlineUsers() {

        List<Integer> onlineUsers = socketServers.stream()
                .filter(client -> client.getUserId() != 0)
                .map(client -> client.getUserId())
                .collect(Collectors.toList());

        return onlineUsers;
    }

    /**
     * 信息群发，我们要排除服务端自己不接收到推送信息
     * 所以我们在发送的时候将服务端排除掉
     *
     * @param message 返回给前端的信息,格式参照项目resources目录下的messageReturn.json
     */
    public synchronized static void sendAll(String message) {
        //群发，不能发送给服务端自己
        socketServers.stream().filter(client -> client.getUserId() != 0)
                .forEach(client -> {
                    try {
                        client.getSession().getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        logger.info("服务端推送给所有客户端 :【{}】", message);
    }

    public synchronized static void sendChatGroup(int userId, int chatGroupId, JSONObject msgObject, JSONArray atArray) {
        String message = msgObject.toJSONString();
        //群发，不能发送给服务端自己
        socketServers.stream().filter(client -> client.getUserId() != 0)
                .forEach(client -> {
                    try {
                        //发给在线的并且是同一个群组里的用户
                        if (client.getChatGroupId() == chatGroupId) {
                            client.getSession().getBasicRemote().sendText(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
		//给群组里不是设置免打扰标识的用户推送通知（排除自己）
        List<ImChatGroupUser> chatGroupUserList = imChatGroupUserService.listByChatGroupId(chatGroupId);
        List<String> pushIds = new ArrayList<>();
        for (ImChatGroupUser groupUser : chatGroupUserList) {
            if (userId != groupUser.getUserId()) {
                //如果有@消息，列表再显示有人@你
                if(null != atArray && atArray.size()>0){
                    for(int i=0;i<atArray.size();i++){
                        JSONObject atObject = atArray.getJSONObject(i);
                        int atUserId = atObject.getInteger("userId");
                        ImMessage imMessage = imMessageService.getImMessageByGroupId(atUserId,chatGroupId);
                        if(null != imMessage){
                            imMessage.setAtFlag(true);
                            imMessageService.updateAtFlag(imMessage);
                        }
                    }
                }
                //还有发给不在聊天界面但在即时通讯列表界面的用户
                Client tmpClient = getOnlineUser(groupUser.getUserId(),0);
                if(null != getOnlineUser(groupUser.getUserId(),0)){
                    try {
                        tmpClient.getSession().getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                //如果用户不在当前群组的聊天界面中，未读数量+1
				if(!isOnline(groupUser.getUserId(),chatGroupId)){
					ImMessage imMessage = imMessageService.getImMessageByGroupId(groupUser.getUserId(),chatGroupId);
                    if(null != imMessage){
                        imMessage.setUnreadNum(imMessage.getUnreadNum()+1);
                        imMessageService.updateUnreadNum(imMessage);
                    }
				}

            }
        }

    }


}
