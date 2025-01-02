# Book_Shop_Application

# The Bookshop Application is a web-based project designed for managing book records. It allows users to register new books, view a list of all books, and perform actions like editing or deleting book records. This application is developed using Java Servlets, JSP, JDBC, and MySQL.


# HTML CODE
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/bootstrap.css">

<style>
     table {
        border: 2px solid #333;
        width: 100%;
    }

    th, td {
        border: 1px solid #333;
        padding: 8px;
        text-align: left;
    }
</style>
</head>
<body class="container-fluid card" style="width:40rem">
<h2 class="bg-danger text-white text-center">Book Registration</h2>
<form action="register" method="post">

<table class ="table table-hover">
<tr>
	<td>Book name</td>
	<td><input type="text" name="bookname"></td>
	</tr>
<tr>
	<td>Book Edition</td>
	<td><input type="text" name="bookEdition"></td>
	</tr>
<tr>
	<td>Book Price</td>
	<td><input type="text" name="bookPrice"></td>
	</tr>

	<td><input type="submit" value="register"></td>
	<td><input type="reset" value="cancel">
</td>
</tr>
	
</table>
<a href='booklist'>Booklist</a>
</form>

</body>
</html>

# RegisterServlet 
This servlet handles the registration of book details in a database. It processes HTTP POST requests and performs the following steps:

Receives User Input: Collects book details such as name, edition, and price from the user via request parameters.
Database Connection: Establishes a connection to a MySQL database using JDBC.
Data Insertion: Executes an SQL INSERT statement to save the book details in the bookdata table.
Response to User: Provides feedback on whether the registration was successful or not.

# CODE

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

# BookListServlet Code


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


# EditScreenServlet Code


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


# EditServlet Code

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


# DeleteServlet Code

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

@WebServlet("/deleteurl")

public class DeleteServelet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter out = resp.getWriter();
		
		int id=Integer.parseInt(req.getParameter("id"));
		
		
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
			   
					PreparedStatement ps = c.prepareStatement("DELETE from bookdata WHERE id=?"))
			{
					ps.setInt(1, id);
					
					int count=ps.executeUpdate();
					if(count == 1) {
						out.println("<h2>Record is Deleted Successfully</h2>");
					}else {
						out.println("<h2>Record is not Deleted Successfully</h2>");
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

