package entities;

import utils.SafeConverter;

public class Application extends BasicEntity {
    // attributes
    private String applicant;
    private String status;
    private String rating;


    // column names
    private String APPLICANT = "APPLICANT";
    private String STATUS = "STATUS";
    private String RATING = "RATING";

    public Application(){
        super();

        this.columnNames.add(APPLICANT);
        this.columnNames.add(STATUS);
        this.columnNames.add(RATING);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if (APPLICANT.equals(columnName)){
            return this.applicant;
        }

        if (STATUS.equals(columnName)){
            return this.status;
        }

        if (RATING.equals(columnName)){
            return this.status;
        }

        return super.getValueForColumnName(columnName);
    }


    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(APPLICANT.equals(columnName)){
            this.setApplicant(SafeConverter.toSafeString(value));
        }

        if(STATUS.equals(columnName)){
            this.setStatus(SafeConverter.toSafeString(value));
        }

        if(RATING.equals(columnName)){
            this.setRating(SafeConverter.toSafeString(value));
        }

        super.setValueForColumnName(columnName, value);
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
