### USER PROFILE MICROSERVICE API


### Technologies Used
- **Framework**: Spring Boot 
- **Programming Language**: Java
- **Database**: PostgreSQL
- **Containerization**: Docker

### Prerequisites
- **Java 17+**: Required for building and running the application
- **PostgreSQL**: For database management
- **Docker** (optional): For containerized deployment
- **IDE**: of you preference (recommend: Intelli J)


### API Functionalities Features Covered
- Authenticate user
- Create user profile
- Edit user profile
- Edit user profile status
- Change password
- Get a list of all user profiles
- Get a user profile using user Id
- Delete user profile
- Suspend user profile
- Search user profile by name, email or username
- Unit tests (didn't cover all case scenarios)

### API Functionalities Features Not Covered
- Swagger documentation (didn't manage to to finalize configuration due time constraints)
- Audit Trails (Limited time)


### Steps to run the project
**Before anything you have to download the project GitHub repository through this link:** https://github.com/Yves-Byiringiro/user-profile-api-spring-boot.git
to download, you can click on **Download ZIP** button after you open the link, or you can run the following command:

`git clone https://github.com/Yves-Byiringiro/user-profile-api-spring-boot.git`

**Note**: This project can be run without using Docker or with using Docker, it depends on someone's preferences


#### **Assumption**
- Using Intelli J  as IDE
- Have basic knowledge to run and build container
- Using PostgreSQL database

#### 1. Without Docker
- After cloning the project, open it in your prefered ID, by selecting the folder called:`user-profile-api-spring-boot`
- Configure the PostgreSQL in the project, by the updating the section below found under `src/main/resources/application.properties` .
  
```bash 
  spring.datasource.url=jdbc:postgresql://localhost:5432/database_name
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  
 - Replace **database_name** with your database name
 - Replace **your_username** with your database user name
 - Replace **your_password** with your database user password
 
 Note: In this project, I pushed the local credentials (database_name, your_username, your_password) described above, but it's not recommended for security purpose
 instead can be added in .env file, and put .env file in .gitignore file to avoid exposing credentials.
```
- After configuring the database, you can run the project by  navigating to DemoApplication class
which is under `src/main/java/DemoApplication` clicking on green play button in the top right corner, if you are using Intelli J as IDE, after clicking this button
the project will be build and run at default `http://localhost:8080`

- Alternatively, the project can be run using terminal/command line, this `https://www.masterspringboot.com/getting-started-with-spring-boot/spring-boot-quickstarts/how-to-run-a-spring-boot-application-from-the-command-line/` can help.

- More information regarding API endpoints available look at the end of the page .....



#### 2. With Docker
- After cloning the project, navigate to the root folder where yu see pom.xml, README.md and other folders and files
and open `docker-compose.yml` file by using command `nano docker-compose.yml` or open it in your IDE/Code editor.

- Update the sections under environment, to be able to connect to PostreSQL database, replace `profiles` with your database name, `ybyiring` as your database user name and `Ngfin2024!` with your database user password.
```bash
  - SPRING_DATASOURCE_URL=jdbc:postgresql://postgresql:5432/profiles
  - SPRING_DATASOURCE_USERNAME=ybyiring
  - SPRING_DATASOURCE_PASSWORD=Ngfin2024!
 ```
```
  - POSTGRES_PASSWORD=Ngfin2024!
  - POSTGRES_USER=ybyiring
  - POSTGRES_DB=profiles
```
- After that, open this file `src/main/resources/application.properties` and  uncomment line `#spring.datasource.url=jdbc:postgresql://postgresql:5432/profiles` to be able to connect your database in container
 and comment this line `spring.datasource.url=jdbc:postgresql://localhost:5432/profiles`.

- Because the postgreSQl database in container is running on the default port of port which is `5432`, you have to stop it first first by open
terminal and run the command `sudo systemctl stop postgresql`, and later on after done with testing the project, don't forget to start the postreSQl service by running `sudo systemctl start postgresql
`.

- After this, you can build and start the docker container by following the command below, and the application will be available at `http://localhost:8080`:
- `docker-compose up --build`



### API Endpoints Available

1. Authenticate: http://localhost:8080/api/auth/login
   - METHOD: POST
   - Body (username, password),

2. Register: http://localhost:8080/api/auth/register
    - METHOD: POST
    - Body (name, username, email, dob, password)
   
3. Get Profiles: http://localhost:8080/api/user-profiles
    - METHOD: GET
    - Require token, Auth Type is: Bearer Token,

4. Get single profile: http://localhost:8080/api/user-profile/1103
    - METHOD: GET
    - Require token, Auth Type is: Bearer Token,
    - 1100, is the userProfileId
   
5. Update Profile: http://localhost:8080/api/update-profile/1103
    - METHOD: PUT
    - Require token, Auth Type is: Bearer Token
    - 1100, is the userProfileId
    - Body (name, username, email, status), it is nor mandatory to provide all

6. Update Profile Status: http://localhost:8080/api/update-profile-status/1103
    - METHOD: PUT
    - Require token, Auth Type is: Bearer Token 
    - 1100, is the userProfileId
    - Body (status), it must be ACTIVE, INACTIVE and SUSPENDED

7. Delete Profile: http://localhost:8080/api/delete-profile/32
    - METHOD: DELETE
    - Require token, Auth Type is: Bearer Token
    - 32, is the userProfileId

8. Change Password: http://localhost:8080/api/change-password/452
    - METHOD: PUT
    - Require token, Auth Type is: Bearer Token
    - 752, is the userProfileId
    - Body (old_password, new_password, confirm_password)
