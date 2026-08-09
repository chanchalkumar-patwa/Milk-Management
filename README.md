# 🥛 Milk Management System

A web-based **Milk Management System** developed to digitize and simplify the daily operations of a dairy business. The system manages farmers, milk collection, customers, milk distribution, authentication, reports, and graphical statistics through a centralized platform.

The application is developed using **Java JSP, Servlets, JDBC, and MySQL**, and is deployed on **Apache Tomcat**.

---

## 📌 Project Overview

The Milk Management System is designed to replace manual dairy management processes with a computerized system.

In a traditional dairy environment, maintaining farmer records, recording daily milk collection, calculating milk payments, managing customer distribution, and preparing reports manually can be time-consuming and error-prone.

This system provides a centralized solution where an administrator can:

- Manage farmer information
- Manage customer information
- Record daily milk collection
- Record milk quality parameters
- Calculate milk collection amounts
- Record milk distribution
- Manage registered and unregistered customers
- Generate collection and distribution reports
- Filter reports based on different criteria
- View graphical statistics
- Export collection reports to Excel
- Secure the application using administrator authentication

---

## 🎯 Objectives

The primary objectives of the project are:

1. To automate daily milk collection management.
2. To maintain centralized farmer and customer records.
3. To reduce errors in manual calculations.
4. To calculate milk payments based on quantity and rate.
5. To maintain milk distribution records.
6. To provide useful reports for dairy management.
7. To provide graphical representation of milk distribution data.
8. To improve data accessibility and organization.
9. To provide authentication and session-based security.
10. To reduce paperwork and manual record maintenance.

---

## 🚀 Features

### 🔐 Admin Authentication

- Secure administrator login
- Session-based authentication
- Protected pages for authenticated users
- Logout functionality
- Session validation
- Prevention of unauthorized access after logout

---

### 👨‍🌾 Farmer Management

The system allows administrators to manage dairy farmers.

#### Available Operations

- Add new farmer
- View farmer records
- Update farmer information
- Delete farmer information
- Store farmer contact details
- Store farmer email and address
- Assign a unique Farmer ID

#### Farmer Information

- Farmer ID
- Name
- Contact Number
- Email
- Address

---

### 🥛 Milk Collection Management

The milk collection module is used to record milk received from farmers.

Administrators can record:

- Farmer
- Collection Date
- Shift
- Milk Quantity
- Fat Percentage
- SNF Percentage
- Rate per Liter
- Total Amount

#### Supported Shifts

- Morning
- Evening

The system calculates the total collection amount based on the quantity of milk and applicable rate.

### Milk Quality Parameters

The system maintains important milk quality information such as:

- Fat %
- SNF %

This allows the dairy to maintain more detailed collection records and analyze milk quality.

---

### 👥 Customer Management

The customer module allows the administrator to maintain customer information.

#### Customer Information

- Customer ID
- Name
- Contact Number
- Email
- Address

The contact number is used as an important reference for identifying customers during distribution and reporting.

---

### 🚚 Milk Distribution Management

The distribution module records milk supplied to customers.

The administrator can record:

- Customer
- Customer Contact Number
- Distribution Date
- Shift
- Quantity in Liters
- Rate per Liter
- Total Amount

#### Supported Customers

The system supports both:

**Registered Customers**

Customers already available in the customer database.

**Unregistered Customers**

Customers who are not registered can still receive milk. Their relevant information can be recorded directly during the distribution process.

This makes the system flexible for real-world dairy operations.

---

## 📊 Reports

The system provides reporting functionality for analyzing milk collection records.

Reports can be filtered using different parameters.

### Available Filters

- Start Date
- End Date
- Shift
- Mobile Number

The date range is used to generate reports for a specific period, while additional filters can be used when required.

### Report Information

The generated report can contain information such as:

- Farmer details
- Collection date
- Shift
- Quantity
- Fat percentage
- SNF percentage
- Rate per liter
- Total amount

The results are dynamically displayed on the reports page.

---

## 📈 Graphical Statistics

The system provides graphical representation of milk distribution data using **JFreeChart**.

One of the implemented statistics compares milk distribution between:

- Morning Shift
- Evening Shift

This provides dairy administrators with a quick visual understanding of distribution patterns.

The chart functionality can be used to analyze recent distribution activity and identify differences between shifts.

---

## SQL Query

CREATE DATABASE dairy_db;
use dairy_db;
CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL
);


CREATE TABLE farmer (
	farmer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL, 
    contact_number VARCHAR(15), 
    email VARCHAR(20),
    address VARCHAR(200)
);

ALTER TABLE farmer 
ADD CONSTRAINT unique_email UNIQUE (email),
ADD CONSTRAINT unique_contact UNIQUE (contact_number);

CREATE TABLE collection ( 
    entry_id INT AUTO_INCREMENT PRIMARY KEY,
    farmer_id INT,
    entry_date DATE NOT NULL,
    shift ENUM('Morning', 'Evening') NOT NULL,
    quantity_liters DECIMAL(8,2) NOT NULL,
    fat_percentage DECIMAL(5,2),
    snf_percentage DECIMAL(5,2),
    rate_per_liter DECIMAL(8,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (farmer_id) REFERENCES farmer(farmer_id)
);

CREATE TABLE customer (
	customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL, 
    contact_number VARCHAR(15) UNIQUE NOT NULL,
    address VARCHAR(200) NOT NULL,
    email VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE distribution (
    distribution_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NULL,
    customer_name VARCHAR(100),
    contact_number VARCHAR(15),
    distribution_date DATE NOT NULL,
    shift ENUM('Morning', 'Evening') NOT NULL,
    quantity_liters DECIMAL(8,2) NOT NULL,
    rate_per_liter DECIMAL(8,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);
