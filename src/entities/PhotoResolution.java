package entities;

import utils.SafeConverter;

public class PhotoResolution extends BasicEntity {
    // attributes
    private int photoID;
    private String name;
    private double price;

    // column names
    private String PHOTOID = "PHOTOID";
    private String NAME = "NAME";
    private String PRICE = "PRICE";

    public PhotoResolution(){
        super();

        this.columnNames.add(PHOTOID);
        this.columnNames.add(NAME);
        this.columnNames.add(PRICE);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if(PHOTOID.equals(columnName)){
            return this.photoID;
        }

        if(NAME.equals(columnName)){
            return this.columnNames;
        }

        if(PRICE.equals(columnName)){
            return this.price;
        }

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(PHOTOID.equals(columnName)){
            this.setPhotoID(SafeConverter.toSafeInt(value));
        }

        if(NAME.equals(columnName)){
            this.setName(SafeConverter.toSafeString(value));
        }

        if(PRICE.equals(columnName)){
            this.setPrice(SafeConverter.toSafeDouble(value));
        }

        super.setValueForColumnName(columnName, value);
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
