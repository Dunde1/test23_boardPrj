package com.example.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.board.service.BoardService;
import com.example.board.vo.BoardVO;

@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	BoardService bs;
	
	@RequestMapping(value = "/getAllBoard.do", method = RequestMethod.GET)
	public String getAllBoard(HttpServletRequest request,BoardVO vo) {
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
		request.setAttribute("count", count);
		request.setAttribute("articleList", articleList);
		return "list";
	}
	
	@RequestMapping(value = "/writeForm.do", method = RequestMethod.GET)
	public String writeForm(HttpServletRequest request,BoardVO vo) {
		return "writeForm";
	}
}
