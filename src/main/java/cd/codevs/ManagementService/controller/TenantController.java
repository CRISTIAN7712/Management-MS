package cd.codevs.ManagementService.controller;

import cd.codevs.ManagementService.entity.Tenant;
import cd.codevs.ManagementService.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    /*@PostMapping
    public ResponseEntity<Tenant> registerTenant(@RequestBody Tenant tenant) {
        return ResponseEntity.ok(tenantService.registerTenant(tenant));
    }*/

    @GetMapping("/{tenantId}")
    public ResponseEntity<Boolean> tenantExists(@PathVariable String tenantId) {
        return ResponseEntity.ok(
                tenantService.findByApiKey(tenantId).isPresent()
        );
    }

    @GetMapping("/{tenantId}/exists")
    public ResponseEntity<Boolean> checkTenantExists(@PathVariable String tenantId) {
        boolean exists = tenantService.tenantExists(tenantId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        Tenant savedTenant = tenantService.createTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTenant);
    }
}

