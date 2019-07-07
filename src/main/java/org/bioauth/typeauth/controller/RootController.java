package org.bioauth.typeauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/")
public class RootController {

	@GetMapping("/")
	public String mainPage(){
		return "index";
	}
}
