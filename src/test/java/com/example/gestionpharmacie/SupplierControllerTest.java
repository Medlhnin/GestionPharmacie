package com.example.gestionpharmacie;

import com.example.gestionpharmacie.Suppliers.Supplier;
import com.example.gestionpharmacie.Suppliers.SupplierController;
import com.example.gestionpharmacie.Suppliers.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierControllerTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierController supplierController;

    private Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier(1L, "Pharma Corp", "123 Street", "contact@pharma.com", "0123456789", "www.pharma.com");
    }

    @Test
    void testAddSupplier() {
        Supplier newSupplier = new Supplier(null, "Pharma Corp", "123 Street", "contact@pharma.com", "0123456789", "www.pharma.com");
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        UriComponentsBuilder ucb = UriComponentsBuilder.newInstance();

        ResponseEntity<Void> response = supplierController.addSupplier(newSupplier, ucb);

        assertEquals(201, response.getStatusCodeValue());
        URI location = response.getHeaders().getLocation();
        assertNotNull(location);
        assertTrue(location.toString().contains("/api/v1/suppliers/1"));
    }

    @Test
    void testGetAllSuppliers() {
        List<Supplier> suppliers = Arrays.asList(supplier);
        when(supplierRepository.findAll()).thenReturn(suppliers);

        ResponseEntity<List<Supplier>> response = supplierController.getAllSuppliers();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testPutSupplier_Success() {
        Supplier updatedSupplier = new Supplier(1L, "Updated Corp", "New Address", "updated@corp.com", "0987654321", "www.updated.com");
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(updatedSupplier);

        ResponseEntity<Void> response = supplierController.putSupplier(1L, updatedSupplier);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testPutSupplier_NotFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = supplierController.putSupplier(1L, supplier);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteProduct_Success() {
        when(supplierRepository.existsById(1L)).thenReturn(true);
        doNothing().when(supplierRepository).deleteById(1L);

        ResponseEntity<Void> response = supplierController.deleteProduct(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(supplierRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = supplierController.deleteProduct(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
