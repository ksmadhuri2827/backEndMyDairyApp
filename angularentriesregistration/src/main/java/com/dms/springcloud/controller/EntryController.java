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

import com.dms.springcloud.dto.EntriesAndUser;
import com.dms.springcloud.dto.User;
import com.dms.springcloud.entity.Entry;
import com.dms.springcloud.restclient.UserRestClient;
import com.dms.springcloud.service.EntryServiceInterface;

@RestController
@RequestMapping("/entries")
public class EntryController {
	
	@Autowired
	private EntryServiceInterface entryServiceInterface;
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private UserRestClient userRestClient;
	
	
	
	@GetMapping("/")
	public List<Entry> findAllEntries()
	{
		 List<Entry> entrieslist=entryServiceInterface.findAll();
		 return entrieslist;
		
	}
	
    @PostMapping("/")
    public Entry saveEntry(@RequestBody Entry entry)
    {
    	
    	User user=userRestClient.getUserDetails(entry.getUserid());
    	System.out.println(user);
    	
    	Entry savedEntry=entryServiceInterface.saveEntry(entry);
    	return savedEntry;
    }
    
   @PutMapping("/")
   public Entry updatedEntry(@RequestBody Entry entry)
   {
   	Entry updatedEntry=entryServiceInterface.updateEntry(entry);
   	return updatedEntry;
   }
   @PutMapping("/{id}")
   public Entry updatedEntrybyId(@PathVariable("id") int id,@RequestBody Entry entry)
   {
	   Entry entry1=entryServiceInterface.findById(id);
	   entry1.setDescription(entry.getDescription());
	   entry1.setEntrydate(entry.getEntrydate());
	   entry1.setUserid(entry.getUserid());
   	   Entry updatedEntry=entryServiceInterface.updateEntry(entry1);
   	   return updatedEntry;
   }
   @PatchMapping("/{id}")
   public Entry updatedEntryPatchbyId(@PathVariable("id") int id,@RequestBody Entry entry)
   {
	   Entry entry1=entryServiceInterface.findById(id);
	   String description=entry.getDescription();
	   Date entrydate=entry.getEntrydate();
	   Long userid=entry.getUserid();
	   if(description!=null && description.length()>0)
		   entry1.setDescription(description);
	   if(entrydate!=null)
		   entry1.setEntrydate(entrydate);
	   if(userid>0)
		   entry1.setUserid(id);
	   
   	   Entry updatedEntry=entryServiceInterface.updateEntry(entry1);
   	   return updatedEntry;
   }
   @GetMapping("/{id}")
   public Entry getEntry(@PathVariable("id") int id)
   {
	   Entry entry=entryServiceInterface.findById(id);
	   return entry;
   }
   @DeleteMapping(value="/{id}")
   public void deleteEntry(@PathVariable("id") int id)
   {
	   Entry entry=entryServiceInterface.findById(id);
	   entryServiceInterface.deleteEntry(entry);
	
   }
   
   @GetMapping("/getbyuserid/{userid}")
   public List<Entry> getUsername(@PathVariable("userid") Long userid)
   {
	   List<Entry> entries=null;
		try {
			entries=entryServiceInterface.findByUserid(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
	   return entries;
   }
   
   
   ///////////////////////////////
   
   
   @PostMapping(value="/saveentry")
   public ResponseEntity<Object> saveentry(@RequestBody Entry entry)
   {
	   Long userId=entry.getUserid();
	   System.out.println(userId);
	  User user1=template.getForObject("http://localhost:7061/userregistration/users/"+userId, User.class);
	   Optional<User> userOptional = Optional.ofNullable(user1);

      // Check if the user exists
      if (userOptional.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND)
                  .body("User not found.");
      }

      User databaseUser1 = userOptional.get();
      
   	
   	
   	
   	
   	Entry saveEntry=entryServiceInterface.saveEntry(entry);
   	
   	List<Entry> entries = null;
	       try {
	    	  entries= entryServiceInterface.findByUserid(userId);

	    	  
	       } catch (Exception e) {
	           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                   .body("An error occurred while fetching entries: " + e.getMessage());
	       }

	       // Create and return the UserLoginWithEntriesDto
	       EntriesAndUser entriesAndUser = new EntriesAndUser();
	       entriesAndUser.setEntrieslist(entries);
	       entriesAndUser.setUser(databaseUser1);

	       return ResponseEntity.status(HttpStatus.OK)
	               .body(entriesAndUser);
		
   	
   }
   
   
   
   
   
}
