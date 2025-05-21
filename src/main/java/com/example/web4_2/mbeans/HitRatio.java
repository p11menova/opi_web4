package com.example.web4_2.mbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component("hitRatioMBean")
@ManagedResource(
        objectName = "сom.example.web4_2:name=HitRatioMBean",

        description = "MBean, определяющий процентное отношение попаданий " +
                "к общему числу кликов пользователя по координатной плоскости.\n"
)
public class HitRatio implements HitRatioMBean {

    private final PointCounter pointCounter;

    @Autowired
    public HitRatio(PointCounter pointCounter) {
        this.pointCounter = pointCounter;
    }

    @Override
    public double getHitRatioPercentage() {
        int total = pointCounter.getTotalPoints();
        int missed = pointCounter.getMissedPoints();

        if (total == 0) {
            return 0.0;
        }
        int hits = total - missed;

        return Math.round(((double) hits / total * 100.0) * 100.0) / 100.0;
    }
}