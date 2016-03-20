package uci;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLData;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.mysql.jdbc.PreparedStatement;

public class BugTableServlet extends HttpServlet {
			public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 
			
			String name = null;
			String email = null;
			String app_Name = null;
			String bug_Description = null;
			String due_Date = null;
			String priority = null;
			
			response.setContentType("text/html");   // Code creates printwriter object to print out   Brought in from EdsServSQL servlet.
			PrintWriter out = response.getWriter(); 
			out.println("<html>"); 
			out.println("<body>");		
			Connection result = null;
					
			try {
				//read value from HTML form				
				InitialContext ic = new InitialContext();
				Context initialContext = (Context) ic.lookup("java:comp/env");
				DataSource datasource = (DataSource) 
				initialContext.lookup("jdbc/MySQLDS");
				result = datasource.getConnection();
							
				Statement stmt = result.createStatement();
			//	PreparedStatement pstmt = con.prepareStatement();						
				name = request.getParameter("name");
				email = request.getParameter("email");
				app_Name = request.getParameter("app_Name");
				bug_Description = request.getParameter("bug_Description");
				due_Date = request.getParameter("due_Date");
				priority = request.getParameter("priority");
							
				/*  Another way of populating db, although we would need to resolve an "pstmt cannot be resolved error"				
				PreparedStatement pstmt = con.prepareStatement("INSERT INTO Bug_Table VALUES(?, ?, ?)");  
				pstmt.setString(1, clientName);
				pstmt.setString(2, clientEmail);
				pstmt.setString(3, clientAppNam);
				pstmt.setString(4,clientBugDet);
				pstmt.setString(5, clientPriority);

				int i = pstmt.executeUpdate();*/
				
				// Set up JSP forward				
				//String nextJSP = "/Bug_Tablejsp.jsp";  
				RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher("/Bug_Tablejsp.jsp");
			
				//Set values that can be retrieved in the JSP				
				request.setAttribute(name, dispatcher);
				request.setAttribute(email, dispatcher);
				request.setAttribute(app_Name, dispatcher);
				request.setAttribute(bug_Description, dispatcher);
				request.setAttribute(due_Date, dispatcher);
				request.setAttribute(priority, dispatcher);
				
				dispatcher.forward(request, response);
				
				// Send data to the db.....Obtained code to StackOverflow.
				int i=stmt.executeUpdate("INSERT INTO Bug_Table('"+name+"','"+email+"','"+app_Name+"','"+bug_Description+"','"+due_Date+"','"+priority+"')");
				
			} catch (ServletException e)  {
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (name == null)  {
					throw new NullPointerException();
					}
				}							
			}
			
			public void doGet(HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException  {
					
					doPost(request, response); // call doPost() for flow of control logic	
				}
			}

