package entities;

import utils.SafeConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasicEntity implements Serializable {
    private static final String ID = "ID";
    private int id;

    protected List<String> columnNames;

    public BasicEntity(){
        this.setId(Integer.MIN_VALUE);
        this.columnNames = new ArrayList<String>();
        this.columnNames.add(ID);
    }

    public void setValueForColumnName(String columnName, Object value){
        if(ID.equals(columnName)){
            setId(SafeConverter.toSafeInt(value));
        }
    }

    public Object getValueForColumnName(String columnName){
        if(ID.equals(columnName)){
            return this.id;
        }

        return null;
    }

    public String primaryKeyColumnName(){
        return ID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicEntity other = (BasicEntity) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public List<String> columnNames() {
        return columnNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}