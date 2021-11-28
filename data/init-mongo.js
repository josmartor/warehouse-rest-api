// Create user
db.createUser(
  {
    user: 'api_user',
    pwd: 'api_user_pwd',
    roles: [{ role: 'readWrite', db: 'warehouse' }],
  },
);

// Create collection and indexes
db.createCollection('ColTrackingDevicesConfigs');
db.ColTrackingDevicesConfigs.createIndex( { "simCard.simId": 1 }, { unique: true } )

// Insert test data
db.ColTrackingDevicesConfigs.insertMany([
    {"_id" : "id1", "temperature" : -30.0, "simCard" : {"simId" : "1", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "ACTIVE"}},
    {"_id" : "id2", "temperature" : -25.0, "simCard" : {"simId" : "2", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "ACTIVE"}},
    {"_id" : "id3", "temperature" : -10.0, "simCard" : {"simId" : "3", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "WAITING_FOR_ACTIVATION"}},
    {"_id" : "id4", "temperature" : 0.0, "simCard" : {"simId" : "4", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "DEACTIVATED"}},
    {"_id" : "id5", "temperature" : 15.0, "simCard" : {"simId" : "5", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "BLOCKED"}},
    {"_id" : "id6", "temperature" : 25.0, "simCard" : {"simId" : "6", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "ACTIVE"}},
    {"_id" : "id7", "temperature" : 30.0, "simCard" : {"simId" : "7", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "WAITING_FOR_ACTIVATION"}},
    {"_id" : "id8", "temperature" : 85.0, "simCard" : {"simId" : "8", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "DEACTIVATED"}},
    {"_id" : "id9", "temperature" : 99.0, "simCard" : {"simId" : "9", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "BLOCKED"}},
    {"_id" : "id10", "temperature" : 101.0, "simCard" : {"simId" : "10", "operatorCode" : "eier438-fs", "country" : "EN", "status" : "ACTIVE"}}
 ])