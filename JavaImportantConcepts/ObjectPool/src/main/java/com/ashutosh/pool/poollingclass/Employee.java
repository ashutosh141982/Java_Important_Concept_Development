package com.ashutosh.pool.poollingclass;

public class Employee implements  ObjectPolling{
    int employeeID;
    String employeeName;
    Address address;

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void clean() {
        this.setEmployeeID(-1);
        this.setEmployeeName("");
        this.getAddress().setFullAddress("");
        this.getAddress().setCity("");
        this.getAddress().setCountry("");
        this.getAddress().setState("");
        this.getAddress().getPhoneNumbers().clear();
        System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE------------CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", employeeName='" + employeeName + '\'' +
                ", address=" + address +
                '}';
    }
}
