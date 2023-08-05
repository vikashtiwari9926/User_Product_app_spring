package org.jsp.userproduct.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jsp.userproduct.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	EntityManager manager;
	
	public User saveUser(User u) {
		manager.persist(u);
		EntityTransaction transaction=manager.getTransaction();
		transaction.begin();
		transaction.commit();
		return u;
	}
	

	public User updateUser(User u) {
		manager.merge(u);
		EntityTransaction transaction=manager.getTransaction();
		transaction.begin();
		transaction.commit();
		return u;
	}
	
	public User findUserById(int u_id) {
		return manager.find(User.class, u_id);
	}
	
	public User fetchUserByIdandPhone(int id,long phone) {
		Query q=manager.createQuery("select u from User u where u.id=?1 and u.phone=?2");
		q.setParameter(1, id);
		q.setParameter(2, phone);
		try {
			return (User)q.getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
	}
	
	public void deleteUser(int u_id) {
		User u=manager.find(User.class, u_id);
		if(u!=null) {
			manager.remove(u);
			EntityTransaction transaction=manager.getTransaction();
			transaction.begin();
			transaction.commit();
		}
	}
}
