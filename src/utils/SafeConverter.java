package utils;

public class SafeConverter {
    public static int toSafeInt(Object o){
        int id = 0;
        try{
            id = Integer.parseInt(o == null ? "0" : o.toString());
        }catch(NumberFormatException e){

        }
        return id;
    }


    public static float toSafeFloat(Object o){
        float id = 0;
        try{
            id = Float.parseFloat(o == null ? "0" : o.toString());
        }catch(NumberFormatException e){

        }
        return id;
    }

    public static double toSafeDouble(Object o){
        double id = 0;

        try {
            id = Double.parseDouble(o == null ? "0" : o.toString());
        }
        catch (NumberFormatException e){

        }

        return id;
    }

    public static boolean toSafeBoolean(Object o){
        boolean result = false;

        try{
            result = Boolean.parseBoolean(o == null ? "0" : o.toString());
        }
        catch (NumberFormatException e){

        }

        return result;
    }

    public static String toSafeString(Object o){
        return o == null ? "" : o.toString();
    }
}