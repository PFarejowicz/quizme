package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;
import hw6Quiz.model.FillInTheBlank;
import hw6Quiz.model.MultipleChoice;
import hw6Quiz.model.PictureResponse;
import hw6Quiz.model.QuestionResponse;

import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
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
 * Servlet implementation class QuizGradeServlet
 */
@WebServlet("/QuizSinglePageDispatcherServlet")
public class QuizSinglePageDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizSinglePageDispatcherServlet() {
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
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuestionManager questionManager = (QuestionManager) getServletContext().getAttribute("question manager");
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		HttpSession session = request.getSession();
		
		// get time elapsed
		Date startTime = (Date) session.getAttribute("start_time");
		Calendar cal = Calendar.getInstance();
		Date endTime = new Date(cal.getTimeInMillis()); 
		long diff = endTime.getTime() - startTime.getTime();
		int diffMin = (int) (diff / (60 * 1000));
		int diffSec = (int) (diff / 1000);
		String timeElapsedStr = diffMin + " minutes, " + diffSec + " seconds";
		
		ArrayList<Integer> questions = (ArrayList<Integer>) request.getSession().getAttribute("questions");
		HashMap<Integer, Integer> quesFrequency = null;
		boolean random_order = request.getParameter("random_order").equals("true");
		boolean isPracticeMode = request.getParameter("practice_mode").equals("true");
		System.out.println("single servlet " + isPracticeMode);
		boolean isQuizFinished = false; 
		int score = 0; 
		int question_number = 1;
		
		if (isPracticeMode) {
			quesFrequency = (HashMap<Integer, Integer>) request.getSession().getAttribute("ques_frequency");
			System.out.println(quesFrequency);
			if (random_order) Collections.shuffle(questions);
		} else {
			isQuizFinished = true;
		}
		
		for (int question_id : questions) {
			String type = questionManager.getTypeByID(question_id);
			if (type.equals("QuestionResponse")) {
				QuestionResponse question = (QuestionResponse) questionManager.getQuestionByID(question_id);
				if (question.getAnswerText().equals(request.getParameter("question_" + question_number).toLowerCase())) {
					score++;
					if (isPracticeMode) quesFrequency.put(question_id, quesFrequency.get(question_id) - 1);
				}
			} else if (type.equals("FillInTheBlank")) {
				FillInTheBlank question = (FillInTheBlank) questionManager.getQuestionByID(question_id);
				int num_answers = question.getNumBlanks();
				for (int i = 0; i < num_answers; i++) {
					if (question.getAnswerText().get(i).equals(request.getParameter("question_" + question_number + "_" + i).toLowerCase())) {
						score++;
						if (isPracticeMode) quesFrequency.put(question_id, quesFrequency.get(question_id) - 1);
					}
				}
			} else if (type.equals("MultipleChoice")) {
				MultipleChoice question = (MultipleChoice) questionManager.getQuestionByID(question_id);
				if (question.getAnswerText().equals(request.getParameter("question_" + question_number).toLowerCase())) {
					score++;
					if (isPracticeMode) quesFrequency.put(question_id, quesFrequency.get(question_id) - 1);
				}
			} else if (type.equals("PictureResponse")) {
				PictureResponse question = (PictureResponse) questionManager.getQuestionByID(question_id);
				if (question.getAnswerText().equals(request.getParameter("question_" + question_number).toLowerCase())) {
					score++;
					if (isPracticeMode) quesFrequency.put(question_id, quesFrequency.get(question_id) - 1);
				}
			}
			question_number++;
		}
		
		// Delete questions if answered correctly 3 times
		if (isPracticeMode) {
			for (int i = 0; i < questions.size(); i++) {
				int question_id = questions.get(i);
				if (quesFrequency.get(question_id) == 0) {
					quesFrequency.remove(question_id);
					questions.remove(i);
				}
			}
			request.getSession().setAttribute("questions", questions);
			isQuizFinished = quesFrequency.isEmpty();					// check if all questions answered correctly 3 times
		} 
		
		if (isPracticeMode && isQuizFinished) {
			RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
			// RequestDispatcher dispatch = request.getRequestDispatcher("practice_finished.jsp"); TODO
			dispatch.forward(request, response);
		} else if (isQuizFinished) {
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_results.jsp?quiz_id="+quiz_id+"&score="+score+"&time_elapsed="+timeElapsedStr);
			dispatch.forward(request, response);
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_single_page_view.jsp?practice_mode="+isPracticeMode+"&random_order=" + request.getParameter("random_order"));
			dispatch.forward(request, response);
		}
	}
}
