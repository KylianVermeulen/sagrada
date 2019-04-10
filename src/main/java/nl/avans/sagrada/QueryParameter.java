package nl.avans.sagrada;

public class QueryParameter {
    public static final int BOOLEAN = 100, DATE = 101, DOUBLE = 102, FLOAT = 103,
                            INT = 104, STRING = 105, TIME = 106, TIMESTAMP = 107, UNSET = -1;
    private int type = UNSET;
    private Object value = null;

    public QueryParameter(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getTypeString() {
        switch (type) {
            case BOOLEAN:
                return "Boolean";
            case DATE:
                return "Date";
            case DOUBLE:
                return "Double";
            case FLOAT:
                return "Float";
            case INT:
                return "Int";
            case STRING:
                return "String";
            case TIME:
                return "Time";
            case TIMESTAMP:
                return "Timestamp";
            case UNSET:
                return "Not defined (-1)";
        }
        return "Unknown (" + type + ")";
    }

    @Override
    public String toString() {
        return getTypeString() + " ==> " + value;
    }
}
