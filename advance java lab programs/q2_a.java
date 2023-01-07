import java.sql.*;
import java.util.*;
public class p2{
	public static void main(String[] args) {
		try {
			Scanner inp=new Scanner(System.in);
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Department","root","");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from dept");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			PreparedStatement ps=con.prepareStatement("select * from dept where year=2000");
			rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			System.out.println("Enter year to search:");
			int y=inp.nextInt();
			ps=con.prepareStatement("select * from dept where year=?");
			ps.setInt(1, y);
			rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			System.out.println("Enter id and name to search:");
			int id=inp.nextInt();
			inp.nextLine();
			String name=inp.nextLine();
			ps=con.prepareStatement("select * from dept where id=? and name=?");
			ps.setInt(1, id);
			ps.setString(2, name);
			ResultSet rsp=ps.executeQuery();
			while(rsp.next())
			{
				System.out.println(rsp.getInt(1)+" "+rsp.getString(2)+" "+rsp.getInt(3)+" "+rsp.getString(4)+" "+rsp.getInt(5));
			}
			System.out.println("Enter details to add:");
			id=inp.nextInt();
			inp.nextLine();
			name=inp.nextLine();
			y=inp.nextInt();
			inp.nextLine();
			String head=inp.nextLine();
			int num=inp.nextInt();
			ps=con.prepareStatement("insert into dept values(?,?,?,?,?)");
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setInt(3, y);
			ps.setString(4, head);
			ps.setInt(5,num);
			int i=ps.executeUpdate();
			ps=con.prepareStatement("select * from dept");
			rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}