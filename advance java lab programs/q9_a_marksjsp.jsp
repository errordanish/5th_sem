<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String[] marks=request.getParameter("marks").split(",");
double avg=0;
int sum=0;
for(int i=0;i<marks.length;i++)
	sum=sum+Integer.parseInt(marks[i]);
avg=(double)sum/5;
if(avg>=90)
	out.println("S");
else if(avg<90&&avg>=80 )
	out.println("A");
else
	out.println("fail");
	
%>
</body>
</html>