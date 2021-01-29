package com.example.board.entity;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.board.vo.BoardVO;

@Repository
public class BoardEntity {

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs ;
	@Autowired
	private DataSource ds;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String GET_ARTICLE_COUNT = "select count(*) from board";
	private final String GET_BOARD_UDT = "update board set readcount=readcount+1 where num = ?";
	private final String GET_BOARD_GET = "select * from board where num = ?";
	private final String GET_ALL_BOARD = "select * from (select rownum numrow,"
			+ " A.* from(select * from board)A) where numrow >= ? and numrow <= ? order by ref desc, re_step asc";
	private final String INSERT_BOARD_CNT = "select max(num) from board";
	private final String INSERT_BOARD_UDT = "update board set re_step=re_step+1 where ref= ? and re_step> ?";
	private final String INSERT_BOARD_INS = "insert into board(num,writer,email,subject,passwd,reg_date,readcount,ref,re_step,re_level,content,ip)"
			+ " values(seq_log.nextval,?,?,?,?,?,?,?,?,?,?,?)";
	
	private String result;

	public void insertBoard(BoardVO vo){
		int num = vo.getNum();
		int ref = vo.getRef();
		int re_step = vo.getRe_step();
		int re_level = vo.getRe_level();
		int number = 0;
		
		number = jdbcTemplate.queryForInt(INSERT_BOARD_CNT);
		number++;

		if(num != 0){
			jdbcTemplate.update(INSERT_BOARD_UDT, ref, re_step);
			re_step=re_step+1;
			re_level=re_level+1;
		}else{
			ref=number;
			re_step=0;
			re_level=0;
		}
		jdbcTemplate.update(INSERT_BOARD_INS, vo.getWriter(), vo.getEmail(), vo.getSubject(), vo.getPasswd(),
							vo.getReg_date(), vo.getReadcount(), ref, re_step, re_level, vo.getContent(), vo.getIp());
	}
	
	public int getArticleCount(){
        return jdbcTemplate.queryForInt(GET_ARTICLE_COUNT);
    }

	public List getAllBoard(int start, int end)  throws Exception {
		Object[] args = {start-1, end};
		return jdbcTemplate.query(GET_ALL_BOARD, args, new BoardRowMapper());
	}

    public BoardVO getBoard(BoardVO vo) throws Exception {
        Object[] args = {vo.getNum()};
        jdbcTemplate.update(GET_BOARD_UDT, vo.getNum());
        return jdbcTemplate.queryForObject(GET_BOARD_GET, args, new BoardRowMapper());
    }

    public BoardVO updateGetBoard(BoardVO vo) throws Exception {
    	int num = vo.getNum();      
        try {
            con = ds.getConnection();

            pstmt = con.prepareStatement(
            	"select * from board where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	vo = new BoardVO();
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
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
		return vo;
    }

    public int updateBoard(BoardVO vo) throws Exception {
        ResultSet rs= null;
        String dbpasswd="";
        String sql="";
		int x=-1;
        try {
            con = ds.getConnection();

			pstmt = con.prepareStatement(
            	"select passwd from board where num = ?");
            pstmt.setInt(1, vo.getNum());
            rs = pstmt.executeQuery();

			if(rs.next()){
			  dbpasswd= rs.getString("passwd"); 
			  if(dbpasswd.equals(vo.getPasswd())){
                sql="update board set writer=?,email=?,subject=?,passwd=?";
			    sql+=",content=? where num=?";
                pstmt = con.prepareStatement(sql);

                pstmt.setString(1, vo.getWriter());
                pstmt.setString(2, vo.getEmail());
                pstmt.setString(3, vo.getSubject());
                pstmt.setString(4, vo.getPasswd());
                pstmt.setString(5, vo.getContent());
			    pstmt.setInt(6, vo.getNum());
                pstmt.executeUpdate();
				x= 1;
			  }else{
				x= 0;
			  }
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
		return x;
    }

    public void deleteBoard(BoardVO vo) {
        String dbpasswd="";
        vo.setResult(-1);
        try {
			con = ds.getConnection();

            pstmt = con.prepareStatement(
            	"select passwd from board where num = ?");
            pstmt.setInt(1, vo.getNum());
            rs = pstmt.executeQuery();

			if(rs.next()){
				dbpasswd= rs.getString("passwd"); 
				if(dbpasswd.equals(vo.getPasswd())){
					pstmt = con.prepareStatement("delete from board where num=?");
                    pstmt.setInt(1, vo.getNum());
                    pstmt.executeUpdate();
                    vo.setResult(1);  //�ۻ��� ����
				}else
					vo.setResult(0); //��й�ȣ Ʋ��
			}
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (con != null) try { con.close(); } catch(SQLException ex) {}
        }
	
    }

	 
}