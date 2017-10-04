package com.coefficient.rest.generic.dao;

import java.util.List;

import org.hibernate.Session;

public interface GenericDao<E, I> {

	public Session getCurrentSession();

	public List<E> findAll();

	public E find(I id);

	public E create(E e);

	public void update(E e);

	public void saveOrUpdate(E e);

	public void delete(E e);

	public void flush();
}
