package com.dms.springcloud.controller;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dms.springcloud.entity.User;
import com.dms.springcloud.service.UserServiceInterface;
import com.dms.springcloud.dto.Entry;
import com.dms.springcloud.dto.UserLoginWithEntriesDto;

@RestController
@RequestMapping("/users")
public class UserController {
	
		
		@Autowired
		private UserServiceInterface userServiceInterface;
		
		@Autowired
		private RestTemplate template;
		
		@GetMapping("/")
		public List<User> findAllEntries()
		{
			 List<User> userslist=userServiceInterface.findAll();
			 return userslist;
			
		}
		
	    @PostMapping("/")
	    public User saveUser(@RequestBody User user)
	    {
	    	User savedUser=userServiceInterface.saveUser(user);
	    	return savedUser;
	    }
	    @PostMapping("/register")
	    public User registerUser(@RequestBody User user)
	    {
	    	User savedUser=userServiceInterface.saveUser(user);
	    	return savedUser;
	    }
	    
	   @PutMapping("/")
	   public User updatedUser(@RequestBody User user)
	   {
	   	User updatedUser=userServiceInterface.updateUser(user);
	   	return updatedUser;
	   }
	   @PutMapping("/{id}")
	   public User updatedUserbyId(@PathVariable("id") int id,@RequestBody User user)
	   {
		   User user1=userServiceInterface.findById(id);
		   String username=user.getUsername();
		   String password=user.getPassword();
		   user1.setUsername(username);
		   user1.setPassword(password);
	   	   User updatedUser=userServiceInterface.updateUser(user1);
	   	   return updatedUser;
	   }
	   @PatchMapping("/{id}")
	   public User updatedUserPatchbyId(@PathVariable("id") int id,@RequestBody User user)
	   {
		   User user1=userServiceInterface.findById(id);
		   String username=user.getUsername();
		   String password=user.getPassword();
		   if(username!=null && username.length()>0)
			   user1.setUsername(username);
		   if(password!=null && password.length()>0)
			   user1.setPassword(password);
		   
	   	   User updatedUser=userServiceInterface.updateUser(user1);
	   	   return updatedUser;
	   }
	   
	   @GetMapping("/{id}")
	   public User getUser(@PathVariable("id") int id)
	   {
		   User user=userServiceInterface.findById(id);
		   return user;
	   }
	   @DeleteMapping(value="/{id}")
	   public void deleteUser(@PathVariable("id") int id)
	   {
		   User user=userServiceInterface.findById(id);
		   userServiceInterface.deleteUser(user);
		
	   }
	   
	   @GetMapping("/getbyusername/{username}")
	   public User getUsername(@PathVariable("username") String username)
	   {
		   User user=userServiceInterface.findByUsername(username);
		   return user;
	   }
	   
	   ///////////////////////////////////////////
	   
	   @PostMapping(value="/authenticate")
	   public ResponseEntity<Object> authenticateUser(@RequestBody User user) {
	       Optional<User> userOptional = Optional.ofNullable(userServiceInterface.findByUsername(user.getUsername()));

	       // Check if the user exists
	       if (userOptional.isEmpty()) {
	           return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                   .body("User not found.");
	       }

	       User databaseUser1 = userOptional.get();

	       // Verify password
	       if (!user.getPassword().equals(databaseUser1.getPassword())) {
	           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                   .body("Invalid password.");
	       }

	       // Fetch entries for the user
	       Long userId = databaseUser1.getId();
	       List<Entry> entries = null;
	       try {
	           ResponseEntity<Entry[]> getAllEntries = template.getForEntity(
	                   "http://localhost:6161/entriesinsertion/entries/getbyuserid/" + userId, Entry[].class);

	           if (getAllEntries.getStatusCode() == HttpStatus.OK) {
	               Entry[] entriesArray = getAllEntries.getBody();
	               entries = Arrays.asList(entriesArray);
	           } else {
	               return ResponseEntity.status(getAllEntries.getStatusCode())
	                       .body("Failed to retrieve entries for user.");
	           }
	       } catch (Exception e) {
	           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                   .body("An error occurred while fetching entries: " + e.getMessage());
	       }

	       // Create and return the UserLoginWithEntriesDto
	       UserLoginWithEntriesDto userLoginWithEntriesDto = new UserLoginWithEntriesDto();
	       userLoginWithEntriesDto.setEntrieslist(entries);
	       userLoginWithEntriesDto.setUser(databaseUser1);

	       return ResponseEntity.status(HttpStatus.OK)
	               .body(userLoginWithEntriesDto);
	   }
	   
	   
	   

}

	    
	
	   
