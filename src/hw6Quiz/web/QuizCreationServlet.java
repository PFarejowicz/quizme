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
		
		// Set parameters for quiz and add to database
		String name = "";																			// defaults it to blank
		name = request.getParameter("name");
		if (name.equals("")) name = "Untitled";
		String description = "";
		description = request.getParameter("description");
		String category = request.getParameter("category");
		String tags = request.getParameter("tags");
		boolean random_order = request.getParameter("random_order").equals("yes");
		boolean multiple_pages = request.getParameter("multiple_pages").equals("yes");
		boolean immediate_correction = request.getParameter("immediate_correction").equals("yes");
		boolean edit_mode = request.getParameter("edit_mode").equals("true");
		
		if (edit_mode) {
			int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
			quizManager.updateQuiz(name, description, category, tags, random_order, multiple_pages, immediate_correction, quiz_id);
			
			RequestDispatcher dispatch = request.getRequestDispatcher("edit_question.jsp?quiz_id="+quiz_id);
			dispatch.forward(request, response);
		} else {
			quizManager.addQuiz(name, description, user_id, category, tags, random_order, multiple_pages, immediate_correction, 0);
			
			// Check achievements
			quizManager.authorAchievement(user_id);
			
			int quiz_id = quizManager.getIDByName(name);
			RequestDispatcher dispatch = request.getRequestDispatcher("add_question.jsp?quiz_id="+quiz_id+"&points=0&edit_mode=false");
			dispatch.forward(request, response);
		}
	}
}
