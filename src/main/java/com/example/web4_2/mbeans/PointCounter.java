package com.example.web4_2.mbeans;


import org.springframework.jmx.export.annotation.ManagedResource;

import org.springframework.stereotype.Component;

import javax.management.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ManagedResource(
        objectName = "сom.example.web4_2:name=PointCounterMBean",
        description = "MBean, считающий общее число установленных пользователем точек," +
                " а также число точек, не попадающих в область. В случае, если количество установленных" +
                " пользователем точек стало кратно 10, разработанный MBean должен отправлять оповещение об этом событии."
)
public class PointCounter implements PointCounterMBean, NotificationEmitter, NotificationListener {
    private final AtomicInteger totalPoints = new AtomicInteger(0);
    private final AtomicInteger missedPoints = new AtomicInteger(0);

    public static final String POINT_COUNT_NOTIFICATION_TYPE = "web4_2.mbeans.point_counter.event.multiple_of_10";


    private final NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[0];
    }

    @Override
    public int getTotalPoints() {
        return totalPoints.get();
    }

    @Override
    public int getMissedPoints() {
        return missedPoints.get();
    }

    @Override
    public void addPoint(boolean hit) {
        int currentTotal = totalPoints.incrementAndGet();
        if (!hit) {
            missedPoints.incrementAndGet();
        }

        if (currentTotal > 0 && currentTotal % 10 == 0) {
            Notification notification = new Notification(
                    POINT_COUNT_NOTIFICATION_TYPE,
                    this, // источник уведомления (текущий MBean)
                    System.currentTimeMillis(), // это типа порядковый номер уведомления и частая практика использовать таймстемп
                    "число точек, установленных пользователем =" + currentTotal + ". и это значение кратно 10"
            );

            broadcaster.sendNotification(notification);

        }
    }


    @Override
    public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener, filter, handback);
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println(notification);
    }
    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
        broadcaster.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
    }
}
