public class Main {
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions("band_db", "postgres", "admin420");
//        db.createTable("band_info");
//        db.insertRow("band_info", "Slayer", "Metal Blade Records", "trash metal", 1981);
//        db.insertRow("band_info", "Burzum", "Deathlike Silence Productions", "black metal", 1991);
//        db.insertRow("band_info", "Bob Marley and the Wailers", "Studio One", "reggae", 1963);
//        db.insertRow("band_info", "Fall of Efrafa", "Fight for Your Mind", "crust punk", 2005);
//        db.insertRow("band_info", "the ride of your life.", "DTH Studios", "surf punk", 2019);
        db.readData("band_info");
    }
}