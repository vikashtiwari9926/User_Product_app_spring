package org.jsp.userproduct.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.jsp.userproduct.dto.Product;
import org.jsp.userproduct.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

	@Autowired
	EntityManager manager;
	
	public Product saveProduct(Product p,int u_id) {
		User u=manager.find(User.class, u_id);
		if(u!=null) {
			u.getProducts().add(p);
			p.setUsers(u);
			manager.persist(p);
			EntityTransaction transaction=manager.getTransaction();
			transaction.begin();
			transaction.commit();
			return p;
		}
		return null;
	}
	
	public Product updateProduct(Product p,int u_id) {
		User u=manager.find(User.class, u_id);
		if(u!=null) {
			u.getProducts().add(p);
			p.setUsers(u);
			manager.merge(p);
			EntityTransaction transaction=manager.getTransaction();
			transaction.begin();
			transaction.commit();
			return p;
		}
		return null;
	}
	
	public List<Product> findProductByUserId(int id){
		Query q=manager.createQuery("select u.products from User u where u.id=?1");
		q.setParameter(1, id);
		return q.getResultList();
	}
	
	public void deleteProduct(int u_id,int p_id) {
		User u=manager.find(User.class, u_id);
		if(u!=null) {
			Product p=manager.find(Product.class, p_id);
			if(p!=null) {
				p.setUsers(null);
				manager.remove(p);
				EntityTransaction transaction=manager.getTransaction();
				transaction.begin();
				transaction.commit();
				
			}
		}
		
	}
}
