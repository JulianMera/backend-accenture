package com.demo.backend_accenture.controller;

import com.demo.backend_accenture.dto.FranchiseResponseDTO;
import com.demo.backend_accenture.dto.TopProductResponseDTO;
import com.demo.backend_accenture.exception.GlobalExceptionHandler;
import com.demo.backend_accenture.service.FranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FranchiseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FranchiseService franchiseService;

    @BeforeEach
    void setUp() {
        FranchiseController controller = new FranchiseController(franchiseService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createFranchise_shouldReturn200AndBody() throws Exception {
        String body = "{\"name\":\"Franquicia Test\"}";
        FranchiseResponseDTO response = FranchiseResponseDTO.builder().id(1L).name("Franquicia Test").build();
        when(franchiseService.createFranchise(any())).thenReturn(response);

        mockMvc.perform(post("/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Franquicia Test"));
    }

    @Test
    void getTopProductsByFranchiseId_shouldReturn200AndList() throws Exception {
        Long franchiseId = 1L;
        List<TopProductResponseDTO> list = List.of(
                TopProductResponseDTO.builder().productName("P1").branchName("S1").stock(10).build()
        );
        when(franchiseService.getTopProductsByFranchiseId(franchiseId)).thenReturn(list);

        mockMvc.perform(get("/franchises/{franchiseId}/top-products", franchiseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productName").value("P1"))
                .andExpect(jsonPath("$[0].stock").value(10));
    }

    @Test
    void updateFranchiseName_shouldReturn200AndBody() throws Exception {
        Long franchiseId = 1L;
        String body = "{\"name\":\"Nuevo Nombre\"}";
        FranchiseResponseDTO response = FranchiseResponseDTO.builder().id(franchiseId).name("Nuevo Nombre").build();
        when(franchiseService.updateFranchiseName(eq(franchiseId), eq("Nuevo Nombre"))).thenReturn(response);

        mockMvc.perform(put("/franchises/{franchiseId}", franchiseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nuevo Nombre"));
    }
}
