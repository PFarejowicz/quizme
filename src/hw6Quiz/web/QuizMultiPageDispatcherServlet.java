package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;
import hw6Quiz.model.FillInTheBlank;
import hw6Quiz.model.MultipleChoice;
import hw6Quiz.model.PictureResponse;
import hw6Quiz.model.QuestionResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuizDispatcherServlet
 */
@WebServlet("/QuizDispatcherServlet")
public class QuizMultiPageDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizMultiPageDispatcherServlet() {
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
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		HttpSession session = request.getSession();
		if (request.getSession() == null) {									// check null session
			throw new NullPointerException();
		} 
		ArrayList<Integer> questions = (ArrayList<Integer>) request.getSession().getAttribute("questions");
		int score = Integer.parseInt(request.getParameter("score")); 
		int question_number = Integer.parseInt(request.getParameter("question_num"));
		int question_id = questions.get(question_number - 1);
		
		boolean isPracticeMode = request.getParameter("practice_mode").equals("true");
		boolean random_order = request.getParameter("random_order").equals("true");
		boolean isQuizFinished = false; 
		HashMap<Integer, Integer> quesFrequency = new HashMap<Integer, Integer>(); 
		if (isPracticeMode) {
			quesFrequency = (HashMap<Integer, Integer>) session.getAttribute("ques_frequency");
		} else {
			isQuizFinished = (question_number >= questions.size());
		}
		
		String timeElapsedStr = "";
		if (isQuizFinished) {
			Date startTime = (Date) session.getAttribute("start_time");
			Calendar cal = Calendar.getInstance();
			Date endTime = new Date(cal.getTimeInMillis()); 
			long diff = endTime.getTime() - startTime.getTime();
			int diffMin = (int) (diff / (60 * 1000));
			int diffSec = (int) (diff / 1000);
			timeElapsedStr = diffMin + " minutes, " + diffSec + " seconds";
		}
		
		String type = questionManager.getTypeByID(question_id);
		String correct_answer = "false";
		if (type.equals("QuestionResponse")) {
			QuestionResponse question = (QuestionResponse) questionManager.getQuestionByID(question_id);
			if (question.getAnswerText().equals(request.getParameter("question_" + question_number).toLowerCase())) {
				correct_answer = "true";
				score++;
			}
		} else if (type.equals("FillInTheBlank")) {
			FillInTheBlank question = (FillInTheBlank) questionManager.getQuestionByID(question_id);
			int num_answers = question.getNumBlanks();
			int partials = 0;
			for (int i = 0; i < num_answers; i++) {
				if (question.getAnswerText().get(i).equals(request.getParameter("question_" + question_number + "_" + i).toLowerCase())) {
					score++;
					partials++;
				}
			}
			if (partials == num_answers) correct_answer = "true";
		} else if (type.equals("MultipleChoice")) {
			MultipleChoice question = (MultipleChoice) questionManager.getQuestionByID(question_id);
			if (question.getAnswerText().equals(request.getParameter("question_" + question_number).toLowerCase())) {
				correct_answer = "true";
				score++;
			}
		} else if (type.equals("PictureResponse")) {
			PictureResponse question = (PictureResponse) questionManager.getQuestionByID(question_id);
			if (question.getAnswerText().equals(request.getParameter("question_" + question_number).toLowerCase())) {
				correct_answer = "true";
				score++;
			}
		}
		
		if (isPracticeMode) {
			if (correct_answer.equals("true")) {							// decrease question frequency for practice mode
				int newFreq = quesFrequency.get(question_id) - 1;
				if (newFreq == 0) {
					quesFrequency.remove(question_id);
					questions.remove(questions.indexOf(question_id));
				} else {
					quesFrequency.put(question_id, newFreq);
				}
				isQuizFinished = quesFrequency.isEmpty();					// check if all questions answered correctly 3 times
			}
			if (question_number >= questions.size()) {						// restart after going through all the questions
				if (random_order) Collections.shuffle(questions);
				question_number = 0;
			} 
		}
		
		if (request.getParameter("immediate_correction").equals("true")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_immediate_feedback.jsp?correct_answer="+correct_answer+"&question_num="+question_number+"&is_quiz_finished="+isQuizFinished+"&score="+score+"&immediate_correction=true&time_elapsed="+timeElapsedStr);
			dispatch.forward(request, response);
		} else {
			if (isQuizFinished && isPracticeMode) {
				RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
				// RequestDispatcher dispatch = request.getRequestDispatcher("practice_finished.jsp"); TODO
				dispatch.forward(request, response);
			} else if (isQuizFinished) {
				RequestDispatcher dispatch = request.getRequestDispatcher("quiz_results.jsp?score="+score+"&time_elapsed="+timeElapsedStr);
				dispatch.forward(request, response);
			} else {
				RequestDispatcher dispatch = request.getRequestDispatcher("quiz_multiple_page_view.jsp?practice_mode="+isPracticeMode+"&question_num="+question_number+"&score="+score+"&quiz_id="+quiz_id+"&random_order="+random_order+"immediate_correction="+request.getParameter("immediate_correction"));
				dispatch.forward(request, response);
			}
		}
	}
}