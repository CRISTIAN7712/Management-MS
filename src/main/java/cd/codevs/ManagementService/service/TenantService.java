package cd.codevs.ManagementService.service;

import cd.codevs.ManagementService.entity.Tenant;
import cd.codevs.ManagementService.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public Tenant registerTenant(Tenant tenant) {
        tenant.setApiKey(UUID.randomUUID().toString()); // Generar un API Key único
        return tenantRepository.save(tenant);
    }

    public Optional<Tenant> findByApiKey(String apiKey) {
        return tenantRepository.findByApiKey(apiKey);
    }

    public boolean tenantExists(String tenantId) {
        return tenantRepository.existsByTenantId(tenantId);
    }

    public Tenant createTenant(Tenant tenant) {
        if (tenant.getTenantId() == null) {
            tenant.setTenantId(generateTenantId(tenant.getName())); // Generar tenantId único.
        }

        if (tenant.getApiKey() == null) {
            tenant.setApiKey(UUID.randomUUID().toString()); // Generar API Key único.
        }

        return tenantRepository.save(tenant);
    }

    private String generateTenantId(String name) {
        // Por ejemplo, generar un tenantId basado en el nombre del negocio.
        return name.toLowerCase().replaceAll("\\s+", "_") + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
