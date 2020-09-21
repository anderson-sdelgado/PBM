package br.com.usinasantafe.pbm.model.pst;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;

public class GenericRecordable<T> {

	private Dao dao;
	private DatabaseHelper instance;

	public GenericRecordable() {
		instance = DatabaseHelper.getInstance();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insert(T element, Class classe) {
		try {
			dao = instance.getDao(classe);
			dao.create(element);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteAll(Class classe) {
		try {
			dao = instance.getDao(classe);
			DeleteBuilder<String, Object> deleteBuilder = dao.deleteBuilder();
			deleteBuilder.delete();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
