package com.im.dao;


import com.im.entity.Department;
import com.im.entity.User;
import com.im.util.MyBatisDao;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DepartmentDao {


    List<Department> listDepartment(Department department);

    @Select("select * from department where type = #{type} and bureauId=31 order by seq asc")
    List<Department> listByType(Department department);

    @Select("select d.*,b.bureauName from department d inner join bureau b on d.bureauId=b.id where d.id=#{id}")
    Department getDepartmentById(@Param("id") int id);

    /**
     *根据ids获取部门列表
     */
    @Select("select * from department where FIND_IN_SET(id,#{ids}) order by seq asc")
    List<Department> getDempartmentListByIds(@Param("ids") String ids);

    /**
     *获取所有处室
     */
    @Select("select d.*,b.bureauName,CONCAT('1-',d.id) as aid from department d inner join bureau b on d.bureauId=b.id order by seq asc")
    List<Department> listAll();

    /**
     * 获取所有处室
     * @param bureauId
     * @return
     */
    @Select("select d.*,b.bureauName,CONCAT('1-',d.id) as aid from department d inner join bureau b on d.bureauId=b.id and d.bureauId=#{bureauId} order by seq asc")
    List<Department> allList(Integer bureauId);

    @Select("select d.*,d.departmentName AS label,b.bureauName from department d inner join bureau b on d.bureauId=b.id where d.bureauId=#{bureauId} order by seq asc")
    List<Department> listDepartmentByBureauId(@Param("bureauId") int bureauId);
    /**
     * 添加处室
     * @param department department
     * @return int 影响记录数
     */
    @Insert("insert into department (departmentName,bureauId,createdOn,createdBy,type,seq)values(#{departmentName},#{bureauId},#{createdOn},#{createdBy},#{type},#{seq})")
    int addDpartment(Department department);

    /**
     * 删除处室
     * @param id 唯一编号
     * @return int 影响记录数
     */
    @Delete("delete from department WHERE id = #{id}")
    int deleteDepartmentById(@Param("id") int id);

    @Update("update department set departmentName=#{departmentName},bureauId=#{bureauId},modifiedOn=#{modifiedOn},modifiedBy=#{modifiedBy},seq=#{seq} where id=#{id}")
    int updateDepartment(Department department);

    @Select("select * from department where id = #{departmentId}")
    Department getDepartmentNmaeById(@Param("departmentId") int departmentId);

    @Select("select * from department where departmentName = #{departmentName} limit 1")
    Department getDepartmentByName(@Param("departmentName") String departmentName);

    @Select("select * from department where departmentName = #{departmentName} and bureauId=#{bureauId}")
    Department getDepartmentByNameBureauId(@Param("departmentName") String departmentName, @Param("bureauId") Integer bureauId);

    @Select("select u.* from user u left join department d on u.departmentId = d.id " +
            "left join bureau bu on d.bureauId = bu.id left join userresource ur on u.id = ur.userId " +
            "where bu.bureauName = #{bureauName} and d.departmentName = #{deptName} and ur.type='powerposition' and ur.code='csfzr' limit 1")
    User getDepartmentLeader(Map<String, Object> map);
}
