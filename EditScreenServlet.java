package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreeen")

public class EditScreenServlet  extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter out = resp.getWriter();
		
		
		//set content type
		resp.setContentType("text/html");
		
		
		//get the id of record
		int id=Integer.parseInt(req.getParameter("id"));
		
		
		//LOAD jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Generate the connection
			try(
				Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/batch197", "root", "root");
			    PreparedStatement ps = c.prepareStatement("SELECT bookname,BookEdition,BookPrice FROM bookdata Where id=?"))
			{
				ps.setInt(1, id);
				ResultSet rs=ps.executeQuery();
				
	           


				
				if (rs.next()) {
				
				
				out.println("<form action='editurl?id="+id+"' method='post'>");
				out.println("<table align='center'>");
				out.println("<tr>");
				out.println("<td>Book Name </td>");
				out.println("<td><input type='text'  name='bookname' value='"+rs.getString(1)+"'></td> ");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>BookEdition</td>");
				out.println("<td><input type='text'  name='bookedition' value='"+rs.getString(2)+"'></td> ");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Bookprice</td>");
				out.println("<td><input type='text'  name='bookprice' value='"+rs.getFloat(3)+"'></td> ");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<tdSubmit</td>");
				out.println("<td><input type='submit'   value='Edit'></td> ");
				out.println("<td><input type='reset'   value='cancel'></td> ");
				out.println("</tr>");
				
				out.println("</table>");
				out.println("</form>");
				}else {
					out.println("<h2>No record found for bookname: " + id + "</h2>");
				}
				
				
			}catch (SQLException e) {
				e.printStackTrace();
				out.print("<h1>" +e.getMessage()+"</h2>");
			}catch(Exception e) {
				e.printStackTrace();
				out.print("<h1>" +e.getMessage()+"</h2>");

		}
			out.println("<a href='index.html'>Home</a>");
	}

}
