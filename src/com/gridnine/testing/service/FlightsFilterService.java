package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightsFilterService {
    private final List<Flight> flights;

    public FlightsFilterService(List<Flight> flights) {
        this.flights = new ArrayList<>(flights);
    }

    public List<Flight> build() {
        return flights;
    }

    public List<Flight> filterDepartureBeforeNow() {
        flights.removeIf(flight -> flight.getSegments()
                .stream().anyMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())));
        return flights;
    }

    public List<Flight> filterArrivalBeforeDeparture() {
        flights.removeIf(flight -> flight.getSegments()
                .stream().anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())));
        return flights;
    }

    public List<Flight> filterSumTimeOnGroundMoreThanTwoHours() {
        flights.removeIf(flight -> {
            List<Segment> segments = flight.getSegments();
            LocalDateTime curDeparture;
            LocalDateTime lastArrival;
            Duration duration = Duration.ZERO;
            for (int i = 1; i < segments.size(); i++) {
                curDeparture = segments.get(i).getDepartureDate();
                lastArrival = segments.get(i - 1).getArrivalDate();
                duration = duration.plus(Duration.between(curDeparture, lastArrival).abs());
            }
            return duration.toHours() >= 2;
        });
        return flights;
    }
}
