package com.wemsg.service;

import com.wemsg.model.Message;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final WeChatDbService dbService;

    public MessageService(WeChatDbService dbService) {
        this.dbService = dbService;
    }

    public List<Message> getMessages(String dbPath, String password, String talkerId) {
        if (dbPath == null || dbPath.isEmpty()) {
            return getMockMessages(talkerId);
        }

        List<Message> messages = new ArrayList<>();
        // 通常消息存储在 'MSG.db' 或类似文件中，表名为 'message' 或 'MSG'
        // 暂时假设表名为 'message'
        String sql = "SELECT MsgId, MsgSvrId, Type, IsSender, CreateTime, StrTalker, StrContent FROM message WHERE StrTalker = ? ORDER BY CreateTime ASC LIMIT 1000";

        DataSource ds = dbService.connect(dbPath, password);
        try (Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, talkerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Message m = new Message();
                    m.setMsgId(rs.getLong("MsgId"));
                    m.setMsgSvrId(rs.getLong("MsgSvrId"));
                    m.setType(rs.getString("Type"));
                    m.setIsSender(rs.getInt("IsSender"));
                    m.setCreateTime(rs.getLong("CreateTime"));
                    m.setTalker(rs.getString("StrTalker"));
                    m.setContent(rs.getString("StrContent"));
                    messages.add(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    private List<Message> getMockMessages(String talkerId) {
        List<Message> mocks = new ArrayList<>();
        long now = System.currentTimeMillis() / 1000;

        Message m1 = new Message();
        m1.setMsgId(1L);
        m1.setIsSender(0); // 接收
        m1.setType("1"); // 文本
        m1.setCreateTime(now - 3600);
        m1.setTalker(talkerId);
        m1.setContent("你好，最近怎么样？");
        mocks.add(m1);

        Message m2 = new Message();
        m2.setMsgId(2L);
        m2.setIsSender(1); // 发送
        m2.setType("1");
        m2.setCreateTime(now - 3500);
        m2.setTalker(talkerId);
        m2.setContent("我很好，谢谢！正在做项目。");
        mocks.add(m2);

        Message m3 = new Message();
        m3.setMsgId(3L);
        m3.setIsSender(0);
        m3.setType("1");
        m3.setCreateTime(now - 3400);
        m3.setTalker(talkerId);
        m3.setContent("听起来不错。把文件发给我。");
        mocks.add(m3);

        return mocks;
    }
}
