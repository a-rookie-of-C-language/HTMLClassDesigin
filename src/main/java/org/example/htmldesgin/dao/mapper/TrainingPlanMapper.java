package org.example.htmldesgin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.htmldesgin.utils.TrainingPlan;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TrainingPlanMapper {
    // 根据日期范围查询培训计划
    List<TrainingPlan> selectByDateRange(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    // 根据ID查询培训计划
    TrainingPlan selectById(@Param("id") Long id);

    // 插入新的培训计划
    int insert(TrainingPlan trainingPlan);

    // 更新培训计划
    int update(TrainingPlan trainingPlan);

    // 根据ID删除培训计划
    int deleteById(@Param("id") Long id);
    
    // 根据项目编号查询公司名称
    String selectCompanyNameByProjectNo(@Param("projectNo") String projectNo);
}