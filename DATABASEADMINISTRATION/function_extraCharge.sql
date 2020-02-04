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
			wr.Rate * (@consump+1 - wr.Cons_from)
		FROM 
			WaterRates wr
		WHERE
			wr.C_Type = @ctype AND
			@consump BETWEEN wr.Cons_from AND wr.Cons_to)
END
GO
