package bg.tyordanovv.responses.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DeliverySummaryList {
    private List<DeliverySummary> deliverySummaryList;

    public DeliverySummaryList() {
        this.deliverySummaryList = new ArrayList<>();
    }
}
