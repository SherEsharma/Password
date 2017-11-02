package task;

import java.sql.*;
import java.lang.reflect.*;

public class DataBaseConnectivity {
	Connection con;
	Statement s;
	PreparedStatement ps;
	public DataBaseConnectivity() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/userdata","root","root");
		s=con.createStatement();
		//System.out.println(con);
		
		
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ResultSet get(String str)
	{
		ResultSet rs=null;
		try {
			rs=s.executeQuery(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public int set(String[] str)
	{
		int j=0;
	
		try {
			ps=con.prepareStatement("insert into user_session_details values(?,?,?,?,?)");
			
		
			
				
				ps.setString(1,str[0] );
				ps.setString(2,str[1] );
				ps.setString(3,str[2] );
				ps.setString(4,str[3] );
				ps.setInt(5,1 );
				
			
			j=ps.executeUpdate();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j ;
	}
	public int set1(String[] str,float counter)
	{
		int j=0;
	
		try {
			ps=con.prepareStatement("insert into attendance_rec values(?,?,?,?)");
			
		
			
				
				ps.setString(1,str[0] );
				ps.setString(2,str[1] );
				ps.setString(3,str[2] );
				//ps.setString(4,str[3] );
				ps.setFloat(4, counter);
				
				
			
			j=ps.executeUpdate();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j ;
	}
	public int set2(String[] str,float counter)
	{
		int j=0;
	
		try {
			ps=con.prepareStatement("update attendance_rec set total_time=? , attendance_counter = ? WHERE emp_id = ? AND date = ? ");
			
		
			
				
				ps.setString(1,str[2] );
				ps.setFloat(2,counter );
				ps.setString(3,str[0] );
				ps.setString(4,str[1] );
				
				
				
			
			j=ps.executeUpdate();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j ;
	}
	public int set3(String str)
	{
		int j=0;
	
		try {
			
			String query ="UPDATE user_session_details SET status_flag=CASE WHEN status_flag=1 THEN 0 WHEN status_flag=0 THEN 1 END WHERE email_id='"+str+"'";

			ps=con.prepareStatement(query);
			
		
			
				
				
				
				
			
			j=ps.executeUpdate();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return j ;
	}
	
	public void closeall() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}

