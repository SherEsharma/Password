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
 * Servlet implementation class AdminPanel
 */
@WebServlet("/AdminPanel")
public class AdminPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPanel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs=request.getSession(false);
		PrintWriter out=response.getWriter();
		if(hs!=null)
		{
		
		
			out.println("<html><body  bgcolor='#E6E6FA'>");
			
			out.println("<form action='AdminLogout'>");
			out.println("<input type='submit' name='Submit' value='Logout'>");
			out.println("<table>");
			out.println("<tr><td><label style='color: maroon;'> View all user detaills </label></td></tr>");
			out.println("<tr><td><a href='ViewDetails'> View all user detaills </a></td></tr>");
		    out.println("</table>");
            out.println("</form></body></html>");
		}
		else
		{
			RequestDispatcher rd=request.getRequestDispatcher("AdminLogin.html");
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
