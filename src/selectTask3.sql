//TASK 3

SELECT user, year, SUM (sales) AS sales, LAG(SUM (sales)) OVER (PARTITION BY user ORDER BY year ) AS previous_year, SUM (sales) - LAG(SUM (sales)) OVER (PARTITION BY user ORDER BY year ) AS difference_previous_year
FROM hibernate_db.tabletask3
GROUP BY user, year;