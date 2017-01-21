USE [master]
GO

/* For security reasons the login is created disabled and with a random password. */
/****** Object:  Login [carshowroomapp]    Script Date: 21/01/2017 05:10:12 ******/
CREATE LOGIN [carshowroomapp] WITH PASSWORD=N'r/SIAZPLQLrhhqbSnEQF2r7AY2gnHXLH6i5aOIOC+eQ=', DEFAULT_DATABASE=[master], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO

ALTER LOGIN [carshowroomapp] ENABLE
GO

/****** Object:  User [carshowroomapp]    Script Date: 21/01/2017 05:11:21 ******/
CREATE USER [carshowroomapp] FOR LOGIN [carshowroomapp] WITH DEFAULT_SCHEMA=[dbo]
GO