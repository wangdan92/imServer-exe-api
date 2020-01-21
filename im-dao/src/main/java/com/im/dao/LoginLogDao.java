package com.im.dao;
import com.im.entity.LoginLog;
import com.im.util.MyBatisDao;
import org.apache.ibatis.annotations.Insert;

@MyBatisDao
public interface LoginLogDao {

    @Insert("INSERT INTO loginlog (id,userId,loginDatetime,ip,whereLogin)VALUES(#{id},#{userId},#{loginDatetime},#{ip},#{whereLogin})")
    int addLoginLog(LoginLog loginLog);
}
