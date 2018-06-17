USE ContactDB
GO

--DROP TABLE dbo.AvailabilityTbl

/****** Object:  Table dbo.Store    Script Date: 6/15/2018 7:05:37 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE dbo.AvailabilityTbl(
	AvailID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	EmployeeID int NULL FOREIGN KEY REFERENCES Employee(EmployeeID),
	[DayOfWeek] int NULL,
	StartTime datetime NULL,
	EndTime datetime NULL,
) ON [PRIMARY]
GO
