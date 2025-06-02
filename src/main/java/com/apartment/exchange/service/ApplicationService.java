package com.apartment.exchange.service;

import com.apartment.exchange.model.Application;
import com.apartment.exchange.model.Apartment;
import com.apartment.exchange.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    // Метод пошуку відповідної заявки та видалення її
    public Application findAndRemoveMatchingApplication(Application newApplication) {
        List<Application> allApplications = repository.findAll();

        for (Application existing : allApplications) {
            Apartment offered = existing.getOfferedApartment();
            Apartment desired = existing.getDesiredApartment();

            Apartment newOffered = newApplication.getOfferedApartment();
            Apartment newDesired = newApplication.getDesiredApartment();

            // Перевірка збігу: кількість кімнат і поверховість
            boolean matchRoomsAndFloor =
                    desired.getRooms() == newOffered.getRooms() &&
                            desired.getFloor() == newOffered.getFloor() &&
                            offered.getRooms() == newDesired.getRooms() &&
                            offered.getFloor() == newDesired.getFloor();

            // Перевірка площі в межах 10% від площі нової заявки
            boolean matchArea =
                    Math.abs(desired.getArea() - newOffered.getArea()) / newOffered.getArea() <= 0.1 &&
                            Math.abs(offered.getArea() - newDesired.getArea()) / newDesired.getArea() <= 0.1;

            if (matchRoomsAndFloor && matchArea) {
                // Збіг знайдено — видаляємо стару заявку
                repository.delete(existing);
                return existing;
            }
        }

        // Збігів не знайдено
        return null;
    }

    // Додавання заявки
    public void addApplication(Application application) {
        repository.save(application);
    }

    public List<Application> getAllApplications() {
        return repository.findAll();
    }

}
