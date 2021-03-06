USE [master]
GO
/****** Object:  Database [CarShowroom]    Script Date: 21/01/2017 00:48:50 ******/
CREATE DATABASE [CarShowroom]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CarShowroom', FILENAME = N'C:\Work\_data\Data\CarShowroom.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'CarShowroom_log', FILENAME = N'C:\Work\_data\Data\CarShowroom_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [CarShowroom] SET COMPATIBILITY_LEVEL = 130
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CarShowroom].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CarShowroom] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CarShowroom] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CarShowroom] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CarShowroom] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CarShowroom] SET ARITHABORT OFF 
GO
ALTER DATABASE [CarShowroom] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [CarShowroom] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CarShowroom] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CarShowroom] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CarShowroom] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CarShowroom] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CarShowroom] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CarShowroom] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CarShowroom] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CarShowroom] SET  DISABLE_BROKER 
GO
ALTER DATABASE [CarShowroom] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CarShowroom] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CarShowroom] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CarShowroom] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CarShowroom] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CarShowroom] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CarShowroom] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CarShowroom] SET RECOVERY FULL 
GO
ALTER DATABASE [CarShowroom] SET  MULTI_USER 
GO
ALTER DATABASE [CarShowroom] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CarShowroom] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CarShowroom] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CarShowroom] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [CarShowroom] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'CarShowroom', N'ON'
GO
ALTER DATABASE [CarShowroom] SET QUERY_STORE = OFF
GO
USE [CarShowroom]
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [CarShowroom]
GO
/****** Object:  Table [dbo].[CarDeals]    Script Date: 21/01/2017 00:48:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CarDeals](
	[CarDealId] [int] IDENTITY(1,1) NOT NULL,
	[CarId] [int] NOT NULL,
	[CustomerId] [int] NOT NULL,
	[DealDate] [datetime2](7) NOT NULL,
	[SalesRepresentativeId] [int] NOT NULL,
	[DealAmount] [decimal](10, 2) NOT NULL,
	[IsDeleted] [bit] NOT NULL,
 CONSTRAINT [PK_CarDeals] PRIMARY KEY CLUSTERED 
(
	[CarDealId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Cars]    Script Date: 21/01/2017 00:48:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cars](
	[CarId] [int] IDENTITY(1,1) NOT NULL,
	[Make] [nvarchar](50) NOT NULL,
	[Model] [nvarchar](50) NOT NULL,
	[Year] [int] NOT NULL,
	[Color] [nchar](15) NOT NULL,
	[FuelType] [nvarchar](50) NOT NULL,
	[BodyStyle] [nvarchar](50) NOT NULL,
	[Transmission] [nvarchar](50) NOT NULL,
	[NumberOfSeats] [int] NOT NULL,
	[Price] [decimal](10, 2) NOT NULL,
	[Mileage] [int] NOT NULL,
	[IsDeleted] [bit] NOT NULL,
 CONSTRAINT [PK_Cars] PRIMARY KEY CLUSTERED 
(
	[CarId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[CarsFeaturesMap]    Script Date: 21/01/2017 00:48:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CarsFeaturesMap](
	[CarId] [int] NOT NULL,
	[CarFeatureId] [int] NOT NULL,
 CONSTRAINT [PK_CarsFeaturesMap] PRIMARY KEY CLUSTERED 
(
	[CarId] ASC,
	[CarFeatureId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Customers]    Script Date: 21/01/2017 00:48:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customers](
	[CustomerId] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](50) NOT NULL,
	[LastName] [nvarchar](50) NOT NULL,
	[City] [nvarchar](50) NOT NULL,
	[CustomerSince] [datetime2](7) NOT NULL,
	[IsDeleted] [bit] NOT NULL,
 CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[CustomerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Logins]    Script Date: 21/01/2017 00:48:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Logins](
	[LoginId] [int] NOT NULL,
	[UserProfileId] [int] NOT NULL,
	[LoginName] [nvarchar](50) NOT NULL,
	[PasswordHash] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_Logins] PRIMARY KEY CLUSTERED 
(
	[LoginId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PaymentOptions]    Script Date: 21/01/2017 00:48:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PaymentOptions](
	[CarDealId] [int] NOT NULL,
	[DurationInMonths] [int] NOT NULL,
	[FirstPaymentDate] [datetime2](7) NOT NULL,
	[Deposit] [decimal](10, 2) NOT NULL,
 CONSTRAINT [PK_PaymentOptions] PRIMARY KEY CLUSTERED 
(
	[CarDealId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Payments]    Script Date: 21/01/2017 00:48:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Payments](
	[PaymentId] [int] IDENTITY(1,1) NOT NULL,
	[CarDealId] [int] NOT NULL,
	[PaymentDate] [datetime2](7) NOT NULL,
	[Amount] [decimal](10, 2) NOT NULL,
 CONSTRAINT [PK_Payments] PRIMARY KEY CLUSTERED 
(
	[PaymentId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[UserProfiles]    Script Date: 21/01/2017 00:48:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserProfiles](
	[UserProfileId] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](50) NOT NULL,
	[LastName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_UserProfiles] PRIMARY KEY CLUSTERED 
(
	[UserProfileId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[CarDeals] ON 

GO
INSERT [dbo].[CarDeals] ([CarDealId], [CarId], [CustomerId], [DealDate], [SalesRepresentativeId], [DealAmount], [IsDeleted]) VALUES (3, 2, 1, CAST(N'2017-01-20T00:00:00.0000000' AS DateTime2), 1, CAST(499000.00 AS Decimal(10, 2)), 0)
GO
INSERT [dbo].[CarDeals] ([CarDealId], [CarId], [CustomerId], [DealDate], [SalesRepresentativeId], [DealAmount], [IsDeleted]) VALUES (4, 4, 1, CAST(N'2017-01-20T00:00:00.0000000' AS DateTime2), 1, CAST(760.00 AS Decimal(10, 2)), 0)
GO
INSERT [dbo].[CarDeals] ([CarDealId], [CarId], [CustomerId], [DealDate], [SalesRepresentativeId], [DealAmount], [IsDeleted]) VALUES (5, 3, 2, CAST(N'2017-01-20T00:00:00.0000000' AS DateTime2), 1, CAST(449900.00 AS Decimal(10, 2)), 0)
GO
INSERT [dbo].[CarDeals] ([CarDealId], [CarId], [CustomerId], [DealDate], [SalesRepresentativeId], [DealAmount], [IsDeleted]) VALUES (6, 5, 5, CAST(N'2017-01-20T00:00:00.0000000' AS DateTime2), 1, CAST(110000.00 AS Decimal(10, 2)), 0)
GO
SET IDENTITY_INSERT [dbo].[CarDeals] OFF
GO
SET IDENTITY_INSERT [dbo].[Cars] ON 

GO
INSERT [dbo].[Cars] ([CarId], [Make], [Model], [Year], [Color], [FuelType], [BodyStyle], [Transmission], [NumberOfSeats], [Price], [Mileage], [IsDeleted]) VALUES (1, N'S600', N'Mercedes Benz', 2017, N'Black          ', N'1', N'1', N'2', 4, CAST(500000.00 AS Decimal(10, 2)), 0, 0)
GO
INSERT [dbo].[Cars] ([CarId], [Make], [Model], [Year], [Color], [FuelType], [BodyStyle], [Transmission], [NumberOfSeats], [Price], [Mileage], [IsDeleted]) VALUES (2, N'Bentley', N'Continental GT', 2017, N'Gray           ', N'1', N'3', N'2', 4, CAST(500000.00 AS Decimal(10, 2)), 0, 0)
GO
INSERT [dbo].[Cars] ([CarId], [Make], [Model], [Year], [Color], [FuelType], [BodyStyle], [Transmission], [NumberOfSeats], [Price], [Mileage], [IsDeleted]) VALUES (3, N'BMW', N'760Li', 2017, N'Electric Blue  ', N'1', N'1', N'2', 4, CAST(450000.00 AS Decimal(10, 2)), 0, 0)
GO
INSERT [dbo].[Cars] ([CarId], [Make], [Model], [Year], [Color], [FuelType], [BodyStyle], [Transmission], [NumberOfSeats], [Price], [Mileage], [IsDeleted]) VALUES (4, N'Audi', N'80', 1980, N'Red            ', N'2', N'2', N'1', 4, CAST(1000.00 AS Decimal(10, 2)), 1000000, 0)
GO
INSERT [dbo].[Cars] ([CarId], [Make], [Model], [Year], [Color], [FuelType], [BodyStyle], [Transmission], [NumberOfSeats], [Price], [Mileage], [IsDeleted]) VALUES (5, N'Masserati', N'Quadraporte', 2016, N'Black          ', N'1', N'1', N'2', 4, CAST(110000.00 AS Decimal(10, 2)), 1, 0)
GO
SET IDENTITY_INSERT [dbo].[Cars] OFF
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (1, 14)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (1, 15)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (1, 16)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (1, 17)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (2, 14)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (3, 14)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 1)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 2)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 3)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 4)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 5)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 6)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 7)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 8)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 9)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 10)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 11)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 12)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 13)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 14)
GO
INSERT [dbo].[CarsFeaturesMap] ([CarId], [CarFeatureId]) VALUES (5, 15)
GO
SET IDENTITY_INSERT [dbo].[Customers] ON 

GO
INSERT [dbo].[Customers] ([CustomerId], [FirstName], [LastName], [City], [CustomerSince], [IsDeleted]) VALUES (1, N'Stephanie', N'Francois', N'Trou aux Biches', CAST(N'2010-01-01T00:00:00.0000000' AS DateTime2), 0)
GO
INSERT [dbo].[Customers] ([CustomerId], [FirstName], [LastName], [City], [CustomerSince], [IsDeleted]) VALUES (2, N'John', N'Dow', N'New York', CAST(N'2015-01-01T00:00:00.0000000' AS DateTime2), 0)
GO
INSERT [dbo].[Customers] ([CustomerId], [FirstName], [LastName], [City], [CustomerSince], [IsDeleted]) VALUES (3, N'Jane', N'Dow', N'Chicago', CAST(N'2000-01-01T00:00:00.0000000' AS DateTime2), 0)
GO
INSERT [dbo].[Customers] ([CustomerId], [FirstName], [LastName], [City], [CustomerSince], [IsDeleted]) VALUES (4, N'John', N'Smith', N'Trou aux biches', CAST(N'2000-01-01T00:00:00.0000000' AS DateTime2), 0)
GO
INSERT [dbo].[Customers] ([CustomerId], [FirstName], [LastName], [City], [CustomerSince], [IsDeleted]) VALUES (5, N'Joanna', N'Bower', N'Atlantic City', CAST(N'2000-01-01T00:00:00.0000000' AS DateTime2), 0)
GO
INSERT [dbo].[Customers] ([CustomerId], [FirstName], [LastName], [City], [CustomerSince], [IsDeleted]) VALUES (6, N'Dick', N'Dickinson', N'Dicity', CAST(N'2017-01-20T00:00:00.0000000' AS DateTime2), 1)
GO
SET IDENTITY_INSERT [dbo].[Customers] OFF
GO
INSERT [dbo].[Logins] ([LoginId], [UserProfileId], [LoginName], [PasswordHash]) VALUES (1, 1, N'Stephanie', N'e52d98c459819a11775936d8dfbb7929')
GO
INSERT [dbo].[Logins] ([LoginId], [UserProfileId], [LoginName], [PasswordHash]) VALUES (2, 2, N'Ali', N'e54cfb3714f76cedd4b27889e1f6a174')
GO
INSERT [dbo].[PaymentOptions] ([CarDealId], [DurationInMonths], [FirstPaymentDate], [Deposit]) VALUES (3, 12, CAST(N'2017-01-27T00:00:00.0000000' AS DateTime2), CAST(1000.00 AS Decimal(10, 2)))
GO
INSERT [dbo].[PaymentOptions] ([CarDealId], [DurationInMonths], [FirstPaymentDate], [Deposit]) VALUES (4, 24, CAST(N'2017-02-24T00:00:00.0000000' AS DateTime2), CAST(240.00 AS Decimal(10, 2)))
GO
INSERT [dbo].[PaymentOptions] ([CarDealId], [DurationInMonths], [FirstPaymentDate], [Deposit]) VALUES (5, 24, CAST(N'2017-03-20T00:00:00.0000000' AS DateTime2), CAST(100.00 AS Decimal(10, 2)))
GO
INSERT [dbo].[PaymentOptions] ([CarDealId], [DurationInMonths], [FirstPaymentDate], [Deposit]) VALUES (6, 12, CAST(N'2017-03-10T00:00:00.0000000' AS DateTime2), CAST(0.00 AS Decimal(10, 2)))
GO
SET IDENTITY_INSERT [dbo].[Payments] ON 

GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (1, 3, CAST(N'2017-01-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (2, 3, CAST(N'2017-02-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (3, 3, CAST(N'2017-03-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (4, 3, CAST(N'2017-04-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (5, 3, CAST(N'2017-05-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (6, 3, CAST(N'2017-06-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (7, 3, CAST(N'2017-07-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (8, 3, CAST(N'2017-08-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (9, 3, CAST(N'2017-09-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (10, 3, CAST(N'2017-10-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (11, 3, CAST(N'2017-11-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (12, 3, CAST(N'2017-12-27T00:00:00.0000000' AS DateTime2), CAST(41583.33 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (97, 4, CAST(N'2017-02-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (98, 4, CAST(N'2017-03-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (99, 4, CAST(N'2017-04-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (100, 4, CAST(N'2017-05-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (101, 4, CAST(N'2017-06-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (102, 4, CAST(N'2017-07-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (103, 4, CAST(N'2017-08-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (104, 4, CAST(N'2017-09-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (105, 4, CAST(N'2017-10-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (106, 4, CAST(N'2017-11-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (107, 4, CAST(N'2017-12-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (108, 4, CAST(N'2018-01-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (109, 4, CAST(N'2018-02-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (110, 4, CAST(N'2018-03-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (111, 4, CAST(N'2018-04-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (112, 4, CAST(N'2018-05-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (113, 4, CAST(N'2018-06-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (114, 4, CAST(N'2018-07-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (115, 4, CAST(N'2018-08-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (116, 4, CAST(N'2018-09-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (117, 4, CAST(N'2018-10-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (118, 4, CAST(N'2018-11-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (119, 4, CAST(N'2018-12-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (120, 4, CAST(N'2019-01-24T00:00:00.0000000' AS DateTime2), CAST(31.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (121, 6, CAST(N'2017-03-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (122, 6, CAST(N'2017-04-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (123, 6, CAST(N'2017-05-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (124, 6, CAST(N'2017-06-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (125, 6, CAST(N'2017-07-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (126, 6, CAST(N'2017-08-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (127, 6, CAST(N'2017-09-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (128, 6, CAST(N'2017-10-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (129, 6, CAST(N'2017-11-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (130, 6, CAST(N'2017-12-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (131, 6, CAST(N'2018-01-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (132, 6, CAST(N'2018-02-10T00:00:00.0000000' AS DateTime2), CAST(9166.67 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (133, 5, CAST(N'2017-03-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (134, 5, CAST(N'2017-04-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (135, 5, CAST(N'2017-05-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (136, 5, CAST(N'2017-06-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (137, 5, CAST(N'2017-07-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (138, 5, CAST(N'2017-08-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (139, 5, CAST(N'2017-09-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (140, 5, CAST(N'2017-10-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (141, 5, CAST(N'2017-11-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (142, 5, CAST(N'2017-12-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (143, 5, CAST(N'2018-01-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (144, 5, CAST(N'2018-02-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (145, 5, CAST(N'2018-03-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (146, 5, CAST(N'2018-04-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (147, 5, CAST(N'2018-05-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (148, 5, CAST(N'2018-06-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (149, 5, CAST(N'2018-07-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (150, 5, CAST(N'2018-08-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (151, 5, CAST(N'2018-09-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (152, 5, CAST(N'2018-10-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (153, 5, CAST(N'2018-11-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (154, 5, CAST(N'2018-12-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (155, 5, CAST(N'2019-01-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
INSERT [dbo].[Payments] ([PaymentId], [CarDealId], [PaymentDate], [Amount]) VALUES (156, 5, CAST(N'2019-02-20T00:00:00.0000000' AS DateTime2), CAST(18745.83 AS Decimal(10, 2)))
GO
SET IDENTITY_INSERT [dbo].[Payments] OFF
GO
SET IDENTITY_INSERT [dbo].[UserProfiles] ON 

GO
INSERT [dbo].[UserProfiles] ([UserProfileId], [FirstName], [LastName]) VALUES (1, N'Stephanie', N'Francois')
GO
INSERT [dbo].[UserProfiles] ([UserProfileId], [FirstName], [LastName]) VALUES (2, N'Ali', N'Azhar')
GO
SET IDENTITY_INSERT [dbo].[UserProfiles] OFF
GO
ALTER TABLE [dbo].[CarDeals] ADD  CONSTRAINT [DF_CarDeals_IsDeleted]  DEFAULT ((0)) FOR [IsDeleted]
GO
ALTER TABLE [dbo].[Cars] ADD  CONSTRAINT [DF_Cars_IsDeleted]  DEFAULT ((0)) FOR [IsDeleted]
GO
ALTER TABLE [dbo].[Customers] ADD  CONSTRAINT [DF_Customer_CustomerSince]  DEFAULT (getdate()) FOR [CustomerSince]
GO
ALTER TABLE [dbo].[Customers] ADD  CONSTRAINT [DF_Customer_IsDeleted]  DEFAULT ((0)) FOR [IsDeleted]
GO
ALTER TABLE [dbo].[CarDeals]  WITH CHECK ADD  CONSTRAINT [FK_CarDeals_Cars] FOREIGN KEY([CarId])
REFERENCES [dbo].[Cars] ([CarId])
GO
ALTER TABLE [dbo].[CarDeals] CHECK CONSTRAINT [FK_CarDeals_Cars]
GO
ALTER TABLE [dbo].[CarDeals]  WITH CHECK ADD  CONSTRAINT [FK_CarDeals_Customers] FOREIGN KEY([CustomerId])
REFERENCES [dbo].[Customers] ([CustomerId])
GO
ALTER TABLE [dbo].[CarDeals] CHECK CONSTRAINT [FK_CarDeals_Customers]
GO
ALTER TABLE [dbo].[CarDeals]  WITH CHECK ADD  CONSTRAINT [FK_CarDeals_UserProfiles1] FOREIGN KEY([SalesRepresentativeId])
REFERENCES [dbo].[UserProfiles] ([UserProfileId])
GO
ALTER TABLE [dbo].[CarDeals] CHECK CONSTRAINT [FK_CarDeals_UserProfiles1]
GO
ALTER TABLE [dbo].[CarsFeaturesMap]  WITH CHECK ADD  CONSTRAINT [FK_CarsFeaturesMap_Cars] FOREIGN KEY([CarId])
REFERENCES [dbo].[Cars] ([CarId])
GO
ALTER TABLE [dbo].[CarsFeaturesMap] CHECK CONSTRAINT [FK_CarsFeaturesMap_Cars]
GO
ALTER TABLE [dbo].[Logins]  WITH CHECK ADD  CONSTRAINT [FK_Logins_UserProfiles] FOREIGN KEY([UserProfileId])
REFERENCES [dbo].[UserProfiles] ([UserProfileId])
GO
ALTER TABLE [dbo].[Logins] CHECK CONSTRAINT [FK_Logins_UserProfiles]
GO
ALTER TABLE [dbo].[PaymentOptions]  WITH CHECK ADD  CONSTRAINT [FK_PaymentOptions_CarDeals] FOREIGN KEY([CarDealId])
REFERENCES [dbo].[CarDeals] ([CarDealId])
GO
ALTER TABLE [dbo].[PaymentOptions] CHECK CONSTRAINT [FK_PaymentOptions_CarDeals]
GO
ALTER TABLE [dbo].[Payments]  WITH CHECK ADD  CONSTRAINT [FK_Payments_CarDeals] FOREIGN KEY([CarDealId])
REFERENCES [dbo].[CarDeals] ([CarDealId])
GO
ALTER TABLE [dbo].[Payments] CHECK CONSTRAINT [FK_Payments_CarDeals]
GO
USE [master]
GO
ALTER DATABASE [CarShowroom] SET  READ_WRITE 
GO
