package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;

import java.io.IOException;

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
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int rating = Integer.parseInt(request.getParameter("rating"));
		String review = request.getParameter("review");
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		quizManager.addRatingAndReview(quiz_id, rating, review);
		RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
		dispatch.forward(request, response); 
	}
}