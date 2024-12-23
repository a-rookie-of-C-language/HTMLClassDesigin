package org.example.htmldesgin.service;

import org.example.htmldesgin.utils.Project;
import org.example.htmldesgin.dao.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Service
public class ProjectService{

    @Autowired
    private ProjectMapper projectMapper;

    public List<Project> getAllProjects() {
        return projectMapper.selectAll();
    }

    public Project getProjectByNo(String projectNo) {
        return projectMapper.selectByProjectNo(projectNo);
    }

    @Transactional
    public void addProject(Project project) {
        // 验证项目号唯一性
        if (projectMapper.selectByProjectNo(project.getProjectNo()) != null) {
            throw new RuntimeException("项目号已存在");
        }

        // 验证同一集团下企业名称唯一性
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

        // 验证同一集团下企业名称唯一性（排除自身）
        Project sameNameProject = projectMapper.selectByGroupAndCompany(
                project.getGroupName(), project.getCompanyName());
        if (sameNameProject != null && !sameNameProject.getProjectNo().equals(project.getProjectNo())) {
            throw new RuntimeException("同一集团下企业名称不能重复");
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
}