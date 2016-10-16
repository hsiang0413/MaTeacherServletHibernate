package model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.ProductBean;
import model.ProductDAO;
import model.misc.HibernateUtil;

public class ProductDAOHibernate implements ProductDAO {
	private SessionFactory sessionFactory = null;
		
	public ProductDAOHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public static void main(String[] args) {
		try {
			HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
					
			ProductDAOHibernate dao = 
					new ProductDAOHibernate(HibernateUtil.getSessionFactory());
			ProductBean bean = dao.select(1);
			System.out.println(bean);
			
			HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
		} finally {
			HibernateUtil.closeSessionFactory();
		}
	}	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public ProductBean select(int id) {
		return (ProductBean)
				this.getSession().get(ProductBean.class, id);
	}
	@Override
	public List<ProductBean> select() {
		Query query =
				this.getSession().createQuery("from ProductBean");
		return (List<ProductBean>) query.list();
	}
	@Override
	public ProductBean insert(ProductBean bean) {
		ProductBean result = (ProductBean)
				this.getSession().get(ProductBean.class, bean.getId());
		if(result==null) {
			this.getSession().save(bean);
			return bean;
		}
		return null;
	}
	@Override
	public ProductBean update(String name,
			double price, Date make, int expire, int id) {
		ProductBean result = (ProductBean)
				this.getSession().get(ProductBean.class, id);
		if(result!=null) {
			result.setName(name);
			result.setPrice(price);
			result.setMake(make);
			result.setExpire(expire);
		}
		return result;
	}
	@Override
	public boolean delete(int id) {
		ProductBean bean = (ProductBean) this.getSession().get(ProductBean.class, id);
		if(bean!=null) {
			this.getSession().delete(bean);
			return true;
		}
		return false;
	}
}
