package com.example.campusconnectsql.Controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.campusconnectsql.Model.CampusconnectsqlModel;
import com.example.campusconnectsql.Service.CampusconnectsqlService;

public class CampusconnectsqlController {
     
    @Autowired
    private CampusconnectsqlService riddleService;

  
    @PostMapping(value = "/save")
    private String saveStudent(@RequestBody CampusconnectsqlModel user) {

        riddleService.saveorUpdate(user);
        return user.getScoreId();
    }

    @GetMapping(value = "/getall")
    public Iterable<CampusconnectsqlModel> getStudents() {
        return riddleService.listAll();
    }

    @PutMapping(value = "/edit/{id}")
    private CampusconnectsqlModel update(@RequestBody CampusconnectsqlModel user, @PathVariable(name = "id") String scoreid) {
        user.setScoreId(scoreid);
        riddleService.saveorUpdate(user);
        return user;
    }

    @DeleteMapping("/delete/{id}")
    private void deleteStudent(@PathVariable("id") String _id) {
        riddleService.deleteUser(_id);
    }
    @RequestMapping("/search/{id}")
    private CampusconnectsqlModel getStudents(@PathVariable(name = "id") String userid) {
        return riddleService.getUserByID(userid);
    }
    private static final Logger logger=LoggerFactory.getLogger(CampusConnectController.class);
    @GetMapping("/")
    public String hello()
    { 
        logger.info("Info is printed");
        return "Hello all good morning :)";
    }
}
