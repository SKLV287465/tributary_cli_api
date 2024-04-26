package tributary.core.deserializers;

public interface ValueDeserializer<T> {
    T deserialize(String value);
}
