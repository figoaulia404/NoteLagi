package com.example.notelagi.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("real_password")
    @Expose
    private String realPassword;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("regid")
    @Expose
    private String regid;
    @SerializedName("status_login")
    @Expose
    private Integer statusLogin;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("last_sincron")
    @Expose
    private String lastSincron;
    @SerializedName("app_ver")
    @Expose
    private Object appVer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealPassword() {
        return realPassword;
    }

    public void setRealPassword(String realPassword) {
        this.realPassword = realPassword;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public Integer getStatusLogin() {
        return statusLogin;
    }

    public void setStatusLogin(Integer statusLogin) {
        this.statusLogin = statusLogin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastSincron() {
        return lastSincron;
    }

    public void setLastSincron(String lastSincron) {
        this.lastSincron = lastSincron;
    }

    public Object getAppVer() {
        return appVer;
    }

    public void setAppVer(Object appVer) {
        this.appVer = appVer;
    }
}
