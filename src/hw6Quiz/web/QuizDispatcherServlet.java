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
import java.util.Date;

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
public class QuizDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizDispatcherServlet() {
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
		QuestionManager questionManager = (QuestionManager) getServletContext().getAttribute("question manager");
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		ArrayList<Integer> questions = (ArrayList<Integer>) request.getSession().getAttribute("questions");
		int score = Integer.parseInt(request.getParameter("score")); 
		int question_number = Integer.parseInt(request.getParameter("question_num"));
		int question_id = questions.get(question_number-1);
		
		String timeElapsedStr = "";
		if (question_number >= questions.size()) {
			HttpSession session = request.getSession();
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
			if (question.getAnswerText().equals(request.getParameter("question_" + question_number))) {
				correct_answer = "true";
				score++;
			}
		} else if (type.equals("FillInTheBlank")) {
			FillInTheBlank question = (FillInTheBlank) questionManager.getQuestionByID(question_id);
			int num_answers = question.getNumBlanks();
			int partials = 0;
			for (int i = 0; i < num_answers; i++) {
				if (question.getAnswerText().get(i).equals(request.getParameter("question_" + question_number + "_" + i))) {
					score++;
					partials++;
				}
			}
			if (partials == num_answers) correct_answer = "true";
		} else if (type.equals("MultipleChoice")) {
			MultipleChoice question = (MultipleChoice) questionManager.getQuestionByID(question_id);
			if (question.getAnswerText().equals(request.getParameter("question_" + question_number))) {
				correct_answer = "true";
				score++;
			}
		} else if (type.equals("PictureResponse")) {
			PictureResponse question = (PictureResponse) questionManager.getQuestionByID(question_id);
			if (question.getAnswerText().equals(request.getParameter("question_" + question_number))) {
				correct_answer = "true";
				score++;
			}
		}
		if (request.getParameter("immediate_correction").equals("true")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_immediate_feedback.jsp?correct_answer="+correct_answer+"question_num="+question_number+"&sentinel="+questions.size()+"&score="+score+"&immediate_correction="+request.getParameter("immediate_correction")+"&time_elapsed="+timeElapsedStr);
			dispatch.forward(request, response);
		} else {
			if (question_number >= questions.size()) {
				quizManager.addQuizResult(quiz_id, (Integer) request.getSession().getAttribute("user id"), score);
				RequestDispatcher dispatch = request.getRequestDispatcher("quiz_results.jsp?score="+score+"&time_elapsed="+timeElapsedStr);
				dispatch.forward(request, response);
			} else {
				RequestDispatcher dispatch = request.getRequestDispatcher("quiz_multiple_page_view.jsp?question_num="+question_number+"&score="+score+"&immediate_correction="+request.getParameter("immediate_correction"));
				dispatch.forward(request, response);
			}
		}
	}

}
