package entities;

import utils.SafeConverter;

public class User extends BasicEntity {

    // attributes
    private String username;
    private String password;
    private String email;
    private String country;
    private String account_type;
    private String account_status;

    // column names
    private String USERNAME = "USERNAME";
    private String PASSWORD = "PASSWORD";
    private String EMAIL = "EMAIL";
    private String COUNTRY = "COUNTRY";
    private String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    private String ACCOUNT_STATUS = "ACCOUNT_STATUS";


    public User(){
        super();

        this.columnNames.add(USERNAME);
        this.columnNames.add(PASSWORD);
        this.columnNames.add(EMAIL);
        this.columnNames.add(COUNTRY);
        this.columnNames.add(ACCOUNT_TYPE);
        this.columnNames.add(ACCOUNT_STATUS);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if(USERNAME.equals(columnName)){
            return this.username;
        }

        if(PASSWORD.equals(columnName)){
            return this.password;
        }

        if(EMAIL.equals(columnName)){
            return this.email;
        }

        if(COUNTRY.equals(columnName)){
            return this.country;
        }

        if(ACCOUNT_TYPE.equals(columnName)){
            return this.account_type;
        }

        if(ACCOUNT_STATUS.equals(columnName)){
            return this.account_status;
        }

        return super.getValueForColumnName(columnName);
    }


    @Override
    public void setValueForColumnName(String columnName, Object value) {

        if(USERNAME.equals(columnName)){
            this.setUsername(SafeConverter.toSafeString(value));
        }

        if(PASSWORD.equals(columnName)){
            this.setPassword(SafeConverter.toSafeString(value));
        }

        if(EMAIL.equals(columnName)){
            this.setEmail(SafeConverter.toSafeString(value));
        }

        if(COUNTRY.equals(columnName)){
            this.setCountry(SafeConverter.toSafeString(value));
        }

        if(ACCOUNT_TYPE.equals(columnName)){
            this.setAccount_type(SafeConverter.toSafeString(value));
        }

        if(ACCOUNT_STATUS.equals(columnName)){
            this.setAccount_status(SafeConverter.toSafeString(value));
        }

        super.setValueForColumnName(columnName, value);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }
}
