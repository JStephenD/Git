USE BillingCollection

SELECT DISTINCT
	c.Acct_No,
	'Full Name' = c.C_LName + ', ' + c.C_FName,
	'Consumption' = 'Above ' + LEFT(mi.Cur_Consumption, 1) + '0',
	'min_charge' = SUM(wr.Min_Charge),
	'extra_charge' = SUM(dbo.getExtraCharge(mi.Cur_Consumption, wr.C_Type)),
	'total' = SUM((wr.Min_Charge + dbo.getExtraCharge(mi.Cur_Consumption, wr.C_Type)))
FROM 
	Clients c
INNER JOIN
	Mtr_Reading mi ON mi.Acct_No = c.Acct_No
INNER JOIN
	WaterRates wr ON wr.C_Type = c.C_Type
WHERE
	c.C_Type = 08 AND
	YEAR(mi.MR_Date) = 2018 AND
	mi.Cur_Consumption >= wr.Cons_from
GROUP BY 
	c.Acct_No, c.C_FName, c.C_LName, 
	mi.Cur_Consumption,
	/* wr.Min_Charge, wr.Rate, wr.Cons_from */
ORDER BY
	'Full Name'