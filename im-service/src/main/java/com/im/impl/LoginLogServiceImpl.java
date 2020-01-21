package com.im.impl;


import com.im.LoginLogService;
import com.im.dao.LoginLogDao;
import com.im.entity.LoginLog;
import com.im.util.AddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    public int addLoginLog(LoginLog loginLog) {
        AddressUtils addressUtils = new AddressUtils();
        try {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
            executor.execute(() -> {
                try {
                    String ip = addressUtils.getInnetIp();
                    String v4ip = addressUtils.getV4IP();
                    loginLog.setIp(ip);
                    loginLog.setV4ip(v4ip);
                    loginLogDao.addLoginLog(loginLog);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
