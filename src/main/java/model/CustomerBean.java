package model;

import java.util.Arrays;

import org.hibernate.Session;

import model.misc.HibernateUtil;

public class CustomerBean {
	private String custid;
	private byte[] password;
	private String email;
	private java.util.Date birth;
	public static void main(String[] args) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			
			Session session =
					HibernateUtil.getSessionFactory().getCurrentSession();
			
			CustomerBean select = (CustomerBean) session.load(CustomerBean.class, "Alex");
			System.out.println(select);
			
//			CustomerBean insert = new CustomerBean();
//			insert.setCustid("Xxx");
//			insert.setPassword("X".getBytes());
//			insert.setEmail("xxx@iii.org.tw");
//			insert.setBirth(new java.util.Date());
//			session.save(insert);
			
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
		} finally {
			HibernateUtil.closeSessionFactory();
		}
	}
	@Override
	public String toString() {
		return "model.CustomerBean ["+ custid+ ","+
				Arrays.toString(password)+ ","+ email+ ","+ birth+ "]";
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public java.util.Date getBirth() {
		return birth;
	}
	public void setBirth(java.util.Date birth) {
		this.birth = birth;
	}
}
