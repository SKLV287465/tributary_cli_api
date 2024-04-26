package tributary.core;

import java.util.List;

public class CoreResponse {
    private TributaryCluster tributaryCluster;
    private List<ConsumerGroup> consumerGroups;
    private List<Producer> producers;

    public CoreResponse(TributaryCluster tributaryCluster, List<ConsumerGroup> consumerGroups,
            List<Producer> producers) {
        this.tributaryCluster = tributaryCluster;
        this.consumerGroups = consumerGroups;
        this.producers = producers;
    }

    public TributaryCluster getTributaryCluster() {
        return tributaryCluster;
    }

    public List<ConsumerGroup> getConsumerGroups() {
        return consumerGroups;
    }

    public List<Producer> getProducers() {
        return producers;
    }

}
