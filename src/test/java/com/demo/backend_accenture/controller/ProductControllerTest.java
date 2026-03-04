package com.demo.backend_accenture.controller;

import com.demo.backend_accenture.entity.Branch;
import com.demo.backend_accenture.entity.Product;
import com.demo.backend_accenture.exception.GlobalExceptionHandler;
import com.demo.backend_accenture.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        ProductController controller = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void addProduct_shouldReturn200AndBody() throws Exception {
        Long branchId = 1L;
        String body = "{\"name\":\"Producto 1\",\"stock\":10}";
        Branch branch = Branch.builder().id(branchId).name("Sucursal").build();
        Product product = Product.builder().id(1L).name("Producto 1").stock(10).branch(branch).build();
        when(productService.addProduct(eq(branchId), eq("Producto 1"), eq(10))).thenReturn(product);

        mockMvc.perform(post("/products/branch/{branchId}", branchId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Producto 1"))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void deleteProduct_shouldReturn200() throws Exception {
        Long productId = 1L;
        mockMvc.perform(delete("/products/{productId}", productId))
                .andExpect(status().isOk());
    }

    @Test
    void updateProductName_shouldReturn200AndBody() throws Exception {
        Long productId = 1L;
        String body = "{\"name\":\"Producto Renombrado\"}";
        Product updated = Product.builder().id(productId).name("Producto Renombrado").stock(5).build();
        when(productService.updateProductName(eq(productId), eq("Producto Renombrado"))).thenReturn(updated);

        mockMvc.perform(put("/products/{productId}/name", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Producto Renombrado"));
    }
}
