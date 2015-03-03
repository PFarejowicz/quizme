package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuizStartServlet
 */
@WebServlet("/QuizStartServlet")
public class QuizStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizStartServlet() {
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
		QuestionManager questionManager = (QuestionManager) getServletContext().getAttribute("question manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		
		ArrayList<Integer> questions = questionManager.getQuestionIDs(quiz_id);
		boolean random_order = request.getParameter("random_order").equals("true");
		if (random_order) Collections.shuffle(questions);
		session.setAttribute("questions", questions);
		
		boolean multiple_pages = request.getParameter("multiple_pages").equals("true");
		if (multiple_pages) {
			request.setAttribute("question_num", 1);
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_multiple_page_view.jsp");
			dispatch.forward(request, response); 
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_single_page_view.jsp");
			dispatch.forward(request, response); 
		}
	}

}