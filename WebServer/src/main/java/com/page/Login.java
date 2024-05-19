package com.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			System.out.println("LoginServlet...");
			String username = request.getParameter("username");
			String password= request.getParameter("password");
			PrintWriter out=response.getWriter();
			
			ServletContext ctx=getServletContext();
			String dbdriver=ctx.getInitParameter("dbdriver");
			String dburl=ctx.getInitParameter("dburl");
			String dbuser=ctx.getInitParameter("dbuser");
			String dbpass=ctx.getInitParameter("dbpass");
			
			
			out.print("<html><body>");
			Class.forName(dbdriver);
			Connection con=DriverManager.getConnection(dburl,dbuser,dbpass);
			String query="Select * from loginorsignin where username=? and password=?";
			PreparedStatement stat= con.prepareStatement(query);
			stat.setString(1, username);
			stat.setString(2, password);
		
			ResultSet rs = stat.executeQuery();
		
			if(rs.next()){
			
//			//putting data in session	
//			HttpSession session=request.getSession();
//			
//			session.setAttribute("username", request.getParameter("username"));
//		
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			rd.forward(request, response);
			}
			
			else{
				System.out.print("invalid user");
				RequestDispatcher rd=request.getRequestDispatcher("Invalid.jsp");
				rd.forward(request, response);
			}
			
			
			while(rs.next()) {
				int id1 = rs.getInt(1);
				String col2 = rs.getString(2);
				System.out.println(col2);
				String col3= rs.getString(3);
				System.out.println(col3);
				String col4 =rs.getString(4);
				String col5=  rs.getString(5);
				System.out.println(col5);


				if(col2.equals(username) && col3.equals(password) && col5.equals("user")) {
					RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
					rd.forward(request, response);
					
				}
				else if(col2.equals(username) && col3.equals(password) && col5.equals("adminonly")) {
					RequestDispatcher rd=request.getRequestDispatcher("admin.jsp");
					rd.forward(request, response);
				}
				
				

			}
		
		out.print("</body></html>");
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
	
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
