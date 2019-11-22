package br.com.session;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FilterConnection implements Filter
{
	public void init(FilterConfig config) throws ServletException
	{
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException
	{
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse.setDateHeader("Expires", 0);
		
		String context = request.getServletContext().getContextPath();
		
		try
		{
			HttpSession session = ((HttpServletRequest)request).getSession();
			String username = null;
			
			if(session != null)
			{
				username = (String)session.getAttribute("email");
			}
			
			if(username == null)
			{
				session.setAttribute("msg", "Voce nao está logado no sistema!");
				((HttpServletResponse)response).sendRedirect(context+"/index.html");
				
			} else
			{
				chain.doFilter(request, response);
			}
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void destroy()
	{
		
	}
}