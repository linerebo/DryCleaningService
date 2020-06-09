USE master;

--DROP DATABASE DryCleaningServiceDB;

GO

CREATE DATABASE DryCleaningServiceDB;

GO

USE DryCleaningServiceDB;

--strong tables

CREATE TABLE tblPostalCode (
    fldZipCode VARCHAR(4) PRIMARY KEY,
    fldCity VARCHAR(100) NOT NULL,
);

CREATE TABLE tblCustomer (
    fldCustomerID INT PRIMARY KEY,
    fldCustomerFirstName VARCHAR(50) NOT NULL,
    fldCustomerLastName VARCHAR(50) NOT NULL,
    fldCustomerPhone VARCHAR(20) NOT NULL,
    fldCustomerMail VARCHAR(100) NOT NULL
);

CREATE TABLE tblLaundryType (
    fldLaundryTypeID INT PRIMARY KEY,
    fldLaundryTypeName VARCHAR(30) NOT NULL,
    fldLaundryTypePrice INT NOT NULL,
    fldLaundryTypeTimeToClean INT NOT NULL,

);

CREATE TABLE tblDepartment (
    fldDepartmentID INT PRIMARY KEY,
    fldDepartmentName VARCHAR(200) NOT NULL
);

CREATE TABLE tblEventType(
	fldEventTypeID INT PRIMARY KEY,
	fldEventTypeName VARCHAR(50) NOT NULL,
	fldEventTypeDescription VARCHAR(200)
);

-- weak tables

CREATE TABLE tblSystemUser (
    fldSystemUserID INT PRIMARY KEY,
    fldSystemUserFirstName VARCHAR(50), --possible to be null
    fldSystemUserLastName VARCHAR(50), --possible to be null
	fldSystemUserPassword VARCHAR(4) NOT NULL,
    fldDepartmentID INT NOT NULL,
    FOREIGN KEY(fldDepartmentID) REFERENCES tblDepartment(fldDepartmentID)
);

CREATE TABLE tblDeliveryPoint (
    fldDeliveryPointID INT PRIMARY KEY,
    fldDeliveryPointAddress VARCHAR(100) NOT NULL,
    fldZipCode VARCHAR(4) NOT NULL,
    FOREIGN KEY(fldZipCode) REFERENCES tblPostalCode(fldZipCode),
    fldDeliveryPointRoute VARCHAR(10) NOT NULL,
);

CREATE TABLE tblLaundryItem (
    fldLaundryItemID INT PRIMARY KEY,
    fldLaundryTypeID INT NOT NULL,
    FOREIGN KEY(fldLaundryTypeID) REFERENCES tblLaundryType(fldLaundryTypeID),
    fldLaundryItemColor VARCHAR(50),
    fldLaundryItemStatus BIT NOT NULL, -- BIT = boolean, data ikke i ''
);

CREATE TABLE tblLaundrySize (
	fldLaundrySizeID INT PRIMARY KEY,
	fldLaundryItemID INT NOT NULL,
	FOREIGN KEY(fldLaundryItemID) REFERENCES tblLaundryItem(fldLaundryItemID),
	fldLaundrySize INT
);

CREATE TABLE tblOrder (
    fldOrderID INT PRIMARY KEY,
    fldCustomerID INT NOT NULL,
    FOREIGN KEY(fldCustomerID) REFERENCES tblCustomer(fldCustomerID),
    fldOrderAmount INT NOT NULL,
    fldDeliveryPointID INT NOT NULL,
    FOREIGN KEY(fldDeliveryPointID) REFERENCES tblDeliveryPoint(fldDeliveryPointID)
);

CREATE TABLE tblEventHistory (
    fldEventID INT PRIMARY KEY,
    fldOrderID INT,
	FOREIGN KEY(fldOrderID) REFERENCES tblOrder(fldOrderID),
    fldEventDateTimeStamp DATETIME2 NOT NULL, --datetime 2 is more precise and does not use rounding, like datetime
	fldEventTypeID INT,
	FOREIGN KEY(fldEventTypeID) REFERENCES tblEventType(fldEventTypeID),
	fldSystemUserID INT,
	FOREIGN KEY(fldSystemUserID) REFERENCES tblSystemUser(fldSystemUserID),
	fldEventHistoryCurrentStatus BIT NOT NULL
);

