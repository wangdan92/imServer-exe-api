package com.im.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author WD
 * @title: KeyWordUtil
 * @projectName tz-oa
 * @description: 关键词过滤
 * @date 2019/12/18--17:28
 */
public class KeyWordUtil {
    //敏感词库文件路径
    public static String filePath = "D:\\temp\\dictionary.txt";
    //敏感词
    public static Set<String> words;
    //敏感词
    public static Map<String,String> wordMap;
    //最小匹配规则
    public static int minMatchTYpe = 1;
    //最大匹配规则
    public static int maxMatchType = 2;


    private static Logger logger=LoggerFactory.getLogger(KeyWordUtil.class);

    static{
        KeyWordUtil.words = readTxtByLine();
        addBadWordToHashMap(KeyWordUtil.words);
    }

    /**
     * 读文件获取敏感词
     * @return
     */
    public static Set<String> readTxtByLine() {
        BufferedReader reader=null;
        String temp=null;
        Set<String> keyWordSet = new HashSet<String>();
        try{
            File file = ResourceUtils.getFile("classpath:conf/dictionary.txt");
            //文件流是否存在
            if(!file.exists()){
                return keyWordSet;
            }
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            while((temp=reader.readLine())!=null){
                keyWordSet.add(temp);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return keyWordSet;
    }


    public static Set<String> readTxtByLine(String path) {
        BufferedReader reader=null;
        String temp=null;
        Set<String> keyWordSet = new HashSet<String>();
        try{
            File file=new File(path);
            //文件流是否存在
            if(!file.exists()){
                return keyWordSet;
            }
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            while((temp=reader.readLine())!=null){
                keyWordSet.add(temp);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        KeyWordUtil.words=keyWordSet;
        addBadWordToHashMap(keyWordSet);
        return keyWordSet;
    }


    public static Set<String> readTxtByLine(File keywordsFile) {
        BufferedReader reader=null;
        String temp=null;
        Set<String> keyWordSet = new HashSet<String>();
        try{
            if(!keywordsFile.exists()){
                return keyWordSet;
            }
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(keywordsFile),"UTF-8"));
            while((temp=reader.readLine())!=null){
                keyWordSet.add(temp);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        KeyWordUtil.words=keyWordSet;
        addBadWordToHashMap(keyWordSet);
        return keyWordSet;
    }


    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    @SuppressWarnings({ "rawtypes"})
    public static int checkBadWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = wordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if(minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        /*“粉饰”匹配词库：“粉饰太平”竟然说是敏感词
         * “个人”匹配词库：“个人崇拜”竟然说是敏感词
         * if(matchFlag < 2 && !flag){
            matchFlag = 0;
        }*/
        if(!flag){
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
     * 判断文字是否包含敏感字符
     * @param txt  文字
     * @param matchType  匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    public static boolean isContaintBadWord(String txt,int matchType){
        boolean flag = false;
        for(int i = 0 ; i < txt.length() ; i++){
            int matchFlag = checkBadWord(txt, i, matchType); //判断是否包含敏感字符
            if(matchFlag > 0){    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 替换敏感字字符
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @version 1.0
     */
    public static String replaceBadWord(String txt,int matchType,String replaceChar){
        String resultTxt = txt;
        Set<String> set = getBadWord(txt, matchType);     //获取所有的敏感词
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }
    /**
     * 获取文字中的敏感词
     * @param txt 文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    public static Set<String> getBadWord(String txt , int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();

        for(int i = 0 ; i < txt.length() ; i++){
            int length = checkBadWord(txt, i, matchType);    //判断是否包含敏感字符
            if(length > 0){    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;
    }

    /**
     * 获取替换字符串
     * @param replaceChar
     * @param length
     * @return
     * @version 1.0
     */
    private static String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * TODO 将我们的敏感词库构建成了一个类似与一颗一颗的树，这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。
     * @param keyWordSet 敏感词库
     * @author yqwang0907
     * @date 2018年2月28日下午5:28:08
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void addBadWordToHashMap(Set<String> keyWordSet) {
        wordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = wordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }



    public static String  checkBadWordAndReplace(String msg,int matchType){
        long beginTime = System.currentTimeMillis();
        try {
            if(StringUtils.isNotBlank(msg)){
                System.out.println("敏感词池库共有敏感词的数量：" + KeyWordUtil.wordMap.size());
                //判断是否包含敏感词汇
                Boolean i = KeyWordUtil.isContaintBadWord(msg, matchType);
                if (i){
                    msg= replaceBadWord(msg, 2, "*");
                }
            }
            System.out.println("替换后端语句："+msg);
        }catch (Exception e){
            logger.info("检查替换敏感词触发异常",e);
        }
        long endTime = System.currentTimeMillis();
        logger.info("检查替换敏感词总共消耗时间为：" + (endTime - beginTime));
        return msg;
    }


    public static void main(String[] args) throws Exception{
        String string="王丹爱国,习近平爱国,北京欢迎您";
        String msg = checkBadWordAndReplace(string, 2);
        System.out.println("msg:"+msg);
    }

}
