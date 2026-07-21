package com.app.ecom.Contoller;

import com.app.ecom.Entity.User;
import com.app.ecom.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
   private  final UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAlluser(){
        return ResponseEntity.ok(userService.fetchAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getuser(@PathVariable long id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public  ResponseEntity<String> createuser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User added Succesfully");
    }
    @PutMapping("{id}")
    public  ResponseEntity<String> updateuser(@PathVariable long id,@RequestBody User userupdated){
        boolean updated=userService.updatedUser(id,userupdated);
        if(updated)
            return ResponseEntity.ok("User Updated Succesfully");
        return  ResponseEntity.notFound().build();
    }
}
