package com.im.impl;

import com.im.ImBadWordsService;
import com.im.dao.ImBadWordsDao;
import com.im.entity.ImBadWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WD
 * @title: ImBadWordsServiceImpl
 * @projectName wjw-back
 * @description: 敏感词实现类
 * @date 2019/12/25--15:03
 */
@Service
public class ImBadWordsServiceImpl implements ImBadWordsService {

    @Autowired
    private ImBadWordsDao imBadWordsDao;

    /**
     * 新增敏感词
     * @param imBadWords
     * @return
     * @throws Exception
     */
    @Override
    public int addBadWords(ImBadWords imBadWords) throws Exception {
        return imBadWordsDao.addBadWords(imBadWords);
    }

    /**
     * 批量新增敏感词
     * @param badWordList
     * @return
     */
    @Override
    public int addBadWordsForBatch(List<ImBadWords> badWordList) {
        return imBadWordsDao.addBadWordsForBatch(badWordList);
    }

    /**
     * 修改敏感词
     * @param imBadWords
     * @return
     * @throws Exception
     */
    @Override
    public int updateBadWords(ImBadWords imBadWords) throws Exception {
        return imBadWordsDao.updateBadWords(imBadWords);
    }


    /**
     * 删除敏感词
     * @param imBadWords
     * @return
     * @throws Exception
     */
    @Override
    public int deleteBadWords(ImBadWords imBadWords) throws Exception {
        return imBadWordsDao.deleteBadWords(imBadWords);
    }

    /**
     * 查询敏感词
     * @param imBadWords
     * @return
     * @throws Exception
     */
    @Override
    public List<ImBadWords> selectBadWords(ImBadWords imBadWords) throws Exception {
        return null;
    }


    /**
     * 根据主键查询敏感词
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ImBadWords selectBadWordById(long id) throws Exception {
        return null;
    }
}
