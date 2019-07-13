package org.bioauth.typeauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class RootController {

	@GetMapping("/")
	public String mainPage(){
		return "index";
	}
}
