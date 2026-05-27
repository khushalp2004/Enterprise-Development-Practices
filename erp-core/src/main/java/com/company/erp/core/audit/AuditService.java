package com.company.erp.core.audit;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditService.class);
    
    public void logAudit(String action, String details, Long entityId) {
        log.info("AUDIT: Action={}, Details={}, EntityId={}", action, details, entityId);
    }
}
