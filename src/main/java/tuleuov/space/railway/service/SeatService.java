package tuleuov.space.railway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuleuov.space.railway.dto.SeatDTO;
import tuleuov.space.railway.entity.Seat;
import tuleuov.space.railway.repository.SeatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }
    public Seat updateSeatStatus(Seat seat) {
        Optional<Seat> optionalSeat = seatRepository.findById(seat.getId());
        if (optionalSeat.isPresent()) {
            Seat existingSeat = optionalSeat.get();
            existingSeat.setIsOccupied(seat.getIsOccupied());
            return seatRepository.save(existingSeat);
        } else {
            throw new SeatNotFoundException("Seat with id " + seat.getId() + " not found.");
        }
    }

    public Seat getSeatByNumberAndCarriageId(int seatNumber, Long carriageId) {
        Optional<Seat> optionalSeat = seatRepository.findBySeatNumberAndCarriageId(seatNumber, carriageId);
        return optionalSeat.orElse(null); // or throw exception if needed
    }

    public Seat getSeatByIdAndCarriageId(Long seatId, Long carriageId) {
        Optional<Seat> optionalSeat = seatRepository.findByIdAndCarriageId(seatId, carriageId);
        return optionalSeat.orElse(null); // or throw exception if needed
    }

    public void updateSeat(Long carriageId, int seatNumber, boolean occupied) {
        Optional<Seat> optionalSeat = seatRepository.findBySeatNumberAndCarriageId(seatNumber, carriageId);
        if (optionalSeat.isPresent()) {
            Seat seat = optionalSeat.get();
            seat.setIsOccupied(occupied);
            seatRepository.save(seat);
        } else {
            throw new SeatNotFoundException("Seat with number " + seatNumber + " in carriage " + carriageId + " not found");
        }
    }
    public boolean isAnySeatOccupiedInCarriage(Long carriageId) {
        return seatRepository.existsByCarriageIdAndIsOccupiedTrue(carriageId);
    }
    public List<Seat> getSeatsByCarriageId(Long carriageId) {
        return seatRepository.findByCarriageId(carriageId);
    }
    public List<SeatDTO> mapSeatsToSeatDTOs(List<Seat> seats) {
        List<SeatDTO> seatDTOs = new ArrayList<>();
        for (Seat seat : seats) {
            SeatDTO seatDTO = new SeatDTO();
            seatDTO.setId(seat.getId());
            seatDTO.setSeatNumber(seat.getSeatNumber());
            seatDTO.setPrice(seat.getPrice());
            seatDTO.setOccupied(seat.getIsOccupied());
            seatDTOs.add(seatDTO);
        }
        return seatDTOs;
    }
}
