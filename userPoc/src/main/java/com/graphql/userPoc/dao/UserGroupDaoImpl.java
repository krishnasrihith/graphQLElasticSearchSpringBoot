package com.graphql.userPoc.dao;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.graphql.userPoc.model.User;
import com.graphql.userPoc.model.UserGroup;

public class UserGroupDaoImpl implements UserGroupDao{

	
	   @Value("${elasticsearch.index.name}")
	    private String indexName;

	    @Value("${elasticsearch.userGroup.type}")
	    private String userGroupTypeName;
	    
	    @Autowired
	    ElasticsearchTemplate esTemplate;
	@Override
	public UserGroup getUserGroup(String groupId) {
		// TODO Auto-generated method stub
		 SearchQuery searchQuery = new NativeSearchQueryBuilder()
	                .withFilter(QueryBuilders.matchQuery("groupId",groupId)).build();
	        List<UserGroup> userGroups = esTemplate.queryForList(searchQuery, UserGroup.class);
	        if(!userGroups.isEmpty()) {
	            return userGroups.get(0);
	        }
	        return null;
	}

	@Override
	public UserGroup adduserGroup(UserGroup usergroup) {
		// TODO Auto-generated method stub
		IndexQuery userGroupQuery=new IndexQuery();
		userGroupQuery.setIndexName(indexName);
		userGroupQuery.setType(userGroupTypeName);
		userGroupQuery.setObject(usergroup);
		return usergroup;
	}

}
