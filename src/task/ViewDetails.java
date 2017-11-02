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
 * Servlet implementation class ViewDetails
 */
@WebServlet("/ViewDetails")
public class ViewDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs=request.getSession(false);
		PrintWriter out=response.getWriter();
	      DataBaseConnectivity db=new DataBaseConnectivity();
		String[][] details;
		int row=0;
		if(hs!=null)
		{
			 String query1="select count(*) as total_rec from user_session_details";
		     ResultSet rs1=db.get(query1);
		     if(rs1 !=null)
			 	{
			 		try {
			 			while(rs1.next())
			 			{
			 		
			 			row=rs1.getInt("total_rec");
			 		
			 			 
			 			}
			 		}
			 		catch (SQLException e) {
			 			// TODO Auto-generated catch block
			 			e.printStackTrace();
			 		}
			 	}
		     details=new String[row][4];
		     if(row>0)
		     {
		    	 
			     String query="select email_id,name,mobile_no,status_flag from user_session_details";
			     DataBaseConnectivity db1=new DataBaseConnectivity();
			 	ResultSet rs=db1.get(query);
			 	if(rs !=null)
			 	{
			 		int j=0;
			 		try {
			 			while(rs.next())
			 			{
			 		
			 			details[j][0]=rs.getString(1);
			 			details[j][1]=rs.getString(2);
			 			details[j][2]=rs.getString(3);
			 			details[j][3]=Integer.toString(rs.getInt(4));
			 			 j=j+1;
			 			}
			 		}
			 		catch (SQLException e) {
			 			// TODO Auto-generated catch block
			 			e.printStackTrace();
			 		}
			 	}	 
		     }
		     String view="";
			if(row>0)
			{
			
				for(int k=0;k<row;k++)
				{
					 String	temp="<tr><td>"+details[k][0]+"</td>"+"<td>"+details[k][1]+"</td>"+"<td>"+details[k][2]+"</td>";
						if(details[k][3].equals("0"))
						{
							temp=temp+"<td bgcolor='#00FF00'><a href='StatusChange?id="+details[k][0]+"'> Disable </a></td>";
							temp=temp+"<td><a href='ViewAttendance?id="+details[k][0]+"'>View</a></td>";	
							temp=temp+"<td><a href='SortAttendance?id="+details[k][0]+"'>Sort</a></td></tr>";
						}
						if(details[k][3].equals("1"))
						{
							temp=temp+"<td bgcolor='#FF0000'><a href='StatusChange?id="+details[k][0]+"'> Enable </a></td>";
							temp=temp+"<td><a href='ViewAttendance?id="+details[k][0]+"'>View</a></td>";	
							temp=temp+"<td><a href='SortAttendance?id="+details[k][0]+"'>Sort</a></td></tr>";
						}	
							
					view=view+temp;
				}			
			
			
			}
			
			
			
			
			
		
			out.println("<html><body  bgcolor='#E6E6FA'>");
			out.println("<h3><label style='color: maroon;'> View all user detaills </label></h3>");
			out.println("<form action='AdminLogout'>");
			out.println("<input type='submit' name='Submit' value='Logout'>");
			out.println("<table  border='1'>");
			out.println("<tr>");
			out.println("<td><label style='color: maroon;'>User Id </label></td>");
			out.println("<td><label style='color: maroon;'>User Name</label></td>");
			out.println("<td><label style='color: maroon;'>Mobile No. </label></td>");
			out.println("<td><label style='color: maroon;'>Status </label></td>");
			out.println("<td><label style='color: maroon;'>View Attendance </label></td>");
			out.println("</tr>");
			
			
			out.println(view);
			
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
