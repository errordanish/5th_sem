

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
@WebServlet("/cgpa")
public class cgpa extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String name=request.getParameter("name");
		String usn=request.getParameter("usn");
		String[] m=request.getParameter("marks").split(",");
		double avg=0;
		int sum=0;
		for(int i=0;i<m.length;i++)
			sum=sum+Integer.parseInt(m[i]);
		avg=(double)sum/m.length;
		out.println("name is "+name+" usn is "+usn+" cgpa is "+avg);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
