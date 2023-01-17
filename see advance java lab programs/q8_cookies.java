

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
@WebServlet("/cookies")
public class cookies extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie ck1=new Cookie("n1","v1");
		Cookie ck2=new Cookie("n2","v2");
		Cookie ck3=new Cookie("n3","v3");
		Cookie ck4=new Cookie("n4","v4");
		ck3.setMaxAge(60);
		ck4.setMaxAge(60);
		response.addCookie(ck1);
		response.addCookie(ck2);
		response.addCookie(ck3);
		response.addCookie(ck4);
		PrintWriter pw=response.getWriter();
		response.setContentType("text/html");
		Cookie ck[]=request.getCookies();
		/*for(int i=0;i<ck.length;i++)
		{
			pw.println("<br>"+ck[i].getName()+" "+ck[i].getValue());
		}*/
		pw.println("<br>"+ck[0].getName()+" "+ck[0].getValue());
		pw.println("<br>"+ck[1].getName()+" "+ck[1].getValue());
		pw.println("<br>"+ck[2].getName()+" "+ck[2].getValue());
		pw.println("<br>"+ck[3].getName()+" "+ck[3].getValue());
		pw.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
