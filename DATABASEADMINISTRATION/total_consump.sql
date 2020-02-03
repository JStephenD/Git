USE BillingCollection

SELECT
	'Zone' = COALESCE(z.ZoneName, 'TOTAL FOR MONTH'),
	'Month' = COALESCE(STR(MONTH(mi.MR_Date)), 'YEAR TOTAL'),
	'Total Consumption' = SUM(mi.Cur_Consumption),
	'Total Bill' = SUM(wr.Min_Charge) + SUM(dbo.getExtraCharge(mi.Cur_Consumption, wr.C_Type))
FROM
	Clients c
INNER JOIN
	Mtr_Reading mi ON c.Acct_No = mi.Acct_No
INNER JOIN
	WaterRates wr ON wr.C_Type = c.C_Type
INNER JOIN
	Zones z ON z.ZoneID = c.Zone_No
WHERE
	wr.C_Type IN (01, 02, 03) AND
	YEAR(mi.MR_Date) = 2018 AND
	mi.Cur_Consumption BETWEEN wr.Cons_from AND wr.Cons_to
GROUP BY
	/*z.ZoneName, mi.MR_Date*/
	ROLLUP(MONTH(mi.MR_Date), z.ZoneName)
ORDER BY
	z.ZoneName, Month
