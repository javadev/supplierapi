# SupplierAPI

###### APIs project to access RoomDB.

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
On a Swagger token need to be added in "Authorize" popup. This will automatically add it to all calls.

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
