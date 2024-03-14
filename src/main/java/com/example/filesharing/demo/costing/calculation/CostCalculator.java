package com.example.filesharing.demo.costing.calculation;

import com.example.filesharing.demo.costing.types.Expression;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostCalculator {

    private String name = "";
    private double hourlyRate = 50;
    private double dailyRate = 8;
    private double monthlyRate = 22;
    private double weeklyRate = 5;
    private double yearlyRate = 12;
    private double taxRate = 10;

    private double subtotal = 0;
    private double total = 0;
    private double tax = 0;
    private double hourly = 0;
    private double daily = 0;
    private double weekly = 0;
    private double monthly = 0;
    private double yearly = 0;

    public CostCalculator(){
        monthlyRate = Math.round(30.43 / 7 * weeklyRate);
        yearlyRate = monthlyRate * yearlyRate;
    }

    public double calculateCost(String period) {
        double cost = 0;
        switch(period.toLowerCase()) {
            case "hourly":
                cost = hourlyRate;
                break;
            case "daily":
                cost = hourlyRate * dailyRate;
                break;
            case "weekly":
                cost = hourlyRate * dailyRate * weeklyRate;
                break;
            case "monthly":
                cost = hourlyRate * dailyRate * monthlyRate;
                break;
            case "yearly":
                cost = hourlyRate * dailyRate * monthlyRate * yearlyRate;
                break;
            default:
                cost = hourlyRate * dailyRate;
                break;
        }
        return cost + (cost * taxRate / 100);
    }

    public double calculateExpression(double num1, double num2, Expression expression) {
        switch (expression) {
            case PLUS:
                return num1 + num2;
            case MINUS:
                return num1 - num2;
            case MULTIPLY:
                return num1 * num2;
            case DIVIDE:
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
            default:
                throw new IllegalArgumentException("Invalid math symbol");
        }
    }

    public CostCalculator calculateSubtotal(){
        return this;
    }
    
    public CostCalculator calculateTax(){
        return this;
    }

    public CostCalculator calculateTotal(){
        return this;
    }

    public CostCalculator calculateHourly(){
        return this;
    }

    public CostCalculator calculateDaily(){
        daily = hourlyRate * dailyRate;
        return this;
    }

    public CostCalculator calculateWeekly(){
        weekly = hourlyRate * dailyRate * weeklyRate;
        return this;
    }

    public CostCalculator calculateMonthly(){
        monthly = hourlyRate * dailyRate * monthlyRate;
        return this;
    }

    public CostCalculator calculateYearly(){
        yearly = hourlyRate * dailyRate * monthlyRate * yearlyRate;
        return this;
    }
}
