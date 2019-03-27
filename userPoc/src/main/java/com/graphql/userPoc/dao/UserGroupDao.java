package com.graphql.userPoc.dao;

import com.graphql.userPoc.model.UserGroup;

public interface UserGroupDao {

	UserGroup getUserGroup(String id);
	UserGroup adduserGroup(UserGroup usergroup);
}
