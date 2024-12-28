package org.example.htmldesgin.contorller;

import org.example.htmldesgin.service.TrainingPlanService;
import org.example.htmldesgin.utils.Project;
import org.example.htmldesgin.service.ProjectService;
import org.example.htmldesgin.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private TrainingPlanService trainingPlanService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Project> projects = projectService.getProjectsByPage(page, pageSize);
        int total = projectService.getTotalCount();
        Map<String, Object> data = new HashMap<>();
        data.put("records", projects);
        data.put("total", total);
        return Result.success(data);
    }

    @GetMapping("/report")
    public Result getProjectReport(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            List<Project> projects = projectService.getProjectReportWithUpdatedHours(page, pageSize);
            int total = projectService.getTotalCount();
            Map<String, Object> data = new HashMap<>();
            data.put("records", projects);
            data.put("total", total);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取项目报表失败：" + e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody Project project) {
        try {
            projectService.addProject(project);
            trainingPlanService.createTrainingPlan(trainingPlanService.createTrainingPlan(project));
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    public Result update(@RequestBody Project project) {
        try {
            projectService.updateProject(project);
            return Result.success("更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{projectNo}")
    public Result delete(@PathVariable String projectNo) {
        try {
            projectService.deleteProject(projectNo);
            trainingPlanService.deleteTrainingPlan(projectNo);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/available")
    public Result getAvailableProjects() {
        System.out.println(projectService.getAvailableProjects());
        return Result.success(projectService.getAvailableProjects());
    }

    @PostMapping("/updateRemainingAmount")
    public Result updateRemainingAmount(@RequestBody Map<String, Object> request) {
        String projectNo = (String) request.get("projectNo");
        Double remainingAmount = Double.parseDouble((String) request.get("remainingAmount"));
        projectService.updateRemainingAmount(projectNo, remainingAmount);
        return Result.success("更新成功");
    }
}