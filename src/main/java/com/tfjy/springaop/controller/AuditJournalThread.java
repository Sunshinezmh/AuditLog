package com.tfjy.springaop.controller;

import com.tfjy.springaop.bean.AuditJournalEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AuditJournalThread extends Thread {

    private AuditJournalEntity auditJournalEntity;

    @Override
    public void run() {
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sqlDate = df.format(cale.getTime());

        String sql="INSERT INTO tc_audit_journal VALUES ('"+auditJournalEntity.getId()+"','"+auditJournalEntity.getClassName()+"'," +
                "'"+auditJournalEntity.getMethodName()+"','"+auditJournalEntity.getParameter()+"','1'," +
                "'"+auditJournalEntity.getOperatorIp()+"',0,'"+auditJournalEntity.getFunctionTime()+"','2'," +
                "'3','','"+"zmh"+"',0,'"+sqlDate+"','"+sqlDate+"')";
        System.out.println(sql);
        try {
            Connection connection= JournalRecord.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            int resultSet=statement.executeUpdate(sql);
            System.out.println(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public AuditJournalEntity getAuditJournalEntity() {
        return auditJournalEntity;
    }

    public void setAuditJournalEntity(AuditJournalEntity auditJournalEntity) {
        this.auditJournalEntity = auditJournalEntity;
    }
}
