package com.wemsg.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Message") // 映射到 MSG.db 中的 'message' 表
public class Message {

    @Id
    private Long msgId; // 'MsgId'

    private Long msgSvrId; // 'MsgSvrId'
    private String type; // 'Type' (int)
    private Integer subType; // 'SubType'
    private Integer isSender; // 'IsSender'
    private Long createTime; // 'CreateTime'
    private String talker; // 'StrTalker' - 聊天 ID（用户或群组）
    private String content; // 'StrContent' - 实际消息内容
    private String imgPath; // 'imgPath'

    public Long getMsgId() { return msgId; }
    public void setMsgId(Long msgId) { this.msgId = msgId; }

    public Long getMsgSvrId() { return msgSvrId; }
    public void setMsgSvrId(Long msgSvrId) { this.msgSvrId = msgSvrId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getSubType() { return subType; }
    public void setSubType(Integer subType) { this.subType = subType; }

    public Integer getIsSender() { return isSender; }
    public void setIsSender(Integer isSender) { this.isSender = isSender; }

    public Long getCreateTime() { return createTime; }
    public void setCreateTime(Long createTime) { this.createTime = createTime; }

    public String getTalker() { return talker; }
    public void setTalker(String talker) { this.talker = talker; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImgPath() { return imgPath; }
    public void setImgPath(String imgPath) { this.imgPath = imgPath; }
}
