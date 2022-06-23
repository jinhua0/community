package com.newcoder.community.entity;

import java.util.Date;

/**
 * @Description:
 * @ClassName: DiscussPost
 * @author: jinhua
 */
public class DiscussPost {

    private int id;
    private int userId;
    private String title;
    private String content;
    private int type; //0 普通， 1 置顶
    private int status; // 0 正常，1 精华， 2拉黑
    private Date createTime;
    private int commmentCount;
    private double score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCommmentCount() {
        return commmentCount;
    }

    public void setCommmentCount(int commmentCount) {
        this.commmentCount = commmentCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "DiscussPost{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", commmentCount=" + commmentCount +
                ", score=" + score +
                '}';
    }
}