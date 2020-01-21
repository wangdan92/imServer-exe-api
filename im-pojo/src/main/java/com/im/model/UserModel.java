package com.im.model;
import com.im.entity.Jurisdiction;
import com.im.entity.User;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZhangPeng
 * @date 2017/11/27
 */
public class UserModel extends BaseModel implements Serializable {
    private String roleName;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String createRealName;
    private String updateRealName;
    private String newPassword;
    private String confirmPassword;
    private String departmentName;
    private String[] condition;
    private String aid;
    private List<Jurisdiction> list;
    private List<User> tableData;
    private int colorType;

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }

    public List<User> getTableData() {
        return tableData;
    }

    public void setTableData(List<User> tableData) {
        this.tableData = tableData;
    }

    @ApiModelProperty(value = "下单权限")
    private boolean orderjurisdiction=false;//1.下单，2.审核，3，查询
    @ApiModelProperty(value = "审核权限")
    private boolean auditjurisdiction=false;//1.下单，2.审核，3，查询
    @ApiModelProperty(value = "查询权限")
    private boolean viewjurisdiction=false;//1.下单，2.审核，3，查询
    @ApiModelProperty(value = "院的ID")
    private Integer compoundId;
    @ApiModelProperty(value = "楼的ID")
    private Integer buildingId;
    @ApiModelProperty(value = "层的ID")
    private  Integer floorId;




    public void setCompoundId(Integer compoundId) {
        this.compoundId = compoundId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Integer getCompoundId() {
        return compoundId;
    }

    public void setCompoundId(int compoundId) {
        this.compoundId = compoundId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public boolean isOrderjurisdiction() {
        return orderjurisdiction;
    }

    public void setOrderjurisdiction(boolean orderjurisdiction) {
        this.orderjurisdiction = orderjurisdiction;
    }

    public boolean isAuditjurisdiction() {
        return auditjurisdiction;
    }

    public void setAuditjurisdiction(boolean auditjurisdiction) {
        this.auditjurisdiction = auditjurisdiction;
    }

    public boolean isViewjurisdiction() {
        return viewjurisdiction;
    }

    public void setViewjurisdiction(boolean viewjurisdiction) {
        this.viewjurisdiction = viewjurisdiction;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public List<Jurisdiction> getList() {
        return list;
    }

    public void setList(List<Jurisdiction> list) {
        this.list = list;
    }

    public String[] getCondition() {
        return condition;
    }

    public void setCondition(String[] condition) {
        this.condition = condition;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCreateRealName() {
        return createRealName;
    }

    public void setCreateRealName(String createRealName) {
        this.createRealName = createRealName;
    }

    public String getUpdateRealName() {
        return updateRealName;
    }

    public void setUpdateRealName(String updateRealName) {
        this.updateRealName = updateRealName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
