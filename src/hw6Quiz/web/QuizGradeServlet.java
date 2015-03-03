package hw6Quiz.web;

import hw6Quiz.manager.QuestionManager;
import hw6Quiz.manager.QuizManager;
import hw6Quiz.model.Quiz;

import java.io.IOException;

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
		QuizManager quizManager = (QuizManager) getServletContext().getAttribute("quiz manager");
		QuestionManager questionManager = (QuestionManager) getServletContext().getAttribute("question manager");
		int quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		Quiz quiz = quizManager.getQuiz(quiz_id);
		
		ArrayList<Integer> questions = (ArrayList<Integer>) session.getAttribute("questions");
		int question_number = 1;
		for (int question_id : questions) {
			String type = questionManager.getTypeByID(question_id);
			if (type.equals("QuestionResponse")) {
				QuestionResponse question = (QuestionResponse) questionManager.getQuestionByID(question_id);
				out.println("<p>" + question_number + ".) " + question.getQuestionText() + "</p>");
				out.println("<input type=\"text\" name=\"question_" + question_number + "\"/>");
			} else if (type.equals("FillInTheBlank")) {
				FillInTheBlank question = (FillInTheBlank) questionManager.getQuestionByID(question_id);
				out.println("<p>" + question_number + ".) " + question.getQuestionText() + "</p>");
				int num_answers = question.getNumBlanks();
				for (int i = 0; i < num_answers; i++) {
					out.println("<input type=\"text\" name=\"question_" + question_number + "_" + num_answers+ "\"/>");
				}
			} else if (type.equals("MultipleChoice")) {
				MultipleChoice question = (MultipleChoice) questionManager.getQuestionByID(question_id);
				out.println("<p>" + question_number + ".) " + question.getQuestionText() + "</p>");
				ArrayList<String> choices = question.getChoicesText();
				for (String choice : choices) {
					out.println("<input type=\"radio\" name=\"question_" + question_number + "\" value=\"" + choice + "\">" + choice + "<br>");
				}
			} else if (type.equals("PictureResponse")) {
				PictureResponse question = (PictureResponse) questionManager.getQuestionByID(question_id);
				out.println("<p>" + question_number + ".) " + "</p>");
				out.println("<img src=" + question.getImageURL() + "/>");
				out.println("<input type=\"text\" name=\"question_" + question_number + "\"/>");
			}
			question_number++;
		}
	}

}
