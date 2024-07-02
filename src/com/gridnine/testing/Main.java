package com.gridnine.testing;

import com.gridnine.testing.dao.FlightBuilder;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.service.FlightsFilterService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("Исключен вылет до текущего момента времени:\n" +
                new FlightsFilterService(flights).filterDepartureBeforeNow());
        System.out.println("Исключены сегменты с датой прилёта раньше даты вылета:\n" +
                new FlightsFilterService(flights).filterArrivalBeforeDeparture());
        System.out.println("Исключены перелеты, где общее время, проведённое на земле, превышает два часа:\n" +
                new FlightsFilterService(flights).filterSumTimeOnGroundMoreThanTwoHours());
    }
}
