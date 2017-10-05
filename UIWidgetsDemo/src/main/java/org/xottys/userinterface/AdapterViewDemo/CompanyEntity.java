/**
 * ListView Demo中用到的数据
 * <p>
 * <br/>Copyright (C), 2017-2018, wcy
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name: CompanyEntity
 * <br/>Date:Oct，2017
 *
 * @author wcy
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import com.google.gson.annotations.SerializedName;

public class CompanyEntity {
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private String code;
    @SerializedName("logo")
    private String logo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
