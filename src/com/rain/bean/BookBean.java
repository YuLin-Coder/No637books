package com.rain.bean;

public class BookBean {
	/**
	 * 图书的数据表的bean
	 */
	private int bid;// id
	private String name;// 图书名称
	private String card;// 图书号
	private String autho;// 作者
	private String type;// 图书的分类
	private String press;// 出版社
	private int times;// 借阅次数
	private float bookPrice;// 图书价格
	private float borrowPrice;// 借阅价格
	private int state;// 状态 1可借阅 0已借阅
	private float overduePrice;// 逾期金额/天

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public float getOverduePrice() {
		return overduePrice;
	}

	public void setOverduePrice(float overduePrice) {
		this.overduePrice = overduePrice;
	}

	public String getAutho() {
		return autho;
	}

	public void setAutho(String autho) {
		this.autho = autho;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}

	public float getBorrowPrice() {
		return borrowPrice;
	}

	public void setBorrowPrice(float borrowPrice) {
		this.borrowPrice = borrowPrice;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
