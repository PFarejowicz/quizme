package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;
import hw6Quiz.model.FillInTheBlank;
import hw6Quiz.model.MultipleChoice;
import hw6Quiz.model.PictureResponse;
import hw6Quiz.model.QuestionResponse;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuestionEditServlet
 */
@WebServlet("/QuestionEditServlet")
public class QuestionEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionEditServlet() {
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
		QuestionManager quesManager = (QuestionManager) getServletContext().getAttribute("question manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int question_id = Integer.parseInt(request.getParameter("question_id"));
		int user_id = 1;			// TODO set default for testing
		if (request.getSession().getAttribute("user id") != null) {
			user_id = (Integer) request.getSession().getAttribute("user id");
		}
		String prompt = request.getParameter("prompt");
		String answer = request.getParameter("answer");
		if (request.getParameter("ques_type").equals("question_response")) {
			QuestionResponse questionObj = new QuestionResponse(quiz_id, user_id, prompt, answer);
			quesManager.updateQuestion(question_id, questionObj);
		} else if (request.getParameter("ques_type").equals("fill_blank")) {
			FillInTheBlank questionObj = new FillInTheBlank(quiz_id, user_id, prompt, answer);
			quesManager.updateQuestion(question_id, questionObj);
			int point_diff = questionObj.getNumBlanks() - Integer.parseInt("prev_points");
			if (point_diff != 0) {
				QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
				quizManager.updateQuizPoints(quiz_id, quizManager.getQuizPoints(quiz_id)+point_diff);
			}
		} else if (request.getParameter("ques_type").equals("multiple_choice")) {
			String choices = request.getParameter("choices");
			MultipleChoice questionObj = new MultipleChoice(quiz_id, user_id, prompt, choices, answer);
			quesManager.updateQuestion(question_id, questionObj);
		} else if (request.getParameter("ques_type").equals("picture")) {
			PictureResponse questionObj = new PictureResponse(quiz_id, user_id, prompt, answer);
			quesManager.updateQuestion(question_id, questionObj);
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("edit_question.jsp?quiz_id="+quiz_id);
		dispatch.forward(request, response); 	
	}

}
