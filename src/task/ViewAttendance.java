package task;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class ViewAttendance
 */
@WebServlet("/ViewAttendance")
public class ViewAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAttendance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs=request.getSession(false);
		PrintWriter out=response.getWriter();
		String user_id=request.getParameter("id");
		String query1="select count(*) as total_rec from attendance_rec where emp_id='"+user_id+"'";
	     DataBaseConnectivity db2=new DataBaseConnectivity();
	 	ResultSet rss=db2.get(query1);int c=0;
	 	if(rss !=null)
	 	{
	 		
	 		try {
	 			while(rss.next())
	 			{c=rss.getInt("total_rec");}
	 		}
	 		catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}	
	 		
	 	}
	 	String[] date,total_hr,attendance;
		date=new String[c];total_hr=new String[c];attendance=new String[c];
		if(hs!=null)
		{ 
		String query="select date,total_time,attendance_counter from attendance_rec where emp_id='"+user_id+"'";
	     DataBaseConnectivity db1=new DataBaseConnectivity();
	 	ResultSet rs=db1.get(query);
	 	if(rs !=null)
	 	{
	 		int j=0;
	 		try {
	 			while(rs.next())
	 			{
	 		
	 			date[j]=rs.getString("date");
	 			total_hr[j]=rs.getString("total_time");
	 			float temp=rs.getFloat("attendance_counter");
	 			String temp1="";
	 			if(temp==0.0){temp1="Absent";}
	 			if(temp==0.5){temp1="Half Day";}
	 			if(temp==1.0){temp1="Full Day";}
	 			attendance[j]=temp1;
	 			
	 			 j=j+1;
	 			}
	 		}
	 		catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 		String view="";
			
			for(int k=0;k<date.length;k++)
			{
				 String	temp="<tr><td>"+date[k]+"</td>"+"<td>"+total_hr[k]+"</td>"+"<td>"+attendance[k]+"</td></tr>";
				
				 view=view+temp;
			}			
		if(view.equals("")){view="<tr>Corresponding this user there is no login entry</tr>";}
			out.println("<html><body  bgcolor='#E6E6FA'>");
			out.println("<h3><label style='color: maroon;'> View all attendances of "+user_id+ " </label></h3>");
			out.println("<form action='ViewDetails'>");
			out.println("<input type='submit' name='Submit' value='Back'>");
			out.println("<table  border='1'>");
			out.println("<tr>");
			out.println("<td><label style='color: maroon;'>date</label></td>");
			out.println("<td><label style='color: maroon;'>Working Hours</label></td>");
			out.println("<td><label style='color: maroon;'>Attendane Mark </label></td>");
			out.println("</tr>");
			
			
			out.println(view);
			
		    out.println("</table>");
            out.println("</form></body></html>");
		
	 		
	 	}
	 	
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
