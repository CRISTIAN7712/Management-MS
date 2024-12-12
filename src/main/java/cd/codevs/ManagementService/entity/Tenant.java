package cd.codevs.ManagementService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @Column(nullable = false, unique = true)
    private String tenantId; // Tenant ID que debe generarse automáticamente.

    private String apiKey;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    // Getters y Setters
}

