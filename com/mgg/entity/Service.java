package com.mgg.entity;

public class Service extends Item {

    private final double hourlyRate;
    private com.mgg.entity.Employee employee;
    private double numHours;

    public Service(String code, String name, double hourlyRate) {
        super(code, name);
        this.hourlyRate = hourlyRate;
    }

    public Service(Service service, com.mgg.entity.Employee employee, double numHours) {
        this(service.getCode(), service.getName(), service.getHourlyRate());
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

    public double calculatePrice() {
        return (double) Math.round((this.hourlyRate * this.numHours) * 100) / 100;
    }

    public double calculateTax() {
        return (double) Math.round(((this.hourlyRate * this.numHours) * .0285) * 100) / 100;
    }

    /**
     * Puts the entire receipt for a sale into a string format
     *
     * @return receipt in a formatted string
     */
    public String receiptToString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(String.format("(Service #%s by %s, %s %.2fhrs@$%.2f/hr)", this.getCode(), this.getEmployee().getLastName(), this.getEmployee().getFirstName(), this.getNumHours(), this.getHourlyRate()));
        formattedString.append(" ".repeat(Math.max(0, 57 - formattedString.length())));
        return String.format("%s\n    %s$%10.2f", this.getName(), formattedString, this.calculatePrice());
    }
}
