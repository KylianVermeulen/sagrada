package nl.avans.sagrada;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HelperQuery {
    private String type;
    private String sql;
    private List<QueryParameter[]> parametersList;
    private ResultSet resultSet;

    /**
     * Partial constructor
     *
     * @param sql query
     * @param type "query" or "update" (insert, update, delete)
     * @param parameters QueryParameter...
     */
    public HelperQuery(String sql, String type, QueryParameter... parameters) {
        this.sql = sql;
        this.type = type;
        parametersList = new ArrayList<>();
        parametersList.add(parameters);
    }

    /**
     * Full constructor
     *
     * @param sql query
     * @param type "query" (select) or "update" (insert, update, delete)
     * @param parametersList List<QueryParameter[]>
     */
    public HelperQuery(String sql, String type, List<QueryParameter[]> parametersList) {
        this.sql = sql;
        this.type = type;
        this.parametersList = parametersList;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QueryParameter[]> getParametersList() {
        return parametersList;
    }

    public void setParametersList(List<QueryParameter[]> parametersList) {
        this.parametersList = parametersList;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
}
