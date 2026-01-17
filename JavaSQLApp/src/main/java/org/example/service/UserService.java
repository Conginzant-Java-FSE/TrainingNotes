package org.example.service;

import org.example.dao.UserDao;
import org.example.models.User;

import java.util.List;

public class UserService {
    UserDao dao;

    public UserService(){
        this.dao = new UserDao();
    }

    public User addUser(User user){
        //valid
        return dao.addUser(user);
    }

    public List<User> getAllUsers(){
        return dao.getAllUsers();
    }

    public User updateUserAge(int age, int user_id){
        return dao.updateAge(age, user_id);
    }
}
