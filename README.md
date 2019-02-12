# Software II Project - WGU C195
JavaFX application intended to be used as a customer management system for a fictitious company. Communicates with a pre-existing mySQL database to manage and store customer and appointment records, as well as user information.

## Main Functionalities
- Supports adding, modifying, and removing both customers and appointments.
- Adjusts dates and times based upon the system's locale.
- Generates different reports inside the application.
- Notifications to alert the user of upcoming appointments.
- Login page is localized in English and German.

## Login
- Users must login using valid credentials to access the application and its functions.
- Users can only be managed outside of the application.
- Current user: User: "test", Password: "test".
- Error messages generated if login information is incorrect.
- Both successful and un-unsuccessful login attempts are recorded in the logging folder.

## Appointments
* Upon loading the page, if there are any appointments scheduled within the next 15 minutes, a notfication alerting the user of the upcoming appointment will be generated.
* Appointments can be viewed monthly or weekly in the appointment table. Directional buttons allow the user to move either forward or backwards in time.
* Appointments can be added in the Add Appointment view accessed by the "Add" button.
  - Appointment information is validated upon submission.
  - Appointment will be checked to ensure no overlap with another appointment and that it falls within local business hours. (9 AM - 5 PM).
* Appointments can be updated or deleted in the Modify Appointment view accessed by the "Update/Delete" button. User must first select an appointment in the table to modify.
  - Appointment information is populated based on the user's selection.
  - Appointments can be modified and updated, pending information passes validation.
  - Appointments can be deleted from the database, pending a confirmation window.
  
## Customers
* Customer records can be viewed in the customers table.
* Customers can be added by the Add Customer view accessed by the "Add" button.
  - Customer information is validated upon submission.
* Customers can be updated or deleted in the Modify Appointment view accessed by the "Update/Delete" button. User must first select a customer in the table to modify.
  - Customer information is populated based on the user's selection.
  - Customers can be modified and updated, pending information passes validation.
  - Customers can be deleted from the database, pending a confirmation window.
  
## Reports
* Reports can be generated from this view and viewed by selecting the respective tab along the left side of the application.
* Appointment Type By Month
  - Generates a table containing the number of monthly occurances of each type of appointment.
* Schedule by Customer
  - Generates a table listing the appointments that are scheduled with the customer selected by the user in the drop down box.
* Schedule by Consultant
  - Generates a table listing the appointments that are scheduled with the consultant selected by the user in the drop down box.
