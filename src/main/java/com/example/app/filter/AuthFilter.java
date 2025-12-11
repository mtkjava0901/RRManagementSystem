package com.example.app.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();

		// 管理者ページ制御
		if (uri.startsWith(contextPath + "/admin")) {
			if (!uri.equals(contextPath + "/admin/login")
					&& (session == null || session.getAttribute("admin") == null)) {

				res.sendRedirect(contextPath + "/admin/login");
				return;
			}
		}

		// ユーザーページ制御
		boolean userProtected = uri.equals(contextPath + "/") ||
				uri.startsWith(contextPath + "/book") ||
				uri.startsWith(contextPath + "/notif");

		if (userProtected) {
			if (!uri.equals(contextPath + "/login")
					&& (session == null || session.getAttribute("user") == null)) {

				res.sendRedirect(contextPath + "/login");
				return;
			}
		}

		// 認証済みなら次のFilter / Controllerへ
		chain.doFilter(request, response);
	}

}
