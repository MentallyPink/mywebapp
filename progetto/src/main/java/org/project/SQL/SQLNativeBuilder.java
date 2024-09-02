package org.project.SQL;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SQLNativeBuilder {

	private StringBuilder sql = new StringBuilder();
	
	public SQLNativeBuilder() {
	}

	public void append(String query) {
		if (query != null && !query.isEmpty())
			sql.append(query + "\n");
	}

	public void append(String query, Object pm) {
		if (query != null && !query.isEmpty() && pm != null) {
			query.replaceFirst("?", "'" + pm.toString() + "'");
			append(query);
		}

	}

	public void appendInClause(String query, Collection<?> list) {
		if (query != null && !query.isEmpty() && list != null) {
			query += "(";
			for (Object param : list) {
				if (param instanceof String)
					query += " '" + param + "' "; /* ' param ' */
				else
					query += param; /* param */
				query += ",";
			}
			query += ")";
			append(query);
		}
	}
	
	@Override
	public String toString() {
		return sql.toString();
	}
}
