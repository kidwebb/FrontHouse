USE [ContactDB]
GO

--DROP TABLE [dbo].[Store]

/****** Object:  Table [dbo].[Store]    Script Date: 6/15/2018 7:14:41 PM ******/
GO

/****** Object:  Table [dbo].[Store]    Script Date: 6/15/2018 7:14:41 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Store](
	[StoreID] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[StoreName] [nvarchar](50) NULL
) ON [PRIMARY]
GO


