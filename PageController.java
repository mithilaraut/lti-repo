package com.lti.vehicle.controller;

	import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.servlet.ModelAndView;

import com.lti.vehicle.model.UserDetails;
import com.lti.vehicle.model.VehicleDetails;
import com.lti.vehicle.service.UserService;

	@Controller
	public class PageController {
		
		private UserService userService;

		@Autowired
		public void setUserService(UserService ps) {
			this.userService = ps;
		}
		@Autowired
		/* private CategoryDAO categoryDAO; */
		
		@RequestMapping(value={"/"})
		public ModelAndView goHome() {
			ModelAndView mv=new ModelAndView("index.jsp");
		return mv;
		}
		
	 	
//		@RequestMapping(value="/login")
//		public ModelAndView login() {
//			ModelAndView mv=new ModelAndView("login");
//			return mv;
//		}
//		
//		@RequestMapping(value="/loginverification",method=RequestMethod.POST)
//		public String LoginValidation(Model model,HttpServletRequest req)
//		{
//			
//		}
		@RequestMapping(value="/welcome")
		public ModelAndView welcome() {
			ModelAndView mv=new ModelAndView("welcome");
			return mv;
		}
		
		/*
		 * @RequestMapping(value="/loginverification",method=RequestMethod.POST) public
		 * String LoginVerify(Model model,HttpServletRequest req) { String
		 * email=req.getParameter("email"); String
		 * password=req.getParameter("password");
		 * 
		 * System.out.println(email); System.out.println(password); String
		 * pass=userService.getByEmail(email); if(pass.equals(password)) {
		 * System.out.println("hello"); return "redirect:/"; }
		 * 
		 * return "login"; }
		 */
		@RequestMapping(value="/login")
		public String login(Model model) {

			model.addAttribute("userDetails", new UserDetails());
			
			return "login";
		}
		
		
		@RequestMapping(value="/loginverification",method=RequestMethod.POST)
		public String LoginValidation(@ModelAttribute ("userDetails") @Valid UserDetails u,
				BindingResult result,HttpServletRequest req ,HttpSession session,Model model)
		{
			String email=req.getParameter("email");
			String password=req.getParameter("password");
			
			
			if(email.equals("admin@gmail.com")&& password.equals("admin"))
		 
			{
				return "admin";
			
			}
			else if(userService.verifyUser(email, password))
			{
				session.setAttribute("email", u.getEmail());
				return "redirect:/";
			}
			else
			return "login";
		}

@RequestMapping(value="/register")
		public String register(Model m) {
			m.addAttribute("user", new UserDetails());
			//ModelAndView mv=new ModelAndView("register");
			return "register";
		}
		
		@RequestMapping(value="/register/add" ,method = RequestMethod.POST)
			public String addUser(@ModelAttribute("user") UserDetails u,BindingResult result, Model model) {
		try {
			if (!result.hasErrors()) {			
				if (u.getId() == null || u.getId() == 0) {
						// new user, add it		
						//User user = handleFileUpload(result, fileUpload, u);					
						
						this.userService.addUser(u);
				}  
			 
				return "redirect:/login";
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	
		return "register";
		}
		
		@RequestMapping(value="/logout")
		public String logout(HttpSession session)
		{
			session.invalidate();
			return "redirect:/";
		}

		
		
		
//		@RequestMapping(value="/seller_login")
//		public ModelAndView seller() {
//			ModelAndView mv=new ModelAndView("seller_login");
//			mv.addObject("title","Sell");
//			mv.addObject("userClickSell",true);
//			return mv;
//		}
	//	
//		@RequestMapping(value="/registerPage",method=RequestMethod.POST)
//		public String validateregistrationPage(@Valid @ModelAttribute("authuser") 
//		User authuser ,BindingResult bindingResult,Model model,HttpServletRequest req)
//		{
//			
//			String view="";
//		if(bindingResult.hasErrors())
//		{
//			view="success";
//			return view;
//		}
//		else
//		{
//			String username=req.getParameter("userEmail");
//			String password=req.getParameter("password");
	//	
	//	
//			userService.addUser(authuser);
//			
//			view="success";
//			return view;
//			
//		}
//		}
		
	}
