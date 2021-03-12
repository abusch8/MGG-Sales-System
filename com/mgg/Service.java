package com.mgg;

public class Service extends Item {

    private double hourlyRate;
    private Employee employee;
    private double numHours;

    public Service(String code, String name, double hourlyRate, Employee employee, double numHours) {
        super(code, name);
        this.hourlyRate = hourlyRate;
        this.employee = employee;
        this.numHours = numHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public double getNumHours() {
        return numHours;
    }
}
