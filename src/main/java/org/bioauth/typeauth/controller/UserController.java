package org.bioauth.typeauth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String test()
	{
		return ("Test /api/user !!!");
	}
}
