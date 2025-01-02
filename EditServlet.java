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

@WebServlet("/editurl")

public class EditServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter out = resp.getWriter();
		
		int id=Integer.parseInt(req.getParameter("id"));
		
		
		//set content type
		resp.setContentType("text/html");
		
		
		//get the Bookname of record
		String Bookname=req.getParameter("bookname");
		String BookEdition=req.getParameter("bookEdition");
		float Bookprice=Float.parseFloat(req.getParameter("bookprice"));
		
		//LOAD jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Generate the connection
			try(
				Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/batch197", "root", "root");
			   
					PreparedStatement ps = c.prepareStatement("UPDATE bookdata SET Bookname=?,BookEdition=? ,Bookprice=? WHERE id=?"))
			{
					ps.setString(1, Bookname);
					ps.setString(2, BookEdition);
					ps.setFloat(3, Bookprice);
					ps.setInt(4, id);
					int count=ps.executeUpdate();
					if(count == 1) {
						out.println("<h2>Record is Edited Successfully</h2>");
					}else {
						out.println("<h2>Record is not Edited Successfully</h2>");
					}
				
				
			}catch (SQLException e) {
				e.printStackTrace();
				out.print("<h1>" +e.getMessage()+"</h2>");
			}catch(Exception e) {
				e.printStackTrace();
				out.print("<h1>" +e.getMessage()+"</h2>");

		}
			
			out.println("<a href='index.html'>Home</a>");
			out.println();
			out.println("<a href=booklist>Book List</a>");
	}

}
