package tributary.core.deserializers;

public class StringDeserializer implements ValueDeserializer<String> {
    @Override
    public String deserialize(String value) {
        return value;
    }

}
