db = db.getSiblingDB("outfit-service");
db.createUser({
    user: "outfits-user",
    pwd: "outfits-user",
    roles: [{ role: "readWrite", db: "outfit-service" }],
});