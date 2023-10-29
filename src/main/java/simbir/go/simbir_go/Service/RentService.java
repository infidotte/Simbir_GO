package simbir.go.simbir_go.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Entity.Rent;
import simbir.go.simbir_go.Entity.Transport;
import simbir.go.simbir_go.Exception.MethodNotAllowedException;
import simbir.go.simbir_go.Exception.RentNotFoundException;
import simbir.go.simbir_go.Exception.TransportNotFoundException;
import simbir.go.simbir_go.Exception.UserNotFoundException;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Record.RentRequest;
import simbir.go.simbir_go.Repository.RentRepository;
import simbir.go.simbir_go.Utilit.DateCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentService {
    private final TransportService transportService;
    private final AuthService authService;
    private final AccountService accountService;
    private final PaymentService paymentService;
    private final RentRepository repository;
    private final DateTimeFormatter isoFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<Transport> getTransportForRent(Double latitude,
                                               Double longitude,
                                               Double radius,
                                               String type
    ) {
        List<Transport> transports = transportService.findAll();
        if (!type.equalsIgnoreCase("all"))
            return transports.stream()
                    .filter(t -> Objects.equals(t.getTransportType(), type) &&
                            Math.abs(Math.sqrt(
                                    Math.pow(t.getLatitude() - latitude, 2) + Math.pow(t.getLongitude() - longitude, 2)
                            )) <= radius
                            &&
                            t.getCanBeRented()
                    ).collect(Collectors.toList());
        else
            return transports.stream().filter(t ->
                    Math.abs(Math.sqrt(
                            Math.pow(t.getLatitude() - latitude, 2) + Math.pow(t.getLongitude() - longitude, 2)
                    )) <= radius
                            && t.getCanBeRented()
            ).collect(Collectors.toList());
    }

    public Rent getRentById(Long rentId) throws RentNotFoundException, MethodNotAllowedException {
        Account currentAccount = authService.me();
        Rent rent = repository.findById(rentId).orElseThrow(() -> new RentNotFoundException("id: (" + rentId + ")"));
        if (rent.getUser().equals(currentAccount) || rent.getTransport().getOwner().equals(currentAccount)) {
            return rent;
        } else {
            throw new MethodNotAllowedException("User must be renter or owner");
        }
    }

    public Rent adminGetRentById(Long rentId) throws RentNotFoundException {
        return repository.findById(rentId).orElseThrow(() -> new RentNotFoundException("id: (" + rentId + ")"));
    }

    public ApiResponse delete(Long id) {
        repository.deleteById(id);
        return new ApiResponse("message", "Rent was deleted");
    }

    public List<Rent> getCurrentUserRentHistory() {
        return repository.findByUser(authService.me());
    }

    public List<Rent> getUserRentHistory(Long userId) {
        return repository.findByUser_Id(userId);
    }

    public List<Rent> getTransportRentHistory(Long transportId) throws TransportNotFoundException, MethodNotAllowedException {
        Account current = authService.me();
        Transport transport = transportService.getTransportById(transportId);
        if (transport.getOwner().equals(current)) {
            return repository.findByTransport_Id(transportId);
        } else {
            throw new MethodNotAllowedException("User must be owner");
        }
    }

    public List<Rent> adminGetTransportRentHistory(String transportId) {
        return repository.findByTransport_Id(Long.parseLong(transportId));
    }

    public Rent startRent(Long transportId,
                          String rentType
    ) throws TransportNotFoundException, MethodNotAllowedException {
        Account current = authService.me();
        Transport transport = transportService.getTransportById(transportId);
        if (!transport.getOwner().equals(current) && transport.getCanBeRented()) {
            transport.setCanBeRented(false);
            Rent rent = new Rent();
            rent.setUser(current);
            rent.setTransport(transport);
            rent.setTimeStart(LocalDateTime.now().format(isoFormat));
            rent.setPriceType(rentType);
            if (rentType.equalsIgnoreCase("minutes")) {
                if (transport.getMinutePrice() != 0) rent.setPriceOfUnit(transport.getMinutePrice());
                else
                    throw new MethodNotAllowedException("The specified type of payment does not coincide with the type of payment specified in the description of transportation");
            } else if (rentType.equalsIgnoreCase("days")) {
                if (transport.getDayPrice() != 0) rent.setPriceOfUnit(transport.getDayPrice());
                else
                    throw new MethodNotAllowedException("The specified type of payment does not coincide with the type of payment specified in the description of transportation");
            } else throw new MethodNotAllowedException("Wrong price type");
            transportService.save(transport);
            return repository.save(rent);
        } else throw new MethodNotAllowedException("User is owner or transport is not available");
    }

    public Rent userEndRent(Long rentId,
                            Double latitude,
                            Double longitude
    ) throws MethodNotAllowedException, RentNotFoundException, UserNotFoundException {
        Account current = authService.me();
        Rent rent = repository.findById(rentId).orElseThrow(() -> new RentNotFoundException("id: (" + rentId + ")"));
        if (rent.getUser().equals(current)) {
            return endRent(latitude, longitude, rent);
        } else throw new MethodNotAllowedException("User must be renter");
    }

    public Rent createRent(RentRequest request) throws UserNotFoundException, TransportNotFoundException, MethodNotAllowedException {
        Rent rent = new Rent();
        return save(rent, request);
    }

    public Rent updateRent(Long id, RentRequest request) throws RentNotFoundException, UserNotFoundException, TransportNotFoundException, MethodNotAllowedException {
        return save(adminGetRentById(id), request);
    }


    private Rent save(Rent rent, RentRequest request) throws TransportNotFoundException, UserNotFoundException, MethodNotAllowedException {
        Transport transport = transportService.getTransportById(request.transportId());
        Account account = accountService.findById(request.userId());
        rent.setTransport(transport);
        rent.setUser(account);
        rent.setTimeStart(request.timeStart());
        rent.setTimeEnd(request.timeEnd());
        rent.setPriceType(request.priceType());
        rent.setPriceOfUnit(request.priceOfUnit());
        rent.setPriceType(request.priceType());
        rent.setFinalPrice(request.finalPrice());
        return repository.save(rent);
    }

    public Rent adminEndRent(Long rentId,
                             Double latitude,
                             Double longitude) throws RentNotFoundException, UserNotFoundException, MethodNotAllowedException {
        Rent rent = repository.findById(rentId).orElseThrow(() -> new RentNotFoundException("id: (" + rentId + ")"));
        return endRent(latitude, longitude, rent);
    }

    private Rent endRent(Double latitude, Double longitude, Rent rent) throws UserNotFoundException, MethodNotAllowedException {
        LocalDateTime end = LocalDateTime.now();
        rent.setTimeEnd(end.format(isoFormat));
        Transport transport = rent.getTransport();
        transport.setLatitude(latitude);
        transport.setLongitude(longitude);
        transport.setCanBeRented(true);
        double finalPrice = getFinalPrice(rent, end);
        rent.setFinalPrice(finalPrice);
        paymentService.payTo(rent.getUser().getId(), transport.getOwner().getId(), finalPrice);
        rent.setTransport(transport);
        transportService.save(transport);
        return repository.save(rent);
    }

    private double getFinalPrice(Rent rent, LocalDateTime end) throws MethodNotAllowedException {
        LocalDateTime start = LocalDateTime.parse(rent.getTimeStart(), isoFormat);
        DateCalculator calculator = new DateCalculator(start, end);
        double finalPrice;
        if (rent.getPriceType().equalsIgnoreCase("minutes")) {
            finalPrice = calculator.calculateMinutes() * rent.getPriceOfUnit();
        } else if (rent.getPriceType().equalsIgnoreCase("days")) {
            finalPrice = calculator.calculateDays() * rent.getPriceOfUnit();
        } else throw new MethodNotAllowedException("Wrong price type");
        return finalPrice;
    }

}
