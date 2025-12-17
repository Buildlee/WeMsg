package com.wemsg.model;


import jakarta.persistence.Entity; // Spring Boot 3 使用 jakarta
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Contact") // 这实际上可能映射到微信数据库中的 'rcontact'
public class Contact {

    @Id
    private String username; // 微信数据库中的 'username' 列

    private String alias;
    private String nickname;
    private String remark;
    private String signature; // 'pyInitial' 等字段可能有用

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }

    // 我们通常会通过 JDBC 手动映射这些字段，因为微信数据库模式与 JPA 期望的并不完全匹配，
    // 且需要进行大量自定义，但对于内部模型表示来说，这样做很好。
}
