package Model;

public class Product {

    String sale_id;
    String sale_item_id;
    String product_id;
    String product_name;
    String qty;
    String unit;
    String unit_value;
    String price;
    String qty_in_kg;
    String rewards;
    String item_status;

    public Product() {
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public String getSale_item_id() {
        return sale_item_id;
    }

    public void setSale_item_id(String sale_item_id) {
        this.sale_item_id = sale_item_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(String unit_value) {
        this.unit_value = unit_value;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty_in_kg() {
        return qty_in_kg;
    }

    public void setQty_in_kg(String qty_in_kg) {
        this.qty_in_kg = qty_in_kg;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public String getItem_status() {
        return item_status;
    }

    public void setItem_status(String item_status) {
        this.item_status = item_status;
    }
}
