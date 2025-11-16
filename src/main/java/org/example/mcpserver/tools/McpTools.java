package org.example.mcpserver.tools;

import org.example.mcpserver.records.Product;
import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class McpTools {
    @McpTool(name = "Get Product", description = "Get Product Details")
    public Product getProduct(@McpArg String name) {
        return new Product(name, "Product description", 150.0, 20);
    }

    @McpTool(name = "Get All Products", description = "Get All Products  Details")
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", "Laptop description", 4000, 10));
        products.add(new Product("Phone", "Phone description", 2000, 20));
        products.add(new Product("Mouse", "Mouse description", 300, 40));

        return products;
    }
}
