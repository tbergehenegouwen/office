package pos.orders;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import pos.products.Product;

/**
 *
 * @author ralph
 */
public class Basket extends Observable {

    private final Map<Product, Integer> products;

    public Basket() {
        super();
        products = new LinkedHashMap<>();
    }

    public void addProduct(Product product) {
        // check if product is allready added to the basket
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
        setChanged();
        notifyObservers();
    }

    public void empty() {
        products.clear();
        setChanged();
        notifyObservers();
    }

    public List<Product> getProducts() {
        List<Product> list = new LinkedList<>(products.keySet());
        return Collections.unmodifiableList(list);
    }

    public int getProductAmount(Product product) {
        return products.get(product);
    }

    public int size() {
        return products.size();
    }

    public int getTotalCosts() {
        int total = 0;
        for (Entry<Product, Integer> entry : products.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}