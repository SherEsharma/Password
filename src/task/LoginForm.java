package task;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginForm
 */
@WebServlet("/LoginForm")
public class LoginForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public static String getEncryptedPassword(String clearTextPassword)   {  
		  
	    
	    try {
	      MessageDigest md = MessageDigest.getInstance("SHA-256");
	      md.update(clearTextPassword.getBytes());
	      return new sun.misc.BASE64Encoder().encode(md.digest());
	    } catch (NoSuchAlgorithmException e) {
	      //_log.error("Failed to encrypt password.", e);
	    }
	    return "";
	  }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String de[]=new String[1];
		int flag=9999,status=24;
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String user_id=request.getParameter("email_id");
		
		
		String passwrd= getEncryptedPassword(request.getParameter("password"));
		
		String query="select count(*) as total_rec,status_flag from user_session_details where email_id='"+user_id+"'"+ " AND password='"+passwrd+"'";
		DataBaseConnectivity db=new DataBaseConnectivity();
	ResultSet rs1=db.get(query);
	if(rs1 !=null)
	{
		try {
			while(rs1.next())
			{
			flag=rs1.getInt("total_rec");
			status=rs1.getInt("status_flag");
		
			 
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	if(flag==9999 || flag==0)
	{

		out.println("<h3 style='color: red ;'>invalid user id and password</h3>");
		RequestDispatcher rd=request.getRequestDispatcher("Login.html");
		rd.include(request, response);
	}if(status==1)
	{

		out.println("<h3 style='color: red ;'>your account is not approved yet</h3>");
		RequestDispatcher rd=request.getRequestDispatcher("Login.html");
		rd.include(request, response);
	}
	else
	{
		if(status==0)
		{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat myFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String loginTime=myFormat.format(calendar.getTime());
		
		HttpSession hs=request.getSession(true);
		hs.setAttribute("email_id",user_id );
		hs.setAttribute("login_time",loginTime);
		
		RequestDispatcher rd=request.getRequestDispatcher("Welcome");
		rd.forward(request, response);
		}
		
		
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
