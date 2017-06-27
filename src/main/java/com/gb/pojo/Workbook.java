package com.gb.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Workbook implements Serializable{
    private static final long serialVersionUID = 6814198226357178032L;
    private BigDecimal id;

    private String code;

    private String describe;

    private String type;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}