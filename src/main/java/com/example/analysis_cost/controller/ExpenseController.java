package com.example.analysis_cost.controller;


import com.example.analysis_cost.model.Expense;
import com.example.analysis_cost.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getAllExpenses(){
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable String category){
        return expenseService.getExpensesByCategory(category);
    }

    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Integer id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Integer id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/total/{year}/{month}")
    public Double getTotalExpensesForMonth(@PathVariable int year, @PathVariable int month) {
        return expenseService.getTotalExpensesForMonth(year, month);
    }

    @GetMapping("/total-by-categories/{year}/{month}")
    public Map<String, Double> getTotalExpensesByCategories(@PathVariable int year, @PathVariable int month) {
        return expenseService.getTotalExpensesByCategoriesForMonth(year, month);
    }

    @GetMapping("/total-by-category/{category}/{year}/{month}")
    public Map<String, Double>  getTotalExpensesByCategory(@PathVariable String category, @PathVariable int year, @PathVariable int month) {
        return expenseService.getTotalExpensesByCategoryForMonth(year, month, category);
    }
}