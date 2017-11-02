package task;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationForm
 */
@WebServlet("/RegistrationForm")
public class RegistrationForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBaseConnectivity db=new  DataBaseConnectivity();
		PrintWriter out= response.getWriter();
			if(request.getParameter("name")!=null && request.getParameter("email_id")!=null && request.getParameter("password")!=null )
		{
			String[] details=new String[4];
			String full_name=request.getParameter("name");  
		    String email_id=request.getParameter("email_id");
		    String mobile_no=request.getParameter("mobile_no");
		   
		   String passwrd= getEncryptedPassword(request.getParameter("password"));
		 
		   
		   details[0]=email_id;		 
		   details[1]=full_name;
		   details[2]=passwrd;
		   details[3]=mobile_no;
		 
		   
		   
		   
		   int flag=db.set(details);
		   if(flag==0)
		   {
			   out.println("<h3 style='color: red ;'>This username not available please try another one</h3>");
				RequestDispatcher rd=request.getRequestDispatcher("Register.html");
				rd.include(request, response); 
		   }
		   else
		   {
			   Mailer.send(email_id, "Registration", "you are successfully register to application..!");
			   out.println("<h3 style='color: green ;'>You are successfully RegisterY</h3>");
				RequestDispatcher rd=request.getRequestDispatcher("First.html");
				rd.include(request, response);
		   }
		   //System.out.println(flag);
		  
		}
			else
			{
				 out.println("<h3 style='color: red ;'>please enter essential details</h3>");
					RequestDispatcher rd=request.getRequestDispatcher("Register.html");
					rd.include(request, response); 
			}
			
	}
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
