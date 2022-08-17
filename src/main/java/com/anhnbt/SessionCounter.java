package com.anhnbt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.anhnbt.dao.StatisticsCounter;
import com.anhnbt.dao.StatisticsCounterImpl;

/**
 * Application Lifecycle Listener implementation class SessionCounter
 *
 */
@WebListener
public class SessionCounter implements HttpSessionListener {

	private Map<String, List<String>> sessions;
	private StatisticsCounter statisticsCounter;

	public SessionCounter() {
		System.out.println("SessionCounter Created");
		statisticsCounter = new StatisticsCounterImpl();
		sessions = new HashMap<>();
		sessions.put("TONG_CUC", new ArrayList<>());
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		System.out.println("SessionCounter sessionCreated");
		sessions.get("TONG_CUC").add(session.getId());
		session.setAttribute("counter", this);

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		sessions.get("TONG_CUC").remove(session.getId());
		session.setAttribute("counter", this);
		System.out.println("SessionCounter sessionDestroyed");

	}

	public int getActiveSessionNumber(String siteName) {
		return sessions.get(siteName).size();
	}

}
