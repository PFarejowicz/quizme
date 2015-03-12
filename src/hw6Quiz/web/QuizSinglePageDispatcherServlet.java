package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;
import hw6Quiz.model.*;

import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
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
		if (request.getParameter("quit") != null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
			dispatch.forward(request, response); 
		} else {
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
			String timeTakenStr = diffMin + " minutes, " + diffSec + " seconds";
			
			ArrayList<Integer> questions = (ArrayList<Integer>) session.getAttribute("questions");
			HashMap<Integer, Integer> quesFrequency = null;
			boolean random_order = request.getParameter("random_order").equals("true");
			boolean isPracticeMode = request.getParameter("practice_mode").equals("true");
			boolean isQuizFinished = false; 
			int score = 0; 
			int question_number = 1;
			
			if (isPracticeMode) {
				quesFrequency = (HashMap<Integer, Integer>) request.getSession().getAttribute("ques_frequency");
			} else {
				isQuizFinished = true;
			}
			
			// Check answers
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
					int partials = 0;
					for (int i = 0; i < num_answers; i++) {
						if (question.getAnswerAsList().get(i).equals(request.getParameter("question_" + question_number + "_" + i).toLowerCase())) {
							score++;
							partials++;
						}
					}
					if (partials == num_answers && isPracticeMode) quesFrequency.put(question_id, quesFrequency.get(question_id) - 1);
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
				} else if (type.equals("MultiAnswer")) {
					MultiAnswer question = (MultiAnswer) questionManager.getQuestionByID(question_id);
					int num_answers = question.getNumAnswers();
					int partials = 0;
					boolean inOrder = question.getInOrder();
					for (int i = 0; i < num_answers; i++) {
						String user_input = request.getParameter("question_" + question_number + "_" + i).toLowerCase();
						if (inOrder && question.getAnswerAsList().get(i).equals(user_input)) {
							score++;
							partials++;
						} else if (!inOrder) {
							HashSet<String> answerList = new HashSet<String>(question.getAnswerAsList());
							if (answerList.contains(user_input)) {
								score++;
								partials++;
								answerList.remove(user_input);
							}
						}
					}
					if (partials == num_answers && isPracticeMode) quesFrequency.put(question_id, quesFrequency.get(question_id) - 1);
				} else if (type.equals("MultipleChoiceMultipleAnswers")) {
					MultipleChoiceMultipleAnswers question = (MultipleChoiceMultipleAnswers) questionManager.getQuestionByID(question_id);
					int num_answers = question.getNumAnswers();
					int partials = 0;
					for (int i = 0; i < num_answers; i++) {
						if (question.getAnswerAsList().get(i).equals(request.getParameter("question_" + question_number + "_" + question.getAnswerAsList().get(i)).toLowerCase())) {
							score++;
							partials++;
						}
					}
					if (partials == num_answers && isPracticeMode) quesFrequency.put(question_id, quesFrequency.get(question_id) - 1);
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
						i--;
					}
				}
				if (random_order) Collections.shuffle(questions);
				session.setAttribute("questions", questions);
				session.setAttribute("ques_frequency", quesFrequency);
				isQuizFinished = quesFrequency.isEmpty();					// check if all questions answered correctly 3 times
			} 
			
			if (isPracticeMode && isQuizFinished) {
				 RequestDispatcher dispatch = request.getRequestDispatcher("practice_finished.jsp");
				dispatch.forward(request, response);
			} else if (isQuizFinished) {
				RequestDispatcher dispatch = request.getRequestDispatcher("quiz_results.jsp?quiz_id="+quiz_id+"&score="+score+"&time_taken="+timeTakenStr);
				dispatch.forward(request, response);
			} else {
				RequestDispatcher dispatch = request.getRequestDispatcher("quiz_single_page_view.jsp?practice_mode="+isPracticeMode+"&random_order=" + request.getParameter("random_order"));
				dispatch.forward(request, response);
			}
		}
	}
}
