package com.dms.springcloud.restclient;
import org.springframework.cloud.openfeign.FeignClient;




import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.dms.springcloud.dto.User;

@FeignClient("API-GATEWAY-SERVICE")
public interface UserRestClient {
	
	@GetMapping("/userregistration/users/{id}")
	
	User getUserDetails(@PathVariable("id") long id);
	
	
	

}
