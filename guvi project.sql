create database smartbanksystem;
   use smartbanksystem;
CREATE TABLE loginss (
    card_number VARCHAR(20) UNIQUE NOT NULL,pin VARCHAR(10) NOT NULL);
   select * from loginss ;
CREATE TABLE signup1(form_no VARCHAR(20) PRIMARY KEY, name VARCHAR(100),father_name VARCHAR(100),dob date,gender VARCHAR(10),
email VARCHAR(100),marital_status VARCHAR(20),address VARCHAR(255),city VARCHAR(50),pin_code VARCHAR(10),state VARCHAR(50));
select * from signup1 ;
CREATE TABLE signup2 (form_no VARCHAR(20),religion VARCHAR(50),category VARCHAR(50),income VARCHAR(50),education
 VARCHAR(100),occupation VARCHAR(50),pan_no VARCHAR(20),aadhar_no VARCHAR(20),senior_citizen VARCHAR(10),existing_account VARCHAR(10),
 PRIMARY KEY (form_no));
select * from signup2 ;
CREATE TABLE signup3 (form_no VARCHAR(20),account_type VARCHAR(50),card_number VARCHAR(20),pin VARCHAR(10),services TEXT,
PRIMARY KEY (form_no));
select * from signup3 ;
CREATE TABLE banks (id INT AUTO_INCREMENT PRIMARY KEY,pin VARCHAR(10) NOT NULL,date DATETIME DEFAULT CURRENT_TIMESTAMP , 
type VARCHAR(20) CHECK (type IN ('Deposit', 'Withdraw')),amount DECIMAL(10,2) NOT NULL);
select * from banks ;
