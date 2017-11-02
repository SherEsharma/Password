package task;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SortAttendance
 */
@WebServlet("/SortAttendance")
public class SortAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SortAttendance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String sort(String[] str) {
    	
    	Date[] arrayOfDates = new Date[str.length];
 		SimpleDateFormat myFormat=new SimpleDateFormat("dd-MM-yyyy");
 		for (int index = 0; index < str.length; index++) {
 		    try {
				arrayOfDates[index] = myFormat.parse(str[index]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
 		Arrays.sort(arrayOfDates);
 		String []sortdates=new String[str.length];
 		for (int index = 0; index < str.length; index++) {
 			sortdates[index] = myFormat.format(arrayOfDates[index]);
 		}
 		String s_mnth,s_yr;
 		String e_mnth,e_yr;
 		String temp;
 		String tp[]= sortdates[0].split("-");
 		s_mnth=tp[1];
 		s_yr=tp[2];
 		String tp1[]= sortdates[sortdates.length-1].split("-");
 		e_mnth=tp1[1];
 		e_yr=tp1[2];
 		return (s_mnth+"-"+s_yr+":"+e_mnth+"-"+e_yr);
	}
    public List<String> list(String s1,String s2) 
    {
    	
		String mnth[]={"","jan","feb","mar","apr","may","jun","jul","aug","sept","oct","nov","dec"};
		List<String> mnth_list=new ArrayList<String>();
		
		int mm=0,yy=0;
		int flag=0;
		int k=0,j=0;
		String op="";
		int ip=Integer.parseInt(s1);
		boolean b=ip<=12;
		
		if(b)
		{
			
			    mm=ip;
				yy=Integer.parseInt(s2);
				
				for(k=mm;k<=12& mm<=12;k++)
				{
					
					flag=flag+1;
					op=mnth[mm]+"-"+yy;
					mnth_list.add(op);
				    mm++;
					
				}
				
				if(flag!=12)
				{
					mm=1;yy=yy+1;
					j=12-flag;
					for(k=1;k<=j;k++)
					{
						
						
						op=mnth[mm]+"-"+yy;
						mnth_list.add(op);
					    mm++;
						
					}
				}
				
				return mnth_list;	
			
		}
		else
		{
		return mnth_list;
			
		}
	}
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
	 	String[] date;
		date=new String[c];
		if(hs!=null)
		{ 
		String query="select date from attendance_rec where emp_id='"+user_id+"'";
	     DataBaseConnectivity db1=new DataBaseConnectivity();
	 	ResultSet rs=db1.get(query);
	 	if(rs !=null)
	 	{
	 		int j=0;
	 		try {
	 			while(rs.next())
	 			{
	 		
	 			date[j]=rs.getString("date");
	 			
	 			
	 			 j=j+1;
	 			}
	 		}
	 		catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 		
	 		String fromtoTo=sort(date);
	 		String[] ss=fromtoTo.split(":");
	 		String from_mnth=ss[0].substring(0, 2);
	 		String from_year=ss[0].substring(3);
	 		List<String> ls=list(from_mnth, from_year);
	 cal(user_id,ls);
	 		
	 		String view1="",view2="",view="";
	 	    // String temp="<td><select name='fmonth' id='frm_mnth_yr'><option value='' selected='selected'> Month-Year</option><option value='"+ls.get(0)+"'>"+ls.get(0)+"</option></td>";
	 	     String	temp="<td><label>"+ls.get(0)+"</label></td>";

	 	     view1=view1+temp;
			 // String	temp1="<td><select name='tomonth' id='tofrm_mnth_yr'><option value='' selected='selected'> Month-Year</option><option value='"+ls.get(ls.size() - 1)+"'>"+ls.get(ls.size() - 1)+"</option></td>";
			  String	temp1="<td><label>"+ls.get(ls.size() - 1)+"</label></td>";

			  view2=view2+temp1;			
			  view="<table border='1'><tr>";
			  Iterator<String> it=ls.iterator();
				 
				while (it.hasNext()) {
					String tm="<td>"+it.next()+"</td>";
				view=view +tm;
					
				}
				view=view+"</tr></table>";
			
			out.println("<html><body  bgcolor='#E6E6FA'>");
			out.println("<h3><label style='color: maroon;'> View all attendances of "+user_id+ " </label></h3>");
			out.println("<form action='SortingAttendData'>");
			out.println("<a href='ViewDetails'>Back</a>");
			out.println("<table  border='1'>");
			out.println("<tr>");
			out.println("<td><label style='color: maroon;'>From</label></td>");
			out.println(view1);//mnth-year dd
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td><label style='color: maroon;'>To</label></td>");
			out.println(view2);//mnth-year dd
			out.println("</tr>");
		
		
		    out.println("</table>");
		    out.println(view);
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
	public void cal(String str,List l) 
	{
		String mnth[]={"","jan","feb","mar","apr","may","jun","jul","aug","sept","oct","nov","dec"};
		String mnths[]={"00","01","02","03","04","05","06","07","08","09","10","11","12"};
		String user_id=str;
		 Iterator<String> it=l.iterator();
		 String arr[]=new String[12];int p=0;
			while (it.hasNext()) {
				String tm=it.next();
			String temp=tm.substring(0, tm.indexOf("-"));
			for(int j=0;j<mnth.length;j++)
			{
				if(temp.equals(mnth[j])){arr[p]=mnths[j]+tm.substring(tm.indexOf("-"));}
			}
			p++;
			
			}
			
		
	 	String query= "select sum(attendance_counter) as total_attendance from attendance_rec where emp_id='"+user_id+"' and date like '%%-"+arr[0]+"'";
	 	DataBaseConnectivity db2=new DataBaseConnectivity();
	 	ResultSet rss=db2.get(query);float c=0;
	 	if(rss !=null)
	 	{
	 		
	 		try {
	 			while(rss.next())
	 			{c=rss.getInt("total_attendance");}
	 		}
	 		catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}	
	 		
	 	}
	 	
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
