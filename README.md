# Git
Dastal InfoMan OOP

- 1
  - SELECT 
    catdesc,COUNT(prodid) as num_products
FROM 
    category, products
WHERE
    category.catcode=products.catcode
ORDER BY
    catdesc

- 2
  - SELECT cname,sdate,sales.invno,catdesc,pdesc,qty,uprice,(qty*uprice) as amount 
    FROM customer 
        INNER JOIN sales ON customer.custid=sales.custid
            INNER JOIN salesitems ON sales.invno=salesitems.invno
                INNER JOIN products ON salesitems.prodid=products.prodid
                    INNER JOIN category ON products.catcode=category.catcode
                        ORDER BY invno

- 3
  - SELECT cname, SUM(qty\*uprice) as sales_amount FROM customer INNER JOIN sales ON customer.custid=sales.custid INNER JOIN salesitems ON sales.invno=salesitems.invno INNER JOIN products ON salesitems.prodid=products.prodid GROUP BY cname ORDER BY sales_amount DESC LIMIT 1
  
- 4
  - SELECT catdesc, SUM(qty\*uprice) as total_profit FROM category,products,salesitems WHERE category.catcode=products.catcode AND products.prodid=salesitems.prodid AND category.catcode='G01'
UNION
SELECT catdesc, SUM(qty\*uprice) as total_profit FROM category,products,salesitems WHERE category.catcode=products.catcode AND products.prodid=salesitems.prodid AND category.catcode='G02'

- 5
  - SELECT cname, COUNT(customer.custid) as num_of_sales FROM customer INNER JOIN sales ON customer.custid=sales.custid GROUP BY cname HAVING COUNT(customer.custid) > 1
  
- 6
  - SELECT cname FROM customer WHERE customer.custid NOT IN (SELECT customer.custid FROM customer INNER JOIN sales ON customer.custid=sales.custid)
  
- 7 
  - SELECT stype, SUM(qty\*uprice) as total_amount 
FROM sales 
INNER JOIN salesitems ON sales.invno=salesitems.invno
INNER JOIN products ON salesitems.prodid=products.prodid  WHERE stype='Cash'
UNION
SELECT stype, SUM(qty\*uprice) as total_amount 
FROM sales 
INNER JOIN salesitems ON sales.invno=salesitems.invno
INNER JOIN products ON salesitems.prodid=products.prodid  WHERE stype='Credit'

- 8
  - SELECT cname,sdate,stype 
FROM customer 
INNER JOIN sales ON customer.custid=sales.custid
HAVING sdate BETWEEN '2019-07-19' AND DATE_ADD('2019-07-19',INTERVAL 1 MONTH)
