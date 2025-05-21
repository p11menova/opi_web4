package com.example.web4_2.service;

import com.example.web4_2.mbeans.HitRatio;
import com.example.web4_2.mbeans.PointCounter;
import com.example.web4_2.models.Point;
import com.example.web4_2.models.User;
import com.example.web4_2.repository.PointRepository;
import com.example.web4_2.service.areaCheck.AreaChecker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PointService {
    private final PointRepository pointRepository;
    private final PointCounter pointCounter;
    private final HitRatio hitRatio;

    public PointService(PointRepository pointRepository, PointCounter pointCounter, HitRatio hitRatio) {
        this.pointRepository = pointRepository;
        this.pointCounter = pointCounter;
        this.hitRatio = hitRatio;
    }

    public String processPoint(float x, float y, float r, User user){
        long executionStartTime = System.currentTimeMillis();

        Point point = new Point();
        point.setX(x);
        point.setY(y);
        point.setRadius(r);
        point.setResult(AreaChecker.check(x , y, r));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(formatter);

        point.setCur_time(formattedTime);
        point.setScript_time(System.currentTimeMillis() - executionStartTime);

        point.setUser(user);
        user.getPoints().add(point);

        pointCounter.addPoint(point.isResult()); // MBean counter

        System.out.println(point.toString());
        System.out.println("дада счас отдам новую точку");

        pointRepository.save(point);

        return point.toString();
    }
    public int getTotalPointsCount() {
        return pointCounter.getTotalPoints();
    }

    public int getMissedPointsCount() {
        return pointCounter.getMissedPoints();
    }

    public double getHitRatioPercentage() {
        return hitRatio.getHitRatioPercentage();
    }
}
