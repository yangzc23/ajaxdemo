package com.testin.examples;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testin.utils.DBUtil;

@WebServlet("/abc")
public class HelloServlet2 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) {
		PrintWriter pw = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		StringBuilder text = new StringBuilder();
		text.append("{\"result\":\"success\",\"message\":\"\",\"data\":[");
        //解决响应正文内容包含中文会出现乱码的问题
        resp.setContentType("application/json;charset=utf-8");
		try {
			pw = resp.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {	//
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM student_yzc");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				text.append("{\"sno\":"+rs.getInt(1)+","+
		                "\"sname\":\""+rs.getString(2)+"\""+",\"gender\":\""+rs.getString(3)+"\",\"birth\":"+
		                "\""+rs.getDate(4)+"\"},");
			}
            text = new StringBuilder(text.substring(0, text.length()-1));
            text.append("]}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println(text);
	}
}
