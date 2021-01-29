package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.entity.BoardEntity;
import com.example.board.vo.BoardVO;

@Service
public class BoardService {
	@Autowired
	private BoardEntity be;
	private List articleList;

	public void insertBoard(BoardVO vo) {
		be.insertBoard(vo);
	}

	public int getArticleCount() {
		return be.getArticleCount();
	}

	public List getAllBoard(int start, int end) throws Exception {
		return be.getAllBoard(start, end);
	}

	public BoardVO getBoard(BoardVO vo) throws Exception {
		return be.getBoard(vo);
	}

	public BoardVO updateGetBoard(BoardVO vo) throws Exception {
		return be.updateGetBoard(vo);
	}

	public int updateBoard(BoardVO vo) throws Exception {
		return be.updateBoard(vo);
	}

	public void deleteBoard(BoardVO vo) {
		be.deleteBoard(vo);
	}

}
