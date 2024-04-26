package tributary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tributary.api.API;
import tributary.core.CoreResponse;
import tributary.core.allocationStrategy.AllocationStrategy;
import tributary.core.rebalancingStrategy.RangeStrategy;
import tributary.core.rebalancingStrategy.RoundRobinStrategy;

public class CreateTests {
    @Test
    public void testCreateTopic() {
        API api = new API();
        // simple test creates
        CoreResponse res = api.createTopic("topic1", "Integer");
        assertNotNull(TestUtils.getTopicById(res, "topic1"));
        res = api.createTopic("topic2", "Integer");
        assertNotNull(TestUtils.getTopicById(res, "topic2"));
        assertNull(TestUtils.getTopicById(res, "topic3"));

        // testing that you can't have two topics with the same id
        res = api.createTopic("topic1", "String");
        long topics = res.getTributaryCluster().getTopics().stream().filter(t -> t.getId().equals("topic1")).count();
        assertEquals(topics, 1);

        // testing that the type has to be either "Integer" or "String"
        res = api.createTopic("topic4", "String");
        assertEquals(TestUtils.getTopicById(res, "topic4").getType(), "String");
        res = api.createTopic("topic5", "Integer");
        assertEquals(TestUtils.getTopicById(res, "topic5").getType(), "Integer");
        res = api.createTopic("topic6", "othertype");
        assertNull(TestUtils.getTopicById(res, "topic6"));
    }

    @Test
    public void testCreatePartition() {
        API api = new API();
        // simple partition creates
        CoreResponse res = api.createTopic("topic1", "Integer");
        res = api.createPartition("topic1", "partition1");
        assertNotNull(TestUtils.getPartitionFromTopic(res, "partition1", "topic1"));

        res = api.createTopic("topic2", "String");
        res = api.createPartition("topic2", "partition2");
        assertNotNull(TestUtils.getTributaryCluster(res).getTopics().stream()
                .filter(t -> t.getPartition("partition2") != null).findFirst().orElse(null));
    }

    @Test
    public void testCreateConsumerGroup() {
        API api = new API();

        CoreResponse res = api.createTopic("topic1", "Integer");
        res = api.createConsumerGroup("consumerGroup1", "topic1", "Range");
        assertEquals(res.getConsumerGroups().stream().count(), 1);
        assertTrue(TestUtils.getConsumerGroupById(res, "consumerGroup1")
                .getRebalancingStrategy() instanceof RangeStrategy);

        res = api.createConsumerGroup("consumerGroup2", "topic1", "RoundRobin");
        assertEquals(res.getConsumerGroups().stream().count(), 2);
        assertTrue(TestUtils.getConsumerGroupById(res, "consumerGroup2")
                .getRebalancingStrategy() instanceof RoundRobinStrategy);
    }

    @Test
    public void testCreateConsumer() {
        API api = new API();

        CoreResponse res = api.createTopic("topic1", "Integer");
        res = api.createConsumerGroup("consumerGroup1", "topic1", "Range");
        res = api.createConsumer("consumerGroup1", "consumer1");
        assertEquals(res.getConsumerGroups().stream().filter(cg -> cg.hasConsumer("consumer1")).count(), 1);
    }

    @Test
    public void testDeleteConsumer() {
        API api = new API();

        CoreResponse res = api.createTopic("topic1", "Integer");
        res = api.createConsumerGroup("consumerGroup1", "topic1", "Range");
        res = api.createConsumer("consumerGroup1", "consumer1");
        assertTrue(TestUtils.getConsumerGroupById(res, "consumerGroup1").hasConsumer("consumer1"));
        res = api.deleteConsumer("consumer1");
        assertFalse(TestUtils.getConsumerGroupById(res, "consumerGroup1").hasConsumer("consumer1"));
    }

    @Test
    public void testCreateProducer() {
        API api = new API();

        CoreResponse res = api.createProducer("producer1", "Integer", "Random");
        assertEquals(TestUtils.getProducers(res).size(), 1);
        assertEquals(TestUtils.getProducerById(res, "producer1").getType(), "Integer");
        assertEquals(TestUtils.getProducerById(res, "producer1").getAllocationStrategy(), AllocationStrategy.RANDOM);
    }
}
