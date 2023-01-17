
import java.io.IOException;
import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Employeeinsert")
public class Employeeinsert extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee1","root","");
			PreparedStatement ps=con.prepareStatement("insert into employee values(?,?,?,?)");
			String id=request.getParameter("id");
			String name=request.getParameter("name");
			String address=request.getParameter("address");
			String dob=request.getParameter("dob");
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, address);
			ps.setString(4, dob);
			int i=ps.executeUpdate();
			ps=con.prepareStatement("select * from employee");
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				
				out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
				
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
