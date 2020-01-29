USE BillingCollection

DECLARE @list TABLE (Acct_No varchar(30))
INSERT INTO 
	@list 
SELECT
	'Acct_No' = c.Acct_No
FROM 
	Clients c
WHERE 
	c.Acct_No NOT IN (SELECT co.Acct_No FROM ClientOtherFees co 
						WHERE co.ItemID = 01
						AND YEAR(co.FirstInstallment) BETWEEN 2018 AND 2019
						)
	AND c.Acct_No IN (SELECT mi.Acct_No FROM Mtr_Install mi WHERE YEAR(mi.DateInstalled) BETWEEN 2018 AND 2019)



IF NOT EXISTS (SELECT * FROM sys.objects WHERE name='AAAA')
BEGIN
	PRINT 0

	CREATE TABLE AAAA (
		Acct_No varchar(12) not null,
		ItemID char(2) not null,
		Receivable money not null,
		Collected money not null,
		RequiredDP money not null,
		MosPayable int not null,
		MosLeft int not null,
		FirstInstallment datetime
	)
	INSERT INTO AAAA
	SELECT 
		* 
	FROM 
		ClientOtherFees

	PRINT 1

	INSERT INTO AAAA
	SELECT
		'Acct_No' = mi.Acct_No,
		'01',
		1750.00,
		0.00,
		0.00,
		5,
		5,
		mi.DateInstalled
	FROM
		Mtr_Install mi
	WHERE mi.Acct_No IN (SELECT l.Acct_No FROM @list l)

	PRINT 2
END
