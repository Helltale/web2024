package com.andreev.web.repository;

import com.andreev.web.entity.Project;

import java.util.Date;
import java.util.List;

public interface IProjectRepository {
    Project createProject(Project project);
    boolean changeProject(int id, Project project);
    void deleteProject(int id);
    Project getProject(int id);
    List<Project> getProjectFiltred(Date d1, Date d2);
    List<Project> getProjectAll();
}
