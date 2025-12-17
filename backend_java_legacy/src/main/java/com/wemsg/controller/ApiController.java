package com.wemsg.controller;

import com.wemsg.model.Contact;
import com.wemsg.model.Message;
import com.wemsg.service.ContactService;
import com.wemsg.service.MessageService;
import com.wemsg.service.WeChatDbService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // 允许 Vite 前端跨域访问
public class ApiController {

    private final WeChatDbService weChatDbService;
    private final ContactService contactService;
    private final MessageService messageService;

    public ApiController(WeChatDbService weChatDbService, ContactService contactService,
            MessageService messageService) {
        this.weChatDbService = weChatDbService;
        this.contactService = contactService;
        this.messageService = messageService;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "Backend is running";
    }

    @PostMapping("/connect")
    public String connectToDb(@RequestParam(required = false) String dbPath,
            @RequestParam(required = false) String password) {
        // 仅测试连接
        if (dbPath == null)
            return "未提供数据库路径 (使用模拟数据)";
        boolean success = weChatDbService.testConnection(dbPath, password);
        return success ? "已连接" : "连接失败";
    }

    @GetMapping("/contacts")
    public List<Contact> getContacts(@RequestParam(required = false) String dbPath,
            @RequestParam(required = false) String password) {
        return contactService.getAllContacts(dbPath, password);
    }

    @GetMapping("/messages")
    public List<Message> getMessages(@RequestParam String talkerId, @RequestParam(required = false) String dbPath,
            @RequestParam(required = false) String password) {
        return messageService.getMessages(dbPath, password, talkerId);
    }
}
