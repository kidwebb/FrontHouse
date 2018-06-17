USE ContactDB
GO

--DROP TABLE dbo.Employee

/****** Object:  Table dbo.Store    Script Date: 6/15/2018 7:05:37 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE dbo.Employee(
	EmployeeID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	UserName nvarchar(50) NULL,
	Password nvarchar(50) NULL,
	FirstName nvarchar(50) NULL,
	LastName nvarchar(50) NULL,
	Email nvarchar(100) NULL,
	Phone nvarchar(50) NULL,
	StoreID int NULL FOREIGN KEY REFERENCES Store(StoreID),
	JobType int NULL,
	Status int NULL,
) ON [PRIMARY]
GO
