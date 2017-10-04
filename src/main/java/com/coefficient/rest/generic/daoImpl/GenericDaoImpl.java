package com.coefficient.rest.generic.daoImpl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.coefficient.rest.generic.dao.GenericDao;

@Repository("genericDao")
public class GenericDaoImpl<E, I extends Serializable> implements GenericDao<E, I> {
	private Class<E> entityClass;

	public GenericDaoImpl() {
	}

	@Autowired
	private SessionFactory sessionFactory;

	public GenericDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<E> findAll() throws DataAccessException {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(entityClass);
		Root<E> root = query.from(entityClass);
		query.select(root);
		return getCurrentSession().createQuery(query).getResultList();
	}

	@Override
	public E find(I id) {
		return (E) getCurrentSession().get(entityClass, id);
	}

	@Override
	public E create(E e) {
		Serializable id = getCurrentSession().save(e);
		return (E) getCurrentSession().get(entityClass, id);
	}

	@Override
	public void update(E e) {
		getCurrentSession().update(e);
	}

	@Override
	public void saveOrUpdate(E e) {
		getCurrentSession().saveOrUpdate(e);
	}

	@Override
	public void delete(E e) {
		getCurrentSession().delete(e);
	}

	@Override
	public void flush() {
		getCurrentSession().flush();
	}

}