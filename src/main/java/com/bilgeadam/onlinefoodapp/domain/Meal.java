package com.bilgeadam.onlinefoodapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Entity(name = "MEAL")
public class Meal {

    @Id
    @Column(name = "CODE")
    private String code;

    @OneToOne
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
    private Employee employee;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "PHOTO")
    private String photo;

    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "CREATION_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date creationDate;

    public Meal() {
    }

    public Meal(String code, Employee employee, String name, Long price, String photo, String detail, Date creationDate) {
        this.code = code;
        this.employee = employee;
        this.name = name;
        this.price = price;
        this.photo = photo;
        this.detail = detail;
        this.creationDate = creationDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
