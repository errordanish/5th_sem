

import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/calculator")
public class calculator extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			PrintWriter out=response.getWriter();
			response.setContentType("text/html");
			int num1=Integer.parseInt(request.getParameter("num1"));
			int num2=Integer.parseInt(request.getParameter("num2"));
			String value=request.getParameter("value");
			switch(value)
			{
			case "add":out.println(num1+num2);break;
			case "sub":out.println(num1-num2);break;
			case "mul":out.println(num1*num2);break;
			case "div":if(num2==0)out.println("division by 0");else out.println(num1/num2);break;
			case "expo":out.println(Math.exp(num1));break;
			default:out.println("no match");
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
