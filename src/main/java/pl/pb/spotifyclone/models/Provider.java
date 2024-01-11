package pl.pb.spotifyclone.models;

import java.util.ArrayList;
import java.util.List;

public class Provider<T> {
  private List<Subscriber<T>> subscribers;

  public Provider() {
    subscribers = new ArrayList<>();
  }

  public void subscribe(Subscriber<T> s) {
    subscribers.add(s);
  }

  protected void notifySubscribers(T object) {
    for(Subscriber<T> subscriber : subscribers) {
      subscriber.update(object);
    }
  }
}
