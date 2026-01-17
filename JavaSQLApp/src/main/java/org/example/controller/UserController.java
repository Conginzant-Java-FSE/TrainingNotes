package org.example.controller;

import org.example.models.User;
import org.example.service.UserService;

import java.util.List;
import java.util.Scanner;

public class UserController {
    UserService service;

    public UserController(){
        this.service = new UserService();
    }

    public void addUser(){
        System.out.println("add users?");
        User user = new User(11, "Kate", 22, "pass@123");

        if(service.addUser(user) == null){
            System.out.println("User not added: SQL Error");
        }else {
            System.out.println("User successfully added");
        }
    }

    public void getAllUsers(){
        System.out.println("Fecthing all users...");
        List<User> users = service.getAllUsers();
        if(users == null || users.size() == 0){
            System.out.println("No users fetched");
        }else {
            for(User u : users){
                System.out.println(u.getUsername() + " " + u.getAge());
            }
        }
    }

    public void updateUserAge(){
        System.out.println("Updating age... enter age & id");
        Scanner scanner = new Scanner(System.in);
        int age = scanner.nextInt();
        int id = scanner.nextInt();

        User user = service.updateUserAge(age, id);
        System.out.println(user.getUsername() + " " + user.getAge());
    }
}
