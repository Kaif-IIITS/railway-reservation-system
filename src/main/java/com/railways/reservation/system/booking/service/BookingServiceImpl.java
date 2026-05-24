package com.railways.reservation.system.booking.service;

import com.railways.reservation.system.booking.dto.request.IssueTicketRequest;
import com.railways.reservation.system.booking.dto.response.TicketResponse;
import com.railways.reservation.system.booking.entity.Booking;
import com.railways.reservation.system.booking.entity.BookingStatus;
import com.railways.reservation.system.booking.repository.BookingRepository;
import com.railways.reservation.system.exception.BusinessException;
import com.railways.reservation.system.seat.entity.Seat;
import com.railways.reservation.system.seat.repository.SeatRepository;
import com.railways.reservation.system.train.entity.Train;
import com.railways.reservation.system.train.repository.TrainRepository;
import com.railways.reservation.system.trainstop.entity.TrainStop;
import com.railways.reservation.system.trainstop.repository.TrainStopRepository;
import com.railways.reservation.system.user.entity.User;
import com.railways.reservation.system.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.railways.reservation.system.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final TrainRepository trainRepository;
    @Override
    @Transactional
    public TicketResponse bookTicket(IssueTicketRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new BusinessException(TRAIN_NOT_EXISTS));

        TrainStop sourceStop =
                trainStopRepository
                        .findByTrainIdAndStationId(
                                request.getTrainId(),
                                request.getSourceStationId()
                        )
                        .orElseThrow(() -> new BusinessException(STOP_NOT_EXISTS));

        TrainStop destinationStop =
                trainStopRepository
                        .findByTrainIdAndStationId(
                                request.getTrainId(),
                                request.getDestinationStationId()
                        )
                        .orElseThrow(() -> new BusinessException(STOP_NOT_EXISTS));

        if (sourceStop.getStopOrder() >= destinationStop.getStopOrder()) {
            throw new BusinessException(INVALID_ROUTE);
        }

        List<Booking> bookings =
                bookingRepository.findByTrainIdAndJourneyDateAndBookingStatus(
                        request.getTrainId(),
                        request.getJourneyDate(),
                        BookingStatus.BOOKED
                );

        List<Seat> seats =
                seatRepository.findByTrainId(request.getTrainId());

        int newSource = sourceStop.getStopOrder();
        int newDestination = destinationStop.getStopOrder();

        Seat availableSeat = null;

        for (Seat seat : seats) {

            boolean available = true;

            for (Booking booking : bookings) {

                if (!booking.getSeat().getId().equals(seat.getId())) {
                    continue;
                }

                int existingSource =
                        booking.getSourceStop().getStopOrder();

                int existingDestination =
                        booking.getDestinationStop().getStopOrder();

                boolean overlap =
                        newSource < existingDestination
                                &&
                                existingSource < newDestination;

                if (overlap) {
                    available = false;
                    break;
                }
            }

            if (available) {
                availableSeat = seat;
                break;
            }
        }

        if (availableSeat == null) {
            throw new BusinessException(SEAT_NOT_AVAILABLE);
        }

        Booking booking = Booking.builder()
                .user(user)
                .train(train)
                .seat(availableSeat)
                .sourceStop(sourceStop)
                .destinationStop(destinationStop)
                .journeyDate(request.getJourneyDate())
                .bookingStatus(BookingStatus.BOOKED)
                .fare(500)
                .build();

        bookingRepository.save(booking);

        return mapTicket(booking);
    }

    private final TrainStopRepository trainStopRepository;

    @Override
    @Transactional
    public void cancelBooking(Long bookingId,Long userId) {

        Booking booking = bookingRepository.findByIdAndUserId(bookingId,userId).orElseThrow(()->new BusinessException(BOOKING_NOT_FOUND));
        bookingRepository.delete(booking);
    }

    @Override
    @Transactional
    public List<TicketResponse> myBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream().map(this::mapTicket).toList();
    }

    @Override
    @Transactional
    public TicketResponse bookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BusinessException(BOOKING_NOT_FOUND));
        return mapTicket(booking);
    }

    TicketResponse mapTicket(Booking b){
        return TicketResponse.builder()
                .bookingId(b.getId()).trainName(b.getTrain().getName()).coachNumber(b.getSeat().getCoachNumber()).seatNumber(b.getSeat().getSeatNumber()).seatType(b.getSeat().getSeatType()).sourceStation(b.getSourceStop().getStation().getName()).destinationStation(b.getDestinationStop().getStation().getName()).journeyDate(b.getJourneyDate()).bookingStatus(b.getBookingStatus()).fare(b.getFare()).bookedAt(b.getBookedAt()).build();
    }
}
