package cd.codevs.ManagementService.service;

import cd.codevs.ManagementService.entity.Tenant;
import cd.codevs.ManagementService.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    // Registrar un nuevo tenant con generación de API Key automática
    public Tenant registerTenant(Tenant tenant) {
        tenant.setApiKey(UUID.randomUUID().toString());
        return tenantRepository.save(tenant);
    }

    // Buscar un tenant por su API Key
    public Optional<Tenant> findByApiKey(String apiKey) {
        return tenantRepository.findByApiKey(apiKey);
    }

    // Buscar un tenant por tenantId
    public Optional<Tenant> findByTenantId(String tenantId) {
        return tenantRepository.findByTenantId(tenantId);
    }

    // Verificar si existe un tenant con un tenantId específico
    public boolean tenantExists(String tenantId) {
        return tenantRepository.existsByTenantId(tenantId);
    }

    // Crear un tenant con validaciones y generación automática de tenantId y API Key
    public Tenant createTenant(Tenant tenant) {
        if (tenant.getTenantId() == null) {
            tenant.setTenantId(generateTenantId(tenant.getName()));
        }
        if (tenant.getApiKey() == null) {
            tenant.setApiKey(UUID.randomUUID().toString());
        }
        return tenantRepository.save(tenant);
    }

    // Actualizar un tenant existente
    @Transactional
    public Tenant updateTenant(Long id, Tenant updatedTenant) {
        Optional<Tenant> existingTenantOpt = tenantRepository.findById(id);
        if (existingTenantOpt.isPresent()) {
            Tenant existingTenant = existingTenantOpt.get();
            existingTenant.setName(updatedTenant.getName());
            existingTenant.setEmail(updatedTenant.getEmail());
            existingTenant.setPhone(updatedTenant.getPhone());
            existingTenant.setDbUrl(updatedTenant.getDbUrl());
            existingTenant.setDbUsername(updatedTenant.getDbUsername());
            existingTenant.setDbPassword(updatedTenant.getDbPassword());
            return tenantRepository.save(existingTenant);
        } else {
            throw new RuntimeException("Tenant with ID " + id + " not found");
        }
    }

    // Eliminar un tenant por ID
    public void deleteTenant(Long id) {
        if (tenantRepository.existsById(id)) {
            tenantRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tenant with ID " + id + " not found");
        }
    }

    // Obtener un tenant por ID
    public Optional<Tenant> getTenantById(Long id) {
        return tenantRepository.findById(id);
    }

    // Listar todos los tenants
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    // Generar un tenantId único basado en el nombre del tenant
    private String generateTenantId(String name) {
        return name.toLowerCase().replaceAll("\\s+", "_") + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
