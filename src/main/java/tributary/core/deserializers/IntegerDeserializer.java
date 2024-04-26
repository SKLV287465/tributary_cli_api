package tributary.core.deserializers;

public class IntegerDeserializer implements ValueDeserializer<Integer> {
    @Override
    public Integer deserialize(String value) {
        return Integer.parseInt(value);
    }

}
