package org.example.htmldesgin.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.htmldesgin.utils.Project;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper
public interface ProjectMapper {
    List<Project> selectAll();
    
    Project selectByProjectNo(String projectNo);
    
    Project selectByGroupAndCompany(String groupName, String companyName);
    
    int insert(Project project);
    
    int update(Project project);
    
    int delete(String projectNo);
    
    List<Project> selectByPage(int offset, int limit);
    
    int selectCount();

    List<String> selectAvailableProjects();

    void updateProjectHours(Project project);

    void updateRemainingAmount(Project project);
}