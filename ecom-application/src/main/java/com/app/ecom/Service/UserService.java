package com.app.ecom.Service;

import com.app.ecom.Entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();
    private  long nextid=1l;

    @GetMapping("/api/users")
    public List<User> fetchAllUser() {
        return userList;
    }


    public void addUser(@RequestBody User user) {
        user.setId(nextid++);
        userList.add(user);

    }

    public Optional<User> fetchUser(long id) {
     return  userList.stream()
             .filter(user -> user.getId() == id)
             .findFirst();
        }

    public boolean updatedUser(long id, User userupdated) {
        return  userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .map(user -> {
                    user.setFirstName(userupdated.getFirstName());
                    user.setLastName(userupdated.getLastName());
                    return true;
                })
                .orElse(false);
    }
}

