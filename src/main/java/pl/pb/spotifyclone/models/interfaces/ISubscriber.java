package pl.pb.spotifyclone.models.interfaces;

public interface ISubscriber<T> {
  void update(T object);
}
