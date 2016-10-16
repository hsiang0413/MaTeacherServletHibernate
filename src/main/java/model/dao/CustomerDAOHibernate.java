package model.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.CustomerBean;
import model.CustomerDAO;
import model.misc.HibernateUtil;

public class CustomerDAOHibernate implements CustomerDAO {
	public static void main(String[] args) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			
			CustomerDAOHibernate dao = new CustomerDAOHibernate(HibernateUtil.getSessionFactory());
			CustomerBean bean = dao.select("Alex");
			System.out.println(bean);
			
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();;
		} finally {
			HibernateUtil.closeSessionFactory();
		}
	}
	private SessionFactory sessionFactory = null;
	
	public CustomerDAOHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public CustomerBean select(String custid) {
		return (CustomerBean)
				this.getSession().get(CustomerBean.class, custid);
	}
	@Override
	public boolean update(byte[] password,
			String email, Date birth, String custid) {
		CustomerBean result = (CustomerBean)
				this.getSession().get(CustomerBean.class, custid);
		if(result!=null) {
			result.setPassword(password);
			return true;
		}
		return false;
	}

}
