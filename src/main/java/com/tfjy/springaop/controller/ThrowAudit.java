package com.tfjy.springaop.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ThrowAudit extends Thread {
    private String journalId;

    public ThrowAudit(String journalId) {
        this.journalId = journalId;
    }


    @Override
    public void run() {
        Connection connection=JournalRecord.getConnection();
        String sql="UPDATE tc_audit_journal SET is_exception=1 WHERE id='"+journalId+"'";
        System.out.println(sql);
        try {
            Statement statement=connection.createStatement();
            int i = statement.executeUpdate(sql);
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
