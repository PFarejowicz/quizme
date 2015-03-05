package hw6Quiz.web;

import hw6Quiz.manager.FriendsManager;
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
 * Servlet implementation class FriendRequestServlet
 */
@WebServlet("/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequestServlet() {
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
		String decision = request.getParameter("requestSent");


		if (userManager.containsUser(userEmail) && userManager.containsUser(friendEmail)) {
			friendsManager.sendFriendRequest(userManager.getIDByEmail(userEmail), userManager.getIDByEmail(friendEmail));
			if (decision.equals("Request from Friend Page")) {
				RequestDispatcher dispatch = request.getRequestDispatcher("friend_homepage.jsp");
				dispatch.forward(request, response);
			} else if (decision.equals("Request from Search Page")) {
				RequestDispatcher dispatch = request.getRequestDispatcher("search_user.jsp");
				dispatch.forward(request, response);
			}
		}
				
	}

}
