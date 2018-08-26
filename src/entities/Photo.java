package entities;

import utils.SafeConverter;

import java.sql.Date;

public class Photo extends BasicEntity {

    // attributes
    String title;
    String category;
    String description;
    String uploadedBy;
    String path;
    String approved;
    Date dateUploaded;



    String data;

    // column names
    String TITLE = "TITLE";
    String CATEGORY = "CATEGORY";
    String DESCRIPTION = "DESCRIPTION";
    String UPLOADEDBY = "UPLOADEDBY";
    String PATH = "PATH";
    String APPROVED = "APPROVED";
    String DATEUPLOADED = "DATE_UPLOADED";


    public Photo(){
        super();

        this.columnNames.add(TITLE);
        this.columnNames.add(CATEGORY);
        this.columnNames.add(DESCRIPTION);
        this.columnNames.add(UPLOADEDBY);
        this.columnNames.add(PATH);
        this.columnNames.add(APPROVED);
        this.columnNames.add(DATEUPLOADED);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if(TITLE.equals(columnName)){
            return this.title;
        }

        if(CATEGORY.equals(columnName)){
            return this.category;
        }

        if(DESCRIPTION.equals(columnName)){
            return this.description;
        }

        if(UPLOADEDBY.equals(columnName)){
            return this.uploadedBy;
        }

        if(PATH.equals(columnName)){
            return this.path;
        }

        if(APPROVED.equals(columnName)){
            return this.approved;
        }

        if(DATEUPLOADED.equals(columnName)){
            return this.dateUploaded;
        }

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(TITLE.equals(columnName)){
            this.setTitle(SafeConverter.toSafeString(value));
        }

        if(CATEGORY.equals(columnName)){
            this.setCategory(SafeConverter.toSafeString(value));
        }

        if(DESCRIPTION.equals(columnName)){
            this.setDescription(SafeConverter.toSafeString(value));
        }

        if(UPLOADEDBY.equals(columnName)){
            this.setUploadedBy(SafeConverter.toSafeString(value));
        }

        if(PATH.equals(columnName)){
            this.setPath(SafeConverter.toSafeString(value));
        }

        if(APPROVED.equals(columnName)){
            this.setApproved(SafeConverter.toSafeString(value));
        }

        if(DATEUPLOADED.equals(columnName)){
            this.setDateUploaded(SafeConverter.toSafeDate(value));
        }

        super.setValueForColumnName(columnName, value);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
