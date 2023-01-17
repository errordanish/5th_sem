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
try{
	Class.forName("com.mysql.jdbc.Driver");
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","");
	String usn=request.getParameter("usn");
	PreparedStatement ps=con.prepareStatement("select * from stud where usn=?");
	ps.setString(1,usn);
	ResultSet rs=ps.executeQuery();
	int c=0;
	while(rs.next())
	{
		out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
		c=1;
	}
	if(c==0)
		out.println("Invalid usn");
	
}
catch(Exception e)
{
	out.println(e.getMessage());
}
%>
</body>
</html>