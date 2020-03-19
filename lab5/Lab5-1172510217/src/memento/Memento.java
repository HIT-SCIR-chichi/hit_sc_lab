package memento;

class Memento {

  private State state;// state to save

  /**
   * set the trackNumber state.
   * 
   * @param state the state
   */
  public Memento(State state) {
    this.state = state;
  }

  /**
   * get the state.
   * 
   * @return the state
   */
  public State getState() {
    return state;
  }
}
