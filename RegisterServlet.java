package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter out = resp.getWriter();
		//set content type
		resp.setContentType("text/html");
		
		//GET the book info
		String bookname=req.getParameter("bookname");
		String bookedition=req.getParameter("bookEdition");
//		int id=Integer.parseInt(req.getParameter("id"));
		float bookprice=Float.parseFloat(req.getParameter("bookPrice"));
		
		//LOAD jdbc driver
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Generate the connection
			try(
				Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/batch197", "root", "root");
			PreparedStatement ps = c.prepareStatement("INSERT INTO bookdata(bookname,BookEdition,Bookprice) VALUES (?,?,?)"))
					
					{
				ps.setString(1, bookname);
				ps.setString(2, bookedition);
				ps.setFloat(3, bookprice);
//				ps.setInt(4, id);
				int count =ps.executeUpdate();
				if (count ==1) {
					out.println("<h2> Record is registered Sucessfully</h2>");
				}else {
					out.println("<h2> Record is not registered Sucessfully</h2>");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				out.print("<h1>" +e.getMessage()+"</h2>");
			}catch(Exception e) {
				e.printStackTrace();
				out.print("<h1>" +e.getMessage()+"</h2>");

		}
			out.println("<a href='index.html'>Home</a>");
			out.println("<br>");
			out.println("<a href='booklist'>Booklist</a>");
	}

}
