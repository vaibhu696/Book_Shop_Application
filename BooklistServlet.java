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

@WebServlet("/booklist")

public class BooklistServlet  extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter out = resp.getWriter();
		//set content type
		resp.setContentType("text/html");
		//LOAD jdbc driver
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		// Generate the connection
			try(
				Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/batch197", "root", "root");
			PreparedStatement ps = c.prepareStatement("SELECT ID,bookname,BookEdition,BookPrice,id FROM bookdata")){
				ResultSet rs =ps.executeQuery();
				
				 // Add CSS for background image
//	            out.println("<html>");
//	            out.println("<head>");
//	            out.println("<style>");
//	            out.println("body {");
//	            out.println("    background-image: url('https://files.oaiusercontent.com/file-LqxtLkT6rngVeEfAUWwpxV?se=2025-01-01T11%3A33%3A21Z&sp=r&sv=2024-08-04&sr=b&rscc=max-age%3D604800%2C%20immutable%2C%20private&rscd=attachment%3B%20filename%3D29448b61-1ff2-4c02-a82b-f30ca9cab0a2.webp&sig=ERB8kVaCObVQpcrxGKwxAals3k0ciEYGGIzQgeUrr6o%3D');");
//	            out.println("    background-size: cover;");
//	            out.println("    background-repeat: no-repeat;");
//	            out.println("    background-attachment: fixed;");
//	            out.println("}");
//	            out.println("table {");
//	            out.println("    margin: 50px auto;");
//	            out.println("    background-color: rgba(255, 255, 255, 0.8);");
//	            out.println("    border-collapse: collapse;");
//	            out.println("}");
//	            out.println("th, td {");
//	            out.println("    padding: 10px;");
//	            out.println("    border: 1px solid #ddd;");
//	            out.println("}");
//	            out.println("th {");
//	            out.println("    background-color: #f4f4f4;");
//	            out.println("}");
//	            out.println("</style>");
//	            out.println("</head>");
//	            out.println("<body>");
//
//		         
		         
				out.println("<table border ='1' align ='center'>");
				
				out.println("<tr>");
				out.println("<th>Book ID</th>  ");
				out.println("<th>bookname</th>  ");
				out.println("<th>BookEdition</th>  ");
				out.println("<th>BookPrice</th>  ");
				out.println("<th>Edit</th>  ");
				out.println("<th>Delete</th>  ");
				
				out.println("</tr>  ");
				
				while (rs.next()){
					out.println("<tr>");
					out.println("<td>"+rs.getInt(1)+ "</td>");
					out.println("<td>"+rs.getString(2)+ "</td>");
					out.println("<td>"+rs.getString(3)+ "</td>");
					out.println("<td>"+rs.getFloat(4)+ "</td>");
					out.println("<td><a href='editScreeen?id=" +rs.getString(1)+"'>Edit</a></td>");
					out.println("<td><a href='deleteurl?id=" +rs.getString(1)+"'>Delete</a></td>");
					out.println("</tr>  ");
					
				}
				out.println("</table>");
			
				
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
