package hw6Quiz.web;

import hw6Quiz.manager.*;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FriendServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendServlet() {
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
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		
		FriendsManager friendsManager = (FriendsManager) context.getAttribute("friends manager");
		UserManager userManager = (UserManager) context.getAttribute("user manager");
		String userEmail = (String) request.getSession().getAttribute("email");
		String friendEmail = request.getParameter("friendEmail");
		String decision = request.getParameter("decision");
		
		if (userManager.containsUser(userEmail) && userManager.containsUser(friendEmail)) {
			if (decision.equals("Accept")) {
	 			friendsManager.addFriend(userEmail, friendEmail);
				friendsManager.addFriend(friendEmail, userEmail);
				friendsManager.deleteFriendRequest(userEmail, friendEmail);
			} else if (decision.equals("Reject")) {
				friendsManager.deleteFriendRequest(userEmail, friendEmail);
			}
		}
	}
}
