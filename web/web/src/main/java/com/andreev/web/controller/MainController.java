package com.andreev.web.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.andreev.web.Service.MainService;
import com.andreev.web.entity.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/projects")
public class MainController {
    
    @Autowired
    private MainService service;

    //создание проекта
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project proj) {
        System.out.println("create mapping");
        if(service.createProject(proj) != null) { return new ResponseEntity<>(proj, HttpStatus.CREATED); }
        else { return new ResponseEntity<>(HttpStatus.CONFLICT); }
    }
    
    //обновление
    @PutMapping("/{id}")
    public ResponseEntity<?> changeProject(@PathVariable("id") int id,
                                           @RequestBody Project proj) {
        if (service.changeProject(id, proj) == true) { return new ResponseEntity<>(HttpStatus.OK); }
        else { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
    }

    //удаление
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") int id) {
        service.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //получение по id
    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        Project proj = service.getProject(id);
        if (proj == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        else { return new ResponseEntity<>(proj, HttpStatus.OK); }
    }

    //получение по дате
    @GetMapping("/getbetween")
    public ResponseEntity<?> getProjectWithFilter(@RequestParam("d_start")
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                  @RequestParam("d_end")
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return new ResponseEntity<>(service.getProjectFiltred(start, end), HttpStatus.OK);
    }

    //получение всех
    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects() {
        return new ResponseEntity<>(service.getProjectAll(), HttpStatus.OK);
    }
}
