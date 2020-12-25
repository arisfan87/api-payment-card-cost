package paymentcardcost.api.models;

public class BinlistDto {
    public Number number;
    public String scheme;
    public String type;
    public String brand;
    public boolean prepaid;
    public Country country;
    public Bank bank;
}

class Number{
    public int length;
    public boolean luhn;
}

class Bank {
    public String name;
    public String url;
    public String phone;
    public String city;
}
