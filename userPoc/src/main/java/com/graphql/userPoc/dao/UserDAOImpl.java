package com.graphql.userPoc.dao;



import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Repository;

import com.graphql.userPoc.model.User;
import com.graphql.userPoc.model.UserGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Repository
public class UserDAOImpl implements UserDAO {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("${elasticsearch.index.name}")
    private String indexName;

    @Value("${elasticsearch.user.type}")
    private String userTypeName;

    @Autowired
    private ElasticsearchTemplate esTemplate;
    
    //private UserGroupDao userGroupDao;

    @Override
    public List<User> getAllUsers() {
        SearchQuery getAllQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery()).build();
        return esTemplate.queryForList(getAllQuery, User.class);
    }

    @Override
    public User getUserById(String userId) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.wildcardQuery("userId", userId)).build();
        List<User> users = esTemplate.queryForList(searchQuery, User.class);
        if(!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
    

    @Override
    public User addNewUser(User user) {

        IndexQuery userQuery = new IndexQuery();
        userQuery.setIndexName(indexName);
        userQuery.setType(userTypeName);
        userQuery.setObject(user);

        LOG.info("User indexed: {}", esTemplate.index(userQuery));
        esTemplate.refresh(indexName);

        return user;
    }
    
    @Override
	public User updateUser(User user) {
    	SearchQuery searchQuery=new NativeSearchQueryBuilder()
    			.withFilter(QueryBuilders.wildcardQuery("userId",user.getUserId())).build();
    					//("userId",user.getUserId())).build();
    	List<User> users=esTemplate.queryForList(searchQuery, User.class);
    	System.out.println(users);
		if (!users.isEmpty()) {
			User eUser = users.get(0);
			if (user.getName() != null) {
				eUser.setName(user.getName());
			}
			if (user.getEmail() != null) {
				eUser.setEmail(user.getEmail());
			}
			if (user.getPassword() != null) {
				eUser.setPassword(user.getPassword());
			}
			Map<String, String> map = new HashMap<String, String>();
			if (user.getUserSettings().get("gender") != null) {
				map.put("gender", user.getUserSettings().get("gender"));
				eUser.setUserSettings(map);
			}

			if(user.getUserGroup().getGroupId()!=null) {
				//map.put("groupId",user.getUserGroup().getGroupId());
			eUser.getUserGroup().setGroupId(user.getUserGroup().getGroupId());
			
			}
			IndexQuery userQuery = new IndexQuery();
			userQuery.setIndexName(indexName);
			userQuery.setType(userTypeName);
			userQuery.setId(eUser.getUserId());
			userQuery.setObject(eUser);
			esTemplate.index(userQuery);
			return eUser;
		}
//      IndexRequest indexRequest = new IndexRequest();
//      
//      if(user.getName()!=null) {
//          indexRequest.source("name", user.getName());
//          }
//          if(user.getUserSettings().get("gender")!=null) {
//          	indexRequest.source("userSettings.gender",user.getUserSettings().get("gender"));
//          }
//          if(user.getUserGroup().getGroupId()!=null) {
//        	  indexRequest.source("userGroup.groupId",user.getUserGroup().getGroupId());
//          }
//        
//        UpdateQuery updateQuery = new UpdateQueryBuilder().withId(user.getUserId()).withClass(User.class).withIndexRequest(indexRequest).build();
//        esTemplate.update(updateQuery);
		return null ;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		esTemplate.delete(indexName,userTypeName,userId);
		
	}

	@Override
	public User getByGroupId(String groupId) {
		 SearchQuery searchQuery = new NativeSearchQueryBuilder()
	                .withFilter(QueryBuilders.wildcardQuery("userGroup.groupId", groupId)).build();
	        List<User> users = esTemplate.queryForList(searchQuery, User.class);
	        if(!users.isEmpty()) {
	            return users.get(0);
	        }
		return null;
	}

//	@Override
//	public UserGroup getUserGroup(User user) {
//		return userGroupDao.getUserGroup(user.getGroupId());
//	}
//
//	@Override
//	public List<User> findByGroupId(UserGroup userGroup) {
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				.withFilter(QueryBuilders.matchQuery("groupId",userGroup.getGroupId())).build();
//		
//		return esTemplate.queryForList(searchQuery,User.class);
//	}

	
}
