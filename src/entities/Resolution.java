package entities;

import utils.SafeConverter;

public class Resolution extends BasicEntity {
    // attributes
    private String name;
    private double lowestPrice;
    private double highestPrice;

    // column names
    private final String NAME = "NAME";
    private final String LOWESTPRICE = "LOWEST_PRICE";
    private final String HIGHESTPRICE = "HIGHEST_PRICE";

    public Resolution(){
        super();

        this.columnNames.add(NAME);
        this.columnNames.add(LOWESTPRICE);
        this.columnNames.add(HIGHESTPRICE);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if(NAME.equals(columnName)){
            return this.name;
        }

        if(LOWESTPRICE.equals(columnName)){
            return this.lowestPrice;
        }

        if(HIGHESTPRICE.equals(columnName)){
            return this.highestPrice;
        }

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(NAME.equals(columnName)){
            this.setName(SafeConverter.toSafeString(value));
        }

        if(LOWESTPRICE.equals(columnName)){
            this.setLowestPrice(SafeConverter.toSafeDouble(value));
        }

        if(HIGHESTPRICE.equals(columnName)){
            this.setHighestPrice(SafeConverter.toSafeDouble(value));
        }

        super.setValueForColumnName(columnName, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }
}
