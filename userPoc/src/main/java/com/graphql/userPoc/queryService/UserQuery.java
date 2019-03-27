package com.graphql.userPoc.queryService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graphql.userPoc.dao.UserDAO;
import com.graphql.userPoc.model.User;
import com.graphql.userPoc.model.UserGroup;

import graphql.GraphQLException;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;

@Component
@GraphQLApi
public class UserQuery {
	
//	@Autowired
//	UserRepository userRepository;
	
	@Autowired
	UserDAO userDao;
	
	@GraphQLMutation
	public User createUser(User user) {
		return userDao.addNewUser(user);
	}
	
	@GraphQLMutation
	public User updateUser(User user) {
		return userDao.updateUser(user);
	}
//	@GraphQLMutation
//	public User updateUser(String id,
//			String name,
//			String email,String password) throws NoSuchElementException{
//		
//		User user=userRepository.findById(id).orElse(null);
//		
//		//User user=optionalUser.get();
//		if(user==null) {
//			throw new GraphQLException("user not found");
//		}
//		if(name!=null) {
//			user.setName(name);
//		}
//		if(email!=null) {
//			user.setEmail(email);;
//
//}
//		if(password!=null) {
//			user.setPassword(password);;
//		}
//		
//		return userRepository.save(new User(user.getId(),user.getName(),user.getEmail(),user.getPassword()));
//	}
	
	@GraphQLMutation
	public Boolean deleteUser(String userId) {
		userDao.deleteUser(userId);
		return true;
	}
	
//	@GraphQLQuery
//	public Iterable<User> getAllUsers(){
//		return userRepository.findAll();
//	}
	
	@GraphQLQuery
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
	
//	@GraphQLQuery
//	public Optional<User> user(String id){
//		return userRepository.findById(id);
//	}
	
	@GraphQLQuery
	public User getUserById(String id) {
		return userDao.getUserById(id);
	}
	
	@GraphQLQuery
	public User getUserBygroupId(String groupId) {
		return userDao.getByGroupId(groupId);
	}
//	@GraphQLQuery
//	public UserGroup getUserGroup(@GraphQLContext User user ) {
//		return userDao.getUserGroup(user);
//	}
//	
//	@GraphQLQuery
//	public List<User> findByGroupId(@GraphQLContext UserGroup userGroup){
//		return userDao.findByGroupId(userGroup);
//	}
//	@GraphQLQuery
//	public User user (@GraphQLContext Signin signin) {
//		return signin.getUser();
//		
//	}
//	
//	@GraphQLMutation
//	public Signin signinUser(Auth auth) throws IllegalAccessException {
//	    User user = userRepository.findByEmail(auth.getEmail());
//	    if (user==null) {throw new GraphQLException("user not found");}
//	    if (user.getPassword().equals(auth.getPassword())) {
//	        return new Signin(user.getId(), user);
//	    }
//	    throw new GraphQLException("Invalid credentials");
//	}

}
