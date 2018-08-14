package entities;

import utils.SafeConverter;

public class Company extends BasicEntity {
    // attributes
    private String name;
    private String address;
    private String membership;
    private String pib;

    // column names
    private String NAME = "NAME";
    private String ADDRESS = "ADDRESS";
    private String MEMBERSHIP = "MEMBERSHIP";
    private String PIB = "PIB";

    public Company(){
        super();

        this.columnNames.add(NAME);
        this.columnNames.add(ADDRESS);
        this.columnNames.add(MEMBERSHIP);
        this.columnNames.add(PIB);
    }

    @Override
    public Object getValueForColumnName(String columnName) {

        if(NAME.equals(columnName)){
            return this.name;
        }

        if(ADDRESS.equals(columnName)){
            return this.address;
        }

        if(MEMBERSHIP.equals(columnName)){
            return this.membership;
        }

        if(PIB.equals(columnName)){
            return this.pib;
        }

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(NAME.equals(columnName)){
            this.setName(SafeConverter.toSafeString(value));
        }

        if(ADDRESS.equals(columnName)){
            this.setAddress(SafeConverter.toSafeString(value));
        }

        if(MEMBERSHIP.equals(columnName)){
            this.setMembership(SafeConverter.toSafeString(value));
        }

        if(PIB.equals(columnName)){
            this.setPib(SafeConverter.toSafeString(value));
        }

        super.setValueForColumnName(columnName, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }
}

