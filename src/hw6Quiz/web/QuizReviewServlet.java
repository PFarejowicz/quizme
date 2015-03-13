package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizReviewServlet
 */
@WebServlet("/QuizReviewServlet")
public class QuizReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizReviewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int user_id = (Integer) request.getSession().getAttribute("user id");
		int score = Integer.parseInt(request.getParameter("score"));
		int total = Integer.parseInt(request.getParameter("total"));
		Timestamp time_taken = (Timestamp) request.getSession().getAttribute("time_taken");
		
		// Check achievements
		quizManager.iAmGreatestAchievement(user_id, quiz_id, score);
		
		// Update review and rating of quiz
		int rating = 0; 													// no voting is 0 stars
		if (request.getParameter("rating") != null) {
			rating = Integer.parseInt(request.getParameter("rating"));	
		}
		String review = request.getParameter("review");
		String name = request.getParameter("name");
		quizManager.addQuizResult(quiz_id, user_id, score, total, rating, review, name, time_taken);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
		dispatch.forward(request, response); 
	}
}
