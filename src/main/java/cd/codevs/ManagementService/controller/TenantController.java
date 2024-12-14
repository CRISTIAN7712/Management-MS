package cd.codevs.ManagementService.controller;

import cd.codevs.ManagementService.entity.Tenant;
import cd.codevs.ManagementService.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    // Crear un tenant
    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        Tenant savedTenant = tenantService.createTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTenant);
    }

    // Verificar si un tenant existe por su tenantId
    @GetMapping("/{tenantId}/exists")
    public ResponseEntity<Boolean> checkTenantExists(@PathVariable String tenantId) {
        boolean exists = tenantService.tenantExists(tenantId);
        return ResponseEntity.ok(exists);
    }

    // Obtener un tenant por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Listar todos los tenants
    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenants() {
        List<Tenant> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    // Actualizar un tenant
    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody Tenant tenant) {
        try {
            Tenant updatedTenant = tenantService.updateTenant(id, tenant);
            return ResponseEntity.ok(updatedTenant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Eliminar un tenant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        try {
            tenantService.deleteTenant(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Tenant> getTenantByTenantId(@RequestParam String tenantId) {
        Optional<Tenant> tenant = tenantService.findByTenantId(tenantId);
        if (tenant.isPresent()) {
            return ResponseEntity.ok(tenant.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/searchByApiKey")
    public ResponseEntity<Tenant> getTenantByApiKey(@RequestParam String apiKey) {
        Optional<Tenant> tenant = tenantService.findByApiKey(apiKey);
        if (tenant.isPresent()) {
            return ResponseEntity.ok(tenant.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para validar API Key y Tenant ID
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateApiKey(
            @RequestParam String tenantId,
            @RequestHeader(value = "Authorization", required = false) String apiKey
    ) {
        if (apiKey == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        Optional<Tenant> tenant = tenantService.findByTenantId(tenantId);
        if (tenant.isPresent() && apiKey.equals(tenant.get().getApiKey())) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}