CREATE TABLE tblPayment (
    fldPaymentID INT PRIMARY KEY,
    fldOrderID INT NOT NULL,
    FOREIGN KEY(fldOrderID) REFERENCES tblOrder(fldOrderID),
    fldPaymentDate DATETIME2,
    fldPaymentStatus BIT NOT NULL,
    fldPaymentInvoiceTotal INT
);

CREATE TABLE tblLaundry_Order (
    fldLaundry_OrderID INT PRIMARY KEY,
    fldLaundryItemID INT NOT NULL,
    FOREIGN KEY(fldLaundryItemID) REFERENCES tblLaundryItem(fldLaundryItemID),
    fldOrderID INT NOT NULL,
    FOREIGN KEY(fldOrderID) REFERENCES tblOrder(fldOrderID)
);

CREATE TABLE tblNotAssignableLaundryItems (
    fldNotAssignableLaundryItemsID INT PRIMARY KEY,
    fldLaundryTypeID INT NOT NULL,
    FOREIGN KEY(fldLaundryTypeID) REFERENCES tblLaundryType(fldLaundryTypeID),
    fldColor VARCHAR(50),
    fldFound DATETIME2,
    fldStatus BIT NOT NULL
);

GO

--sequence

--USE DryCleaningServiceDB;

CREATE SEQUENCE dbo.SequenceGenerateIDs
	AS INT
	START WITH 1 INCREMENT BY 1
	MINVALUE 1 MAXVALUE 2147483647 -- 2147483647 is the maximum value for a variable of type int
	CYCLE; -- will start from 1 again, if maxvalue is reached

GO

--stored procedures

CREATE PROCEDURE sp_generateNewID
AS
	BEGIN
		SELECT NEXT VALUE FOR [dbo].[SequenceGenerateIDs]
	END;

--EXECUTE sp_generateNewID; -- tested in sql server, works

GO

CREATE PROCEDURE [dbo].[sp_getAmountOfBlazers]
AS
	BEGIN
		SELECT COUNT(fldLaundryTypeID) AS Blazers
		FROM tblLaundryItem
		WHERE fldLaundryTypeID = 5;
	END;
	
	--EXECUTE sp_getAmountOfBlazers;
GO

CREATE PROCEDURE [dbo].[sp_getAmountOfCarpets]
AS
	BEGIN
		SELECT COUNT(fldLaundryTypeID) AS Carpets
		FROM tblLaundryItem
		WHERE fldLaundryTypeID = 6;
	END;
	
	--EXECUTE sp_getAmountOfCarpets;
GO

CREATE PROCEDURE [dbo].[sp_getAmountOfCoats]
AS
	BEGIN
		SELECT COUNT(fldLaundryTypeID) AS Coats
		FROM tblLaundryItem
		WHERE fldLaundryTypeID = 7;
	END;
	
	--EXECUTE sp_getAmountOfCoats;
GO

CREATE PROCEDURE [dbo].[sp_getAmountOfDresses]
AS
	BEGIN
		SELECT COUNT(fldLaundryTypeID) AS Dresses
		FROM tblLaundryItem
		WHERE fldLaundryTypeID = 8;
	END;
	
	--EXECUTE sp_getAmountOfDresses;
GO

CREATE PROCEDURE [dbo].[sp_getAmountOfShirts]
AS
	BEGIN
		SELECT COUNT(fldLaundryTypeID) AS Shirts
		FROM tblLaundryItem
		WHERE fldLaundryTypeID = 4;
	END;
	
	--EXECUTE sp_getAmountOfShirts; --tested in sql server, works
GO

