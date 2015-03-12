package hw6Quiz.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuestionCreationTypeServlet
 */
@WebServlet("/SelectQuestionTypeServlet")
public class SelectQuestionTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectQuestionTypeServlet() {
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
		String ques_type = request.getParameter("ques_type");
		request.setAttribute("quiz_id", request.getParameter("quiz_id"));
		if (ques_type.equals("text_response")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("create_question_response_question.jsp?points="+request.getParameter("points"));
			dispatch.forward(request, response); 
		} else if (ques_type.equals("fill_blank")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("create_fill_blank_question.jsp?points="+request.getParameter("points"));
			dispatch.forward(request, response); 
		} else if (ques_type.equals("multiple_choice")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("create_multiple_choice_question.jsp?points="+request.getParameter("points"));
			dispatch.forward(request, response); 
		} else if (ques_type.equals("picture")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("create_picture_question.jsp?points="+request.getParameter("points"));
			dispatch.forward(request, response); 
		} else if (ques_type.equals("multianswer")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("create_multianswer_question.jsp?points="+request.getParameter("points"));
			dispatch.forward(request, response); 
		} else if (ques_type.equals("multiple_choice_multiple_answers")) {
			RequestDispatcher dispatch = request.getRequestDispatcher("create_multiple_choice_multiple_answers.jsp?points="+request.getParameter("points"));
			dispatch.forward(request, response); 
		}
	}
}
