package com.gang.growup.repository;

import com.gang.growup.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserUsername(String username);
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.username = :username AND t.type = 'income'")
    Double getTotalIncome(@Param("username") String username);
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.username = :username AND t.type = 'expense'")
    Double getTotalExpenses(@Param("username") String username);
}