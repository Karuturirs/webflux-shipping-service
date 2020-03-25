db.auth("mockbird", "Ilovethisbird");
db.createUser({
    user: "adam",
    pwd: "adamjohn",
    roles: [ { role: "readWrite", db: "shipping" },
             { role: "read", db: "reporting" } ]
  }
);
db = db.getSiblingDB('shipping');
db.enrollment.createIndex( { "expireAt": 1 }, { expireAfterSeconds: 0 } );
db.categories.createIndex( { "expireAt": 1 }, { expireAfterSeconds: 0 } );
db.createCollection("validationrules", {
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
            },
            description: {
               bsonType: "string",
               description: "Description about the rule."
            }
         }
      }
   }
});
db.validationrules.insert({'type':'>=', 'variable':'price', 'valueType':'double', 'value': '50.00' , 'enabled':true , 'description':'Is enabled on price variable whose value should be greated than or equals to value(ex: 50.00)'});
db.validationrules.insert({'type':'==', 'variable':'categoryId', 'valueType':'int', 'value': 'rule.categories'  , 'enabled':true , 'description':'Is enabled on catgeoryId whose value should be list of categories'});
db.validationrules.insert({'type':'==', 'variable':'username', 'valueType':'int', 'value': 'rule.enrollment'  , 'enabled':true , 'description':'Is enabled on enrolled seller usernames whose value should be list of enrollment'});
