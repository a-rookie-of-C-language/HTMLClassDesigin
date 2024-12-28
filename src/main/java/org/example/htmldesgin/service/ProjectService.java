package org.example.htmldesgin.service;

import org.example.htmldesgin.utils.Project;
import org.example.htmldesgin.dao.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Transactional
    public void addProject(Project project) {
        if (projectMapper.selectByProjectNo(project.getProjectNo()) != null) {
            throw new RuntimeException("项目号已存在");
        }
        if (projectMapper.selectByGroupAndCompany(project.getGroupName(), project.getCompanyName()) != null) {
            throw new RuntimeException("同一集团下企业名称不能重复");
        }
        projectMapper.insert(project);
    }

    @Transactional
    public void updateProject(Project project) {
        Project existingProject = projectMapper.selectByProjectNo(project.getProjectNo());
        if (existingProject == null) {
            throw new RuntimeException("项目不存在");
        }
        projectMapper.update(project);
    }

    @Transactional
    public void deleteProject(String projectNo) {
        projectMapper.delete(projectNo);
    }

    public List<Project> getProjectsByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return projectMapper.selectByPage(offset, pageSize);
    }

    public int getTotalCount() {
        return projectMapper.selectCount();
    }

    public List<String> getAvailableProjects() {
        return projectMapper.selectAvailableProjects();
    }

    @Transactional
    public List<Project> getProjectReportWithUpdatedHours(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Project> projects = projectMapper.selectByPage(offset, pageSize);
        return projects.stream()
                .map(this::updateProjectHours)
                .collect(Collectors.toList());
    }

    private Project updateProjectHours(Project project) {
        if (project.getStartDate() == null) {
            return project;
        }
        double consumedHours = calculateConsumedHours(project.getStartDate());
        project.setConsumedHours(consumedHours);
        projectMapper.updateProjectHours(project);
        return project;
    }

    private double calculateConsumedHours(LocalDate startDate) {
        if (startDate == null) {
            return 0;
        }
        LocalDate now = LocalDate.now();
        if (startDate.isAfter(now)) {
            return 0;
        }
        int workDays = 0;
        LocalDate current = startDate;
        while (!current.isAfter(now)) {
            if (current.getDayOfWeek().getValue() <= 5) {
                workDays++;
            }
            current = current.plusDays(1);
        }
        return workDays;
    }

    @Transactional
    public void updateRemainingAmount(String projectNo, Double remainingAmount) {
        Project project = projectMapper.selectByProjectNo(projectNo);
        if (project == null) {
            throw new RuntimeException("项目不存在");
        }
        project.setRemainingAmount(remainingAmount);
        projectMapper.updateRemainingAmount(project);
    }
}