package entities;

import utils.SafeConverter;

public class Category extends BasicEntity {
    // attributes
    private String name;

    // column names
    private String NAME = "NAME";

    public Category(){
        super();

        this.columnNames.add(NAME);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if(NAME.equals(columnName)){
            return this.name;
        }

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(NAME.equals(columnName)){
            this.setName(SafeConverter.toSafeString(value));
        }

        super.setValueForColumnName(columnName, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
