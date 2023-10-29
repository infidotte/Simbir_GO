package simbir.go.simbir_go.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simbir.go.simbir_go.Entity.Account;
import simbir.go.simbir_go.Entity.Transport;
import simbir.go.simbir_go.Exception.MethodNotAllowedException;
import simbir.go.simbir_go.Exception.TransportNotFoundException;
import simbir.go.simbir_go.Record.ApiResponse;
import simbir.go.simbir_go.Record.TransportRequest;
import simbir.go.simbir_go.Repository.TransportRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;

    private final AuthService authService;

    public List<Transport> findAll() {
        return transportRepository.findAll();
    }

    public Transport getTransportById(Long id) throws TransportNotFoundException {
        return transportRepository.findById(id).orElseThrow(() -> new TransportNotFoundException("id ( " + id + " )"));
    }

    public Transport createTransport(TransportRequest request, Account owner) {
        Transport transport = new Transport();
        return setTransportAndSave(request, transport, owner);
    }

    public Transport updateTransport(Long id, TransportRequest request) throws TransportNotFoundException {
        Transport transport = transportRepository.findById(id).orElseThrow(() -> new TransportNotFoundException("id ( " + id + " )"));
        return setTransportAndSave(request, transport, transport.getOwner());
    }

    private Transport setTransportAndSave(TransportRequest request, Transport transport, Account owner) {
        transport.setOwner(owner);
        transport.setCanBeRented(request.getCanBeRented());
        transport.setTransportType(request.getTransportType().toLowerCase());
        transport.setModel(request.getModel().toLowerCase());
        transport.setColor(request.getColor().toLowerCase());
        transport.setIdentifier(request.getIdentifier());
        transport.setDescription(request.getDescription());
        transport.setLatitude(request.getLatitude());
        transport.setLongitude(request.getLongitude());
        transport.setMinutePrice(request.getMinutePrice());
        transport.setDayPrice(request.getDayPrice());
        return transportRepository.save(transport);
    }

    public ApiResponse deleteTransport(Long id) throws TransportNotFoundException, MethodNotAllowedException {
        Transport transport = transportRepository.findById(id).orElseThrow(() -> new TransportNotFoundException("id ( " + id + " )"));
        Account current = authService.me();
        if (transport.getOwner().equals(current) || current.getRole().getName().equals("ROLE_ADMIN")) {
            transportRepository.deleteById(id);
            return new ApiResponse("message", "Successfully delete");
        } else {
            throw new MethodNotAllowedException("User must be owner");
        }
    }

    public Transport save(Transport transport) {
        return transportRepository.save(transport);
    }

    public List<Transport> findAllAfterIdAndLessThenCount(Long start, Integer count, String transportType) {
        if (transportType.equalsIgnoreCase("all")) {
            return transportRepository.findByIdAfter(start).stream().limit(count).collect(Collectors.toList());
        } else {
            return transportRepository.findByIdAfterAndTransportType(start, transportType).stream().limit(count).collect(Collectors.toList());
        }
    }
}
