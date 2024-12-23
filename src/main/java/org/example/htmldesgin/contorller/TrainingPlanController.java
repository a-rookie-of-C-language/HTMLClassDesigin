package org.example.htmldesgin.contorller;

import lombok.RequiredArgsConstructor;
import org.example.htmldesgin.service.TrainingPlanService;
import org.example.htmldesgin.utils.Result;
import org.example.htmldesgin.utils.TrainingPlan;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/trainingPlan")
@RequiredArgsConstructor
public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;

    @GetMapping("/get")
    public Result getTrainingPlans(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return Result.success(trainingPlanService.getTrainingPlans(startDate, endDate));
    }

    @PostMapping("/add")
    public Result createTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
        return Result.success(trainingPlanService.createTrainingPlan(trainingPlan));
    }

    @PutMapping("/update")
    public Result updateTrainingPlan(
            @RequestBody TrainingPlan trainingPlan) {
        return Result.success(trainingPlanService.updateTrainingPlan(trainingPlan));
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteTrainingPlan(@PathVariable Long id) {
        trainingPlanService.deleteTrainingPlan(id);
        return Result.success();
    }

}