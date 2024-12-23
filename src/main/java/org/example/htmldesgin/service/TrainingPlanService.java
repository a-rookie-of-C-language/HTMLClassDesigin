package org.example.htmldesgin.service;

import lombok.RequiredArgsConstructor;
import org.example.htmldesgin.dao.mapper.TrainingPlanMapper;
import org.example.htmldesgin.utils.TrainingPlan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlanService {

    private final TrainingPlanMapper trainingPlanMapper;

    /**
     * 获取指定日期范围内的培训计划
     */
    public List<TrainingPlan> getTrainingPlans(LocalDate startDate, LocalDate endDate) {
        return trainingPlanMapper.selectByDateRange(startDate, endDate);
    }

    /**
     * 创建培训计划
     */
    @Transactional
    public boolean createTrainingPlan(TrainingPlan trainingPlan) {
        return trainingPlanMapper.insert(trainingPlan) == 1;
    }

    /**
     * 更新培训计划
     */
    @Transactional
    public boolean updateTrainingPlan(TrainingPlan trainingPlan) {
        return trainingPlanMapper.update(trainingPlan) == 1;
    }

    /**
     * 删除培训计划
     */
    @Transactional
    public void deleteTrainingPlan(Long id) {
        trainingPlanMapper.deleteById(id);
    }

    /**
     * 根据ID获取培训计划
     */
    public TrainingPlan getTrainingPlanById(Long id) {
        return trainingPlanMapper.selectById(id);
    }
}