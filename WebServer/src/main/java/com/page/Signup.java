package com.page;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Signup
 */
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
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
			String query="insert into database (username,password) values (?,?)";
			PreparedStatement stat= con.prepareStatement(query);
			stat.setString(1, username);
			stat.setString(2, password);
	


		
			int rs = stat.executeUpdate();
		
			if(rs == 1){
			System.out.print("valid user..putting data in session");
			
			//putting data in session	
			HttpSession session=request.getSession();
			
			session.setAttribute("username",username);
		
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			
			}
			
			else{
				System.out.print("invalid user");
				RequestDispatcher rd=request.getRequestDispatcher("signup.jsp");
				rd.forward(request, response);
			}
		
		out.print("</body>Done</html>");
		
			
		} catch(Exception e) {
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
