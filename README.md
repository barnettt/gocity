# gocity
Assessment

I think I understand the requirements
So the following steps Will be followed.
1) Create a spring boot project
2) Create an appropriate application structure based on what is currently
known about the application. We have 3 tiers to the application in the back end.
and I would more than likely build the front end in another application. and
make this backend the target.
3) I have added some basic test case to cover the 2 backend requirements.
that I'm aware of, I have also considered the paging of products list
4) we need to next add the database and write a function to create
the product table from the csv file.
5) Decided to use flyway to manage the database makes changes easier
6) In reality, we should add flyway to the build process and let jenkins run it, but 
for this exercise i'll let spring boot do it. On start up, ill load the product csv data
we need to do some format around the date as Excel is well known for mangling date fields.
7) I'll configure a mysql database for the product inserts and do the dta insert via code.
(I dont want to tamper with the spreadsheet)
we only want the product data load to execute once, so ill add a property that can be set to prevent the data 
load occurring multiple times.
8) "spring.main.allow-bean-definition-overriding=true" not working as expected.
