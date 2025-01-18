package main;

public class teacher {
    private int id;
    private String name;
    private String department;
    private String course;
    private String day;
    private String time;
    private float payrate;
    private float hoursPerDay;
    private float totalHoursPerWeek;
    private float totalHoursPerMonth;  
    private float grossSalary;         
    private float netSalary;           

    // Constructor
    public teacher(int id, String name, String department, String course, String day, String time, float payrate, float hoursPerDay, float totalHoursPerWeek, float totalHoursPerMonth, float grossSalary, float netSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.course = course;
        this.day = day;
        this.time = time;
        this.payrate = payrate;
        this.hoursPerDay = hoursPerDay;
        this.totalHoursPerWeek = totalHoursPerWeek;
        this.totalHoursPerMonth = totalHoursPerMonth;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getCourse() {
        return course;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public float getPayrate() {
        return payrate;
    }

    public float getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(float hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public float getTotalHoursPerWeek() {
        return totalHoursPerWeek;
    }

    public void setTotalHoursPerWeek(float totalHoursPerWeek) {
        this.totalHoursPerWeek = totalHoursPerWeek;
    }

    // New Getters and Setters
    public float getTotalHoursPerMonth() {
        return totalHoursPerMonth;
    }

    public void setTotalHoursPerMonth(float totalHoursPerMonth) {
        this.totalHoursPerMonth = totalHoursPerMonth;
    }

    public float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(float grossSalary) {
        this.grossSalary = grossSalary;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(float netSalary) {
        this.netSalary = netSalary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Course: " + course + ", Day: " + day + ", Time: " + time + ", Payrate: " + payrate +
               ", Total Hours per Month: " + totalHoursPerMonth + ", Gross Salary: " + grossSalary + ", Net Salary: " + netSalary;
    }
}
// Commit 12
// Commit 26
// Commit 40
