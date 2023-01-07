import java.sql.*;
public class q1_a {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from emp");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			System.out.println();
			rs=st.executeQuery("select * from emp");
			while(rs.next())
			{
				if(rs.getString("project").equals("web dev"))
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			System.out.println();
			rs=st.executeQuery("select * from emp");
			while(rs.next())
			{
				if(rs.getString("project").equals("web dev")&&rs.getInt(5)>75000)
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			System.out.println();
			int c=0;
			rs=st.executeQuery("select * from emp");
			while(rs.next())
			{
				if(rs.getInt(5)<50000)
				c++;//System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			System.out.println(c);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
