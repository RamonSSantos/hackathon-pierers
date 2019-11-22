package br.com.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DestroySession extends HttpServlet
{
	private static final long serialVersionUID=1L;
	
	public DestroySession()
	{
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String context = request.getServletContext().getContextPath();
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse.setDateHeader("Expires", 0);
	        
		session.invalidate();
		((HttpServletResponse)response).sendRedirect(context+"/index.html");
	}
}