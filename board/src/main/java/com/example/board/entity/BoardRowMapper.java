package com.example.board.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.board.vo.BoardVO;

public class BoardRowMapper implements RowMapper<BoardVO>{

	@Override
	public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardVO vo = new BoardVO();
		vo.setNum(rs.getInt("num"));
		vo.setWriter(rs.getString("writer"));
		vo.setEmail(rs.getString("email"));
		vo.setSubject(rs.getString("subject"));
		vo.setPasswd(rs.getString("passwd"));
		vo.setReg_date(rs.getTimestamp("reg_date"));
		vo.setReadcount(rs.getInt("readcount"));
		vo.setRef(rs.getInt("ref"));
		vo.setRe_step(rs.getInt("re_step"));
		vo.setRe_level(rs.getInt("re_level"));
		vo.setContent(rs.getString("content"));
		vo.setIp(rs.getString("ip"));
		return vo;
	}

}
