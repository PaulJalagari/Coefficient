package com.coefficient.rest.generic.service;

import java.util.List;

public interface GenericService<E, I> {

	public List<E> findAll();

	public E find(I id);

	public E save(E e);

	public void update(E e);

	public void saveOrUpdate(E e);

	public void delete(E e);

	public void flush();

}
