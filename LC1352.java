import java.util.ArrayList;
import java.util.List;

class ProductOfNumbers {
    private List<Integer> product;
    public ProductOfNumbers() {
        product = new ArrayList<>();
        product.add(1);
    }
    
    public void add(int num) {
        if (num == 0) {
            product = new ArrayList<>();
            product.add(1);
        }
        else {
            product.add(product.get(product.size() - 1) * num);
        }

    }
    
    public int getProduct(int k) {
        int n = product.size();
        if (k < n) {
            return product.get(n - 1) / product.get(n - k - 1);
        }
        else {
            return 0;
        }
    }
}

/**
 * Your ProductOfNumbers object will be instantiated and called as such:
 * ProductOfNumbers obj = new ProductOfNumbers();
 * obj.add(num);
 * int param_2 = obj.getProduct(k);
 */