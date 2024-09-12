package org.project.SQL;

import java.util.Collection;

public class NativeQueryBuilder {

	private StringBuilder sql = new StringBuilder();

	public NativeQueryBuilder() {
	}

	public void append(String query) {
		if (query != null && !query.isEmpty())
			sql.append(query + "\n");
	}

	public void append(String query, Object pm) {
		if (query != null && !query.isEmpty() && pm != null) {
			String queryv2;
			if (pm instanceof String)
				queryv2 = query.replaceFirst("\\?", "'" + pm.toString() + "'");
			else
				queryv2 = query.replaceFirst("\\?", pm.toString());
			append(queryv2);
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
