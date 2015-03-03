package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;
import hw6Quiz.model.FillInTheBlank;
import hw6Quiz.model.MultipleChoice;
import hw6Quiz.model.PictureResponse;
import hw6Quiz.model.QuestionResponse;
import hw6Quiz.model.Quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizGradeServlet
 */
@WebServlet("/QuizGradeServlet")
public class QuizGradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizGradeServlet() {
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
		int score = 0; 
		int question_number = 1;
		for (int question_id : questions) {
			String type = questionManager.getTypeByID(question_id);
			if (type.equals("QuestionResponse")) {
				QuestionResponse question = (QuestionResponse) questionManager.getQuestionByID(question_id);
				if (question.getAnswerText().equals(request.getParameter("question_" + question_number))) {
					score++;
				}
			} else if (type.equals("FillInTheBlank")) {
				FillInTheBlank question = (FillInTheBlank) questionManager.getQuestionByID(question_id);
				if (question.getAnswerText().equals(request.getParameter("question_" + question_number))) {
					score++;
				}
			} else if (type.equals("MultipleChoice")) {
				MultipleChoice question = (MultipleChoice) questionManager.getQuestionByID(question_id);
				if (question.getAnswerText().equals(request.getParameter("question_" + question_number))) {
					score++;
				}
			} else if (type.equals("PictureResponse")) {
				PictureResponse question = (PictureResponse) questionManager.getQuestionByID(question_id);
				if (question.getAnswerText().equals(request.getParameter("question_" + question_number))) {
					score++;
				}
			}
			question_number++;
		}
		quizManager.addQuizResult(quiz_id, (Integer) request.getSession().getAttribute("user id"), score);
		request.setAttribute("score", Integer.toString(score));
		request.setAttribute("quiz_id", Integer.toString(quiz_id));
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz_results.jsp");
		dispatch.forward(request, response); 
	}
}