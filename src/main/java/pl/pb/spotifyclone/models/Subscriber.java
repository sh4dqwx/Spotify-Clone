package pl.pb.spotifyclone.models;

import java.util.function.Function;

public class Subscriber<T> {
  private Function<T, Void> updateAction;

  public Subscriber(Function<T, Void> updateAction) {
    this.updateAction = updateAction;
  }

  public void update(T object) {
    updateAction.apply(object);
  }
}
