package com.example.analysis_cost.service;

import com.example.analysis_cost.model.Expense;
import com.example.analysis_cost.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    public final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Integer id, Expense updatedExpense) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setTimestamp(updatedExpense.getTimestamp());

        return expenseRepository.save(existingExpense);
    }

    public void deleteExpense(Integer id) {
        expenseRepository.deleteById(id);
    }

    public Double getTotalExpensesForMonth(int year, int month) {
        LocalDateTime startMonth = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);
        return expenseRepository.findTotalExpensesForPeriod(startMonth, endMonth);
    }

    public Map<String, Double> getTotalExpensesByCategoriesForMonth(int year, int month){
        LocalDateTime startMonth = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Object[]> results = expenseRepository.findTotalExpensesByCategoriesForPeriod(startMonth, endMonth);
        return results.stream()
                .collect(Collectors.toMap(
                        result->(String)result[0],
                        result->(Double)result[1]
                ));


    }

    public Map<String, Double> getTotalExpensesByCategoryForMonth(int year, int month, String category){
        LocalDateTime startMonth = YearMonth.of(year, month).atDay(1).atStartOfDay();
        LocalDateTime endMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Object[]> resultCategory = expenseRepository.findTotalExpenseByCategoryForPeriod(startMonth, endMonth, category);

        if (resultCategory.isEmpty()) {
            return Map.of(category, 0.0);
        }

        return resultCategory.stream()
                .collect(Collectors.toMap(
                        result->(String)result[0],
                        result->(Double)result[1]
                ));
    }



}