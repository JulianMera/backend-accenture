package com.demo.backend_accenture.controller;

import com.demo.backend_accenture.entity.Branch;
import com.demo.backend_accenture.entity.Franchise;
import com.demo.backend_accenture.exception.GlobalExceptionHandler;
import com.demo.backend_accenture.service.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BranchService branchService;

    @BeforeEach
    void setUp() {
        BranchController controller = new BranchController(branchService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void addBranch_shouldReturn200AndBody() throws Exception {
        Long franchiseId = 1L;
        String branchName = "Sucursal Centro";
        Franchise franchise = Franchise.builder().id(franchiseId).name("Franquicia").build();
        Branch branch = Branch.builder().id(1L).name(branchName).franchise(franchise).build();
        when(branchService.addBranchToFranchise(anyLong(), any(String.class))).thenReturn(branch);

        mockMvc.perform(post("/branches/franchise/{franchiseId}", franchiseId)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(branchName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(branchName));
    }

    @Test
    void updateBranchName_shouldReturn200AndBody() throws Exception {
        Long branchId = 1L;
        String body = "{\"name\":\"Sucursal Actualizada\"}";
        Branch updated = Branch.builder().id(branchId).name("Sucursal Actualizada").build();
        when(branchService.updateBranchName(anyLong(), any(String.class))).thenReturn(updated);

        mockMvc.perform(put("/branches/{branchId}", branchId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sucursal Actualizada"));
    }
}
