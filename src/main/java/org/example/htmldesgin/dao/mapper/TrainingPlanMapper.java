package org.example.htmldesgin.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.htmldesgin.utils.TrainingPlan;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TrainingPlanMapper {
    // 根据日期范围查询培训计划
    List<TrainingPlan> selectByDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("includeWeekend") Boolean includeWeekend,
        @Param("projectNo") String projectNo
    );

    // 根据ID查询培训计划
    TrainingPlan selectById(@Param("id") Long id);

    // 插入新的培训计划
    int add(TrainingPlan trainingPlan);

    // 更新培训计划
    int update(TrainingPlan trainingPlan);

    // 根据ID删除培训计划
    int deleteById(@Param("id") Long id);
    
    // 根据项目编号查询公司名称
    String selectCompanyNameByProjectNo(@Param("projectNo") String projectNo);

    List<TrainingPlan> selectProjectNosAndMore();

    @Select("SELECT * FROM trainingPlan " +
            "WHERE trainingDate BETWEEN #{startDate} AND #{endDate} " +
            "ORDER BY groupName, companyName, trainingDate")
    List<TrainingPlan> getTrainingPlansByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    // 添加新方法：根据集团和公司名称查询项目编号
    @Select("SELECT projectNo FROM project WHERE groupName = #{groupName} AND companyName = #{companyName}")
    List<String> selectProjectNosByGroupAndCompany(
        @Param("groupName") String groupName, 
        @Param("companyName") String companyName
    );
    @Delete("delete from trainingPlan where projectNo = #{projectNo}")
    int deleteByProjectNo(String projectNo);
}