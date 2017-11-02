package task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws ParseException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public long[] timeDiff (String str) throws ParseException {
    	
    	Calendar calendar = Calendar.getInstance();
        SimpleDateFormat myFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
       String currentTime=myFormat.format(calendar.getTime());
      
       Date d1=myFormat.parse(str);
		Date d2=myFormat.parse(currentTime);

		long diff=d2.getTime()-d1.getTime();
		//System.out.println(diff);

long diffSeconds = diff / 1000 % 60;
long diffMinutes = diff / (60 * 1000) % 60;
long diffHours = diff / (60 * 60 * 1000) % 24;
long diffDays = diff / (24 * 60 * 60 * 1000);

long[] df={diffDays,diffHours,diffMinutes,diffSeconds};
    	return df;
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs=request.getSession(false);
		PrintWriter out=response.getWriter();
		String email_id=(String)hs.getAttribute("email_id");
		System.out.println(email_id);
		String login_time=(String)hs.getAttribute("login_time");
		
		String[] time=login_time.split(" ");
	    String date=time[0];
	    long diff[]=new long[4];
		try {
			diff = timeDiff(login_time);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long session_period=diff[1];	
	
	  int flag=9999;
	  String[] details=new String[3];
	  float counter=0.0f;
	 if(session_period<4){counter=0.0f;}
	 if(session_period>4 && session_period<8 ){counter=0.5f;}
	 if(session_period>=8 ){counter=1.0f;}
String query="select count(*) as total_rec from attendance_rec where  emp_id='"+email_id+"'"+ " AND date='"+date+"'"; 
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
if(flag==0)
{

	//insert into table new entry
	details[0]=email_id;
	details[1]=date;
    details[2]=Long.toString(diff[1])+":"+Long.toString(diff[2]);
    
    int flag2=db.set1(details,counter);
    
    
    if(flag2==0)
	   {
		   System.out.println("error");
	   }
	   else
	   {
		   hs.invalidate();
		   out.println("<h3 style='color: green ;'>You are successfully logout</h3>");
		   out.println("<h3 style='color: green ;'>Your completed working hours are "+Long.toString(diff[1])+":"+Long.toString(diff[2])+"</h3>");
			RequestDispatcher rd=request.getRequestDispatcher("Login.html");
			rd.include(request, response);
	   }
    
}
else
{
	//update working time and attendance
	String qry_str="select total_time from attendance_rec where  emp_id='"+email_id+"'"+ " AND date='"+date+"'";  
	ResultSet rs2=db.get(qry_str);
	String prev_wr_hrs="";
	String new_wr_hrs=Long.toString(diff[1])+":"+Long.toString(diff[2]);
	if(rs2 !=null)
	{
		try {
			while(rs2.next())
			{
				prev_wr_hrs=rs2.getString("total_time");
			
		
			 
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	String arr[]=prev_wr_hrs.split(":");
	long total_hr=Long.parseLong(arr[0])+diff[1];
	long total_min=Long.parseLong(arr[1])+diff[2];
	if(total_min>=60)
	{
		total_hr=total_hr+1;
		total_min=total_min-60;
	}
	
	
	long new_total_time=total_hr;
	if(new_total_time<4){counter=0.0f;}
	 if(new_total_time>4 && new_total_time<8 ){counter=0.5f;}
	 if(new_total_time>=8 ){counter=1.0f;}
	 details[0]=email_id;
		details[1]=date;
	    details[2]=Long.toString(total_hr)+":"+Long.toString(total_min);
	    
	    
	   
	    int flag3=db.set2(details,counter);
	  //  System.out.println(flag3);
	    
	    if(flag3==0)
		   {
			   System.out.println("error");
		   }
		   else
		   {
			   hs.invalidate();
			   out.println("<h3 style='color: green ;'>You are successfully logout</h3>");
			   out.println("<h5 style='color: green ;'>Your completed working hours are "+Long.toString(total_hr)+":"+Long.toString(total_min)+"</h5>");
				RequestDispatcher rd=request.getRequestDispatcher("Login.html");
				rd.include(request, response);
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
