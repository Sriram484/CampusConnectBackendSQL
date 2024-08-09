package com.example.campusconnectbackend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.campusconnectbackend.Model.BlockedUser;
import com.example.campusconnectbackend.Service.BlockedUserService;

@RestController
@RequestMapping("/admin/api/blocked")
public class BlockedController {

    @Autowired
    private BlockedUserService blockedService;

    @PostMapping("/block/{blockedPersonId}")
    public void blockUser(
            @PathVariable Integer blockedPersonId) {
        blockedService.blockUser(blockedPersonId);
    }

    @GetMapping("/getBlockedUser")
    public List<BlockedUser> getAllBlockedUsers() {
        return blockedService.getAllBlockedUsers();
    }
}
