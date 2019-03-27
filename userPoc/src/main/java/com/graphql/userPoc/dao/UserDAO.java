package com.graphql.userPoc.dao;

import java.util.List;

import com.graphql.userPoc.model.User;
import com.graphql.userPoc.model.UserGroup;

public interface UserDAO {

    List<User> getAllUsers();
    User getUserById(String userId);
    User addNewUser(User user);
    User updateUser(User user);
    void deleteUser(String userId);
    User getByGroupId(String groupId);
//    UserGroup getUserGroup(User user);
//    List<User> findByGroupId(UserGroup userGroup);
}