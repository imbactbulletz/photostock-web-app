package entities;

public class ApplicationPhoto extends BasicEntity {
    // attributes
    private String application;
    private String path;

    // base64 encoded image
    String data;

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
            this.setApplication(String.valueOf(application));
        }

        if(PATH.equals(columnName)){
            this.setPath(String.valueOf(value));
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
