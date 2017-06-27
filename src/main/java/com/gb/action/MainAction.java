package com.gb.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gb.util.ConfigUtil;

@Controller
@RequestMapping("main")
public class MainAction {//布局
	@RequestMapping("main")
	public String main(){
		return "main/main";
	}
	
	@RequestMapping("north")
	public String north(){
		return "main/north";
	}
	
	@RequestMapping("south")
	public String south(){
		return "main/south";
	}
	
	@RequestMapping("home")
	public String home(){
		return "main/home";
	}

}
