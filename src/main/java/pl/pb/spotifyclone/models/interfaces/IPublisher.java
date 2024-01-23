package pl.pb.spotifyclone.models.interfaces;

public interface IPublisher<T> {
    void subscribe(ISubscriber<T> subscriber);
    void notifySubscribers(T object);
}
