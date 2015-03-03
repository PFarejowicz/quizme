package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.model.QuestionResponse;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuestionCreationServlet
 */
@WebServlet("/QuestionCreationServlet")
public class QuestionCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionCreationServlet() {
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
		request.setAttribute("quiz_id", request.getParameter("quiz_id"));
		if (request.getParameter("previous") != null) {
			// TODO go back
		} else {
			int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
			int user_id = 1;			// TODO set default for testing
			if (request.getSession().getAttribute("user id") != null) {
				user_id = (Integer) request.getSession().getAttribute("user id");
			}
			String prompt = request.getParameter("prompt");
			String answer = request.getParameter("answer");
			QuestionResponse questionObj = new QuestionResponse(quiz_id, user_id, prompt, answer);
//			int points = Integer.parseInt(request.getParameter("points"));
			if (request.getParameter("ques_type").equals("question_response")) {
				quesManager.addQuestion(quiz_id, "QuestionResponse", questionObj);
			} else if (request.getParameter("ques_type").equals("fill_blank")) {
				quesManager.addQuestion(quiz_id, "FillInTheBlank", questionObj);
			} else if (request.getParameter("ques_type").equals("multiple_choice")) {
				quesManager.addQuestion(quiz_id, "MultipleChoice", questionObj);
			} else if (request.getParameter("ques_type").equals("picture")) {
				quesManager.addQuestion(quiz_id, "PictureResponse", questionObj);
			}
			
			if (request.getParameter("next") != null) {						// next question
				RequestDispatcher dispatch = request.getRequestDispatcher("add_question.jsp");
				dispatch.forward(request, response); 	
			} else if (request.getParameter("finish") != null) {			// finished questions
				RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
				dispatch.forward(request, response); 	
			}
		}
	}

}
