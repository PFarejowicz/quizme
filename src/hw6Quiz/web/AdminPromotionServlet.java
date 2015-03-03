package hw6Quiz.web;

import hw6Quiz.manager.AdminManager;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminPromotionServlet
 */
@WebServlet("/AdminPromotionServlet")
public class AdminPromotionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPromotionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminManager adminManager = ((AdminManager)request.getServletContext().getAttribute("admin manager"));
		int id = Integer.parseInt(request.getParameter("id"));
		adminManager.promoteToAdmin(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("admin_homepage.jsp");
		dispatcher.forward(request, response);
	}

}
