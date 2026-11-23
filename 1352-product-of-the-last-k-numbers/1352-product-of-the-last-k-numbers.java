class ProductOfNumbers {
    private java.util.List<Integer> prefixProducts;

    public ProductOfNumbers() {
        prefixProducts = new java.util.ArrayList<>();
        prefixProducts.add(1); // Base for division
    }
    
    public void add(int num) {
        if (num == 0) {
            // Any product including this 0 will be 0. Reset the list.
            prefixProducts = new java.util.ArrayList<>();
            prefixProducts.add(1);
        } else {
            prefixProducts.add(prefixProducts.get(prefixProducts.size() - 1) * num);
        }
    }
    
    public int getProduct(int k) {
        int size = prefixProducts.size();
        // If k >= size, the window extends past a 0, so the product is 0.
        if (k >= size) {
            return 0;
        }
        return prefixProducts.get(size - 1) / prefixProducts.get(size - 1 - k);
    }
}

