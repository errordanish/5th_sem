

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
@WebServlet("/votePage")
public class votePage extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		LocalDate date=LocalDate.now();
		String str=date.toString();
		String[] strs=str.split("-");
		String  name=request.getParameter("name");
		String email=request.getParameter("mail");
		char c=name.charAt(0);
		String[] d=request.getParameter("date").split("-");
		boolean flag=true;
		for(int i=0;i<name.length();i++)
		{
			c=name.charAt(i);
			if(c=='1'||c=='@')
			{
				flag=false;
				break;
			}
		}
		int y=Math.abs(Integer.parseInt(strs[0])-Integer.parseInt(d[0]));
		int m=Math.abs(Integer.parseInt(strs[1])-Integer.parseInt(d[1]));
		int day=Math.abs(Integer.parseInt(strs[2])-Integer.parseInt(d[2]));
		double res=y+(double)m/12+(double)day/365;
		if(!flag)
			out.println("invalid name");
		else {
			if(res>18)
				out.println("eleigible");
			else
				out.println("not");
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
