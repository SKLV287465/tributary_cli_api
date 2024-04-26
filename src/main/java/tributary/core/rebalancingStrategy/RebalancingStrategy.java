package tributary.core.rebalancingStrategy;

import java.util.List;

import tributary.core.Consumer;
import tributary.core.Partition;

public interface RebalancingStrategy {
    public void rebalance(List<Consumer> consumers, List<Partition> partitions);

    public String getName();
}
