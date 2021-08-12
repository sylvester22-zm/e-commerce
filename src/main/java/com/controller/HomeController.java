package com.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.domain.Product;
import com.domain.User;
import com.domain.security.PasswordResetToken;
import com.domain.security.Role;
import com.domain.security.UserRole;
import com.service.ProductService;
import com.service.UserService;
import com.service.impl.UserSecurityService;
import com.utility.MailConstructor;
import com.utility.SecurityUtility;

@Controller
public class HomeController {
	/*
	 * @Autowired private OrderService orderService;
	 */
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSecurityService userSecurityService;
	@Autowired
	private ProductService productService;

	@RequestMapping("/")
	public String index(Model model) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "index";
	}

	@RequestMapping("/login-register")
	public String loginReg() {
		return "Account";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "Account";
	}

	@RequestMapping("/register")
	public String Register() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String RegisterPost(@ModelAttribute("email") String email, @ModelAttribute("username") String username,
			@ModelAttribute("password") String password, HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("email", email);
		model.addAttribute("username", username);
		String encpassword = SecurityUtility.passwordEncoder().encode(password);

		if (userService.findByEmail(email) != null) {
			model.addAttribute("emailExists", true);
			return "register";
		}
		if (userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			return "register";
		}

		User user = new User();
		Role role = new Role();
		role.setRoleId(1);
		role.setName("User");
		Set<UserRole> userRole = new HashSet<>();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(encpassword);
		userRole.add(new UserRole(user, role));
		userService.createUser(user, userRole);
		model.addAttribute("RegistrationSuccessfull", "true");
		return "register";
	}

	@RequestMapping("/myAccount")
	public String Account(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		return "myAccount";

	}
	@RequestMapping("/passwordReset")
	public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);

		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}

		User user = passToken.getUser();
		String username = user.getUsername();

		UserDetails userDetails = userSecurityService.loadUserByUsername(username);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("user", user);

		model.addAttribute("classActiveEdit", true);
		
		return "myAccount";
	}

	@RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
	public String udateAccount(@ModelAttribute("user") User user,
			@ModelAttribute("newpassword") String newPassword,
			Model model) throws Exception {

		User currentUser = userService.findByid(user.getId());
		if (currentUser == null) {
			throw new Exception("user unavailable");
		}

		if (userService.findByUsername(user.getUsername()) != null) {
			if (userService.findByUsername(user.getUsername()).getId() != currentUser.getId()) {
				model.addAttribute("usernameExists", true);
				return "myAccount";
			}

		}
		if (userService.findByEmail(user.getEmail()) != null) {
			if (userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("emailExists", true);
				return "myAccount";
			}
		}
		 
		if(newPassword!=null&&!newPassword.isEmpty()&&!newPassword.equals("")) {
			BCryptPasswordEncoder encoder=SecurityUtility.passwordEncoder();
			String currentPassword=currentUser.getPassword(); 
			if(encoder.matches(user.getPassword(), currentPassword)) {
				currentUser.setPassword(encoder.encode(newPassword));
			}else {
				model.addAttribute("incorrectPassword",true);
				return "myAccount";
			}
				
			
		}
		if (currentUser != null) {

			currentUser.setEmail(user.getEmail());
			currentUser.setFirstName(user.getFirstName());
			currentUser.setLastName(user.getLastName());
			currentUser.setPhone(user.getPhone());
		}

		UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("user", user);
		userService.save(currentUser);
		model.addAttribute("updateSuccess", true);
		return "myAccount";
	}

	@RequestMapping("/forgotPassword")
	public String resetPassword() {
		return "forgotPassword";
	}

	@RequestMapping(value="/forgotPassword",method=RequestMethod.POST)
	public String resetPasswordPost(@ModelAttribute("email") String email,
			Model model, HttpServletRequest request) {

		User user = userService.findByEmail(email);
		if (user == null) {
			model.addAttribute("emailDoesNotExist", true);
			return "forgotPassword";
		}

		String password = SecurityUtility.randomPassword();
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		userService.save(user);
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		SimpleMailMessage SendPassREsetEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(),
				token, user, password);
		mailSender.send(SendPassREsetEmail);
		model.addAttribute("emailSent", true);
		return "forgotPassword";
	}

	@RequestMapping("/Detail")
	public String Detail(@PathParam("id") Long id, Model model) {
		Product product = productService.findOne(id);
		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		model.addAttribute("product", product);
		model.addAttribute("quantityList", qtyList);
		model.addAttribute("quantity", 1);
		return "productDetail";
	}

	@RequestMapping("/searchItem")
	public String Search(@ModelAttribute("product") String product, Model model) {

		List<Product> products = productService.findByProduct(product);
		model.addAttribute("products", products);
		return "index";
	}

	@RequestMapping("/adminHome")
	public String adminLogin() {
		return "Home";

	}
}
