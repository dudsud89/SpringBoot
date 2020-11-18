package com.example.mybatis.dto;

public class Post {
    long postId;
    String memberId;
    String title;
    String content;
    int count;
    String createDateTime;
    String nickName;
    String name;
    String renameFileName;
    String originalFileName;
    

    public Post(String memberId, String title, String content, String nickName, int count, String createDateTime, String renameFileName, String originalFileName) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.count = count;
        this.createDateTime = createDateTime;
        this.renameFileName = renameFileName;
        this.originalFileName = originalFileName;
        
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getRenameFileName() {
        return renameFileName;
    }
    
    public void setRenameFileName(String renameFileName) {
        this.renameFileName = renameFileName;
    }
   
    public String getOriginalFileName() {
        return originalFileName;
    }
    
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
