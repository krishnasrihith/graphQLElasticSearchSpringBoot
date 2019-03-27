//package com.graphql.userPoc.controller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.graphql.userPoc.queryService.UserQuery;
//
//import graphql.ExecutionResult;
//import graphql.ExecutionInput;
//import graphql.GraphQL;
//import graphql.schema.GraphQLSchema;
//import graphql.schema.idl.SchemaPrinter;
//import io.leangen.graphql.GraphQLSchemaGenerator;
//import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
//import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
//import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
//
//@RestController
//public class UserController {
//	
//	 private final GraphQL graphQL;
//
//	    @Autowired
//	    public UserController(UserQuery userQuery) {
//
//	        //Schema generated from query classes
//	        GraphQLSchema schema = new GraphQLSchemaGenerator()
//	                .withResolverBuilders(
//	                        //Resolve by annotations
//	                        new AnnotatedResolverBuilder(),
//	                        //Resolve public methods inside root package
//	                        new PublicResolverBuilder("com.graphql.userPoc"))
//	                .withOperationsFromSingleton(userQuery)
//	                .withValueMapperFactory(new JacksonValueMapperFactory())
//	                .generate();
//	        System.out.println(new SchemaPrinter().print(schema));
//	        graphQL = GraphQL.newGraphQL(schema).build();
//	    }
//
//	    @PostMapping(value="/graphql")
//	    @ResponseBody
//	    public Map<String,Object> execute(@RequestBody Map<String,String> request){
//	    	ExecutionResult result = graphQL.execute(ExecutionInput.newExecutionInput().query(request.get("query")).operationName(request.get("operationName")).build());
//	    	return result.toSpecification();
//	    }
//}
//
