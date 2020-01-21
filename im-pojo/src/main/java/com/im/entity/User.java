package com.im.entity;


import com.im.model.UserModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * User 获取用户信息
 *
 * @author ZhangPeng
 * @date 2010-08-10
 */
public class User extends UserModel implements Serializable {

    public static final String ADMIN_ROLE = "administrator";
    private Integer id;
    private String userName;
    private String realName;
    private String password;
    private Boolean adminFlag = false;
    private String telephone;
    private Integer provinceCode;
    private Integer cityCode;
    private Integer districtCode;
    private String mobile;
    private String email;
    private String departments;
    private String positions;
    private Integer status;
    private Integer roleId;
    private Short userType;
    private String idCard;
    @ApiModelProperty(value = "借调期限")
    private Date expireTime;
    @ApiModelProperty(value = "账号有效期")
    private Date accountValidity;
    private String description;
    private String address;
    private Integer createMan;
    private Integer updateMan;
    private Date createTime;
    private Date updateTime;
    private String pushCode;
    private Byte pushPlatform;
    private Boolean noticeFlag = true;
    private Boolean interfaceFlag = false;
    private Integer bureauId;
    private Integer departmentId;
    private Boolean internalFlag;
    @ApiModelProperty(value = "级别")
    private String rank;
    @ApiModelProperty(value = "院")
    private String compound;
    @ApiModelProperty(value = "楼")
    private String building;
    @ApiModelProperty(value = "层")
    private String floor;
    @ApiModelProperty(value = "房间")
    private String room;
    @ApiModelProperty(value = "头像")
    private String photoPath;
    @ApiModelProperty(value = "数据权限")
    private String dataAuthority;
    @ApiModelProperty(value = "公司id")
    private Integer merchantId;
    @ApiModelProperty(value = "人事ID")
    private Integer personnelId;
    @ApiModelProperty(value = "账号激活状态")
    private Boolean active;
    @ApiModelProperty(value = "司局")
    private String bureau;
    private Boolean xjgwadd;
    private Boolean dbzqlaunch;
    private Boolean hgr;
    private Boolean swr;

    public Boolean getXjgwadd() {
        return xjgwadd;
    }

    public void setXjgwadd(Boolean xjgwadd) {
        this.xjgwadd = xjgwadd;
    }

    public Boolean getDbzqlaunch() {
        return dbzqlaunch;
    }

    public void setDbzqlaunch(Boolean dbzqlaunch) {
        this.dbzqlaunch = dbzqlaunch;
    }

    public Boolean getHgr() {
        return hgr;
    }

    public void setHgr(Boolean hgr) {
        this.hgr = hgr;
    }

    public Boolean getSwr() {
        return swr;
    }

    public void setSwr(Boolean swr) {
        this.swr = swr;
    }

    public String getBureau() {
        return bureau;
    }

    public void setBureau(String bureau) {
        this.bureau = bureau;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Integer personnelId) {
        this.personnelId = personnelId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public static String getAdminRole() {
        return ADMIN_ROLE;
    }

    public String getDataAuthority() {
        return dataAuthority;
    }

    public void setDataAuthority(String dataAuthority) {
        this.dataAuthority = dataAuthority;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Boolean getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Boolean adminFlag) {
        this.adminFlag = adminFlag;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Integer createMan) {
        this.createMan = createMan;
    }

    public Integer getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(Integer updateMan) {
        this.updateMan = updateMan;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPushCode() {
        return pushCode;
    }

    public void setPushCode(String pushCode) {
        this.pushCode = pushCode;
    }

    public Byte getPushPlatform() {
        return pushPlatform;
    }

    public void setPushPlatform(Byte pushPlatform) {
        this.pushPlatform = pushPlatform;
    }

    public Boolean getNoticeFlag() {
        return noticeFlag;
    }

    public void setNoticeFlag(Boolean noticeFlag) {
        this.noticeFlag = noticeFlag;
    }

    public Boolean getInterfaceFlag() {
        return interfaceFlag;
    }

    public void setInterfaceFlag(Boolean interfaceFlag) {
        this.interfaceFlag = interfaceFlag;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getBureauId() {
        return bureauId;
    }

    public void setBureauId(Integer bureauId) {
        this.bureauId = bureauId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getInternalFlag() {
        return internalFlag;
    }

    public void setInternalFlag(Boolean internalFlag) {
        this.internalFlag = internalFlag;
    }

    public Short getUserType() {
        return userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getAccountValidity() {
        return accountValidity;
    }

    public void setAccountValidity(Date accountValidity) {
        this.accountValidity = accountValidity;
    }
}
