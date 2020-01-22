DECLARE @list table (acct varchar(30))
INSERT INTO @list VALUES ('0111201070'), ('0111201075'), ('0111201045'), ('0111201040'), ('0111201035'),
		('0111200005'), ('0121200520'), ('0121200530'), ('0411200425'), ('0411200200')
DECLARE @year int = 2018, 
		@mt0030 int = 12, @mt6031 int = 11, @mt9061 int = 10, @mt91u int = 9

SELECT DISTINCT
	'Account Number' = c.Acct_No,
	'First Name' = c.C_FName,
	'Last Name' = c.C_LName,
	'TOTAL A/R' = ABS((SELECT SUM(inn_l.Amount) 
					FROM Ledgers inn_l
					WHERE inn_l.Acct_No = out_l.Acct_No
						AND YEAR(inn_l.Trans_Date) = @year 
					    AND inn_l.Amount > 0
					)),
	'Days 0-30' = ABS((SELECT SUM(inn_l.Amount)
					FROM Ledgers inn_l
					WHERE inn_l.Acct_No = out_l.Acct_No
						AND YEAR(inn_l.Trans_Date) = @year
						AND MONTH(inn_l.Trans_Date) = @mt0030
						And inn_l.Amount < 0
					)),
	'Days 60-31' = ABS((SELECT SUM(inn_l.Amount)
					FROM Ledgers inn_l
					WHERE inn_l.Acct_No = out_l.Acct_No
						AND YEAR(inn_l.Trans_Date) = @year
						AND MONTH(inn_l.Trans_Date) = @mt6031
						And inn_l.Amount < 0
					)),
	'Days 90-61' = ABS((SELECT SUM(inn_l.Amount)
					FROM Ledgers inn_l
					WHERE inn_l.Acct_No = out_l.Acct_No
						AND YEAR(inn_l.Trans_Date) = @year
						AND MONTH(inn_l.Trans_Date) = @mt9061
						And inn_l.Amount < 0
					)),
	'Days 91+' = ABS((SELECT SUM(inn_l.Amount)
					FROM Ledgers inn_l
					WHERE inn_l.Acct_No = out_l.Acct_No
						AND YEAR(inn_l.Trans_Date) = @year
						AND MONTH(inn_l.Trans_Date) <= @mt91u
						AND inn_l.Amount < 0
					)),
	'Total Payments' = ABS((SELECT SUM(inn_l.Amount)
					FROM Ledgers inn_l
					WHERE inn_l.Acct_No = out_l.Acct_No
						AND YEAR(inn_l.Trans_Date) = @year
						AND inn_l.Amount < 0
					)),
	'Basi Punishment' = (SELECT SUM(inn_l.Amount) 
					FROM Ledgers inn_l
					WHERE inn_l.Acct_No = out_l.Acct_No
						AND YEAR(inn_l.Trans_Date) = @year 
					    AND inn_l.Amount > 0
					)
				+ 
				(SELECT SUM(inn_l.Amount)
					FROM Ledgers inn_l
					WHERE inn_l.Acct_No = out_l.Acct_No
						AND YEAR(inn_l.Trans_Date) = @year
						AND inn_l.Amount < 0
					)
FROM Clients c
INNER JOIN 
	Ledgers out_l ON out_l.Acct_No = c.Acct_No
WHERE
	c.Acct_No IN (SELECT acct FROM @list)