CREATE PROCEDURE [dbo].[sp_getAmountOfTrousers]
AS
	BEGIN
		SELECT COUNT(fldLaundryTypeID) AS sp_getAmountOfTrousers
		FROM tblLaundryItem
		WHERE fldLaundryTypeID = 10;
	END;
	
	--EXECUTE sp_getAmountOfTrousers;
GO

CREATE PROCEDURE [dbo].[sp_getAmountOfTShirts]
AS
	BEGIN
		SELECT COUNT(fldLaundryTypeID) AS TShirts
		FROM tblLaundryItem
		WHERE fldLaundryTypeID = 9;
	END;
	
	--EXECUTE sp_getAmountOfTShirts;
GO

CREATE PROCEDURE sp_getIncomingOrdersPerRoute
AS
	BEGIN
		SELECT tblDeliveryPoint.fldDeliveryPointRoute,
			COUNT (*) AS OrdersPerRoute
				FROM tblOrder JOIN 
				tblDeliveryPoint
				ON tblOrder.fldDeliveryPointID = tblDeliveryPoint.fldDeliveryPointID
				GROUP BY fldDeliveryPointRoute
		END;

		--EXECUTE sp_getIncomingOrdersPerRoute; --works in SQL Server
GO

--test data set

-- data for tblPostalCode
INSERT tblPostalCode (fldZipCode, fldCity)
    VALUES ('6000', 'Kolding');
INSERT tblPostalCode (fldZipCode, fldCity)
    VALUES ('9000', 'Aalborg');
INSERT tblPostalCode (fldZipCode, fldCity)
    VALUES ('5000', 'Odense');

GO

-- data for tblCustomer
INSERT tblCustomer (fldCustomerID, fldCustomerFirstName, fldCustomerLastName, fldCustomerPhone, fldCustomerMail)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Steffi', 'Myra', '56781390', 's.myra@gmail.com');
INSERT tblCustomer (fldCustomerID, fldCustomerFirstName, fldCustomerLastName, fldCustomerPhone, fldCustomerMail)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Markus', 'Kramer', '16752300', 'm.kramer@gmail.com');
INSERT tblCustomer (fldCustomerID, fldCustomerFirstName, fldCustomerLastName, fldCustomerPhone, fldCustomerMail)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Nadja', 'Askov', '10946723', 'n.askov@gmail.com');

GO

-- data for tblLaundryTypes
INSERT tblLaundryType (fldLaundryTypeID, fldLaundryTypeName, fldLaundryTypePrice, fldLaundryTypeTimeToClean)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Shirt', '25', '3');
INSERT tblLaundryType (fldLaundryTypeID, fldLaundryTypeName, fldLaundryTypePrice, fldLaundryTypeTimeToClean)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Blazer', '50', '3');
INSERT tblLaundryType (fldLaundryTypeID, fldLaundryTypeName, fldLaundryTypePrice, fldLaundryTypeTimeToClean)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Carpet', '20', '6');
INSERT tblLaundryType (fldLaundryTypeID, fldLaundryTypeName, fldLaundryTypePrice, fldLaundryTypeTimeToClean)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Coat', '80', '4');
INSERT tblLaundryType (fldLaundryTypeID, fldLaundryTypeName, fldLaundryTypePrice, fldLaundryTypeTimeToClean)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Dress', '100', '4');
INSERT tblLaundryType (fldLaundryTypeID, fldLaundryTypeName, fldLaundryTypePrice, fldLaundryTypeTimeToClean)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'T-Shirt', '20', '3');
INSERT tblLaundryType (fldLaundryTypeID, fldLaundryTypeName, fldLaundryTypePrice, fldLaundryTypeTimeToClean)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Trousers', '30', '3');

GO

-- data for tblDepartment
INSERT tblDepartment (fldDepartmentID, fldDepartmentName)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Shop Assistant');
INSERT tblDepartment (fldDepartmentID, fldDepartmentName)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Driver');
INSERT tblDepartment (fldDepartmentID, fldDepartmentName)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Desk Assistant');
INSERT tblDepartment (fldDepartmentID, fldDepartmentName)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Manager');

