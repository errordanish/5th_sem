<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String name=request.getParameter("name");
int age=Integer.parseInt(request.getParameter("age"));
out.println("name is "+name+" age is "+age);
if(age>62)
	out.println("ticket price is 50");
else if(age<10)
	out.println("ticket price is 30");
else
	out.println("ticket price is 80");
  %>
</body>
</html>