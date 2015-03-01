package hw6Quiz;

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
		
		if (request.getParameter("previous") != null) {
			// TODO go back
		} else {
			int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
			String prompt = request.getParameter("prompt");
			String answer = request.getParameter("answer");
			int points = Integer.parseInt(request.getParameter("points"));
			if (request.getParameter("ques_type").equals("text_response")) {
				quesManager.addTextResponseQuestion(quiz_id, prompt, answer, points);
			} else if (request.getParameter("ques_type").equals("fill_blank")) {
				// TODO
			} else if (request.getParameter("ques_type").equals("multiple_choice")) {
				// TODO
			} else if (request.getParameter("ques_type").equals("picture")) {
				// TODO
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
