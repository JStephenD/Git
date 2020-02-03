USE BillingCollection
GO

CREATE FUNCTION getExtraCharge (
	@consump float,
	@ctype int
)
RETURNS float
AS
BEGIN
	RETURN
		(SELECT 
			IIF(
				@consump <= 10,
				0,
				SUM(wr.Rate * (@consump - wr.Cons_from))
			)
			/*CASE 
				WHEN @consump BETWEEN 0 AND 10
					THEN 0
				ELSE
					SUM(wr.Rate * (@consump - wr.Cons_from))
			END*/
		FROM 
			WaterRates wr
		WHERE
			wr.C_Type = @ctype AND
			@consump >= wr.Cons_from)
END
GO