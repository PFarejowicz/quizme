package hw6Quiz.web;

import hw6Quiz.manager.*;
import hw6Quiz.model.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuizSearchServlet
 */
@WebServlet("/QuizSearchServlet")
public class QuizSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizSearchServlet() {
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
		HttpSession session = request.getSession();
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		String tag = request.getParameter("tag");
		
		ArrayList<Quiz> result = quizManager.searchQuizzes(name, category, tag);
		session.setAttribute("search", result);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz_archive.jsp");
		dispatch.forward(request, response);
	}

}
