SELECT
	c.Acct_No,
	c.C_FName,
	c.C_LName,
	'consump' = SUM(mi.Cur_Consumption),
	'rate' = SUM(wr.Min_Charge),
	'extra' = SUM(wr.Rate * (mi.Cur_Consumption - wr.Cons_from)),
	'total' = SUM(wr.Min_Charge + wr.Rate * (mi.Cur_Consumption - wr.Cons_from))
FROM 
	Clients c
INNER JOIN
	Mtr_Reading mi ON mi.Acct_No = c.Acct_No
INNER JOIN
	WaterRates wr ON wr.C_Type = c.C_Type
WHERE
	c.C_Type = 08 AND
	YEAR(mi.MR_Date) = 2018 AND
	mi.Cur_Consumption BETWEEN wr.Cons_from AND wr.Cons_to
GROUP BY 
	c.Acct_No, c.C_FName, c.C_LName
ORDER BY C_FName