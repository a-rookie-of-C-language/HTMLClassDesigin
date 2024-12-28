package org.example.htmldesgin.contorller;

import lombok.RequiredArgsConstructor;
import org.example.htmldesgin.service.TrainingPlanService;
import org.example.htmldesgin.utils.Result;
import org.example.htmldesgin.utils.TrainingPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/trainingPlan")
@RequiredArgsConstructor
public class TrainingPlanController {

    private static final Logger log = LoggerFactory.getLogger(TrainingPlanController.class);

    private final TrainingPlanService trainingPlanService;

    @GetMapping("/get")
    public Result getTrainingPlans(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) Boolean includeWeekend,
            @RequestParam(required = false) String projectNo) {
        return Result.success(trainingPlanService.getTrainingPlans(
            startDate, 
            endDate, 
            includeWeekend,
            projectNo
        ));
    }

    @PostMapping("/add")
    public Result createTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
        return Result.success(trainingPlanService.createTrainingPlan(trainingPlan));
    }

    @PutMapping("/update")
    public Result updateTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
        log.info("接收到更新请求: {}", trainingPlan);
        boolean success = trainingPlanService.updateTrainingPlan(trainingPlan);
        log.info("更新结果: {}", success);
        return Result.success(success);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteTrainingPlan(@PathVariable Long id) {
        trainingPlanService.deleteTrainingPlan(id);
        return Result.success();
    }

    @GetMapping("/projectNo")
    public Result getTrainingPlanProjectNosAndMore() {
        return Result.success(trainingPlanService.getTrainingPlanProjectNosAndMore());
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportTrainingPlan(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) Boolean includeWeekend,
            @RequestParam(required = false) String projectNo) throws IOException {

        ByteArrayOutputStream outputStream = trainingPlanService.exportToExcel(
            startDate, 
            endDate, 
            includeWeekend,
            projectNo
        );

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=training-plan-" + startDate + "-to-" + endDate + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("/projectNos")
    public Result getProjectNos(
        @RequestParam String groupName,
        @RequestParam String companyName
    ) {
        return Result.success(
            trainingPlanService.getProjectNosByGroupAndCompany(groupName, companyName)
        );
    }

}