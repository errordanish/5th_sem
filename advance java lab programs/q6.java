import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/CALUCLATOR")
public class CALUCLATOR extends HttpServlet {
protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
// TODO Auto-generated method stub
PrintWriter pw=res.getWriter();
int x= Integer.parseInt(req.getParameter("fno"));
int y=Integer.parseInt(req.getParameter("sno"));
String ans=req.getParameter("ans");
if(ans.equals("add"))
{
pw.println("<h3> Addtion = "+(x+y)+"</h3>");
}
else if(ans.equals("sub"))
{
pw.println("<h3> Subtraction = "+(x-y)+"</h3>");
}
else if(ans.equals("mul"))
{
pw.println("<h3> Multiplication = "+(x*y)+"</h3>");
}
else if(ans.equals("div"))
{
pw.println("<h3> Division = "+(x/y)+"</h3>");
}
else if(ans.equals("exp"))
{
pw.println("<h3> e^"+x+" = "+(Math.exp(x))+"</h3>");
} }
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
IOException {
doGet(request, response);
} }
