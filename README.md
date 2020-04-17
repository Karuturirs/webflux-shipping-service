# 		New Shipping Analyser (NSA)

-------------------------------------

NSA is an Reactive Rest API micro service that helps business to find out if the seller items is available for new shipping or not. Build with Spring Webflux + Reactive MongoDB as the data store having all the rules and data in it for new shipping process. 

### Features
-------------------------------------
* Dynamical rules update/modification on runtime with no down time.
* Min code change for new rules depending on the use cases.
* Easy Setup in few sec application.
* Reactive application
* Docker enabled.

#### TODO

* Unit test cases were not implemented due to time constraints.

* Swagger-UI  to have better access to the apis.

  

### Pre-Requirements.
-------------------------------------
    Docker should be installed in laptop.
    maven 3.2+ up.
    java 8+

### Start Application
-------------------------------------
Step 1: Download the code base

Step 2: Got to the project root folder.

```cmd
$ cd webflux-shipping-analyser
```

Step 3: Run mvn installation. 

```cmd
$ mvn clean install
```

Step 4: Run docker compose command

```cmd
$ docker-compose up
```

Step 5: Run
        ```
        curl http://localhost:8080/actuator/health
        ```

#### Business Rules:
-------------------------------------------
The main criteria(Validation Rules) for NSA is dependent on few factors.

* Seller Enrollment for new shipping process (Runtime enrollment).
* Categories that are enrolled for that  new shipping ( Enabled and disabled at run time).
* Price of the item greater or equal to given price( Configurable runtime ).

The above three rule are added by default to the DB, Can be updated or new rules can be added  on runtime using Rest API's.



### API Documentation
-------------------------------------

**Available API can be found here**
        ```http://localhost:8080/actuator/mappings ```

**Important API's: **

By default when you start the application there won't be any data stored.

#### Enroll or Disenroll sellers for new shipping program:

1. Enroll a seller to the new Shipping program.

   ```curl
   curl --location --request POST 'http://localhost:8080/api/v1/shipping/enroll/seller' \
                  --header 'X-Admin-Authentication: U2hpcHBpbmcyMDIwVG9rZW4=' \
                  --header 'sellers: ravik' --data-raw ''
   ```

   where headers **sellers** is username of the seller, **X-Admin-Authentication** is a property token configuration for authenticating api.

2. Dis-enroll a seller from the new shipping program.

   ```curl
   curl --location --request DELETE 'http://localhost:8080/api/v1/shipping/enroll/seller/{ravik}'
   ```

   **{ravik}** is the username of the seller.

3. Get all sellers enrolled.

   ```curl
   curl --location --request GET 'http://localhost:8080/api/v1/shipping/enroll/sellers' 
   ```

####  Enable or disable a category for new shipping program: 

1. Add/Update/disable a category for new shipping program.

   ```curl
   curl --location --request POST 'http://localhost:8080/api/v1/shipping/category' \
   --header 'X-Admin-Authentication: U2hpcHBpbmcyMDIwVG9rZW4=' \
   --header 'Content-Type: application/json' \
   --data-raw '{
    "categoryId":1,
    "categoryName":"Women",
    "enabled":true
   }'
   ```

2.  Get all categories for new shipping program.

   ```curl
   curl --location --request GET 'http://localhost:8080/api/v1/shipping/category?enabled=ALL'
   ```

   Query param **enabled** can have **ALL**(both enabled and disabled), **true**(only enabled categories), **false**(diabled categories). 


#### Availablity for new shipping:

1. Item available for New Shipping

    ```curl 
   curl --location --request POST 'http://localhost:8080/api/v1/shipping/validator' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   	"title":"Car decor",
   	"username": "ravik",
   	"categoryId": "1",
   	"price": 100.00
   }'
   ```

Should get value true for all the api done for the above.

#### Add rule or diable rules for new shipping program:

By defalut 3 rule are added in the DB. 

1. Get all rules added in the application.

   ```curl
   curl --location --request GET 'http://localhost:8080/api/v1/shipping/rules'
   ```

2. Add/Update rule to the application.

**Disabling rule for Price:**

