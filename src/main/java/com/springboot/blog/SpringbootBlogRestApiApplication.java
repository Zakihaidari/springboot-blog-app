package com.springboot.blog;

import com.springboot.blog.entity.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);

//		System.out.println("The Application has been started.");
//		Post post = new Post(1422387592, "Spring Boot Guide", "Introduction to Spring Boot", "Spring Boot makes Java development easy.");
//		System.out.println(post.getId());
//		System.out.println(post.getContent());
//		System.out.println(post.getTitle());
	}


}
