package com.dragon.flow.vo.flowable.ret;

import java.io.Serializable;

/**
 * @author : bruce.liu
 * @title: : UserVo
 * @projectName : flowable
 * @description: 用户的VO
 * @date : 2019/12/313:45
 */
public class UserVo implements Serializable {
    /**
     * 工号
     */
    private String id;
    /**
     * 用户的真实姓名
     */
    private String displayName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像的类型
     */
    protected String mimeType;
    /**
     * 头像
     */
    private byte[] picture;

    /*************************扩展字段*****************************/
    /**
     * 离职状态 1:未离职；0:已离职
     */
    private Integer status;
    /**
     *
     * 标记用户是否是授权用户
     */
    private String privFlag;
    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 公司id
     */
    private String companyId;
    /**
     * 部门id
     */
    private String departmentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPrivFlag() {
        return privFlag;
    }

    public void setPrivFlag(String privFlag) {
        this.privFlag = privFlag;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
