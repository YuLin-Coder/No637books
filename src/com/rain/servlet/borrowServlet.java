package com.rain.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rain.bean.AdminBean;
import com.rain.bean.BookBean;
import com.rain.dao.AdminDao;
import com.rain.dao.BookDao;
import com.rain.util.CommonUtil;

/**
 * Servlet implementation class borrowServlet
 */
@WebServlet("/borrowServlet")
public class borrowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public borrowServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		// 设置编码类型
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		BookDao bookdao = new BookDao();
		AdminDao adminDao = new AdminDao();
		// 为了区分借书和还书的功能，设置tip，tip为1，表示借书
		int tip = Integer.parseInt(request.getParameter("tip"));
		if (tip == 1) {
			// 获取图书id
			int bid = Integer.parseInt(request.getParameter("bid"));
			int show = Integer.parseInt(request.getParameter("show"));
			float borrowPrice = Float.parseFloat(request.getParameter("borrowPrice"));

			HttpSession session = request.getSession();
			AdminBean admin = new AdminBean();
			// 获取到存入session的aid读者id
			String aid = (String) session.getAttribute("aid");
			AdminDao admindao = new AdminDao();
			// 通过aid获取到读者的信息
			admin = admindao.get_AidInfo2(aid);

			// 将借阅记录存入数据表
			bookdao.borrowBook(bid, admin);

			// 判断是否为VIP,如果是VIP 享受借阅图书8折优惠
			if (admin.getIsVIP() == 1) {
				// 扣除相应的读者的余额
				boolean a = admindao.subBalance(aid, (float) (borrowPrice * 0.8));
			} else {
				boolean a = admindao.subBalance(aid, borrowPrice);
			}

			// 将图书状态修改为不可借阅
			bookdao.updateState(0, bid);

			if (show == 1) {
				response.sendRedirect("/books/select.jsp");
			} else {
				response.sendRedirect("/books/bdtimes.jsp");
			}
		} else {
			// 还书功能，获取借阅记录的hid
			int hid = Integer.parseInt(request.getParameter("hid"));
			String card = request.getParameter("card");
			int bid = Integer.parseInt(request.getParameter("bid"));
			int aid = Integer.parseInt(request.getParameter("aid"));
			/**
			 * 还书在管理员和读者界面都有，为了区分，设置了show字段，show为1表示读者界面
			 */
			int show = Integer.parseInt(request.getParameter("show"));
			// 调用还书函数，改变status字段
			bookdao.borrowBook2(hid);

			// 修改该图书状态为可借阅
			boolean a = bookdao.updateStateByCard(1, card);

			// 判断还书时间与当前时间；如果当前时间大于还书时间，要交违约金

			String endTime = request.getParameter("endTime");// 截止时间日期

			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			String dateString = formatter.format(currentTime); // 还书当天日期

			Double num = 0.0;

			try {
				num = CommonUtil.differentDays(endTime, dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(num);
			// 逾期日期
			if (num > 0) {
				BookBean book = bookdao.get_BookInfo(bid);
				AdminBean admin = adminDao.get_AidInfo(aid);

				// 逾期金额
				double yuqi = num * book.getOverduePrice();
				admin.setBalance(admin.getBalance() - yuqi);
				// 扣除逾期金额
				adminDao.updateUser(aid, admin.getUsername(), admin.getPassword(), admin.getName(), admin.getEmail(),
						admin.getPhone(), admin.getLend_num(), admin.getMax_num(), admin.getBalance(),
						admin.getIsVIP());

				// CommonUtil.showAlertMessage(response, "您已产生逾期" + num + "天，逾期费用为：" + yuqi +
				// "元");

				// 修改借阅信息
				bookdao.updateYuQi(hid, num, yuqi);

			}

			if (show == 1) {
				response.sendRedirect("/books/borrow.jsp");
			} else {
				response.sendRedirect("/books/admin_borrow.jsp");
			}

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
