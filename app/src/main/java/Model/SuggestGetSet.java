package Model;

public class SuggestGetSet {

    String id, name, price, storeName;


    public SuggestGetSet(String id, String name, String price, String storeName) {
        this.setId(id);
        this.setName(name);
        this.price = price;
        this.storeName = storeName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}