package com.testin.examples;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/get/lastvisittime")
public class LastVisitTimeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) {
		//
		resp.setContentType("application/json;charset=utf-8");
		PrintWriter pw = null;
		//
		try {
			pw = resp.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取浏览器发送过来的cookie
		Cookie[] cookies = req.getCookies();
		String lastVisitTime = null;
		if(cookies!=null) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("last_visit_time")) {
					//获取上一次访问的时间
					lastVisitTime = cookie.getValue();
					break;
				}
			}
		}

		//获取这次的访问时间
		Date now = new Date();
		//保存这次的访问时间（再次刷新页面的时候，就会显示这次的访问时间）
		Cookie cookie = new Cookie("last_visit_time",now.toString());
		resp.addCookie(cookie);
		
		if(lastVisitTime==null) {//如果用户是第一次访问的话
			pw.println("{\"result\":\"fail\",\"message\":\"获取上一次访问时间失败！\"}");
		}else {
			pw.println("{\"result\":\"success\","
					+ "\"message\":\"获取上一次访问时间成功！\","
					+ "\"time\":\""+lastVisitTime+"\"}");
		}
	}

}
