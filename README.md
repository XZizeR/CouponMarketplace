# Coupon Marketplace
Utilizing login system where you need to authorize your identity in the system, through email and password. Then UUID gives you your session token, which enables you buy coupons as a customer or create them as a company. 
The data stored and managed within MySQL, SQL data mapped by JPA (Java Persistence API). With the help of Hibernate the platform able to add, update, delete, sort, and show data to the customer and company users. Also, the web application uses the advantages of Angular and REST, and it also has a thread that deletes out-dated coupons.


## How to run it
To run this applicatin you have to download it, import it to your IDE, and run it.
The project is using Lombok, so there is a need to install Lombok(https://projectlombok.org) if you have not.
### Client-side:
to add your new angular front-end project
- Run `ng build --prod` on your CLI, go to `/dist` directory and copy all the files.
- Open your back-end developing tool, and in `resource/static/` past the files.
- Run your application on a `localhost:8080` to check out.
### Server-side:
In order to be able to update your SpringBoot project, we have to improt it.
In eclipse: Open `File> Import> Existing Maven Project` Browse to your project folder, select it.
### Access:
To access as admin you have to input the following fields accordingly:
- Email: admin@admin.com
- Password: admin
- Type: admin


## How to improve it
- Dockerize the application.
- Improve the security of session token UUID with JWT signatures.


