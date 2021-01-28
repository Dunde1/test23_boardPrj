package com.example.board.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BoardController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {	//웰컴 페이지 팝업
		
		return "list";
	}
	
	@RequestMapping(value = "/home.do", method = RequestMethod.POST)
	public String home2(Model model) {
		
		return "home2";
	}
	
}
