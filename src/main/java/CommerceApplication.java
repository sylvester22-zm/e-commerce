


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.domain.User;
import com.domain.security.Role;
import com.domain.security.UserRole;
import com.service.UserService;
import com.utility.SecurityUtility;

@SpringBootApplication
public class CommerceApplication implements CommandLineRunner{
	@Autowired
	 UserService userService;		
	public static void main(String[] args) {
		SpringApplication.run(CommerceApplication.class, args);}
		
			
			@Override
			public void run(String... args) throws Exception {
				User user1 = new User();
				user1.setFirstName("sylver");
				user1.setLastName("top");
				user1.setUsername("a");
				user1.setPassword(SecurityUtility.passwordEncoder().encode("b"));
				user1.setEmail("@a");
				Set<UserRole> userRoles = new HashSet<>();
				Role role1= new Role();
				role1.setRoleId(1);
				role1.setName("Role_User");
				userRoles.add(new UserRole(user1, role1));
				
				
				userService.createUser(user1, userRoles);
				
			}
	

	
}