```curl
curl --location --request POST 'http://localhost:8080/api/v1/shipping/rules' \
--header 'X-Admin-Authentication: U2hpcHBpbmcyMDIwVG9rZW4=' \
--header 'Content-Type: application/json' \
--data-raw '{
    "type": ">=",
    "variable": "price",
    "valueType": "double",
    "value": "50.00",
    "enabled": false,
    "description": "Is enabled on price variable whose value should be greated than or equals to value(ex: 50.00)"
}
'
```



**Adding  rule for title:**

```curl
curl --location --request POST 'http://localhost:8080/api/v1/shipping/rules' \
--header 'X-Admin-Authentication: U2hpcHBpbmcyMDIwVG9rZW4=' \
--header 'Content-Type: application/json' \
--data-raw '{
    "type": "==",
    "variable": "title",
    "valueType": "String",
    "value": "toilet papers",
    "enabled": true,
    "description": "Is enabled on title variable whose value should be equals to 'toilet papers'"
}'
```

The rules model is strictly validated by DB.

| variables   | Description                                                  | Possible values                                              |
| ----------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| type        | used to due dynamic validation                               | "==", ">=", "<=",  "!=", "<", ">"                            |
| variable    | the variable for the SellerItem.java class                   | title;   username;  categoryId;   price;                     |
| valueType   | java types for the above variable                            | "int", "char", "boolean", "String", "float", "double"        |
| **value**   | Its should be correct value of string format to compare above variable(ex: price). for categories and seller enrollment we defined rules with token(**rule.categories**) to validate in the code. | value of the  item or **rule.categories** for category or **rule.enrollment** for seller enrollment. |
| enabled     | if this rule should be enabled or disabled                   | true, false                                                  |
| description | More details on the above rule                               |                                                              |




###             For Development

-------------------------------------

##### Pre-Requirements:

  * Java 8+
  * maven 3.6.0 or latest

##### Dev cmd:

  Build:
    ``` mvn clean install ```
  Image deletion:
    ``` docker rmi new-shipping-analyser_app ```

  Start the application:
    ``` docker-compose up```

  That's all. Enjoy.

#### DB model:

MongoDB we create a **shipping** DB and **enrollment, categories, validationrules** collection

> use shipping

> db.enrollment.createIndex({ username: 1},{unique:true});
> db.categories.createIndex({categoryId:1, categoryName: 1},{unique:true});

> db.enrollment.insert({'username':'john'});
> db.enrollment.insert({'username':'ravi'});
> db.categories.insert({'categoryId':1,'categoryName':'womens','enabled':true});
> db.categories.insert({'categoryId':2,'categoryName':'mens','enabled':false});
> db.categories.insert({'categoryId':3,'categoryName':'home','enabled':true});

>db.createCollection("validationrules", {
   validator: {
      $jsonSchema: {
         bsonType: "object",
         required: [ "type", "variable", "valueType", "value", "enabled" ],
         properties: {
            type: {
               enum: ["==", ">=", "<=",  "!=", "<", ">"],
               description: "can only be one of the enum values and is required"
            },
            variable: {
               bsonType: "string",
               description: "must be an string, which variable to be rules set and is required"
            },
            valueType: {
               enum: [ "int", "char", "boolean", "String", "float", "double" ],
               description: "can only be one of the enum values and is required"
            },
            value: {
               bsonType: "string",
               description: "must be a double and is required"
            },
            enabled: {
               bsonType: "bool",
               description: "must be a boolean and is required"
            }
            description: {
               bsonType: "string",
               description: "Description about the rule."
            }
         }
      }
   }
});



> db.validationrules.insert({'type':'>=', 'variable':'price', 'valueType':'double', 'value': 50.00 , 'enabled':true , 'description':'Is enabled on price variable whose value should be greated than or equals to value(ex: 50.00)'})
> db.validationrules.insert({'type':'==', 'variable':'categoryId', 'valueType':'int', 'value': 'rule.categories'  , 'enabled':true , 'description':'Is enabled on catgeoryId whose value should be list of categories'})
> db.validationrules.insert({'type':'==', 'variable':'username', 'valueType':'int', 'value': 'rule.enrollment'  , 'enabled':true , 'description':'Is enabled on enrolled seller usernames whose value should be list of enrollment'})
> db.validationrules.update( { variable : "categoryId" },{ $set : { value : "rule.categories" } } )
