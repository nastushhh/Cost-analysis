package com.example.analysis_cost.repository;

import com.example.analysis_cost.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByCategory(String category);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.timestamp BETWEEN :start AND :end")
    Double findTotalExpensesForPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT e.category, COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.timestamp BETWEEN :start AND :end GROUP by e.category")
    List<Object[]> findTotalExpensesByCategoriesForPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT e.category, COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.category = :category AND e.timestamp BETWEEN :start AND :end GROUP by e.category")
    List<Object[]> findTotalExpenseByCategoryForPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("category") String category);
}