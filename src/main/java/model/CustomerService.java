package model;

import java.util.Arrays;

import model.dao.CustomerDAOHibernate;
import model.misc.HibernateUtil;

public class CustomerService {
	private CustomerDAO customerDao;
	
	public CustomerService(CustomerDAO dao) {
		this.customerDao = dao;
	}
	public static void main(String[] args) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

			CustomerDAO dao = new CustomerDAOHibernate(HibernateUtil.getSessionFactory());
			CustomerService customerService = new CustomerService(dao);
			CustomerBean bean = customerService.login("Babe", "B");
			System.out.println("bean="+bean);
			
			customerService.changPassword("Ellen", "E", "ABC");
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
		} finally {
			HibernateUtil.closeSessionFactory();
		}
	}
	public boolean changPassword(
			String username, String oldPassword, String newPassword) {
		CustomerBean bean = this.login(username, oldPassword);
		if(bean!=null) {
			byte[] temp = newPassword.getBytes();	//�ϥΪ̿�J
			return customerDao.update(
					temp, bean.getEmail(), bean.getBirth(), username);
		}
		return false;
	}
	public CustomerBean login(String username, String password) {
		CustomerBean bean = customerDao.select(username);
		if(bean!=null) {
			if(password!=null && password.length()!=0) {
				byte[] pass = bean.getPassword();	//��Ʈw��X
				byte[] temp = password.getBytes();	//�ϥΪ̿�J
				if(Arrays.equals(pass, temp)) {
					return bean;
				}
			}
		}
		return null;
	}
}
