USE ContactDB
GO

--DROP TABLE dbo.Request

/****** Object:  Table dbo.Store    Script Date: 6/15/2018 7:05:37 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE dbo.Request(
	RequestID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	StoreID int NULL FOREIGN KEY REFERENCES Store(StoreID),
	EmployeeID int NULL FOREIGN KEY REFERENCES Employee(EmployeeID),
	RequestType int NULL,
	RequestStatus int NULL,
	RequestText nvarchar(1000) NULL,
	ScheduleID1 int NULL FOREIGN KEY REFERENCES Schedule(ScheduleID),
	ScheduleID2 int NULL FOREIGN KEY REFERENCES Schedule(ScheduleID)
) ON [PRIMARY]
GO
