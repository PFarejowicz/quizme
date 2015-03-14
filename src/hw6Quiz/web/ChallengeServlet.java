package hw6Quiz.web;

import hw6Quiz.manager.FriendsManager;
import hw6Quiz.manager.MessageManager;
import hw6Quiz.manager.UserManager;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ChallengeServlet
 */
@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		
		MessageManager messageManager = (MessageManager) context.getAttribute("message manager");
		UserManager userManager = (UserManager) context.getAttribute("user manager");
		int sender_id = Integer.parseInt(request.getParameter("sender_id"));
		int receiver_id = Integer.parseInt(request.getParameter("receiver_id"));
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		int score = Integer.parseInt(request.getParameter("score"));
		String time_taken = request.getParameter("time_taken");
		String decision = request.getParameter("decision");
		
		if (decision.equals("Accept Challenge")) {
			messageManager.deleteChallenge(sender_id, receiver_id, quiz_id, score);
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_summary.jsp");
			dispatch.forward(request, response);
		} else if (decision.equals("Reject Challenge")) {
			messageManager.deleteChallenge(sender_id, receiver_id, quiz_id, score);
			RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
			dispatch.forward(request, response);
		} else if (decision.equals("Send Challenge")) {
			messageManager.sendChallenge(sender_id, receiver_id, quiz_id, score);
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz_results.jsp?time_taken="+time_taken);
			dispatch.forward(request, response);
		}
	}

}
