
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
@WebServlet("/police")
public class police extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Police","root","");
			
			String val=request.getParameter("value");
			String str=request.getParameter("str");
			if(val.equals("area"))
			{
				PreparedStatement ps=con.prepareStatement("select * from pinfo where name=?");
				ps.setString(1, str);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				}
			}
			if(val.equals("phone"))
			{
				PreparedStatement ps=con.prepareStatement("select * from pinfo where num=?");
				ps.setInt(1, Integer.parseInt(str));
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
				}
			}
			else {
				out.println("no value");
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
