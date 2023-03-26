<h1 align="center" id="title"> Car workshop REST API 🍃</h1>

> Rest api for a car workshop management system. Made with Spring Boot. 

<br>

<div align="center">
<img src="https://i.imgur.com/mXIGDn7.png" align="center" height="550" width="1000" />
  
<p id="description"> Entity-relationship diagram.</p>
 
</div>  


## ✨Features


*   Register new `clients`, `vehicles`, `employees` and `work orders`
*   Link vehicles to clients
*   Search for clients by email and vehicles by plate number
*   Update work orders's `details` and `current status`



## 🖥️ Made with


* Spring Boot
* JPA
* Hibernate
* JUnit
* H2 Database
* Lombok

## 📮 Postman Api Collection 
[Api Collection](https://www.mediafire.com/file/7i4i44on527ps1k/TallerMecanico.postman_collection.json/file)

## 📚 API Reference

API                       | Description         | Parameters
--------------------------|---------------------|--------------
GET /cliente/{email}        | Get Client by Email    | `email`: Email associated with the Client to fetch
GET /vehiculo/{patente}    | Get Vehicle by Plate number | `patente`: Plate number of the Vehicle to fetch

<br/>

API                       | Description         | Parameters
--------------------------|---------------------|--------------
POST /cliente/       | Register a new Client to the Database    | **Note: adittionally, you can register a vehicle to this client with the same Request. if you do, the vehicle gets saved to the database and it gets linked to this client.**
POST /cliente/{email}/vehiculonuevo   | Register a new Vehicle to the Database and link it to the specified client | `email`: Email associated with the Client to link this vehicle to.
POST /mecanico/    | Register a new Mechanic to the Database | 
POST /empleado/   | Register a new Employee to the Database | 
POST /trabajo/{patente}/{id}   | Register a new Employee to the Database | •`patente`: Plate number of the Vehicle to link to this work order <br/> •`id`: Id of the employee of type 'Recepcionista' to link to this work order
POST /trabajo/?mecanico_id={id_mec}&trabajo_id={id_ot}   | Register a new Labor | `id`: Id of a Mechanic to assign to this Labor Id of the Work order to assign this Labor to

<br/>

API                       | Description         | Parameters
--------------------------|---------------------|--------------
PUT /manodeobra/{id}       | Complete Labor    |  `id`: id of the Labor to update
POST /trabajo/{id}/enreparacion   | Change Work order's status to 'Repairing' | `id`: id of the Work order to update
POST /trabajo/{id_lab}/parafacturar/{quant}   | Change Work order's status to 'Ready for Payment' | •`id`: id of the Work order to update <br/>•`quant`: quantity of replacement parts
POST /trabajo/{id}/facturado   | Change Work order's status to 'Paid' | `id`: id of the Work order to update
POST /trabajo/{id}/cerrar/?admin_id={id_adm}&forma_pago={ptype}&tarjeta={card}&cuotas={paym}  | Change Work order's status to 'Closed' | •`id`: id of the Work order to update <br/>•`id`: id of the employee of type 'Administrativo' to link to this Work order <br/>•`ptype`: payment type <br/>•`card`: card type <br/>•`paym`: Amount of payment installments


## 💬 Feedback

If you have any feedback, please reach out to me at alanbrandan17@hotmail.com
