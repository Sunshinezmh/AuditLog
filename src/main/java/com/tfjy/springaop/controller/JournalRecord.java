package com.tfjy.springaop.controller;

import com.tfjy.springaop.bean.AuditJournalEntity;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JournalRecord {
    private static String dirverName;
    private static String url;
    private static String username;
    private static String password;


    public static Connection getConnection(){
        InputStream inputStream=JournalRecord.class.getClassLoader().getResourceAsStream("./database.properties");
        Properties prop=new Properties();
        Connection connection=null;
        try {
            prop.load(inputStream);
            dirverName = prop.getProperty("dirverName");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            //加载驱动
            try {
                Class.forName(dirverName);
                try {
                    connection= DriverManager.getConnection(url,username,password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static void auditJournal(AuditJournalEntity auditJournalEntity) {
        AuditJournalThread auditJournalThread=new AuditJournalThread();
        auditJournalThread.setAuditJournalEntity(auditJournalEntity);
        auditJournalThread.start();
    }

    public static void throwJournal(String journalId){
        ThrowAudit throwAudit=new ThrowAudit(journalId);
        throwAudit.start();
    }
}
