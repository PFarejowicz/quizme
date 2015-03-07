package hw6Quiz.web;

import hw6Quiz.manager.QuizManager;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizCreationServlet
 */
@WebServlet("/QuizCreationServlet")
public class QuizCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizCreationServlet() {
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
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		int user_id = (Integer) request.getSession().getAttribute("user id"); 
//		int user_id = 1;	//TODO: hardcoded
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		boolean random_order = request.getParameter("random_order").equals("yes");
		boolean multiple_pages = request.getParameter("multiple_pages").equals("yes");
		boolean immediate_correction = request.getParameter("immediate_correction").equals("yes");
		
		quizManager.addQuiz(name, description, user_id, random_order, multiple_pages, immediate_correction, 0);
		// TODO: check if quiz already exists
		request.setAttribute("quiz_id", Integer.toString(quizManager.getIDByName(name)));
		RequestDispatcher dispatch = request.getRequestDispatcher("add_question.jsp?points=0");
		dispatch.forward(request, response); 
	}
}
