package com.ess.assignment.core.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentCountResponse {

    private long totalCount;
    private long activeCount;
    private long inActiveCount;
    private long yetToStartCount;
    private long onGoingCount;
    private long completedCount;
}
