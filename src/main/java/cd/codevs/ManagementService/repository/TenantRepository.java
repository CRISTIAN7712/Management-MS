package cd.codevs.ManagementService.repository;

import cd.codevs.ManagementService.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByTenantId(String tenantId);
    Optional<Tenant> findByApiKey(String apiKey);
    boolean existsByTenantId(String tenantId);

}
