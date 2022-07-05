package com.newcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 过滤敏感词的工具
 * @ClassName: SensitiveFilter
 * @author: jinhua
 */
@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 替换符
    private static final String REPLACEMENT = "***";

    // 初始化前缀树
    private TrieNode root = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                //添加到前缀树
                this.addKeyword(keyword);
            }

        } catch (IOException e) {
            logger.error("加载敏感文件失败" + e.getMessage());
        }
    }

    // 将敏感词添加到前缀树上
    private void addKeyword(String keyword) {
        TrieNode tempNode = root;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            // 如果子节点等于空的话就初始化字节点
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            // 将指针指向子节点，进入下一轮循环
            tempNode = subNode;

            // 打上敏感词结束标记
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     *
     * @param text 待过滤文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        // 指针1
        TrieNode tempNode = root;
        // 指针2
        int begin = 0;
        // 指针3
        int position = 0;
        // 拼接字符串
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过符号
            if (isSymbol(c)) {
                // 后缀树过了根节点就略过，还是根节点，就直接拼接
                if (tempNode == root) {
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 指针指向下一个位置
                position = ++begin;
                // 重新指向根节点
                tempNode = root;
            } else if (tempNode.isKeywordEnd()) {
                sb.append(REPLACEMENT);
                begin = ++position;
                // 重新指向根节点
                tempNode = root;
            } else {
                // 检查下一个字符
                position++;
            }

        }
        sb.append(text.substring(begin));

        return sb.toString();

    }

    //判断是不是特殊符号
    public boolean isSymbol(Character c) {
        // 不是数字 && 非东亚字符
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    // 前缀树
    private class TrieNode {
        // 关键词结束标识
        private boolean isKeywordEnd = false;

        // 子节点（Key 是下级字符， Value是下级节点）
        private Map<Character, TrieNode> subNode = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNode.put(c, node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNode.get(c);
        }
    }
}
