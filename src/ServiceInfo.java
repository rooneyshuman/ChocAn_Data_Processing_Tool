//ServiceInfo: holds info for each service code. Contained within Provider Directory

public class ServiceInfo {
    //Data fields
    private String name;
    private Integer code;

    //Default constructor
    public ServiceInfo() {
        this.name = null;
        this.code = 0;
    }

    //Copy constructor
    public ServiceInfo(ServiceInfo to_copy) {
        this.name = to_copy.name;
        this.code = to_copy.code;
    }

    //Copy constructor w/ args
    public ServiceInfo(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    //Display function
    public void Display() {
        System.out.println("SERVICE INFO: ");
        System.out.println("Service name: " + name);
        System.out.println("Service code: " + code);
    }




}
