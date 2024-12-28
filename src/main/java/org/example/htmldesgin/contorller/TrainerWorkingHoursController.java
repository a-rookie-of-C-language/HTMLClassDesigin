package org.example.htmldesgin.contorller;

import org.example.htmldesgin.service.TrainerWorkingHoursService;
import org.example.htmldesgin.utils.Result;
import org.example.htmldesgin.utils.TrainerWorkingHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/working-hours")
public class TrainerWorkingHoursController {
    @Autowired
    private TrainerWorkingHoursService trainerWorkingHoursService;

    @GetMapping("/get")
    public Result getAll() {
        try {
            List<TrainerWorkingHours> hours = trainerWorkingHoursService.getAllWorkingHours();
            return Result.success(hours);
        } catch (Exception e) {
            return Result.error("获取工时数据失败");
        }
    }

    @GetMapping("/getName")
    public Result getNames() {
        try {
            return Result.success(trainerWorkingHoursService
                    .getAllWorkingHours()
                    .stream()
                    .map(TrainerWorkingHours::getUsername)
                    .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    public Result updateHours(@RequestBody Map<String, Object> params) {
        try {
            String username = (String) params.get("username");
            String month = (String) params.get("month");
            Float hours = Float.parseFloat(params.get("hours").toString());

            boolean success = trainerWorkingHoursService.updateWorkingHours(username, month, hours);
            return success ? Result.success() : Result.error("更新失败");
        } catch (Exception e) {
            return Result.error("更新工时数据失败");
        }
    }
}
