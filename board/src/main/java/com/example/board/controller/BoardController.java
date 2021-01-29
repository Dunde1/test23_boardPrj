package com.example.board.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.service.BoardService;
import com.example.board.vo.BoardVO;

@Controller
public class BoardController {
	@Autowired
	BoardService bs;
	
	@RequestMapping(value = "/writeForm.do", method = RequestMethod.GET)
	public String writeForm() {
		return "writeForm";
	}
	@RequestMapping(value = "/updateForm.do", method = RequestMethod.GET)
	public String updateForm() {
		return "updateForm";
	}
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list() {
		return "list";
	}
	@RequestMapping(value = "/content.do", method = RequestMethod.GET)
	public ModelAndView content(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject(request.getParameter("num"));
		mav.addObject(request.getParameter("pageNum"));
		mav.setViewName("content");
		return mav;
	}
	
	@RequestMapping(value = "/getAllBoard.do", method = RequestMethod.GET)
	public ModelAndView getAllBoard(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		int start = Integer.parseInt(request.getParameter("startRow"));
		int end = Integer.parseInt(request.getParameter("endRow"));
		int count = 0;
		List articleList = null;	
		try {
			count = bs.getArticleCount();
			articleList = bs.getAllBoard(start, end);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("count", count);
		mav.addObject("articleList", articleList);
		mav.setViewName("list");
		return mav;
	}
	
	@RequestMapping(value = "/getBoard.do", method = RequestMethod.GET)
	public ModelAndView getBoard(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");	
 	    String result = "1";
 	    BoardVO vo = new BoardVO(num);
		try {
			vo = bs.getBoard(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("vo", vo);
		mav.addObject("result", result);
		mav.setViewName("content");
		return mav;
	}
	
	@RequestMapping(value = "/insertBoard.do", method = RequestMethod.GET)
	public String insertBoard(HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		String writer = (String)request.getParameter("writer");
		String subject = (String)request.getParameter("subject");
		String email = (String)request.getParameter("email");
		String content = (String)request.getParameter("content");
		String passwd = (String)request.getParameter("passwd");
		Timestamp reg_date = new Timestamp(System.currentTimeMillis());
		String ip = request.getRemoteAddr();
    	int ref = Integer.parseInt(request.getParameter("ref"));
    	int re_step = Integer.parseInt(request.getParameter("re_step"));
    	int re_level = Integer.parseInt(request.getParameter("re_level"));
		BoardVO vo = new BoardVO(num, writer, subject, email, content, passwd, reg_date, ip, ref, re_step, re_level);	
		try {
			bs.insertBoard(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	@RequestMapping(value = "/updateBoard.do", method = RequestMethod.GET)
	public String updateBoard(HttpServletRequest request) {
		//
		return "list";
	}
}
