package com.im;

import com.im.entity.ImBadWords;

import java.util.List;

/**
 * @author WD
 * @title: ImBadWordsService
 * @projectName wjw-back
 * @description:  即时通讯敏感词service层
 * @date 2019/12/25--14:53
 */
public interface ImBadWordsService {
    int addBadWords(ImBadWords imBadWords) throws Exception;
    int updateBadWords(ImBadWords imBadWords) throws Exception;
    int deleteBadWords(ImBadWords imBadWords) throws Exception;
    List<ImBadWords> selectBadWords(ImBadWords imBadWords) throws Exception;
    ImBadWords selectBadWordById(long id) throws Exception;

    int addBadWordsForBatch(List<ImBadWords> badWordList);
}
