package com.gb.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class tbMailHistory implements Serializable{
    private static final long serialVersionUID = -8639164571657834627L;
    private BigDecimal id;
/*
*
* */
    private String addresser;

    private String recipients;

    private String toRecipients;

    private String secretRecipients;

    private String mailStyle;

    private String accessoryName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;
    //1为失败0为成功
    private Short isSuccessful;

    private String enterName;

    private String ipAddress;

    //业务字段
    //邮件正文--文本
    private String mailContent;

    //业务字段
    //发件人密码
    private String sendPwd;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getSendPwd() {
        return sendPwd;
    }

    public void setSendPwd(String sendPwd) {
        this.sendPwd = sendPwd;
    }

    @Override
    public String toString() {
        return "tbMailHistory{" +
                "id=" + id +
                ", addresser='" + addresser + '\'' +
                ", recipients='" + recipients + '\'' +
                ", toRecipients='" + toRecipients + '\'' +
                ", secretRecipients='" + secretRecipients + '\'' +
                ", mailStyle='" + mailStyle + '\'' +
                ", accessoryName='" + accessoryName + '\'' +
                ", createdTime=" + createdTime +
                ", isSuccessful=" + isSuccessful +
                ", enterName='" + enterName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", mailContent='" + mailContent + '\'' +
                ", sendPwd='" + sendPwd + '\'' +
                '}';
    }


    public String getAddresser() {
        return addresser;
    }

    public void setAddresser(String addresser) {
        this.addresser = addresser == null ? null : addresser.trim();
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients == null ? null : recipients.trim();
    }

    public String getToRecipients() {
        return toRecipients;
    }

    public void setToRecipients(String toRecipients) {
        this.toRecipients = toRecipients == null ? null : toRecipients.trim();
    }

    public String getsecretRecipients() {
        return secretRecipients;
    }

    public void setsecretRecipients(String secretRecipients) {
        this.secretRecipients = secretRecipients == null ? null : secretRecipients.trim();
    }

    public String getMailStyle() {
        return mailStyle;
    }

    public void setMailStyle(String mailStyle) {
        this.mailStyle = mailStyle == null ? null : mailStyle.trim();
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName == null ? null : accessoryName.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Short getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(Short isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getEnterName() {
        return enterName;
    }

    public void setEnterName(String enterName) {
        this.enterName = enterName == null ? null : enterName.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }


}