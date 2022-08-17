package com.anhnbt;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Application Lifecycle Listener implementation class ServletRequestListener
 *
 */
@WebListener
public class ServletRequestListener implements javax.servlet.ServletRequestListener {

	/**
	 * Default constructor.
	 */
	public ServletRequestListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent arg0) {
		HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();
		System.out.println(request.getRequestURI());
		System.out.println("ServletRequest destroyed. Remote IP=" + request.getRemoteAddr());
	}

	/**
	 * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent arg0) {
		HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();
		System.out.println(request.getRequestURI());
		System.out.println("ServletRequest initialized. Remote IP=" + request.getRemoteAddr());
	}

}
