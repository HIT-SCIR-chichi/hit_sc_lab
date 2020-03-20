package memento;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {

    private List<Memento> mementos = new ArrayList<>();// save all the trackNum in it

    /**
     * add a memento in the list
     * 
     * @param m the memento to be added
     */
    public void addMemento(Memento m) {
        mementos.add(m);
    }

    /**
     * get the memento recently added
     * 
     * @return the memento recently added
     */
    public Memento getMemento() {
        return mementos.get(mementos.size() - 1);
    }

}
