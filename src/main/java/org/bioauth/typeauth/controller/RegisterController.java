package org.bioauth.typeauth.controller;

import org.bioauth.typeauth.domain.User;
import org.bioauth.typeauth.service.UserServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserServiceDb userServiceMySql;

	@GetMapping
	public String showRegisterForm(Model model)
	{
		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
			return "index";
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping
	public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult)
	{
		if (userServiceMySql.findUserByUsername(user.getUsername()).isPresent())
			bindingResult.rejectValue("username", null, "There is already an account with that username");
		if (bindingResult.hasErrors())
			return "register";
		userServiceMySql.save(user);
		return "login";
	}
}
