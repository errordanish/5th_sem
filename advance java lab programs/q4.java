import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Vote")
public class Vote extends HttpServlet {
protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
// TODO Auto-generated method stub
 res.setContentType("text/html");
 PrintWriter out = res.getWriter();
 String fname=req.getParameter("fname");
 String lname=req.getParameter("lname");
 String email=req.getParameter("email");
 String dob=req.getParameter("dob");
 int AGE = 0;
try {
SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
Date date = formatter.parse(dob);
//Converting obtained Date object to LocalDate object
 Instant instant = date.toInstant();
 ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
 LocalDate givenDate = zone.toLocalDate();
 //Calculating the difference between given date to current date.
 Period period = Period.between(givenDate, LocalDate.now());
 AGE=period.getYears();
} catch (ParseException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

out.println("<h4> Name : "+fname+" "+lname+"</h4>");
out.println("<h4> Email : "+email+"</h4>");
out.println("<h4> Age : "+AGE+"</h4>");
if(AGE>=18)
{
out.println("<h4> Eligible for Vote</h4>");
}else
{
out.println("<h4> Not Eligible for Vote</h4>");
}
 out.close();
}
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
IOException {
// TODO Auto-generated method stub
doGet(request, response);
}
}
