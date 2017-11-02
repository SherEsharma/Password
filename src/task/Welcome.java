package task;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession hs=request.getSession(false);
		if(hs!=null)
		{
		String login_time=(String)hs.getAttribute("login_time");
		String email_id=(String)hs.getAttribute("email_id");
		
	String[] time=login_time.split(" ");
	
	String[] stamp=time[1].split(":");
     int hours=Integer.parseInt(stamp[0]);
		String greeting = null;
		if(hours>=1 && hours<12){
		    greeting = "Good Morning";
		} else if(hours>=12 && hours<16){
		    greeting = "Good Afternoon";
		} else if(hours>=16 && hours<21){
		    greeting = "Good Evening";
		} else if(hours>=21 && hours<=24){
		    greeting = "Good Night";
		}
	
		
		PrintWriter out=response.getWriter();
		out.println("<html><body  bgcolor='#E6E6FA'>");
		out.println("<form action='Logout'>");
		out.println("<input type='submit' name='Submit' value='Logout'>");
		out.println("<h5 align='Left'>"+greeting+" "+email_id +"</h5>");
		out.println("<label> your Login time :"+time[1]  + "</label>");
		out.println("<label> <a href='EditProfile'>Edit profile</a></label>");
		out.println("</form></body></html>");
		}else
		{
			RequestDispatcher rd=request.getRequestDispatcher("Login.html");
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
