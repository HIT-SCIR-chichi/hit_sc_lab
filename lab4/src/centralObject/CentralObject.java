package centralObject;

public class CentralObject {

    private String name = null;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    @Override public String toString() {
        return name;
        
    }

}
