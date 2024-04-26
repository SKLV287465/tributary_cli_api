package tributary.UsabilityTests;

import java.util.Arrays;
import java.util.List;
import tributary.api.API;
import java.util.ArrayList;

public class DepartmentStoreSimulator {
    private static int profit = 0;
    private static API api = new API();
    private static List<String> topics = Arrays.asList("topic1", "topic1", "topic1", "topic1", "topic1", "topic1",
            "topic1", "topic1", "topic1", "topic1", "topic1", "topic1");
    private static List<String> wholeSaler = Arrays.asList("producer1", "producer1", "producer1", "producer1",
            "producer1", "producer1", "producer1", "producer1", "producer1", "producer1", "producer1", "producer1",
            "producer1", "producer1");

    private static List<String> stores = Arrays.asList("partition1", "partition1", "partition1", "partition1",
            "partition1", "partition1", "partition1", "partition1", "partition1", "partition1");

    private static void setup() {
        api.createTopic("topic1", "String");
        api.createConsumerGroup("CG1", "topic1", "RoundRobin");
        api.createPartition("topic1", "partition1");
        api.createConsumer("CG1", "consumer1");
        api.createConsumer("CG1", "consumer2");
        api.createProducer("producer1", "String", "Random");
    }

    private static void sell(List<String> consumers) {
        api.parallelConsume(consumers, new ArrayList<>(stores));
        profit += consumers.size();
    }

    private static void port(List<String> items) {
        api.parallelProduce(new ArrayList<>(wholeSaler), new ArrayList<>(topics), items);
        profit -= items.size();
    }

    private static void showHistory(String consumer) {
        api.playback(consumer, "partition1", "0");
    }

    private static void printProfit() {
        System.out.println("The total profit is: " + profit);
    }

    public static void main(String[] args) {
        // setup the stores
        setup();

        // import all of the items required by the department store to sell in individual stores.
        port(Arrays.asList("src/test/json/eventFiles/StringEvent1.json", "src/test/json/eventFiles/StringEvent2.json",
                "src/test/json/eventFiles/StringEvent3.json", "src/test/json/eventFiles/StringEvent4.json",
                "src/test/json/eventFiles/StringEvent5.json", "src/test/json/eventFiles/StringEvent6.json",
                "src/test/json/eventFiles/StringEvent7.json", "src/test/json/eventFiles/StringEvent8.json",
                "src/test/json/eventFiles/StringEvent9.json", "src/test/json/eventFiles/StringEvent10.json"));
        // sell items in parallel
        sell(Arrays.asList("consumer1", "consumer1", "consumer1", "consumer1", "consumer1", "consumer1", "consumer1",
                "consumer2", "consumer2", "consumer2"));

        // show records of consumer sales
        showHistory("consumer1");
        System.out.print("buffer");
        showHistory("consumer2");
        // calculate the total profit.
        printProfit();
    }
}
