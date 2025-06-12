package com.ashutosh.pool.poollingclass;

public class Student implements ObjectPolling{

    int studentID;
    String studentName;
    Address address;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    /**
     * This method uses list.clear() so need to implement correct List implementation which supports clear() method
     */
    public void clean() {
        this.setStudentID(-1);
        this.setStudentName("");
        this.getAddress().setFullAddress("");
        this.getAddress().setCity("");
        this.getAddress().setCountry("");
        this.getAddress().setState("");
        this.getAddress().getPhoneNumbers().clear();
        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS------------CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", studentName='" + studentName + '\'' +
                ", address=" + address +
                '}';
    }
}
