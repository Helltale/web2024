package com.andreev.web.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andreev.web.entity.Project;
import com.andreev.web.repository.JdbcProjectRepository;

@Service
public class MainService {
    
    @Autowired
    private JdbcProjectRepository jdbcProj;

    
    public Project createProject(Project proj) {
        return jdbcProj.createProject(proj);
    }

    public boolean changeProject(int id, Project proj) {
        return jdbcProj.changeProject(id, proj);
    }

    public void deleteProject(int id) {
        jdbcProj.deleteProject(id);
    }

    public Project getProject(int id) {
        return jdbcProj.getProject(id);
    }

    public List<Project> getProjectFiltred(Date d1, Date d2){ 
        return jdbcProj.getProjectFiltred(d1, d2);
    }

    public List<Project> getProjectAll() {
        return jdbcProj.getProjectAll();
    }
}
