package org.example.htmldesgin.contorller;

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

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Project> projects = projectService.getProjectsByPage(page, pageSize);
        projects.forEach(System.out::println);
        int total = projectService.getTotalCount();
        Map<String, Object> data = new HashMap<>();
        data.put("records", projects);
        data.put("total", total);
        return Result.success(data);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Project project) {
        try {
            projectService.addProject(project);
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
}