GO

--data for tblEventType
INSERT tblEventType (fldEventTypeID, fldEventTypeName, fldEventTypeDescription)
	VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Creation', 'The Customer handed in the laundry and a new order is created.');
INSERT tblEventType (fldEventTypeID, fldEventTypeName, fldEventTypeDescription)
	VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Pick-Up Transportation', 'The order got picked up by the driver to get transported from the delivery point to the cleaning central.');
INSERT tblEventType (fldEventTypeID, fldEventTypeName, fldEventTypeDescription)
	VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Unload at Cleaning Central', 'The Driver unloads the laundry at the Cleaning Central.');
INSERT tblEventType (fldEventTypeID, fldEventTypeName, fldEventTypeDescription)
	VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Cleaning Process Start', 'The order arrived at the cleaning central and the laundry items got a washable lable, so the cleaning process can start.');
INSERT tblEventType (fldEventTypeID, fldEventTypeName, fldEventTypeDescription)
	VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Cleaning Finished', 'All laundry items of the order are cleaned and sorted.');
INSERT tblEventType (fldEventTypeID, fldEventTypeName, fldEventTypeDescription)
	VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Return Transportation', 'The order got picked up by the driver to get transported from the cleaning central to the delivery point.');
INSERT tblEventType (fldEventTypeID, fldEventTypeName, fldEventTypeDescription)
	VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Ready', 'The order has arrived back at the delivery point and is ready to be collected by the customer.');
INSERT tblEventType (fldEventTypeID, fldEventTypeName, fldEventTypeDescription)
	VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Handed Back', 'The order got returned to the customer.');

GO

-- data for tblSystemUser
INSERT tblSystemUser (fldSystemUserID, fldSystemUserFirstName, fldSystemUserLastName, fldSystemUserPassword, fldDepartmentID)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Hans', 'Hansen', '1111', (SELECT fldDepartmentID FROM tblDepartment WHERE fldDepartmentName = 'Driver'));    -- a driver
INSERT tblSystemUser (fldSystemUserID, fldSystemUserFirstName, fldSystemUserLastName, fldSystemUserPassword, fldDepartmentID)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Lars', 'Larssen', '2222', (SELECT fldDepartmentID FROM tblDepartment WHERE fldDepartmentName = 'Driver'));    -- a driver
INSERT tblSystemUser (fldSystemUserID, fldSystemUserFirstName, fldSystemUserLastName, fldSystemUserPassword, fldDepartmentID)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, '', '', '3333', (SELECT fldDepartmentID FROM tblDepartment WHERE fldDepartmentName = 'Shop Assistant'));    -- any shop assistant
INSERT tblSystemUser (fldSystemUserID, fldSystemUserFirstName, fldSystemUserLastName, fldSystemUserPassword, fldDepartmentID)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, '', '', '4444', (SELECT fldDepartmentID FROM tblDepartment WHERE fldDepartmentName = 'Desk Assistant'));    -- any desk assistant
INSERT tblSystemUser (fldSystemUserID, fldSystemUserFirstName, fldSystemUserLastName, fldSystemUserPassword, fldDepartmentID)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, '', '', '5555', (SELECT fldDepartmentID FROM tblDepartment WHERE fldDepartmentName = 'Manager'));    -- any manager

GO

-- data for tblDeliveryPoint
INSERT tblDeliveryPoint (fldDeliveryPointID, fldDeliveryPointAddress, fldZipCode, fldDeliveryPointRoute)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Havnegade 3', '6000', 'SOUTH');
INSERT tblDeliveryPoint (fldDeliveryPointID, fldDeliveryPointAddress, fldZipCode, fldDeliveryPointRoute)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Noerregade 5', '9000', 'NORTH');
INSERT tblDeliveryPoint (fldDeliveryPointID, fldDeliveryPointAddress, fldZipCode, fldDeliveryPointRoute)
    VALUES (NEXT VALUE FOR dbo.SequenceGenerateIDs, 'Lille Straede 7', '5000', 'EAST');

GO
