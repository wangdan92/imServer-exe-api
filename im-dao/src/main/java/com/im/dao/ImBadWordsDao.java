package com.im.dao;

import com.im.entity.ImBadWords;
import com.im.util.MyBatisDao;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author WD
 * @title: ImBadWordsDao
 * @projectName wjw-back
 * @description: 敏感词的持久层
 * @date 2019/12/25--15:09
 */
@MyBatisDao
public interface ImBadWordsDao {


    int addBadWords(ImBadWords imBadWords);

    int addBadWordsForBatch(List<ImBadWords> badWordList);

    @Update("update im_bad_words set  badWord=#{badWord},createTime=#{createTime} where id=#{id}")
    int updateBadWords(ImBadWords imBadWords);

    @Delete("delete from im_bad_words where id=#{id}")
    int deleteBadWords(ImBadWords imBadWords);



}
