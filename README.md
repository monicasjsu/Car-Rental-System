
## **Team : Gang Of Four**

### About the application:
GoFCar is a Role based Car Rental Web Application running on EC2 instance.

### Feature Set: 
The various modules that comprise our application are:
*	Admin
*	User
*	Vehicle
*	Rental Location
*	Reservation
*	Address 

### **Group Members:**

- Geethu Padachery
- Shree Gowri Radhakrishna
- Monica Dommaraju
- Sri sruthi Chilukuri

### XP Core Values Implemented:
* Geethu Padachery - Communication
* Shree Gowri Radhakrishna - Feedback
* Monica Dommaraju - Simplicity
* Sri sruthi Chilukuri - Respect

### [Weekly Scrum Task Board](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/projects/1)
The Project board on Github has been maintained to keep a track of all the tasks happening and also to maintain transparency.
The tasks have been segregated based on their current status as: “To do”, “In progress” and  “ Done”.


### [Project Journal](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/ProjectJournal(GangOfFour).docx)
The weekly version of daily scrum has been documented by all the team members addressing questions like 
What tasks did I work on / complete?,
What am I planning to work on next? 
And What tasks are blocked waiting on another team member?
#### Note: The same report includes the top XP Core Values chosen by each team members along with their applicability.


### [Sprint Task Sheet](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/documentation/Gang%20Of%20Four-%20Sprint%20Task%20Sheet-BurnDown%20(1).xlsx)
Maintains a track of remaining hours of all the tasks that have been taken up either individually or as a group while building the application.
#### Note: The burndown chart generated has been added in sheet 2 of same spreadsheet.


### [UI Wireframes](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/Wireframes/Wireframes.pptx)
Wireframes have been generated using “Pencil” Tool and all the screens have been consolidated to a PPT.


### [CRC Cards](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/documentation/CRC%20Cards%20for%20GangOfFour%20Application.docx)
The Class-Responsibility Collaboration Cards for all the probable classes during the design phase of the application have been added.

### [Architectural Diagram and Software Components](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/Software%20Components%20and%20Architectural%20Diagram.docx)
Please find below the various components of our application and the different architectural diagram:

### Application Development Stack:	
![Application Development Stack](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/Development%20Stack.png)


### AWS Architecture Diagram
![AWS Architecture Diagram](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/AWS%20Architecture%20Diagram.png)


### [Deployment Diagram](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/AWS%20Deployment.docx)
The Web application components for our application GoFCar are deployed into AWS in an Auto Scaled EC2 Cluster with Load Balancer
![Deployment Diagram](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/GoFCar%20Deployment%20Diagram.jpg)


### Component Diagram
![Component Diagram](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/GoF%20Component%20Diagram.jpg)


### Class Diagram
![Class Diagram](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/classdiagram_RentSystem.png)

### Use Case Diagram 
#### Customer Use Case Diagram
![User Use Case Diagram](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/customer%20Use%20Case%20Diagram.jpg)
#### Admin Use Case Diagram
![Admin Use Case Diagram](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/Admin%20Use%20Case%20Diagram.jpg)

### Design decisions for GangOfFour Application

Our Application makes use of the **Model View Controller (MVC) Design pattern**. The basic idea behind this design is that any application contains separate modules for data, control and presentation of any information.

For our GangOfFour application:
The **Model component** consists of all the information that is bypassing through the application in numerous ways i.e
*	Information of the User that is stored in the database for retrieval, modification or deletion.
*	Information of the Admin that is stored in the database for retrieval, modification or deletion.
*	Information of all vehicles and reservations stored onto a database for retrieval, modification or deletion.

Then comes the **View component**:
*	It represents the User Interface of our application that is developed using the React JavaScript library. This component helps the end user manage all of the model’s data stored in the database by interacting with the controller; instead of directly accessing it.

Finally, there is **Controller component**.
*	Comprises of all the backend services developed using the SpringBoot Java based framework that helps the View component fetch the information from Model.

#### MVC Design Pattern for GoF Application:
![MVC Design Pattern](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/documentation/MVC%20Architecture.png)


#### Data Access Object Pattern:

The **DAO pattern** creates a barrier and separates the high level business operations from the low level data.

For the GoF application, the backend services and their operations that stand as the logical layer have been isolated from the rest of the application to maintain encapsulation and abstraction.

DAO separates the persistence logic(Backend services using SpringBoot framework) in a separate layer called Data access layer which enables applications to react safely to change in Persistence mechanism.


### Testing our Application:

We used JUnit Testing and Mockito and Spring Runner to test our application from end to end.

### [GoFCar Application Screenshots](https://github.com/gopinathsjsu/sp20-cmpe-202-sec-49-team-project-gang-of-four/blob/master/Screenshots%20of%20application.pptx)
We have attached our application screenshots in the above link
