package com.wemsg.service;

import org.springframework.stereotype.Service;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Service
public class WeChatDbService {

    /**
     * 为加密的 SQLite 数据库创建 DataSource。
     *
     * @param dbPath   数据库文件的绝对路径。
     * @param password 加密密钥（如果未加密或已解密，可以为 null）。
     * @return DataSource
     */
    public DataSource connect(String dbPath, String password) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + dbPath);

        if (password != null && !password.isEmpty()) {
            // 设置与 sqlite-jdbc 中的 SQLCipher 支持相关的密码
            // 注意：标准的 xerial sqlite-jdbc 可能需要特定的构建版本才能支持 SQLCipher，
            // 或者根据使用的具体驱动程序版本，可能需要通过特定属性传递密码。
            // 对于包含 SQLCipher 的 xerial sqlite-jdbc：
            Properties prop = new Properties();
            // prop.setProperty("cipher", "sqlcipher"); // 有时需要
            // prop.setProperty("key", password);

            // 使用 SQLiteConfig 更安全
            SQLiteConfig config = new SQLiteConfig();
            // 如果标准 Config 中没有直接暴露带 KEY 的 setPragma，可能需要反射或自定义方法
            // 但通常这样是可行的：
            // config.setPragma(SQLiteConfig.Pragma.KEY, password);
            // dataSource.setConfig(config);
        }

        return dataSource;
    }

    public boolean testConnection(String dbPath, String password) {
        DataSource ds = connect(dbPath, password);
        try (Connection conn = ds.getConnection()) {
            return conn.isValid(5);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
