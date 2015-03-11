package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;

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
		HttpSession session = request.getSession();
		QuestionManager questionManager = (QuestionManager) getServletContext().getAttribute("question manager");
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int user_id = (Integer) request.getSession().getAttribute("user id"); 
		
		// Start timer
		Calendar cal = Calendar.getInstance();
		Date startTime = new Date(cal.getTimeInMillis());
		session.setAttribute("start_time", startTime);
		
		// Get parameters for type of quiz 
		boolean multiple_pages = request.getParameter("multiple_pages").equals("true");
		boolean random_order = request.getParameter("random_order").equals("true");
		boolean isPracticeMode = request.getParameter("mode").equals("practice");

		// Set up questions
		ArrayList<Integer> questions = questionManager.getQuestionIDs(quiz_id);
		
		// Check practice mode
		if (isPracticeMode) {
			HashMap<Integer, Integer> quesFrequency = new HashMap<Integer, Integer>();
			int frequency = 3; 															// set number of times a question should repeat
			for (int i : questions) {
				quesFrequency.put(i, frequency); 
			}
			session.setAttribute("ques_frequency", quesFrequency);
			
			// Check achievements
			quizManager.practiceMakesPerfect(user_id);
		} else {
			quizManager.quizTakerAchievement(user_id);
		}
		
		// Check random order
		if (random_order) Collections.shuffle(questions);
		session.setAttribute("questions", questions);
		
		// Check multiple or single pages
		if (multiple_pages) {
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_multiple_page_view.jsp?question_num=0&score=0&practice_mode="+isPracticeMode+"&immediate_correction="+request.getParameter("immediate_correction")+"&random_order="+request.getParameter("random_order"));
			dispatch.forward(request, response); 
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_single_page_view.jsp?practice_mode="+isPracticeMode+"&random_order=" + request.getParameter("random_order"));
			dispatch.forward(request, response); 
		}
	}
}
