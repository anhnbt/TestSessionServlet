package com.anhnbt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.anhnbt.dao.StatisticsCounter;
import com.anhnbt.dao.StatisticsCounterImpl;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StatisticsCounter statisticsCounter;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeServlet() {
		super();
		statisticsCounter = new StatisticsCounterImpl();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		if (session.getAttribute("SITE_NAME") == null) {
			session.setAttribute("SITE_NAME", "TONG_CUC");
			statisticsCounter.update("TONG_CUC");
		}
		if (session.getAttribute("counter") != null) {
			System.out.println("SITE_NAME>>>>>>>>>>>>>>>>>>>>>>>>>>" + session.getAttribute("SITE_NAME"));
			SessionCounter sessionCounter = (SessionCounter) session.getAttribute("counter");
			response.getWriter().append("\nNumber of online user(s): " + sessionCounter.getActiveSessionNumber("TONG_CUC"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
