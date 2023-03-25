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



## 🌱made with


* Spring Boot
* JPA
* Hibernate
* H2 Database
* Lombok

## API Reference

#### • Get Client by Email

```
  GET /cliente/{email}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` |  Email associated with the Client to fetch |

#### • Get Vehicle by Plate number

```
  GET /vehiculo/{patente}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `patente`      | `string` | Plate number of the Vehicle to fetch |

## Feedback

If you have any feedback, please reach out to me at alanbrandan17@hotmail.com
