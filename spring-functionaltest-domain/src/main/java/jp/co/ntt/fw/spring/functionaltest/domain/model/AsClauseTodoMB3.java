/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.domain.model;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;

public class AsClauseTodoMB3 implements Serializable {

    private static final long serialVersionUID = 1L;

    private String todoNum;

    private String todoName;

    private String catCode;

    private boolean status;

    private Date originDate;

    private DateTime completionDate;

    private long ver;

    public String getTodoNum() {
        return todoNum;
    }

    public void setTodoNum(String todoNum) {
        this.todoNum = todoNum;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getOriginDate() {
        return originDate;
    }

    public void setOriginDate(Date originDate) {
        this.originDate = originDate;
    }

    public long getVer() {
        return ver;
    }

    public void setVer(long ver) {
        this.ver = ver;
    }

    public DateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(DateTime completionDate) {
        this.completionDate = completionDate;
    }

}
