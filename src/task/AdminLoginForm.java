package task;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminLoginForm
 */
@WebServlet("/AdminLoginForm")
public class AdminLoginForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */ public static String getEncryptedPassword(String clearTextPassword)   {  
		  
		    
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
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String user_id=request.getParameter("email_id");
		int flag=9999;
		
		String passwrd= getEncryptedPassword(request.getParameter("password"));
		
		String query="select count(*) as total_rec from admin_login where admin_id='"+user_id+"'"+ " AND admin_passwrd='"+passwrd+"'";
		DataBaseConnectivity db=new DataBaseConnectivity();
	ResultSet rs1=db.get(query);
	if(rs1 !=null)
	{
		try {
			while(rs1.next())
			{
			flag=rs1.getInt("total_rec");
			
		
			 
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
		RequestDispatcher rd=request.getRequestDispatcher("AdminLogin.html");
		rd.include(request, response);
	}
	else
	{
		HttpSession hs=request.getSession(true);
		hs.setAttribute("email_id",user_id );
		RequestDispatcher rd=request.getRequestDispatcher("AdminPanel");
		rd.forward(request, response);
	}
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
