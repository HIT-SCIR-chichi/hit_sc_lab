package memento;

public class Originator {

    private State state;

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        System.out.println("Originator:Setting state to " + state);
        this.state = state;
    }

    /**
     * save the state to the Memento
     * 
     * @return the Memento to be save
     */
    public Memento save() {
        System.out.println("Originator:Saving to Memento.");
        return new Memento(state);
    }

    /**
     * restore the state
     * 
     * @param m the state to be restore
     */
    public void restore(Memento m) {
        state = m.getState();
        System.out.println("Originator:TrackNum after restoring from Memento:" + state);
    }

}
