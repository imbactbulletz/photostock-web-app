package entities;

import utils.SafeConverter;

public class BoughtPhoto extends BasicEntity {
    //attributes
    private int photoID;
    private String bought_by;
    private int resolutionID;

    //columns
    private String PHOTOID = "PHOTOID";
    private String BOUGHT_BY = "BOUGHT_BY";
    private String RESOLUTIONID = "RESOLUTIONID";


    public BoughtPhoto(){
        super();

        this.columnNames.add(PHOTOID);
        this.columnNames.add(BOUGHT_BY);
        this.columnNames.add(RESOLUTIONID);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if(PHOTOID.equals(columnName))
            return this.photoID;

        if(BOUGHT_BY.equals(columnName))
            return this.bought_by;

        if(RESOLUTIONID.equals(columnName))
            return this.resolutionID;

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {

        if(PHOTOID.equals(columnName))
            this.setPhotoID(SafeConverter.toSafeInt(value));

        if(BOUGHT_BY.equals(columnName))
            this.setBought_by(SafeConverter.toSafeString(value));

        if(RESOLUTIONID.equals(columnName))
            this.setResolutionID(SafeConverter.toSafeInt(value));

        super.setValueForColumnName(columnName, value);
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getBought_by() {
        return bought_by;
    }

    public void setBought_by(String bought_by) {
        this.bought_by = bought_by;
    }

    public int getResolutionID() {
        return resolutionID;
    }

    public void setResolutionID(int resolutionID) {
        this.resolutionID = resolutionID;
    }
}
