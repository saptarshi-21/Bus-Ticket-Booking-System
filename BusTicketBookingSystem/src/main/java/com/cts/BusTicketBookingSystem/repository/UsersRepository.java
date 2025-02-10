package com.cts.BusTicketBookingSystem.repository;

import com.cts.BusTicketBookingSystem.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByEmail(String email);
}