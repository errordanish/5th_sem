<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
try
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","");
	
int id=Integer.parseInt(request.getParameter("id"));
/*String fname=request.getParameter("fname");
String lname=request.getParameter("lname");
String project=request.getParameter("project");
int salary=Integer.parseInt(request.getParameter("salary"));*/
PreparedStatement ps=con.prepareStatement("select * from emp where id=?");
ps.setInt(1,id);
ResultSet rs=ps.executeQuery();
while(rs.next())
{
	out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5));
}
}
catch(Exception e)
{
	out.println(e.getMessage());
}
%>
</body>
</html>