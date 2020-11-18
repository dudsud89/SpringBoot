package com.example.mybatis.dto;

public class Member {
	String memberId;
    String loginId;
    String password;
    String name;
    String nickname;


    public Member(String loginId, String password, String name, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
     
    }

    public Member() {

    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

  
}
