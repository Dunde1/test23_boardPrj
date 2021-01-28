<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "com.example.board.vo.BoardVO" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ include file="color.jspf"%>

<%!
    int pageSize = 10;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>

<%	
    String pageNum = request.getParameter("pageNum");
    if (pageNum == null) {
        pageNum = "1";
    }    
    int currentPage = Integer.parseInt(pageNum);
    int startRow = (currentPage - 1) * pageSize + 1;
    int endRow = currentPage * pageSize;
    int count = 0;
    int number=0;
 
    List articleList = null;   	
      	

    articleList = (List)request.getAttribute("articleList");
  	if(articleList == null){
%>
<jsp:forward page="/BoardController?cmd=getAllBoard">
	<jsp:param value="<%=count %>" name="count"/>
	<jsp:param value="<%=startRow%>" name="startRow" />
	<jsp:param value="<%=endRow%>" name="endRow" />
</jsp:forward>
<%
   	}
    count = (int)request.getAttribute("count");
    number = count-(currentPage - 1)*pageSize;
%>
<html>
<head>
<title>게시판</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="<%=bodyback_c%>">
<center><b>글목록(전체 글:<%=count%>)</b>
<table width="700">
<tr>
    <td align="right" bgcolor="<%=value_c%>">
    <a href="writeForm.jsp">글쓰기</a>
    </td>
</table>

<%
    if (count == 0) {
%>
<table width="700" border="1" cellpadding="0" cellspacing="0">
<tr>
    <td align="center">
    게시판에 저장된 글이 없습니다.
    </td>
</table>

<%  } else {    %>
<table border="1" width="700" cellpadding="0" cellspacing="0" align="center"> 
    <tr height="30" bgcolor="<%=value_c%>"> 
      <td align="center"  width="50"  >번 호</td> 
      <td align="center"  width="250" >제   목</td> 
      <td align="center"  width="100" >작성자</td>
      <td align="center"  width="150" >작성일</td> 
      <td align="center"  width="50" >조 회</td> 
      <td align="center"  width="100" >IP</td>    
    </tr>
<%  
        for (int i = 0 ; i < articleList.size() ; i++) {
          BoardVO vo = (BoardVO)articleList.get(i);
%>
   <tr height="30">
    <td align="center"  width="50" > <%=number--%></td>
    <td  width="250" >
	<%
	      int wid=0; 
	      if(vo.getRe_level()>0){
	        wid=5*(vo.getRe_level());
	%>
	  <img src="/boardMVC/board/images/level.gif" width="<%=wid%>" height="16">
	  <img src="/boardMVC/board/images/re.gif">
	<%}else{%>
	  <img src="/boardMVC/board/images/level.gif" width="<%=wid%>" height="16">
	<%}%>
           
      <a href="<%=request.getContextPath()%>/board/content.jsp?num=<%=vo.getNum()%>&pageNum=<%=currentPage%>">
           <%=vo.getSubject()%></a> 
          <% if(vo.getReadcount()>=20){%>
         <img src="/boardMVC/board/images/hot.gif" border="0"  height="16"><%}%> </td>
    <td align="center"  width="100"> 
       <a href="mailto:<%=vo.getEmail()%>"><%=vo.getWriter()%></a></td>
    <td align="center"  width="150"><%= sdf.format(vo.getReg_date())%></td>
    <td align="center"  width="50"><%=vo.getReadcount()%></td>
    <td align="center" width="100" ><%=vo.getIp()%></td>
  </tr>
     <%}%>
</table>
<%}%>

<%
    if (count > 0) {
        int pageCount = count / pageSize + ( count % pageSize == 0 ? 0 : 1);
		 
        int startPage = (int)(currentPage/10)*10+1;
		int pageBlock=10;
        int endPage = startPage + pageBlock-1;
        if (endPage > pageCount) endPage = pageCount;
        
        if (startPage > 10) {    %>
        <a href="/boardMVC/board/list.jsp?pageNum=<%= startPage - 10 %>">[이전]</a>
<%      }
        for (int i = startPage ; i <= endPage ; i++) {  %>
        <a href="/boardMVC/board/list.jsp?pageNum=<%= i %>">[<%= i %>]</a>
<%
        }
        if (endPage < pageCount) {  %>
        <a href="/boardMVC/board/list.jsp?pageNum=<%= startPage + 10 %>">[다음]</a>
<%
        }
    }
%>
</center>
</body>
</html>