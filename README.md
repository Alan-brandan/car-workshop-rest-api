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
* H2 Database
* Lombok


## 📚 API Reference

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

---

#### • Register a new Client to the Database

```
  POST /cliente/
```
Note: adittionally, you can register a vehicle to this client with the same Request. if you do, the vehicle gets saved to the database and it gets linked to this client.

#### • Register a new Vehicle to the Database and link it to the specified client

```
  POST /cliente/{email}/vehiculonuevo
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email`      | `string` |  Email associated with the Client to link this vehicle to |

#### • Register a new Mechanic to the Database

```
  POST /mecanico/
```
#### • Register a new Employee to the Database

```
  POST /empleado/
```
#### • Register a new Work order

```
  POST /trabajo/{patente}/{id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `patente`      | `string` | Plate number of the Vehicle to link to this work order |
| `id`      | `Long` |  Id of the employee of type `Recepcionista` to link to this work order |

#### • Register a new Labor

```
  POST /trabajo/?mecanico_id={id_mec}&trabajo_id={id_ot}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id_mec`      | `Long` | Id of a Mechanic to assign to this Labor  |
| `id_ot`      | `Long` |  Id of the Work order to assign this Labor to |

---

#### • Complete Labor

```
  PUT /manodeobra/{id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Id of the Labor to update |

#### • Change Work order's status to 'Repairing'

```
  POST /trabajo/{id}/enreparacion
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Id of the Work order to update |

#### • Change Work order's status to 'Ready for Payment'.

```
  POST /trabajo/{id_lab}/parafacturar/{quant}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id_lab`      | `Long` | Id of the Work order to update |
| `quant`      | `int` | quantity of replacement parts |

#### • Change Work order's status to 'Paid'

```
  POST /trabajo/{id}/facturado
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Id of the Work order to update |

#### • Change Work order's status to 'Closed'

```
  POST /trabajo/{id}/cerrar/?admin_id={id_adm}&forma_pago={ptype}&tarjeta={card}&cuotas={paym}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | Id of the Work order to update |
| `id_adm`      | `Long` | Id of the employee of type `Administrativo` to link to this Work order |
| `ptype`      | `string` | payment type |
| `card`      | `string` | card type |
| `paym`      | `int` | Amount of payment installments |


## 💬 Feedback

If you have any feedback, please reach out to me at alanbrandan17@hotmail.com
