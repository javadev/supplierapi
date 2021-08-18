# SupplierAPI

###### APIs project to access RoomDB.

For API calls should be used **https** only. <br/>
https://api.supplier.roomdb.io

Calls to http will be returned with Forbidden message by Nginx.

## Servers information

**"RoomDB-Database"** server: <br/>
IPv4	162.55.164.107 <br/>
IPv6	2a01:4f8:c010:76fc::/64

**"RoomDB-SupplierAPI"** server: <br/>
IPv4	116.202.15.234 <br/>
IPv6	2a01:4f8:c010:69cb::/64

**DNS records**: <br/>
db.roomdb.io A 162.55.164.107 <br/>
api.supplier.roomdb.io A 116.202.15.234

## Documentation

Swagger is available here: <br/>
https://doc.supplier.roomdb.io/swagger

ReDoc here: <br/>
https://doc.supplier.roomdb.io/redoc

## Security
JWT tokens are used. <br/>
To generate token should be used `"/api/v1/suppliers/get-token"` with existing supplierId and supplierSecret (our analog of username and password).
Then this token need to be added to each call for example:
~~~~
curl -X 'GET' \
  'http://localhost:8683/api/v1/states' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer <YOUR_TOKEN>'
~~~~
<br/>
On a Swagger UI you need to add token in "Authorize" popup. This will automatically add token to each calls.

## Update security token strategy

There are two possible ways to update token.
1. Check expiration time of the current token. If it's expired then recall `get-token` API using supplierId and supplierSecret. If it's still valid but will expire in less than a minute (some small amount of time) `refresh-token` with token.
2. Implement logic that will extract expiration period, save this period. Use expiration period (a bit less than it is) for scheduled call of the `refresh-token` API.         

## Encode password
There is a helper endpoint to encode password `"/api/v1/suppliers/encode-password"`. <br/>
It's hidden from Swagger. <br/> 
On local environment you can temporary comment `@Hidden` annotation to make it visible. <br/>
Or use curl. For example:  
~~~~
curl -X 'GET' \
  'http://localhost:8683/api/v1/suppliers/encode-password?password=someGoodPassword' \
  -H 'accept: application/json'
~~~~


## Additional info:

[Data Base Information](README-DB.md)

[ReDoc documentation. Install and run.](README-redoc.md)
