package entities;

import utils.SafeConverter;

public class Application extends BasicEntity {
    // attributes
    private String applicant;
    private String status;
    private String rating;
    private String company;
    private String companyStatus;

    // column names
    private String APPLICANT = "APPLICANT";
    private String STATUS = "STATUS";
    private String RATING = "RATING";
    private String COMPANY = "COMPANY_NAME";
    private String COMPANY_STATUS = "COMPANY_STATUS";

    public Application(){
        super();

        this.columnNames.add(APPLICANT);
        this.columnNames.add(STATUS);
        this.columnNames.add(RATING);
        this.columnNames.add(COMPANY);
        this.columnNames.add(COMPANY_STATUS);
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

        if(COMPANY_STATUS.equals(columnName)){
            return this.companyStatus;
        }

        if(COMPANY.equals(columnName)){
            return this.company;
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

        if(COMPANY_STATUS.equals(columnName)){
            this.setCompanyStatus(SafeConverter.toSafeString(value));
        }

        if(COMPANY.equals(columnName)){
            this.setCompany(SafeConverter.toSafeString(value));
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

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
