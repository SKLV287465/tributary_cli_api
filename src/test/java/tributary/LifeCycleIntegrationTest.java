package tributary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tributary.api.API;

public class LifeCycleIntegrationTest {
        @Test
        public void testMultipleStrings() {
                API api = new API();
                api.createProducer("producer1", "String", "Random");
                api.createTopic("topic1", "String");
                api.createConsumerGroup("CG1", "topic1", "Range");
                api.createPartition("topic1", "partition1");
                api.createConsumer("CG1", "consumer1");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent1.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent4.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent5.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent6.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent7.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent8.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent9.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent10.json");
                //api.consumeEvent("consumer1", "partition1");
                assertEquals("8(event1:consumer1)(event4:consumer1)(event5:consumer1)(event6:consumer1)"
                                + "(event7:consumer1)(event8:consumer1)(event9:consumer1)(event10:consumer1)",
                                api.consumeEvents("consumer1", "partition1", "8").getTributaryCluster()
                                                .getTopicById("topic1").getPartition("partition1").getStatus());
        }

        @Test
        public void testMultipleIntegers() {
                API api = new API();
                api.createProducer("producer1", "Integer", "Random");
                api.createTopic("topic1", "Integer");
                api.createConsumerGroup("CG1", "topic1", "RoundRobin");
                api.createPartition("topic1", "partition1");
                api.createConsumer("CG1", "consumer1");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/IntEvent1.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/IntEvent4.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/IntEvent5.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/IntEvent6.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/IntEvent7.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/IntEvent8.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/IntEvent9.json");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/IntEvent10.json");
                //api.consumeEvent("consumer1", "partition1");
                assertEquals("8(event11:consumer1)(event14:consumer1)(event15:consumer1)(event16:consumer1)"
                                + "(event17:consumer1)(event18:consumer1)(event19:consumer1)(event20:consumer1)",
                                api.consumeEvents("consumer1", "partition1", "8").getTributaryCluster()
                                                .getTopicById("topic1").getPartition("partition1").getStatus());
        }

        @Test
        public void parallelTest() {
                API api = new API();
                api.createProducer("producer1", "String", "Random");
                api.createProducer("producer2", "String", "Random");
                api.createProducer("producer3", "Integer", "Random");
                api.createProducer("producer4", "Integer", "Random");
                api.createTopic("topic1", "String");
                api.createTopic("topic2", "Integer");
                api.createTopic("topic3", "Integer");
                api.createConsumerGroup("CG1", "topic1", "Range");
                api.createConsumerGroup("CG2", "topic1", "RoundRobin");
                api.createConsumerGroup("CG3", "topic2", "Range");
                api.createConsumerGroup("CG4", "topic2", "RoundRobin");
                api.createConsumerGroup("CG5", "topic3", "Range");
                api.createPartition("topic1", "partition1");
                api.createPartition("topic2", "partition2");
                api.createPartition("topic3", "partition3");
                api.createConsumer("CG1", "consumer1");
                api.createConsumer("CG1", "consumer2");
                api.createConsumer("CG2", "consumer3");
                api.createConsumer("CG3", "consumer4");
                api.createConsumer("CG4", "consumer5");
                api.createConsumer("CG5", "consumer6");
                api.createConsumer("CG1", "consumer7");
                api.createConsumer("CG2", "consumer8");
                api.createConsumer("CG3", "consumer9");
                api.parallelProduce(
                                Arrays.asList("producer3", "producer4", "producer3", "producer4", "producer3",
                                                "producer4", "producer3", "producer4", "producer3", "producer4",
                                                "producer1", "producer2", "producer1", "producer2", "producer1",
                                                "producer2", "producer1", "producer2", "producer1", "producer2"),
                                Arrays.asList("topic2", "topic3", "topic2", "topic3", "topic2", "topic3", "topic2",
                                                "topic3", "topic2", "topic3", "topic1", "topic1", "topic1", "topic1",
                                                "topic1", "topic1", "topic1", "topic1", "topic1", "topic1"),
                                Arrays.asList("src/test/json/eventFiles/IntEvent1.json",
                                                "src/test/json/eventFiles/IntEvent2.json",
                                                "src/test/json/eventFiles/IntEvent3.json",
                                                "src/test/json/eventFiles/IntEvent4.json",
                                                "src/test/json/eventFiles/IntEvent5.json",
                                                "src/test/json/eventFiles/IntEvent6.json",
                                                "src/test/json/eventFiles/IntEvent7.json",
                                                "src/test/json/eventFiles/IntEvent8.json",
                                                "src/test/json/eventFiles/IntEvent9.json",
                                                "src/test/json/eventFiles/IntEvent10.json",
                                                "src/test/json/eventFiles/StringEvent1.json",
                                                "src/test/json/eventFiles/StringEvent2.json",
                                                "src/test/json/eventFiles/StringEvent3.json",
                                                "src/test/json/eventFiles/StringEvent4.json",
                                                "src/test/json/eventFiles/StringEvent5.json",
                                                "src/test/json/eventFiles/StringEvent6.json",
                                                "src/test/json/eventFiles/StringEvent7.json",
                                                "src/test/json/eventFiles/StringEvent8.json",
                                                "src/test/json/eventFiles/StringEvent9.json",
                                                "src/test/json/eventFiles/StringEvent10.json"));
                api.parallelConsume(
                                Arrays.asList("consumer4", "consumer6", "consumer5", "consumer6", "consumer9",
                                                "consumer6", "consumer4", "consumer6", "consumer5", "consumer6",
                                                "consumer1", "consumer2", "consumer3", "consumer7", "consumer8",
                                                "consumer1", "consumer2", "consumer3", "consumer7", "consumer8"),
                                Arrays.asList("partition2", "partition3", "partition2", "partition3", "partition2",
                                                "partition3", "partition2", "partition3", "partition2", "partition3",
                                                "partition1", "partition1", "partition1", "partition1", "partition1",
                                                "partition1", "partition1", "partition1", "partition1", "partition1"));
                // check that events are produced then consumed in parallel
                assertEquals("10(event6:consumer8)(event5:consumer7)(event8:consumer3)".length()
                                + "(event10:consumer2)(event1:consumer1)".length()
                                + "(event3:consumer8)(event9:consumer7)(event2:consumer3)".length()
                                + "(event7:consumer2)(event4:consumer1)".length(),
                                api.playback("consumer2", "partition1", "0").getTributaryCluster()
                                                .getTopicById("topic1").getPartition("partition1").getStatus()
                                                .length());
                assertEquals("5(event13:consumer4)(event15:consumer5)(event11:consumer4)".length()
                                + "(event19:consumer9)(event17:consumer5)".length(),
                                api.playback("consumer3", "partition2", "0").getTributaryCluster()
                                                .getTopicById("topic2").getPartition("partition2").getStatus()
                                                .length());

                assertEquals("5(event18:consumer6)(event20:consumer6)(event16:consumer6)".length()
                                + "(event12:consumer6)(event14:consumer6)".length(),
                                api.playback("consumer9", "partition3", "0").getTributaryCluster()
                                                .getTopicById("topic3").getPartition("partition3").getStatus()
                                                .length());
                api.playback("consumer1", "partition1", "0");
                // must look at system out for playback checking.
        }

        @Test
        public void rebalanceRR() {
                API api = new API();
                api.createProducer("producer1", "String", "Manual");
                api.createTopic("topic1", "String");
                api.createConsumerGroup("CG1", "topic1", "RoundRobin");
                api.createPartition("topic1", "partition1");
                api.createPartition("topic1", "partition2");
                api.createPartition("topic1", "partition3");
                api.createPartition("topic1", "partition4");
                api.createConsumer("CG1", "consumer1");
                api.createConsumer("CG1", "consumer2");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent1.json", "partition1");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent2.json", "partition2");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent3.json", "partition3");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent4.json", "partition4");
                api.consumeEvent("consumer1", "partition1");
                api.consumeEvent("consumer1", "partition3");
                api.consumeEvent("consumer2", "partition2");
                api.consumeEvent("consumer2", "partition4");

                // check that the events are consumed by the correctly allocated consumers
                assertEquals("1(event1:consumer1)", api.playback("consumer1", "partition1", "0").getTributaryCluster()
                                .getPartition("partition1").getStatus());
                assertEquals("1(event2:consumer2)", api.playback("consumer1", "partition1", "0").getTributaryCluster()
                                .getPartition("partition2").getStatus());
                assertEquals("1(event3:consumer1)", api.playback("consumer1", "partition1", "0").getTributaryCluster()
                                .getPartition("partition3").getStatus());
                assertEquals("1(event4:consumer2)", api.playback("consumer1", "partition1", "0").getTributaryCluster()
                                .getPartition("partition4").getStatus());
        }

        @Test
        public void rebalanceRange() {
                API api = new API();
                api.createProducer("producer1", "String", "Manual");
                api.createTopic("topic1", "String");
                api.createConsumerGroup("CG1", "topic1", "Range");
                api.createPartition("topic1", "partition1");
                api.createPartition("topic1", "partition2");
                api.createPartition("topic1", "partition3");
                api.createPartition("topic1", "partition4");
                api.createConsumer("CG1", "consumer1");
                api.createConsumer("CG1", "consumer2");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent1.json", "partition1");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent2.json", "partition2");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent3.json", "partition3");
                api.produceEvent("producer1", "topic1", "src/test/json/eventFiles/StringEvent4.json", "partition4");
                api.consumeEvent("consumer1", "partition1");
                api.consumeEvent("consumer1", "partition2");
                api.consumeEvent("consumer2", "partition3");
                api.consumeEvent("consumer2", "partition4");

                // check that the events are consumed by the correctly allocated consumers
                assertEquals("1(event1:consumer1)", api.playback("consumer1", "partition1", "0").getTributaryCluster()
                                .getPartition("partition1").getStatus());
                assertEquals("1(event2:consumer1)", api.playback("consumer1", "partition1", "0").getTributaryCluster()
                                .getPartition("partition2").getStatus());
                assertEquals("1(event3:consumer2)", api.playback("consumer1", "partition1", "0").getTributaryCluster()
                                .getPartition("partition3").getStatus());
                assertEquals("1(event4:consumer2)", api.playback("consumer1", "partition1", "0").getTributaryCluster()
                                .getPartition("partition4").getStatus());
        }
}
