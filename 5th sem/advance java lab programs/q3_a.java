import java.sql.*;
import java.util.*;
public class p3 {
public static void main(String[] args) {
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Movies","root","");
		Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet rs=st.executeQuery("select * from movie1");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getInt(5));	
		}
		System.out.println();
		int c=0;
		rs=st.executeQuery("select * from movie1");
		while(rs.next())
		{
			c=c+1;
			if(c==5)
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getInt(5));
		}
		System.out.println("Enter values:");
		PreparedStatement ps=con.prepareStatement("insert into movie1 values(?,?,?,?,?)");
		Scanner inp=new Scanner(System.in);
		int a=inp.nextInt();
		int b=inp.nextInt();
		c=inp.nextInt();
		inp.nextLine();
		String d=inp.nextLine();
		String e=inp.nextLine();
		ps.setInt(1, a);
		ps.setString(2, d);
		ps.setString(3, e);
		ps.setInt(4, b);
		ps.setInt(5, c);
		int i=ps.executeUpdate();
		ps=con.prepareStatement("select * from movie1");
		rs=ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getInt(5));	
		}
		rs=st.executeQuery("select * from movie1");
		while(rs.next())
		{
			if(rs.getInt("rating")<5)
				{
				rs.deleteRow();
				rs.updateRow();
				}
		}
		System.out.println();
		rs=st.executeQuery("select * from movie1");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getInt(5));	
		}
		rs=st.executeQuery("select * from movie1");
		while(rs.next())
		{
			if(rs.getInt("id")==10)
				{
				rs.updateString(3, "sci-fi");
				rs.updateRow();
				}
		}
		System.out.println();
		rs=st.executeQuery("select * from movie1");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getInt(5));	
		}
	}
	catch(Exception e) {
		System.out.println(e.getMessage());
	}
}
}
