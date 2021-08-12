package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.UserPayment;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long>{

}
