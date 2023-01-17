import java.sql.*;
public class a{
	public static void main(String[] args) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			con.setAutoCommit(false);
			System.out.println("before rollback data is ");
			Savepoint sp=con.setSavepoint();
			PreparedStatement ps=con.prepareStatement("insert into bankacc values(?,?,?,?)");
			ps.setInt(1, 6);
			ps.setString(2, "efg");
			ps.setString(3, "savings");
			ps.setInt(4, 60000);
			int i=ps.executeUpdate();
			ps=con.prepareStatement("select * from bankacc");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
			}
			System.out.println("rolling back to savepoint");
			con.rollback(sp);
			ps=con.prepareStatement("select * from bankacc");
			rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
			}
			System.out.println("trying to rolling back after commit not possible");
			ps=con.prepareStatement("insert into bankacc values(?,?,?,?)");
			ps.setInt(1, 7);
			ps.setString(2, "efghi");
			ps.setString(3, "savings");
			ps.setInt(4, 70000);
			i=ps.executeUpdate();
			con.commit();
			con.rollback();
			ps=con.prepareStatement("select * from bankacc");
			rs=ps.executeQuery();
			while(rs.next())
				{
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4));
				}
			con.close();
			
		}
		catch(Exception e)
		{
			try {
				con.rollback();
			}
			catch(SQLException e1)
			{
			    e1.printStackTrace();
			}
			
		}
	}
}
