USE ContactDB
GO

--DROP TABLE dbo.Schedule

/****** Object:  Table dbo.Store    Script Date: 6/15/2018 7:05:37 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE dbo.Schedule(
	ScheduleID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	StoreID int NULL FOREIGN KEY REFERENCES Store(StoreID),
	StartOfShift datetime NULL,
	EndOfShift datetime NULL,
	EmployeeID int NULL FOREIGN KEY REFERENCES Employee(EmployeeID),
	ShiftStatus int NULL,
) ON [PRIMARY]
GO
