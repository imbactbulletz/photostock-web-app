package entities;

import utils.SafeConverter;

public class ApplicationPhoto extends BasicEntity {
    // attributes
    private String application;
    private String path;

    // columns
    private String APPLICATION = "APPLICATION";
    private String PATH = "PATH";

    public ApplicationPhoto(){
        super();

        this.columnNames.add(APPLICATION);
        this.columnNames.add(PATH);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if(APPLICATION.equals(columnName)){
            return this.application;
        }
        if(PATH.equals(columnName)){
            return this.path;
        }

        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(APPLICATION.equals(columnName)){
            this.setApplication(application);
        }

        if(PATH.equals(columnName)){
            this.setPath(path);
        }

        super.setValueForColumnName(columnName, value);
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
