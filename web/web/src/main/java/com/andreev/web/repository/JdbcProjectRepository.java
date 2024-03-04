package com.andreev.web.repository;

import java.sql.PreparedStatement;
import java.util.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.andreev.web.entity.Project;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcProjectRepository implements IProjectRepository{
    
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<Project> projectMapper = (rs, rowNum) -> {
        Project proj = new Project();
        proj.setId(rs.getInt("proj_id"));
        proj.setName(rs.getString("proj_name"));
        proj.setDiscription(rs.getString("proj_decription"));
        proj.setStart(rs.getDate("proj_start"));
        proj.setEnd(rs.getDate("proj_end"));
        return proj;
    };

    @Override //+
    public Project createProject(Project proj) {
        String sql = "INSERT INTO project (proj_name, proj_decription, proj_start, proj_end) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "proj_id" });
            ps.setString(1, proj.getName());
            ps.setString(2, proj.getDiscription());
            ps.setDate(3, new java.sql.Date(proj.getStart().getTime()));
            ps.setDate(4, new java.sql.Date(proj.getEnd().getTime()));
            return ps;
        }, keyHolder);
        System.out.println("Created id "+keyHolder.getKey().toString());
        if (result == 1) {
            proj.setId(Integer.parseInt(keyHolder.getKey().toString()));
            return proj;
            
        } else return null;
    }

    @Override //+
    public boolean changeProject(int id, Project proj) {
        try {
            int res = jdbcTemplate.update("UPDATE project SET proj_name = ?, proj_decription = ?, proj_start = ?, proj_end = ? WHERE proj_id = ?", 
            proj.getName(), proj.getDiscription(), proj.getStart(), proj.getEnd(), id);
            if(res == 1){return true;}
            else {return false;} 
        } catch(Exception ex) {ex.printStackTrace(); return false;}
    }

    @Override //+
    public void deleteProject(int id) {
        Map<String, Integer> param = Map.of("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM project where proj_id = :id", param); //принимаем мап
    }

    @Override //+
    public Project getProject(int id) {
        List<Project> result = jdbcTemplate.query("SELECT * FROM project WHERE proj_id = ?", projectMapper, id);
        if (result.size() == 0) { return null; }
        else { return result.get(0); }
    }

    @Override //?
    public List<Project> getProjectFiltred(Date d1, Date d2) {
        List<Project> result = jdbcTemplate.query("SELECT * FROM project WHERE proj_start > ? AND proj_end < ?", projectMapper, d1, d2);
        if (result.size() == 0) { return null; }
        else { return result; }
    }
    
    @Override //+
    public List<Project> getProjectAll() {
        return jdbcTemplate.query("SELECT * FROM project", projectMapper);
    }

}
