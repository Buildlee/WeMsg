package com.wemsg.service;

import com.wemsg.model.Contact;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private final WeChatDbService dbService;

    public ContactService(WeChatDbService dbService) {
        this.dbService = dbService;
    }

    public List<Contact> getAllContacts(String dbPath, String password) {
        List<Contact> contacts = new ArrayList<>();
        // 验证：如果 dbPath 为空或 null，返回模拟数据
        if (dbPath == null || dbPath.isEmpty()) {
            return getMockContacts();
        }

        DataSource ds = dbService.connect(dbPath, password);
        // 查询通常涉及将 'rcontact' 与其他表连接以获取别名、备注等
        // 对于标准的微信数据库: select * from rcontact where type != 33 and verifyFlag = 0 ...
        // 目前使用简化查询:
        String sql = "SELECT username, alias, nickname, conRemark FROM rcontact WHERE type != 33 LIMIT 500";

        try (Connection conn = ds.getConnection();
                PreparedStatement wStmt = conn.prepareStatement(sql);
                ResultSet rs = wStmt.executeQuery()) {

            while (rs.next()) {
                Contact c = new Contact();
                c.setUsername(rs.getString("username"));
                c.setAlias(rs.getString("alias"));
                c.setNickname(rs.getString("nickname"));
                c.setRemark(rs.getString("conRemark"));
                contacts.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 如果连接失败（开发目的），回退到模拟数据（可选，也许抛出异常更好）
            // 返回空列表以指示失败或混合状态
        }
        return contacts;
    }

    private List<Contact> getMockContacts() {
        List<Contact> mocks = new ArrayList<>();

        Contact c1 = new Contact();
        c1.setUsername("wxid_123456");
        c1.setNickname("Alice");
        c1.setRemark("同事");
        c1.setSignature("努力工作!");
        mocks.add(c1);

        Contact c2 = new Contact();
        c2.setUsername("wxid_654321");
        c2.setNickname("Bob");
        c2.setRemark("健身伙伴");
        c2.setSignature("没有付出就没有收获");
        mocks.add(c2);

        for (int i = 0; i < 10; i++) {
            Contact c = new Contact();
            c.setUsername("wxid_user_" + i);
            c.setNickname("用户 " + i);
            mocks.add(c);
        }

        return mocks;
    }
}
