<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import = "vo.BoardVO" %>
<%@ page import = "entity.BoardEntity" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ include file="color.jspf"%>
<html>
<head>
<title>�Խ���</title>
<link href="<%=request.getContextPath()%>/style.css" rel="stylesheet" type="text/css">
</head>

<%
   int num = Integer.parseInt(request.getParameter("num"));
   String pageNum = request.getParameter("pageNum");
   String result = (String)request.getAttribute("result");
   SimpleDateFormat sdf = 
        new SimpleDateFormat("yyyy-MM-dd HH:mm");
  
   	try{
   	 if(result != "1"){
%>
<jsp:forward page="/BoardController?cmd=getBoard">
	<jsp:param value="<%=num %>" name="num"/>
	<jsp:param value="<%=pageNum %>" name="pageNum"/>
</jsp:forward>	   
<%

    }
	BoardVO vo = (BoardVO)request.getAttribute("vo");   
	int ref=vo.getRef();
	int re_step=vo.getRe_step();
	int re_level=vo.getRe_level();
%>
<body bgcolor="<%=bodyback_c%>">  
<center><b>�۳��� ����</b>
<br>
<form>
<table width="500" border="1" cellspacing="0" cellpadding="0"  bgcolor="<%=bodyback_c%>" align="center">  
  <tr height="30">
    <td align="center" width="125" bgcolor="<%=value_c%>">�۹�ȣ</td>
    <td align="center" width="125" align="center">
	     <%=vo.getNum()%></td>
    <td align="center" width="125" bgcolor="<%=value_c%>">��ȸ��</td>
    <td align="center" width="125" align="center">
	     <%=vo.getReadcount()%></td>
  </tr>
  <tr height="30">
    <td align="center" width="125" bgcolor="<%=value_c%>">�ۼ���</td>
    <td align="center" width="125" align="center">
	     <%=vo.getWriter()%></td>
    <td align="center" width="125" bgcolor="<%=value_c%>" >�ۼ���</td>
    <td align="center" width="125" align="center">
	     <%= sdf.format(vo.getReg_date())%></td>
  </tr>
  <tr height="30">
    <td align="center" width="125" bgcolor="<%=value_c%>">������</td>
    <td align="left" width="375" colspan="3">
	     <%=vo.getSubject()%></td>
  </tr>
  <tr>
    <td align="center" width="125" bgcolor="<%=value_c%>">�۳���</td>
    <td align="left" width="375" colspan="3"><pre><%=vo.getContent()%></pre></td>
  </tr>
  <tr height="30">      
    <td colspan="4" bgcolor="<%=value_c%>" align="right" > 
	  <input type="button" value="�ۼ���" 
       onclick="document.location.href='updateForm.jsp?num=<%=vo.getNum()%>&pageNum=<%=pageNum%>'">
	   &nbsp;&nbsp;&nbsp;&nbsp;
	  <input type="button" value="�ۻ���" 
       onclick="document.location.href='deleteForm.jsp?num=<%=vo.getNum()%>&pageNum=<%=pageNum%>'">
	   &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" value="��۾���" 
       onclick="document.location.href='writeForm.jsp?num=<%=num%>&ref=<%=ref%>&re_step=<%=re_step%>&re_level=<%=re_level%>'">
	   &nbsp;&nbsp;&nbsp;&nbsp;
       <input type="button" value="�۸��" 
       onclick="document.location.href='list.jsp?pageNum=<%=pageNum%>'">
    </td>
  </tr>
</table>    
<%
 }catch(Exception e){}    
 %>
</form>      
</body>
</html>