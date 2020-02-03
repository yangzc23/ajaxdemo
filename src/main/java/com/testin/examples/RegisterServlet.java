package com.testin.examples;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testin.utils.DBUtil;

@WebServlet("/save")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) {
		//设置响应内容的类型和编码
		resp.setContentType("application/json;charset=utf-8");
        try {
        	//解决请求参数内容包含中文会出现乱码的问题
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PrintWriter pw = null;
		int sno = 0;
		try {
			pw = resp.getWriter();	//获取输出工具
			//获取学号
			sno = Integer.parseInt(req.getParameter("sno"));
		}catch(Exception e) {
			pw.println("{\"result\":\"fail\",\"message\":\"学号必须是整数！\"}");
			e.printStackTrace();
			return;
		}
		//获取姓名
		String sname = req.getParameter("sname");
		//获取性别
		String gender = req.getParameter("gender").equals("1")?"男":"女";
		//获取生日
		String birth = req.getParameter("birth");
		System.out.println("生日是："+birth);
		if(birth == null || birth.equals("")) {
			birth = new Date(System.currentTimeMillis()).toString();
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			//连接数据库
			conn = DBUtil.getConnection();
			//获取预处理语句对象
			stmt = conn.prepareStatement("insert into student_yzc values(?,?,?,?)");
			stmt.setInt(1, sno);//设置第1个字段的内容
			stmt.setString(2, sname);//设置第2个字段的内容
			stmt.setString(3, gender);//设置第3个字段的内容
			stmt.setString(4, birth);//设置第4个字段的内容
			stmt.execute();//执行sql
			pw.println("{\"result\":\"success\",\"message\":\"登记成功！\"}");
		} catch (Exception e) {
			pw.println("{\"result\":\"fail\",\"message\":\"登记失败！\"}");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				//关闭数据库连接
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
