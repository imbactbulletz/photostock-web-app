import controllers.ControllerApplication;
import controllers.ControllerCompany;
import controllers.ControllerUser;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class PhotostockRESTApp extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(ControllerUser.class);
        classes.add(ControllerCompany.class);
        classes.add(ControllerApplication.class);
        return classes;
    }
}
